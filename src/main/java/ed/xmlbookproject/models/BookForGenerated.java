/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ed.xmlbookproject.models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;
import lombok.Data;

/**
 *
 * @author matin
 */

@Data
@XmlRootElement(name = "book")
@XmlAccessorType(XmlAccessType.FIELD)
public class BookForGenerated {
    @XmlElement(name = "chapter")
    private List<Chapter> chapters;

    
}
