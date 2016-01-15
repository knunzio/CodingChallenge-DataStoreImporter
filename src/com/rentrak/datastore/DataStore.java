package com.rentrak.datastore;

import java.util.List;

/**
 */
public interface DataStore {
     public DataStoreError create( DataStoreObject dataStoreObject);
     public DataStoreError read( DataStoreQuery dataStoreQuery, List<DataStoreObject> dataStoreObject);
     public DataStoreError update( DataStoreObject dataStoreObject);
     public DataStoreError delete( DataStoreObject dataStoreObject);
}
