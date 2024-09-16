/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ed.xmlbookproject.models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

/**
 *
 * @author matin
 */

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Statistics {

    @XmlElement
    private int totalChapters;

    @XmlElement
    private int totalParagraphs;

    @XmlElement
    private int totalWords;

    @XmlElement
    private String creationDateTime;

    @XmlElement
    private String author;

    @XmlElement
    private String applicationClass;

}
