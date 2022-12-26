package com.converter.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@XmlRootElement
public class DocumentResponse {

    private String text;
    private String[] sentences;

}
