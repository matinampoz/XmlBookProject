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
import lombok.Data;

/**
 *
 * @author matin
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
class Chapter {
    
    @XmlAttribute(name = "number")
    private int number;
    
    @XmlElement(name = "paragraph")
    private List<Paragraph> paragraphs;
    
}
