package ru.csc.ir.structure.query.parser;

import org.jetbrains.annotations.NotNull;
import ru.csc.ir.structure.Operator;
import ru.csc.ir.structure.Term;
import ru.csc.ir.structure.impl.TermImpl;
import ru.csc.ir.structure.query.SearchQuery;

import java.util.StringTokenizer;

public class QueryParser {
    private final static String DELIMITERS =  " \n\t()";
    private final static String EXPECTED_OPERAND_ERROR_MESSAGE = "Expected operand (word) ";
    private final static String EXPECTED_OPERATOR_ERROR_MESSAGE = "Expected operator (AND/OR)";

    public static final QueryParser INSTANCE = new QueryParser();

    private QueryParser() {
    }

    public SearchQuery parse(@NotNull String query) {
        SearchQuery searchQuery = new SearchQuery();

        StringTokenizer tokenizer = new StringTokenizer(initialProcess(query), DELIMITERS, false);

        Term prevTerm = null;
        boolean waitingForOperand = true;
        while (tokenizer.hasMoreTokens()) {
            Term term = TermImpl.create(tokenizer.nextToken());
            if (waitingForOperand) {
                matchOperand(term);

                if (prevTerm != null) {
                    extendQuery(searchQuery, (Operator) prevTerm, term);
                } else {
                    searchQuery.single(term);
                }

                waitingForOperand = false;
            } else {
                matchOperator(term);

                waitingForOperand = true;
            }

            prevTerm = term;
        }

        if (waitingForOperand) {
            throw new ErrorParsingException(EXPECTED_OPERAND_ERROR_MESSAGE);
        }

        return searchQuery;
    }

    private void extendQuery(
            @NotNull SearchQuery searchQuery,
            @NotNull Operator operator,
            @NotNull Term operand) {
        switch (operator.getType()) {
            case AND:
                searchQuery.and(operand);
                break;
            case OR:
                searchQuery.or(operand);
                break;
        }
    }

    private void matchOperator(@NotNull Term term) {
        if (!(term instanceof Operator)) {
            throw new ErrorParsingException(EXPECTED_OPERATOR_ERROR_MESSAGE);
        }

    }

    private void matchOperand(@NotNull Term term) {
        if (term instanceof Operator) {
            throw new ErrorParsingException(EXPECTED_OPERAND_ERROR_MESSAGE);
        }

    }

    private String initialProcess(String query) {
        return query
                .replaceAll("\\(", "")
                .replaceAll("\\)", "");
    }
}
