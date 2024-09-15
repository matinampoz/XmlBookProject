package ed.xmlbookproject;

import ed.xmlbookproject.Services.xmlService;
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
 *
 * na vrw pws na krataw mono to morfopoiimeno
 */
public class XmlBookProject {

    public static void main(String[] args) {
        System.out.println("Starting the parsing from txt to xml");
        xmlService.parserTxtToXml("C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_in\\sample-lorem-ipsum-text-file.txt");
        System.out.println("The xml has been created");
        
        System.out.println("");
      
    }
}
