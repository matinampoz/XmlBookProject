package ed.xmlbookproject.Services;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.SchemaOutputResolver;
import java.io.File;
import java.io.IOException;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

/**
 * Service class for XSD generation
 * 
 * Generates an XSD schema from the provided class.
 * 
 * xsdGenerator generates an XSD file for the specified class
 * MySchemaOutputResolver is internal class used to resolve the schema output
 * @author matin
 */
public class XsdService {
    public static void xsdGenerator(Class clazz, String xsdFileName) throws IOException {

        try {
            

            JAXBContext context = JAXBContext.newInstance(clazz);
            context.generateSchema(new MySchemaOutputResolver(xsdFileName));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    
    static class MySchemaOutputResolver extends SchemaOutputResolver {

        private final String xsdFileName;

        public MySchemaOutputResolver(String xsdFileName) {
            this.xsdFileName = xsdFileName;
        }
        
        @Override
        public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
            File file = new File(xsdFileName);
            StreamResult result = new StreamResult(file);
            result.setSystemId(file.toURI().toString());
            return result;
        }
    }
}
