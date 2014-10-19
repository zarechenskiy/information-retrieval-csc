package ru.csc.ir.structure.query;

import org.jetbrains.annotations.NotNull;
import ru.csc.ir.structure.Operator;
import ru.csc.ir.structure.Term;
import ru.csc.ir.structure.query.parser.ErrorParsingException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchQuery {
    private Operator.TYPE operatorType = null;
    private List<Term> terms = new ArrayList<>();
    private List<Operator> operators = new ArrayList<>();

    public void single(@NotNull Term term) {
        terms.clear();
        terms.add(term);
    }

    public void and(@NotNull Term term) {
        setOperatorType(Operator.TYPE.AND);
        terms.add(term);
    }

    public void or(@NotNull Term term) {
        setOperatorType(Operator.TYPE.OR);
        terms.add(term);
    }

    public void dist(@NotNull Term term, @NotNull Operator operator) {
        setOperatorType(Operator.TYPE.DIST);
        terms.add(term);
        operators.add(operator);
    }

    @NotNull
    public List<Operator> getOperators() {
        return Collections.unmodifiableList(operators);
    }

    @NotNull
    public List<Term> getTerms() {
        return Collections.unmodifiableList(terms);
    }

    public Operator.TYPE getOperatorType() {
        return operatorType;
    }

    private void setOperatorType(@NotNull Operator.TYPE type) {
        if (operatorType == null) {
            operatorType = type;
        }

        if (operatorType != type) {
            throw new ErrorParsingException("incorrect query: there should be one operator type");
        }
    }
}
