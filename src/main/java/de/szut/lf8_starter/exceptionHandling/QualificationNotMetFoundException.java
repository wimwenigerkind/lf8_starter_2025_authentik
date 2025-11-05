package de.szut.lf8_starter.exceptionHandling;

public class QualificationNotMetFoundException extends RuntimeException {
    public QualificationNotMetFoundException(String message) {
        super(message);
    }
}