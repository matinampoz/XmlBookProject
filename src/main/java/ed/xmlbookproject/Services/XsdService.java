/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ed.xmlbookproject.Services;

import ed.xmlbookproject.models.BookForGenerated;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.SchemaOutputResolver;
import java.io.File;
import java.io.IOException;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

/**
 *
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
