/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ed.xmlbookproject.Services;

import ed.xmlbookproject.models.BookForGenerated;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.SAXException;

/**
 *
 * @author matin
 */
@Slf4j
public class XmlValidationService {

    public static boolean xmlValidation(String xmlFileName, String xsdFileName, Class xmlClass) {
        boolean validity = xmlValidator(xmlFileName, xsdFileName, xmlClass);
        boolean returnStatus = false;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(xmlClass);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(xsdFileName));
            unmarshaller.setSchema(schema);
            File xmlFile = new File(xmlFileName);
            Object object = unmarshaller.unmarshal(xmlFile);
            returnStatus = true;
        } catch (JAXBException | SAXException e) {
            System.out.println("not valid");
        }
        return returnStatus;
    }

    public static boolean xmlValidator(String xmlFileName, String xsdFileName, Class xmlClass) {

        boolean returnStatus = false;
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(xmlClass);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            log.debug("method starts1");
            Schema schema = schemaFactory.newSchema(new File(xsdFileName)); //edw

            unmarshaller.setSchema(schema);

            File xmlFile = new File(xmlFileName);

            Object object = unmarshaller.unmarshal(xmlFile);
            log.debug("method starts2");
            log.debug("xml validated ", object);
            returnStatus = true;
        } catch (JAXBException | SAXException e) {
            log.debug("not valid xml", e);
        }
        log.debug("method terminates");
        return returnStatus;
    }

}
