package com.rentrak.datastore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.rentrak.dao.ConsumerTrackingData;

/**
 */
public class SimpleRentrakDataStore implements DataStore {

    private final String DATA_STORE_BASE_DIR = "./data";

    private static SimpleRentrakDataStore INSTANCE = null;

    Set<DataStoreObject> dataStoreObjects = new HashSet<>();

    private SimpleRentrakDataStore(){
        initilize();
    }

    /**
     * Initializes the DataStore.
     * Currently, All Data is stored in memory and on a file system.
     */
    private void initilize(){

        File dataStoreDir = new File(DATA_STORE_BASE_DIR);
        if(!dataStoreDir.exists()){
            if (dataStoreDir.mkdir()) {
                System.out.println("Datastore Created.");
            } else {
                System.out.println("Failed to create datastore!");
            }
        }
        File[] files = dataStoreDir.listFiles();

        Gson gson = new Gson();

        for(File file : files){
            //Read the file
            try {

                BufferedReader br = new BufferedReader(new FileReader(file));

                ConsumerTrackingData consumerTrackingData = gson.fromJson(br, ConsumerTrackingData.class);

                dataStoreObjects.add(consumerTrackingData);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Singleton interface.
     * @return
     *     - The SimpleRentrakDataStore Instance
     */
    public static SimpleRentrakDataStore getInstance(){

        if(null == INSTANCE){
            INSTANCE = new SimpleRentrakDataStore();
        }
        return INSTANCE;
    }

    /**
     * 'C' or CRUD methods.
     * @param dataStoreObject
     * @return
     *     - DataStoreError
     */
    synchronized  public DataStoreError create( DataStoreObject dataStoreObject){
        if(!dataStoreObjects.contains(dataStoreObject)) {
            save(dataStoreObject);
        }
        else {
            update(dataStoreObject);
        }
        return DataStoreError.DATA_STORE_ERROR_SUCCESS;
    }

    /**
     * 'R' of CRUD methods.
     * @param dataStoreQuery
     *     - Query Object used to query datastore - UNIMPLEMENTED.
     * @param dataStoreObjectList
     *     - The list to populate.
     * @return
     *     - DataStoreError
     */
    synchronized  public DataStoreError read( DataStoreQuery dataStoreQuery, List<DataStoreObject> dataStoreObjectList){
        //TODO: Add query logic.  Just return all objects for now.
        for(DataStoreObject dataStoreObject : this.dataStoreObjects) {
            dataStoreObjectList.add( dataStoreObject.copy());
        }
        return DataStoreError.DATA_STORE_ERROR_SUCCESS;
    }

    /**
     * 'U' of CRUD method.
     * @param dataStoreObject
     *     - The DataStoreObject to update.
     * @return
     *     - DataStoreError
     */
    synchronized  public DataStoreError update( DataStoreObject dataStoreObject){
        delete(dataStoreObject);
        create(dataStoreObject);
        return DataStoreError.DATA_STORE_ERROR_SUCCESS;
    }

    /**
     * 'D' of CRUD methods.
     * @param dataStoreObject
     *     - The DataStoreObject to update.
     *
     * @return
     *     - DataStoreError
     */
    synchronized  public DataStoreError delete( DataStoreObject dataStoreObject){
        File fileToDelete = new File(DATA_STORE_BASE_DIR + "/" + dataStoreObject.hashCode());
        fileToDelete.delete();
        this.dataStoreObjects.remove(dataStoreObject);
        return DataStoreError.DATA_STORE_ERROR_SUCCESS;
    }

    /**
     * Save method marshalls the object to JSON and writes it to a filed named with the hashcode.
     * @param dataStoreObject
     *     - The DataStoreObject to save.
     */
    private void save(DataStoreObject dataStoreObject){

        Gson gson = new Gson();
        // convert java object to JSON format,
        // and returned as JSON formatted string
        String json = gson.toJson(dataStoreObject);

        try {
            //write converted json data to a file named "file.json"
            FileWriter writer = new FileWriter(DATA_STORE_BASE_DIR + "/" + dataStoreObject.hashCode());
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Save it in memory
        dataStoreObjects.add(dataStoreObject);
    }
}
