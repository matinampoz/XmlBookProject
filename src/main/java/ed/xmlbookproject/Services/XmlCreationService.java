package ed.xmlbookproject.Services;

import ed.xmlbookproject.XmlBookProject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class for XML creation and manipulation
 * 
 * Handles the conversion of text files to XML, formatting the XML, and 
 * generating XMLs with selected chapters.
 * 
 * parserTxtToXml parses a text file and converts it to an unformatted XML
 * transformToFormatedXml transforms the generated unformatted XML into a formatted XML
 * createXmlWithChapters generates an XML containing only the selected chapters from an input XML
 * 
 * @author matina
 */
@Slf4j
public class XmlCreationService {

    public static int parserTxtToXml(String sourceFile, String author) {
        File f = new File(sourceFile);
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();

        int chapterCount = 1;
        int wordCount = 0;
        int uniqueWordCount = 0;
        Map<Integer, String> uniqueWords = new HashMap<>();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String creationDateTime = now.format(formatter);

        try (BufferedReader bf = new BufferedReader(new FileReader(f)); FileWriter fileWriter = new FileWriter("C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_out\\GeneratedStax.xml")) {

            XMLStreamWriter xmlWriter = xmlOutputFactory.createXMLStreamWriter(fileWriter);

            log.debug("unformated xml is starting being creating");
            xmlWriter.writeStartDocument("UTF-8", "1.0");
            xmlWriter.writeStartElement("book");

            String lineOfFile;
            int paragraphNumber = 1;
            int lineNumber = 1;

            xmlWriter.writeStartElement("chapter");
            xmlWriter.writeAttribute("number", String.valueOf(chapterCount));

            xmlWriter.writeStartElement("paragraph");
            xmlWriter.writeAttribute("number", String.valueOf(paragraphNumber));

            while ((lineOfFile = bf.readLine()) != null) {
                if (lineOfFile.isBlank()) {
                    xmlWriter.writeEndElement();
                    if (paragraphNumber % 20 == 0) {
                        xmlWriter.writeEndElement();
                        chapterCount++;
                        xmlWriter.writeStartElement("chapter");
                        xmlWriter.writeAttribute("number", String.valueOf(chapterCount));
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
                    List<String> wordArray = new ArrayList<>(Arrays.asList(lineText.split("\\s+")));

                    for (String word : wordArray) {
                        if (!uniqueWords.containsValue(word)) {
                            uniqueWords.put(uniqueWordCount, word);
                            uniqueWordCount++;
                        }
                    }
                    wordCount += lineText.split("\\s+").length;

                    xmlWriter.writeEndElement();
                    start = index + 1;
                    index = lineOfFile.indexOf(".", start);
                    lineNumber++;
                }
            }

            xmlWriter.writeEndElement();
            xmlWriter.writeEndElement();

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
            xmlWriter.writeStartElement("uniqueWords");
            xmlWriter.writeCharacters(String.valueOf(uniqueWordCount));
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("creationDateTime");
            xmlWriter.writeCharacters(creationDateTime);
            xmlWriter.writeEndElement();

            xmlWriter.writeStartElement("author");
            xmlWriter.writeCharacters(author);
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("applicationClass");
            xmlWriter.writeCharacters(XmlBookProject.class.getSimpleName());
            xmlWriter.writeEndElement();
            xmlWriter.writeEndElement();
            xmlWriter.writeEndElement();
            xmlWriter.writeEndDocument();

            xmlWriter.flush();
            xmlWriter.close();

            transformToFormatedXml("C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_out\\GeneratedStax.xml", "C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_out\\GeneratedStax.xml".replace(".xml", "-Indented.xml"));
            log.debug("xml created");
            return chapterCount;
        } catch (IOException | XMLStreamException ex) {
            System.err.println(ex.getMessage());
            return -1;
        }

    }

    private static void transformToFormatedXml(String inputFilePath, String outputFilePath) {
        try {
            log.debug("formated xml is starting being creating");
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            Source input = new StreamSource(new File(inputFilePath));
            StreamResult output = new StreamResult(new File(outputFilePath));

            transformer.transform(input, output);
            log.debug("formated xml created");
        } catch (TransformerException ex) {
            System.err.println("Error transforming XML: " + ex.getMessage());
        }
    }

    public static void createXmlWithChapters(String inputXml, String outputXml, int start, int end) throws XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();

        try (
                FileInputStream fis = new FileInputStream(inputXml); FileOutputStream fos = new FileOutputStream(outputXml)) {
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(fis);
            XMLEventWriter writer = xmlOutputFactory.createXMLEventWriter(fos);

            int currentChapter = 0;
            boolean withinSelectedChapters = false;

            writer.add(eventFactory.createStartDocument());
            writer.add(eventFactory.createStartElement("", "", "book"));

            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equals("chapter")) {
                        currentChapter++;

                        withinSelectedChapters = (currentChapter >= start && currentChapter <= end);
                    }
                }

                if (withinSelectedChapters) {
                    writer.add(event);
                }

                if (event.isEndElement()) {
                    if (event.asEndElement().getName().getLocalPart().equals("chapter")) {
                        if (currentChapter > end) {
                            withinSelectedChapters = false;
                        }
                    }
                }
            }

            writer.add(eventFactory.createEndElement("", "", "book"));
            writer.add(eventFactory.createEndDocument());

            writer.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
