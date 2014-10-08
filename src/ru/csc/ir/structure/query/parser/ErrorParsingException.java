package ru.csc.ir.structure.query.parser;

public class ErrorParsingException extends RuntimeException {
    public ErrorParsingException(String errorMessage) {
        super(errorMessage);
    }
}
