package com.rentrak.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.rentrak.dao.ConsumerTrackingData;
import com.rentrak.datastore.DataStore;
import com.rentrak.datastore.DataStoreError;
import com.rentrak.datastore.DataStoreObject;
import com.rentrak.datastore.DataStoreQuery;
import com.rentrak.datastore.SimpleRentrakDataStore;

/**
 * Query tool Application Class.
 */
public class DataStoreQueryTool {

    public static void main(String[] args) {
        String selectString = args[0];
        String orderString = args[1];
        String filterString = args[2];

        DataStoreQueryTool tool = new DataStoreQueryTool();
        List<DataStoreObject> consumerTrackingDataList = tool.readDataStore();

        List<ConsumerTrackingData> data = new ArrayList<>();
        for(DataStoreObject dataStoreObject : consumerTrackingDataList){
            ConsumerTrackingData consumerTrackingData = (ConsumerTrackingData)dataStoreObject;
            data.add(consumerTrackingData);
        }
        if(!filterString.equals("UNDEFINED")){
            data = filterRecordsByFilter(filterString, data);
        }
        if(!orderString.equals("UNDEFINED")){
            data = orderRecordsByOrder(splitOption(orderString), data);
        }
        printRecordsBySelection(splitOption(selectString), data);
    }

    /**
     * Helper method to select all elements from the datastore.
     * @return
     *     - All elements from the datastore.
     */
    private List<DataStoreObject> readDataStore() {
        List<DataStoreObject> result = new ArrayList<>();
        DataStore dataStore = SimpleRentrakDataStore.getInstance();
        DataStoreError error = dataStore.read(new DataStoreQuery("select * from ConsumerTrackingData"), result);
        if(DataStoreError.DATA_STORE_ERROR_SUCCESS != error){
            System.out.println("Select all from ConsumerTrackingData DataStore Failed.");
        }
        return result;
    }

    /**
     * Helper method to split the command line options - they will be comma separated.
     * @param option
     *     - Command line Options (ex: STB,REV,DATE)
     * @return
     *     - A list of the split options.
     */
    private static List<String> splitOption(String option){
        List<String> splitOption = new ArrayList<>();
        String[] options = option.split(",");
        splitOption = Arrays.asList(options);
        return splitOption;
    }

    /**
     * Prints the selected fields from the ConsumerTrackingData Objects.
     * @param selections
     *     - The Fields to be selected for printing.
     * @param consumerTrackingDataList
     *     - A list of ConsumerTrackingData Objects to print.
     */
    public static void printRecordsBySelection(List<String> selections, List<ConsumerTrackingData> consumerTrackingDataList){

        for(ConsumerTrackingData consumerTrackingData : consumerTrackingDataList){
            StringBuffer stringBuffer = new StringBuffer();
            for(String selection : selections){
                switch (selection) {
                    case "STB": {
                        stringBuffer.append(consumerTrackingData.getStb());
                        break;
                    }
                    case "TITLE": {
                        stringBuffer.append(consumerTrackingData.getTitle());
                        break;
                    }
                    case "PROVIDER": {
                        stringBuffer.append(consumerTrackingData.getProvider());
                        break;
                    }
                    case "DATE": {
                        stringBuffer.append(consumerTrackingData.getDate());
                        break;
                    }
                    case "REV": {
                        stringBuffer.append(consumerTrackingData.getRev());
                        break;
                    }
                    case "VIEW_TIME": {
                        stringBuffer.append(consumerTrackingData.getView_Time());
                        break;
                    }
                    default:{
                        stringBuffer.append(consumerTrackingData.toPipedString());
                        break;
                    }
                }
                stringBuffer.append("|");
            }
            System.out.println(stringBuffer.toString());
        }
    }

    /**
     * Filters the ConsumerTrackingData List for only those objects that match the filter.
     * @param filter
     *     - A filter containing a field value pair (ex. DATE=2014-04-01)
     * @param consumerTrackingDataList
     *     - A list of ConsumerTrackingData Objects to print.
     * @return
     *     - A filtered ConsumerTrackingData List.
     */
    public static List<ConsumerTrackingData> filterRecordsByFilter(String filter, List<ConsumerTrackingData> consumerTrackingDataList){
        String[] filterFieldAndValue = filter.split("=");
        final String FIELD = filterFieldAndValue[0];
        final String VALUE = filterFieldAndValue[1];
        List<ConsumerTrackingData> filteredConsumerTrackingData = new ArrayList<>();
        for(ConsumerTrackingData consumerTrackingData : consumerTrackingDataList){
                switch (FIELD) {
                    case "STB": {
                        if(consumerTrackingData.getStb().equals(VALUE)){
                           filteredConsumerTrackingData.add(consumerTrackingData) ;
                        }
                        break;
                    }
                    case "TITLE": {
                        if(consumerTrackingData.getTitle().equals(VALUE)){
                            filteredConsumerTrackingData.add(consumerTrackingData) ;
                        }
                        break;
                    }
                    case "PROVIDER": {
                        if(consumerTrackingData.getProvider().equals(VALUE)){
                            filteredConsumerTrackingData.add(consumerTrackingData) ;
                        }
                        break;
                    }
                    case "DATE": {
                        if(consumerTrackingData.getDate().equals(VALUE)){
                            filteredConsumerTrackingData.add(consumerTrackingData) ;
                        }
                        break;
                    }
                    case "REV": {
                        if(consumerTrackingData.getRev().equals(VALUE)){
                            filteredConsumerTrackingData.add(consumerTrackingData) ;
                        }
                        break;
                    }
                    case "VIEW_TIME": {
                        if(consumerTrackingData.getView_Time().equals(VALUE)){
                            filteredConsumerTrackingData.add(consumerTrackingData) ;
                        }
                        break;
                    }
                    default:{
                        break;
                    }
                }
        }
        return filteredConsumerTrackingData;
    }


    /**
     * Orders the ConsumerTrackingDataList by the defined order.
     * @param order
     *     - Order options with is a comma separated list of SCHEMA Fields. (Ex, DATE,STB).
     * @param consumerTrackingDataList
     *     - A list of ConsumerTrackingData Objects to print.
     * @return
     *     - An ordered ConsumerTrackingData List.
     */
    public static List<ConsumerTrackingData> orderRecordsByOrder(List<String> order, List<ConsumerTrackingData> consumerTrackingDataList){
        ConsumerTrackingData.setCompareFieldList(order);
        Collections.sort(consumerTrackingDataList);
        return consumerTrackingDataList;
    }
}



