package ed.xmlbookproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author matin
 */
public class XmlBookProject {

    public static void main(String[] args) {
        parserTxtToXml("C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_in\\sample-lorem-ipsum-text-file.txt");
        
    }

    
    public static void parserTxtToXml(String sourceFile){
        File f = new File(sourceFile);
        try(BufferedReader bf = new BufferedReader(new FileReader(f));
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\matin\\OneDrive\\Έγγραφα\\NetBeansProjects\\XmlBookProject\\src\\data_out\\Generated.xml")),true)){
            pw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            pw.write("<book>\n");
            String lineOfFile;
            int chapterNumber=1;
            int paragraphNumber=1;
            int lineNumber=1;
            pw.write("\t<chapter number=\"" + chapterNumber + "\">\n");
            pw.write("\t\t<paragraph number=\"" + paragraphNumber + "\">\n");
            while((lineOfFile=bf.readLine())!=null){
                if(lineOfFile.isBlank()){
                    pw.write("\t\t</paragraph>\n");
                    if(paragraphNumber%20==0){
                        pw.write("\t</chapter>\n");
                        chapterNumber++;
                        pw.write("\t<chapter number=\"" + chapterNumber + "\">\n");
                        paragraphNumber=0;
                    }
                    paragraphNumber++;
                    pw.write("\t\t<paragraph number=\"" + paragraphNumber + "\">\n");
                }
                int index=lineOfFile.indexOf(".");
                int start=0;
                while(index!=-1){
                    pw.write("\t\t\t<line number=\"" + lineNumber + "\">");
                    pw.write(lineOfFile.substring(start,index+1));
                    pw.write("</line>\n");
                    start = index+1;
                    index = lineOfFile.indexOf(".",start);
                    lineNumber++;
                }
                lineNumber=1;
            }
            pw.write("\t\t</paragraph>\n");
            pw.write("\t</chapter>\n");
            pw.write("</book>\n");
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }
}
