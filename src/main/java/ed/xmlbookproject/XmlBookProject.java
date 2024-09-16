package ed.xmlbookproject;

import ed.xmlbookproject.Services.xmlService;
import ed.xmlbookproject.models.Book;
import java.io.IOException;

/**
 *
 * @author matin
 *
 * na vrw pws na krataw mono to morfopoiimeno
 */
public class XmlBookProject {

    public static void main(String[] args) {
        try {
            System.out.println("Starting the parsing from txt to xml");
            xmlService.parserTxtToXml("src.data_in.sample-lorem-ipsum-text-file.txt");
            System.out.println("The xml has been created");

            System.out.println("Generate the xsd");
            xmlService.xsdGenerator();
            System.out.println("Xsd has benn generated");
            
            System.out.println("Validate the xml");
            if (xmlService.xmlValidation("C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_out\\GeneratedStax-Indented.xml", "C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_out\\GeneratedXsd.xsd", Book.class)) {
                System.out.println("The xml is valid"); }
            else {
                System.out.println("The xml is not valid");
            }
            
            
        } catch (IOException e) {
            System.out.println("Procces failed");
        }

    }
}
