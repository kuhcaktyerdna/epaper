package com.example.epaper.controller;

import com.example.epaper.model.Epaper;
import com.example.epaper.service.JaxbParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private final JaxbParseService jaxbParseService;

    @Autowired
    public DeviceController(JaxbParseService jaxbParseService) {
        this.jaxbParseService = jaxbParseService;
    }

    @PostMapping(consumes = {"multipart/form-data; charset=UTF-8"}, produces = {"application/xml; charset=UTF-8"})
    public Epaper save(@RequestBody final MultipartFile file) throws IOException, JAXBException {
        final Epaper epaperRequest = jaxbParseService.parseFromFile(new ByteArrayInputStream(file.getBytes()));

        return null;
    }

}
