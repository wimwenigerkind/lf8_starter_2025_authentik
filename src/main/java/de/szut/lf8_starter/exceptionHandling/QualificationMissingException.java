package de.szut.lf8_starter.exceptionHandling;

public class QualificationMissingException extends RuntimeException {
    public QualificationMissingException(String message) {
        super(message);
    }
}