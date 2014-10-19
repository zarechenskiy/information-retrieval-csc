package ru.csc.ir.search;

import org.jetbrains.annotations.NotNull;
import ru.csc.ir.index.Index;
import ru.csc.ir.structure.Document;
import ru.csc.ir.structure.DocumentCollection;
import ru.csc.ir.structure.Operator;
import ru.csc.ir.structure.Term;
import ru.csc.ir.structure.impl.CoordinateDocument;
import ru.csc.ir.structure.impl.CoordinateOpImpl;
import ru.csc.ir.structure.impl.DocumentSet;
import ru.csc.ir.structure.query.SearchQuery;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

public class SearchEngine implements Conditional {
    private final Index<Term, Document> index;

    public SearchEngine(@NotNull Index<Term, Document> index) {
        this.index = index;
    }

    @NotNull
    DocumentCollection search(@NotNull SearchQuery query) {
        Optional<DocumentCollection> reduced;
        if (query.getOperatorType() == Operator.TYPE.DIST) {
            List<Term> terms = query.getTerms();
            List<Operator> operators = query.getOperators();

            reduced = IntStream.range(0, query.getTerms().size() - 1)
                    .mapToObj(i -> intersectDistTerms(terms.get(i), terms.get(i + 1), (CoordinateOpImpl) operators.get(i)))
                    .reduce(DocumentCollection::intersect);
        } else {
            reduced = query.getTerms().stream()
                    .map(index::findDocuments)
                    .reduce(reductionStrategy(query));
        }

        return reduced.isPresent() ? reduced.get() : new DocumentSet();
    }

    private DocumentCollection intersectDistTerms(Term left, Term right, CoordinateOpImpl operator) {
        DocumentCollection lDocs = index.findDocuments(left);
        DocumentCollection rDocs = index.findDocuments(right);
        DocumentSet res = new DocumentSet();
        for (Document lDoc : lDocs) {
            for (Document rDoc : rDocs) {
                if (lDoc.getPath().equals(rDoc.getPath())) {
                    int lPos = ((CoordinateDocument) lDoc).getPosition();
                    int rPos = ((CoordinateDocument) rDoc).getPosition();
                    int dist = rPos - lPos;

                    boolean applicable = false;
                    switch (operator.getDirection()) {
                        case LEFT: case RIGHT:
                            applicable = dist == operator.getDistance();
                            break;
                        case ANY:
                            applicable = Math.abs(dist) == operator.getDistance();
                            break;
                    }

                    if (applicable) {
                        res.add(lDoc);
                    }
                }
            }
        }
        return res;
    }

    private BinaryOperator<DocumentCollection> reductionStrategy(SearchQuery query) {
        return Operator.TYPE.AND == query.getOperatorType() ? DocumentCollection::intersect : DocumentCollection::unite;
    }

    void loadIndex(@NotNull File file) {
        index.readIndex(file);
    }
}
