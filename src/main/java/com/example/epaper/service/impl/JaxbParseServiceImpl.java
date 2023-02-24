package com.example.epaper.service.impl;

import com.example.epaper.exception.InvalidRequestBodyException;
import com.example.epaper.service.JaxbParseService;
import com.example.epaper.xsd.Epaper;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * {@inheritDoc}
 */
@Service
public class JaxbParseServiceImpl implements JaxbParseService {

    private static final String XSD_PATH = "/xsd/epaper.xsd";

    /**
     * {@inheritDoc}
     */
    @Override
    public Epaper parseFromFile(final ByteArrayInputStream inputStream) throws JAXBException {
        final JAXBContext jaxbContext = JAXBContext.newInstance(Epaper.class);
        final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        final JAXBElement<Epaper> jaxbElement = unmarshaller.unmarshal(new StreamSource(inputStream), Epaper.class);
        return jaxbElement.getValue();
    }

    /**
     * {@inheritDoc}
     */
    public void validate(final ByteArrayInputStream inputStream) {
        try {
            final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            final Schema schema = factory.newSchema(getClass().getResource(XSD_PATH));
            final Validator validator = schema.newValidator();
            validator.validate(new SAXSource(new InputSource(inputStream)));
        } catch (SAXException | IOException e) {
            throw new InvalidRequestBodyException("File is not valid against xsd schema", e);
        }
    }

}
