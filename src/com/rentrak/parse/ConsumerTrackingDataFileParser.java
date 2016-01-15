package com.rentrak.parse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rentrak.dao.ConsumerTrackingData;
import com.rentrak.datastore.DataStore;
import com.rentrak.datastore.SimpleRentrakDataStore;
import com.rentrak.parse.error.InboundParseError;
import com.rentrak.parse.error.ParseError;
import com.rentrak.parse.error.ParseErrorCode;

/**
 */
public class ConsumerTrackingDataFileParser implements FileParser {

    /**
     * The DataStore for this Type.
     */
    private DataStore dataStore = null;

    public ConsumerTrackingDataFileParser(){
        dataStore = SimpleRentrakDataStore.getInstance();
    }

    /**
     * Parse method to convert the txt record to a dao object and insert in the datastore.
     * @param inboundFileName
     *     - String record file name.
     * @return
     *     - A list of parse errors describing the issues found during parsing.
     */
    public List<ParseError> parse(String inboundFileName){
        List<ParseError> errorList = new ArrayList<>();
        FileReader inputFile = null;
        BufferedReader bufferReader = null;
        try{
            //Create object of FileReader
            inputFile = new FileReader(inboundFileName);

            //Instantiate the BufferedReader Class
            bufferReader = new BufferedReader(inputFile);

            //Variable to hold the one line data
            String line;

            // Read file line by line and print on the console
            while ((line = bufferReader.readLine()) != null)   {
                //System.out.println("Line: " + line);
                //Ignore embedded schema.
                if(FileParserFactory.CONSUMER_TRACKING_DATA_SCHEMA.equals(line)){
                  //System.out.println("Ignoring embedded Schema: " + line);
                   continue;
                }
                try {
                    ConsumerTrackingData consumerTrackingData = parseRecord(line);
                    dataStore.create(consumerTrackingData);
                } catch (RecordFieldException rfe) {
                   errorList.add(new InboundParseError(ParseErrorCode.ERROR_CODE_FIELD_FAILURE, "Field Failure"));
                }
            }
        }catch(Exception e){
            System.out.println("Error while reading file line by line: " + e.getMessage());
            errorList.add(new InboundParseError(ParseErrorCode.ERROR_CODE_FILE_FAILURE, "Error while reading inbound file."));
        }
        finally{
            try {
                if (null != bufferReader) {
                    bufferReader.close();
                }
            } catch(IOException ioe){
                System.err.println("Error closing file.");
                errorList.add(new InboundParseError(ParseErrorCode.ERROR_CODE_FILE_FAILURE, "Failed to close inbound file."));
            }
        }
        errorList.add(new InboundParseError(ParseErrorCode.ERROR_CODE_SUCCESS, "Successfully parsed inbound File."));
        return errorList;
    }

    /**
     * Record parser for individual ConsumerTrackingData Records.
     * @param record
     *      - Pipe separated fields representing data from the ConsumerTrackingData SCHEMA.
     * @return
     *      - A consumerTrackingData instance from this record.
     * @throws RecordFieldException
     *      -
     */
    private ConsumerTrackingData parseRecord(String record) throws RecordFieldException{
        String stb = null;
        String title = null;
        String provider = null;
        String date = null;
        String rev = null;
        String view_time = null;

        final int FIELD_ARRAY_SIZE = 6;

        String[] fields = record.split("\\|");
        if(FIELD_ARRAY_SIZE != fields.length){
            throw new RecordFieldException("Invalid record for ConsumerTrackingData.");
        }

        stb = fields[0].trim();
        title = fields[1].trim();
        provider = fields[2].trim();
        date = fields[3].trim();

        rev = fields[4].trim();
        view_time = fields[5].trim();
        return new ConsumerTrackingData.Builder()
               .STB(stb)
               .TITLE(title)
               .PROVIDER(provider)
               .DATE(date)
               .REV(rev)
               .VIEW_TIME(view_time)
               .build();
    }

}
