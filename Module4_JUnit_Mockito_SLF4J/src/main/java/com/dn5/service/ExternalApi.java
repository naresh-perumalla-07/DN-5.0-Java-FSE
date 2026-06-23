package com.dn5.service;

/**
 * Interface representing an external API dependency.
 * Used in Mockito exercises to demonstrate mocking.
 */
public interface ExternalApi {

    String getData();

    String getDataById(int id);

    void processData(String data);

    void saveRecord(String key, String value);
}
