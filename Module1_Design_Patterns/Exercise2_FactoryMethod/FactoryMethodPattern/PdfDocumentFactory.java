package FactoryMethodPattern;

/** Concrete Creator: PDF Document Factory */
public class PdfDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument() {
        return new PdfDocument();
    }
}
