package FactoryMethodPattern;

/** Concrete Creator: Word Document Factory */
public class WordDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument() {
        return new WordDocument();
    }
}
