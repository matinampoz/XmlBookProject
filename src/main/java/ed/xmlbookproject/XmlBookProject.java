package ed.xmlbookproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
 * 
 * na vrw pws na krataw mono to morfopoiimeno
 */
public class XmlBookProject {
    
    public static void main(String[] args) {
        parserTxtToXml("C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_in\\sample-lorem-ipsum-text-file.txt");
    }

    public static void parserTxtToXml(String sourceFile) {
        File f = new File(sourceFile);
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        try (BufferedReader bf = new BufferedReader(new FileReader(f));
             FileWriter fileWriter = new FileWriter("C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_out\\GeneratedStax.xml")) {

            XMLStreamWriter xmlWriter = xmlOutputFactory.createXMLStreamWriter(fileWriter);

            xmlWriter.writeStartDocument("UTF-8", "1.0");
            xmlWriter.writeStartElement("book");

            String lineOfFile;
            int chapterNumber = 1;
            int paragraphNumber = 1;
            int lineNumber = 1;

            xmlWriter.writeStartElement("chapter");
            xmlWriter.writeAttribute("number", String.valueOf(chapterNumber)); //prosthetei attribute sto chapter

            xmlWriter.writeStartElement("paragraph");
            xmlWriter.writeAttribute("number", String.valueOf(paragraphNumber));

            while ((lineOfFile = bf.readLine()) != null) {
                if (lineOfFile.isBlank()) {
                    xmlWriter.writeEndElement(); // kleinei to tag paragraph
                    if (paragraphNumber % 20 == 0) {
                        xmlWriter.writeEndElement(); // kleinei to tag chapter
                        chapterNumber++;
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
                    xmlWriter.writeCharacters(lineOfFile.substring(start, index + 1));
                    xmlWriter.writeEndElement();
                    start = index + 1;
                    index = lineOfFile.indexOf(".", start);
                    lineNumber++;
                }
                lineNumber = 1;
            }

            xmlWriter.writeEndElement(); 
            xmlWriter.writeEndElement(); 
            xmlWriter.writeEndElement(); 
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
