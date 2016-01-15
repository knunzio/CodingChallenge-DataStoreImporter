package com.rentrak.parse.error;

/**
 */
public interface ParseError {
    public ParseErrorCode getErrorCode();
    public String getErrorDescription();
}
