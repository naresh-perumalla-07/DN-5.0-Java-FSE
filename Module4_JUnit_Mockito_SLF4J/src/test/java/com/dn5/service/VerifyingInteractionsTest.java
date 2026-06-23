package com.dn5.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

/**
 * Mockito Exercise 2: Verifying Interactions
 *
 * Demonstrates:
 *  - verify() — check if a method was called
 *  - verify(mock, times(n)) — check exact call count
 *  - verify(mock, never()) — check method was NOT called
 *  - verify(mock, atLeastOnce()) — check called at least once
 *  - InOrder verification — check call order
 */
@ExtendWith(MockitoExtension.class)
public class VerifyingInteractionsTest {

    @Mock
    private ExternalApi mockApi;

    @Test
    void testVerifyMethodCalled() {
        // Arrange
        MyService service = new MyService(mockApi);

        // Act
        service.fetchData();

        // Assert — verify getData() was called exactly once
        verify(mockApi).getData();
    }

    @Test
    void testVerifyMethodCalledWithSpecificArgs() {
        // Arrange
        when(mockApi.getDataById(anyInt())).thenReturn("data");
        MyService service = new MyService(mockApi);

        // Act
        service.fetchDataById(42);

        // Assert — verify called with specific argument
        verify(mockApi).getDataById(42);
    }

    @Test
    void testVerifyNumberOfInvocations() {
        // Arrange
        MyService service = new MyService(mockApi);

        // Act — call fetchData 3 times
        service.fetchData();
        service.fetchData();
        service.fetchData();

        // Assert — verify exact number of calls
        verify(mockApi, times(3)).getData();
    }

    @Test
    void testVerifyNeverCalled() {
        // Arrange
        MyService service = new MyService(mockApi);

        // Act — only fetch data, not fetchById
        service.fetchData();

        // Assert — getDataById should never have been called
        verify(mockApi, never()).getDataById(anyInt());
    }

    @Test
    void testVerifyAtLeastOnce() {
        // Arrange
        MyService service = new MyService(mockApi);

        // Act
        service.fetchData();
        service.fetchData();

        // Assert — verify called at least once
        verify(mockApi, atLeastOnce()).getData();
        verify(mockApi, atLeast(2)).getData();
    }

    @Test
    void testVerifyInteractionOrder() {
        // Arrange
        MyService service = new MyService(mockApi);

        // Act
        service.processAndSave("key1", "hello");

        // Assert — verify methods were called in correct order
        InOrder inOrder = inOrder(mockApi);
        inOrder.verify(mockApi).processData("hello");
        inOrder.verify(mockApi).saveRecord("key1", "HELLO");
    }

    @Test
    void testVerifyNoMoreInteractions() {
        // Arrange
        MyService service = new MyService(mockApi);

        // Act
        service.fetchData();

        // Assert
        verify(mockApi).getData();
        verifyNoMoreInteractions(mockApi);  // No other method was called
    }
}
