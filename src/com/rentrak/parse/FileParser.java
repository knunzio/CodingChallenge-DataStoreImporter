package com.rentrak.parse;

import java.util.List;

import com.rentrak.parse.error.ParseError;

/**
 */
public interface FileParser {
    List<ParseError> parse(String inboundFileName);
}
