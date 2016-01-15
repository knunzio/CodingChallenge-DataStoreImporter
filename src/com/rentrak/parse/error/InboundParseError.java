package com.rentrak.parse.error;

/**
 */
public class InboundParseError implements ParseError{

    private String errorDescription = null;

    private ParseErrorCode errorCode = ParseErrorCode.ERROR_CODE_DEFAULT_FAILURE;

    public InboundParseError(ParseErrorCode parseErrorCode, String errorDescription){
        this.errorCode = parseErrorCode;
        this.errorDescription = errorDescription;

    }
    public ParseErrorCode getErrorCode(){
       return errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    @Override
    public String toString(){
        return "ErrorCode: " + getErrorCode() +
                "\nError Description: " + getErrorDescription();
    }
}
