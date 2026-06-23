package FactoryMethodPattern;

/**
 * Test class to demonstrate the Factory Method Pattern.
 * 
 * The client code works with factories and products through abstract interfaces,
 * so it can work with any user-defined concrete factories and products.
 */
public class Main {
    public static void main(String[] args) {
        // Create different document factories
        DocumentFactory wordFactory = new WordDocumentFactory();
        DocumentFactory pdfFactory = new PdfDocumentFactory();
        DocumentFactory excelFactory = new ExcelDocumentFactory();

        // Use the template method to manage documents
        System.out.println("=== Word Document ===");
        wordFactory.manageDocument();

        System.out.println("\n=== PDF Document ===");
        pdfFactory.manageDocument();

        System.out.println("\n=== Excel Document ===");
        excelFactory.manageDocument();

        // Create documents directly using factory method
        System.out.println("\n=== Direct Creation ===");
        Document doc = wordFactory.createDocument();
        doc.open();
        doc.close();
    }
}
