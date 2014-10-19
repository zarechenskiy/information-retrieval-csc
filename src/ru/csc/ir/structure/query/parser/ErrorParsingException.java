package ru.csc.ir.structure.query.parser;

public class ErrorParsingException extends RuntimeException {
    private static final long serialVersionUID = 564403076570835304L;

    public ErrorParsingException(String errorMessage) {
        super(errorMessage);
    }
}
