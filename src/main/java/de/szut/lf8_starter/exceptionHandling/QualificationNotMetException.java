package de.szut.lf8_starter.exceptionHandling;

public class QualificationNotMetException extends RuntimeException {
    public QualificationNotMetException(String message) {
        super(message);
    }
}