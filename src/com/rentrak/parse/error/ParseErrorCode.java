package com.rentrak.parse.error;

/**
 */
public enum  ParseErrorCode {
    ERROR_CODE_SCHEMA_FAILURE,
    ERROR_CODE_FACTORY_FAILURE,
    ERROR_CODE_RECORD_FAILURE(),
    ERROR_CODE_FIELD_FAILURE(),
    ERROR_CODE_FILE_FAILURE(),
    ERROR_CODE_SUCCESS(),
    ERROR_CODE_DEFAULT_FAILURE();

    ParseErrorCode() {
    }
}
