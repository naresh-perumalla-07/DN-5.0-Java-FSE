package com.dn5.service;

/**
 * Service class that depends on ExternalApi.
 * Used in Mockito exercises to demonstrate mocking and stubbing.
 */
public class MyService {

    private final ExternalApi externalApi;

    // Constructor injection — makes it easy to inject mocks
    public MyService(ExternalApi externalApi) {
        this.externalApi = externalApi;
    }

    public String fetchData() {
        return externalApi.getData();
    }

    public String fetchDataById(int id) {
        return externalApi.getDataById(id);
    }

    public void processAndSave(String key, String rawData) {
        externalApi.processData(rawData);
        String processed = rawData.toUpperCase();
        externalApi.saveRecord(key, processed);
    }
}
