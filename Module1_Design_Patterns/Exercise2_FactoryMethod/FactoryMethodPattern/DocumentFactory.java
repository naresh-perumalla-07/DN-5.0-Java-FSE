package FactoryMethodPattern;

/**
 * Abstract Factory (Creator) class.
 * Subclasses must implement the createDocument() factory method.
 */
public abstract class DocumentFactory {
    // Factory Method
    public abstract Document createDocument();

    /**
     * Template method that uses the factory method
     * to create a document and perform operations on it.
     */
    public void manageDocument() {
        Document doc = createDocument();
        doc.open();
        doc.save();
        doc.close();
    }
}
