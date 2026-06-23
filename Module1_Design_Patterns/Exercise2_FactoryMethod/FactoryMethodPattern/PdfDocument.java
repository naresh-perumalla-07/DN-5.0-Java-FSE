package FactoryMethodPattern;

/** Concrete Product: PDF Document */
public class PdfDocument implements Document {
    @Override
    public void open() {
        System.out.println("Opening PDF document...");
    }

    @Override
    public void save() {
        System.out.println("Saving PDF document in .pdf format...");
    }

    @Override
    public void close() {
        System.out.println("Closing PDF document.");
    }
}
