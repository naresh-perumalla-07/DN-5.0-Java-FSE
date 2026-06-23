package FactoryMethodPattern;

/** Concrete Creator: Excel Document Factory */
public class ExcelDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument() {
        return new ExcelDocument();
    }
}
