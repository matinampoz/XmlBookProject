/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ed.xmlbookproject.Services;

import ed.xmlbookproject.XmlBookProject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author matin
 */
public class xmlService {
    public static void parserTxtToXml(String sourceFile) {
        File f = new File(sourceFile);
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();

        // Counters for statistics
        int chapterCount = 1;
        int wordCount = 0;
        
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String creationDateTime = now.format(formatter);

        try (BufferedReader bf = new BufferedReader(new FileReader(f)); FileWriter fileWriter = new FileWriter("C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_out\\GeneratedStax.xml")) {

            XMLStreamWriter xmlWriter = xmlOutputFactory.createXMLStreamWriter(fileWriter);

            xmlWriter.writeStartDocument("UTF-8", "1.0");
            xmlWriter.writeStartElement("book");

            String lineOfFile;
            int chapterNumber = 1;
            int paragraphNumber = 1;
            int lineNumber = 1;

            xmlWriter.writeStartElement("chapter");
            xmlWriter.writeAttribute("number", String.valueOf(chapterNumber)); // προσθήκη attribute στο κεφάλαιο

            xmlWriter.writeStartElement("paragraph");
            xmlWriter.writeAttribute("number", String.valueOf(paragraphNumber));

            while ((lineOfFile = bf.readLine()) != null) {
                if (lineOfFile.isBlank()) {
                    xmlWriter.writeEndElement(); // κλείσιμο του tag paragraph
                    if (paragraphNumber % 20 == 0) {
                        xmlWriter.writeEndElement(); // κλείσιμο του tag chapter
                        chapterNumber++;
                        chapterCount++; 
                        xmlWriter.writeStartElement("chapter");
                        xmlWriter.writeAttribute("number", String.valueOf(chapterNumber));
                    }
                    paragraphNumber++;
                  
                    xmlWriter.writeStartElement("paragraph");
                    xmlWriter.writeAttribute("number", String.valueOf(paragraphNumber));
                }

                int index = lineOfFile.indexOf(".");
                int start = 0;
                while (index != -1) {
                    xmlWriter.writeStartElement("line");
                    xmlWriter.writeAttribute("number", String.valueOf(lineNumber));

                    String lineText = lineOfFile.substring(start, index + 1);
                    xmlWriter.writeCharacters(lineText);
                    wordCount += lineText.split("\\s+").length; // Count words

                    xmlWriter.writeEndElement(); // κλείσιμο του tag line
                    start = index + 1;
                    index = lineOfFile.indexOf(".", start);
                    lineNumber++;
                }
            }

            
            xmlWriter.writeEndElement(); // paragraph
            xmlWriter.writeEndElement(); // chapter

            //statitsics
            xmlWriter.writeStartElement("statistics");
            xmlWriter.writeStartElement("totalChapters");
            xmlWriter.writeCharacters(String.valueOf(chapterCount));
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("totalParagraphs");
            xmlWriter.writeCharacters(String.valueOf(paragraphNumber));
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("totalWords");
            xmlWriter.writeCharacters(String.valueOf(wordCount));
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("creationDateTime");
            xmlWriter.writeCharacters(creationDateTime);
            xmlWriter.writeEndElement();

            xmlWriter.writeStartElement("author");
            xmlWriter.writeCharacters("AAAAAAAA");
            xmlWriter.writeEndElement();

            xmlWriter.writeStartElement("applicationClass");
            xmlWriter.writeCharacters(XmlBookProject.class.getSimpleName()); // Όνομα της κλάσης
            xmlWriter.writeEndElement();
            xmlWriter.writeEndElement(); // statistics

            xmlWriter.writeEndElement(); // book
            xmlWriter.writeEndDocument();

            xmlWriter.flush();
            xmlWriter.close();

            transformToIndentedXml("C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_out\\GeneratedStax.xml", "C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_out\\GeneratedStax.xml".replace(".xml", "-Indented.xml"));

        } catch (IOException | XMLStreamException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    private static void transformToIndentedXml(String inputFilePath, String outputFilePath) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //energopoiei esoxes
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); //kathe epipedo esoxis - 4 kena

            Source input = new StreamSource(new File(inputFilePath)); //anagnwsi xml gia epegergasia
            StreamResult output = new StreamResult(new File(outputFilePath)); // save tou xml

            // ο πυρήνας της διαδικασίας μετασχηματισμού XML. Εφαρμόζει τις μετατροπές που καθορίζονται από το XSLT 
            // stylesheet στα δεδομένα XML της πηγής (input) και αποθηκεύει το αποτέλεσμα στο καθορισμένο αρχείο ή ροή 
            // εξόδου (output)
            transformer.transform(input, output);
        } catch (TransformerException ex) {
            System.err.println("Error transforming XML: " + ex.getMessage());
        }
    }
    
}
