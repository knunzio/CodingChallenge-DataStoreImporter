package com.rentrak.parse;

/**
 */
public class FileParserFactory {

    final public static String CONSUMER_TRACKING_DATA_SCHEMA = "STB|TITLE|PROVIDER|DATE|REV|VIEW_TIME";

    public static FileParser getFileParserForSchema(String schema) {
        FileParser fileParser = null;
        if(CONSUMER_TRACKING_DATA_SCHEMA.equals(schema)){
            fileParser =  new ConsumerTrackingDataFileParser();
        }
        return fileParser;
    }

    private FileParserFactory(){};
}
