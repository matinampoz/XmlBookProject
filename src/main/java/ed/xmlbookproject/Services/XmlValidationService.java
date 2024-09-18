package ed.xmlbookproject.Services;

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
 * Service class for XML validation
 * 
 * This class provides methods to validate an XML file against an XSD schema
 * 
 * xmlValidation validates the given XML file against the specified XSD schema
 * xmlValidator is internal method to validate an XML file
 * 
 * @author matina
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
            log.debug("method starts");
            Schema schema = schemaFactory.newSchema(new File(xsdFileName));

            unmarshaller.setSchema(schema);

            File xmlFile = new File(xmlFileName);

            Object object = unmarshaller.unmarshal(xmlFile);
            log.debug("xml validated ", object);
            returnStatus = true;
        } catch (JAXBException | SAXException e) {
            log.debug("not valid xml", e);
        }
        log.debug("method terminates");
        return returnStatus;
    }

}
