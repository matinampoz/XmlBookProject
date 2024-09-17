package ed.xmlbookproject;

import ed.xmlbookproject.Services.xmlService;
import ed.xmlbookproject.models.Book;
import java.io.IOException;
import java.util.Random;

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
            int endChapter = xmlService.parserTxtToXml("C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_in\\sample-lorem-ipsum-text-file.txt");
            System.out.println("The xml has been created");

            Random rand = new Random();
            int randomNumStart = rand.nextInt(endChapter) + 1;
            int randomNumEnd = rand.nextInt(endChapter) + 1;
            if (randomNumEnd < randomNumStart) {
                int temp = randomNumEnd;
                randomNumEnd = randomNumStart;
                randomNumStart = temp;
            }

            System.out.println("Generate the xsd");
            xmlService.xsdGenerator();
            System.out.println("Xsd has benn generated");

            System.out.println("Validate the xml");
            if (xmlService.xmlValidation("C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_out\\GeneratedStax-Indented.xml", "C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_out\\GeneratedXsd.xsd", Book.class)) {
                System.out.println("The xml is valid");
            } else {
                System.out.println("The xml is not valid");
            }
        } catch (IOException e) {
            System.out.println("Procces failed");
        }

    }
}
