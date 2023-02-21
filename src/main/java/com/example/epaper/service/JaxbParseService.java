package com.example.epaper.service;

import com.example.epaper.model.Epaper;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public interface JaxbParseService {

    Epaper parseFromFile(final ByteArrayInputStream inputStream) throws JAXBException, IOException;

}
