package com.dn5.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Mockito Exercise 1: Mocking and Stubbing
 *
 * Demonstrates:
 *  - Creating mock objects with @Mock
 *  - Stubbing methods with when().thenReturn()
 *  - Testing service that depends on an external API
 */
@ExtendWith(MockitoExtension.class)
public class MockingStubbingTest {

    @Mock
    private ExternalApi mockApi;

    @Test
    void testFetchDataWithMock() {
        // Arrange — stub the mock to return predefined value
        when(mockApi.getData()).thenReturn("Mock Data");

        // Act — inject mock into service and call method
        MyService service = new MyService(mockApi);
        String result = service.fetchData();

        // Assert — verify the result matches stubbed value
        assertEquals("Mock Data", result);
    }

    @Test
    void testFetchDataByIdWithMock() {
        // Arrange — stub with specific argument
        when(mockApi.getDataById(1)).thenReturn("Product A");
        when(mockApi.getDataById(2)).thenReturn("Product B");

        // Act
        MyService service = new MyService(mockApi);
        String result1 = service.fetchDataById(1);
        String result2 = service.fetchDataById(2);

        // Assert
        assertEquals("Product A", result1);
        assertEquals("Product B", result2);
    }

    @Test
    void testFetchDataReturnsNullWhenNotStubbed() {
        // Arrange — mock without stubbing returns null by default
        MyService service = new MyService(mockApi);

        // Act
        String result = service.fetchData();

        // Assert
        assertNull(result, "Unstubbed mock should return null for String");
    }

    @Test
    void testStubbingWithDifferentReturnValues() {
        // Arrange — stub consecutive calls
        when(mockApi.getData())
            .thenReturn("First Call")
            .thenReturn("Second Call")
            .thenReturn("Third Call");

        MyService service = new MyService(mockApi);

        // Act & Assert — each call returns different value
        assertEquals("First Call", service.fetchData());
        assertEquals("Second Call", service.fetchData());
        assertEquals("Third Call", service.fetchData());
    }
}
