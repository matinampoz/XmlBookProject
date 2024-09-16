/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ed.xmlbookproject.models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 *
 * @author matin
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Paragraph {
    
     @XmlAttribute(name = "number")
    private int number; 
    
    @XmlElement(name = "line")
    private List<Line> lines;
    
}
