package FactoryMethodPattern;

/**
 * Exercise 2: Implementing the Factory Method Pattern
 * 
 * Document interface - the product interface.
 * Different document types (Word, PDF, Excel) implement this interface.
 */
public interface Document {
    void open();
    void save();
    void close();
}
