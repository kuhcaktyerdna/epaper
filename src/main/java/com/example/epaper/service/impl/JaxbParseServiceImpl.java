package com.example.epaper.service.impl;

import com.example.epaper.model.Epaper;
import com.example.epaper.service.JaxbParseService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@Service
public class JaxbParseServiceImpl implements JaxbParseService {

    @SuppressWarnings("unchecked")
    @Override
    public Epaper parseFromFile(final ByteArrayInputStream inputStream) throws JAXBException, IOException {
        final JAXBContext jaxbContext = JAXBContext.newInstance(Epaper.class);
        final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        final JAXBElement<Epaper> jaxbElement =
                (JAXBElement<Epaper>) unmarshaller.unmarshal(inputStream);
        return jaxbElement.getValue();
    }
}
