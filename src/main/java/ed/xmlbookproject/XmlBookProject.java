package ed.xmlbookproject;

import ed.xmlbookproject.Services.XmlCreationService;
import ed.xmlbookproject.Services.XmlValidationService;
import ed.xmlbookproject.Services.XsdService;
import ed.xmlbookproject.models.Book;
import ed.xmlbookproject.models.BookForGenerated;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;

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
            int endChapter = XmlCreationService.parserTxtToXml("C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_in\\sample-lorem-ipsum-text-file.txt");
            System.out.println("The xml has been created");


            System.out.println("Generate the xsd of the original xml");
            String xsdFileName = "C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_out\\BookXsd.xsd";
            XsdService.xsdGenerator(Book.class, xsdFileName);
            System.out.println("Xsd has benn generated");

            System.out.println("Validate the original xml");
            if (XmlValidationService.xmlValidation("C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_out\\GeneratedStax-Indented.xml", "C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_out\\GeneratedXsd.xsd", BookForGenerated.class)) {
                System.out.println("The xml is valid");
            } else {
                System.out.println("The xml is not valid");
            }
            
            System.out.println("Generate the second xml");
            Random rand = new Random();
            int randomNumStart = rand.nextInt(endChapter) + 1;
            int randomNumEnd = rand.nextInt(endChapter) + 1;
            if (randomNumEnd < randomNumStart) {
                int temp = randomNumEnd;
                randomNumEnd = randomNumStart;
                randomNumStart = temp;
            }
            XmlCreationService.createXmlWithChapters("C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_out\\GeneratedStax-Indented.xml", 
                                             "C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_out\\SelectedChapters.xml", 
                                             randomNumStart, randomNumEnd);
            System.out.println("The second xml has been created");
            
            System.out.println("Generate the xsd of the second xml");
            String xsdFileName2 = "C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_out\\Book2Xsd.xsd";
            XsdService.xsdGenerator(BookForGenerated.class, xsdFileName2);
            System.out.println("Xsd has benn generated");

            System.out.println("Validate the second xml");
            if (XmlValidationService.xmlValidation("C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_out\\GeneratedStax-Indented.xml", "C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_out\\Book2Xsd.xsd", BookForGenerated.class)) {
                System.out.println("The xml is valid");
            } else {
                System.out.println("The xml is not valid");
            }
        } catch (IOException e) {
            System.out.println("Procces failed");
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlBookProject.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
