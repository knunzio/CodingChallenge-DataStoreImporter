package com.rentrak.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rentrak.parse.FileParser;
import com.rentrak.parse.FileParserFactory;
import com.rentrak.parse.error.InboundParseError;
import com.rentrak.parse.error.ParseError;
import com.rentrak.parse.error.ParseErrorCode;

/**
 * Data Importer tool Application Class.
 */
public class DataStoreImporter {

    /**
     * ASSERT: Schema is the first line in the inbound file.
     * Parses the schema from the inbound file.
     * @param inboundFileName
     *     - inbound file name.
     * @return
     *     -
     */
    private static String getSchemaForInboundFile(String inboundFileName){
        FileReader inputFile = null;
        BufferedReader bufferReader = null;
        String schema = null;
        try{
            inputFile = new FileReader(inboundFileName);
            bufferReader = new BufferedReader(inputFile);
            schema = bufferReader.readLine();
            if(null != schema){
                schema.trim();
            }
        }catch(Exception e){
            System.err.println("Error getting schema from file");
        }
        finally {
            try {
                if (null != bufferReader) {
                    bufferReader.close();
                }
            } catch(IOException ioe){
                System.err.println("Error closing file.");
            }
        }
        return schema;
    }

    private static void parseAndStoreInputFromFile(String inputFile) {
        List<ParseError> parseErrorList = new ArrayList<>();
        ParseError error = null;
        String schema = null;

        //Get Schema - first line in file.
        schema = getSchemaForInboundFile(inputFile);
        if(null == schema){
            aggregateParseErrors(new InboundParseError(ParseErrorCode.ERROR_CODE_FACTORY_FAILURE,
                    "No Schema for file."), parseErrorList);
            reportErrors(parseErrorList);
            return;
        }

        FileParser fileParser = FileParserFactory.getFileParserForSchema(schema);
        if(null != fileParser) {

            List<ParseError> errors = fileParser.parse(inputFile);
            aggregateParseErrors(errors, parseErrorList );
        } else {
           aggregateParseErrors(new InboundParseError(ParseErrorCode.ERROR_CODE_FACTORY_FAILURE,
                   "Filed to get parser from factory."),parseErrorList);
        }
        //reportErrors(parseErrorList);
    }

    private static void aggregateParseErrors(ParseError error, List<ParseError> parseErrors){
        parseErrors.add(error);
    }

    private static void aggregateParseErrors(List<ParseError> errors, List<ParseError> parseErrors){
        parseErrors.addAll(errors);
    }

    private static void reportErrors(List<ParseError> parseErrors){
        for(ParseError parseError : parseErrors) {
            System.out.println(parseError);
        }
    }

    public static void main(String[] args) {
        //System.out.println("DataStoreImporter Enter - inputFile : " + args[0]);
        String inputFileName = args[0];

        File inputFile = new File(inputFileName);
        if(!inputFile.exists()) {
            System.out.println("Invalid input file.");
        }
        DataStoreImporter.parseAndStoreInputFromFile(inputFileName);

    }

}
