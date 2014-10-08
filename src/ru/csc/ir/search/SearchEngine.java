package ru.csc.ir.search;

import org.jetbrains.annotations.NotNull;
import ru.csc.ir.index.Index;
import ru.csc.ir.structure.Document;
import ru.csc.ir.structure.DocumentCollection;
import ru.csc.ir.structure.Operator;
import ru.csc.ir.structure.Term;
import ru.csc.ir.structure.impl.DocumentSet;
import ru.csc.ir.structure.impl.TermImpl;
import ru.csc.ir.structure.query.SearchQuery;

import java.io.File;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class SearchEngine implements Conditional {
    private final Index<Term, Document> index;

    public SearchEngine(@NotNull Index<Term, Document> index) {
        this.index = index;
    }

    @NotNull
    DocumentCollection search(@NotNull SearchQuery query) {
        Optional<DocumentCollection> reduced = query.getTerms().stream()
                .map(index::findDocuments)
                .reduce(reductionStrategy(query));

        return reduced.isPresent() ? reduced.get() : new DocumentSet();
    }

    private BinaryOperator<DocumentCollection> reductionStrategy(SearchQuery query) {
        return Operator.TYPE.AND == query.getOperatorType() ? DocumentCollection::intersect : DocumentCollection::unite;
    }

    void loadIndex(@NotNull File file) {
        index.readIndex(file);
    }
}
