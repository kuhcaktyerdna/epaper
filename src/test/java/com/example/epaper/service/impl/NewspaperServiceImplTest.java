package com.example.epaper.service.impl;

import com.example.epaper.exception.NotFoundException;
import com.example.epaper.model.Newspaper;
import com.example.epaper.repository.NewspaperRepository;
import com.example.epaper.xsd.App;
import com.example.epaper.xsd.Device;
import com.example.epaper.xsd.Epaper;
import com.example.epaper.xsd.Screen;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewspaperServiceImplTest {

    private static final String FILENAME = "file.xml";
    private static final String NEWSPAPER_NAME = "newspaperName";
    private static final Long DPI = 123L;
    private static final Long WIDTH = 345L;
    private static final Long HEIGHT = 456L;
    private static final Long ID = 1L;

    @Mock
    private NewspaperRepository newspaperRepository;
    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private NewspaperServiceImpl newspaperService;

    @Test
    void create_ShouldReturnCreatedEntity() {
        final App app = new App() {{
            setNewspaperName(NEWSPAPER_NAME);
        }};

        final Screen screen = new Screen() {{
            setDpi(DPI);
            setWidth(WIDTH);
            setHeight(HEIGHT);
        }};

        final Epaper epaper = new Epaper() {{
            setDeviceInfo(new Device() {{
                setAppInfo(app);
                setScreenInfo(screen);
            }});
        }};

        when(newspaperRepository.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);

        final Newspaper newspaper = newspaperService.create(epaper, FILENAME);
        assertEquals(DPI, newspaper.getDeviceDpi(),
                "device dpi doesn't match expected value");
        assertEquals(WIDTH, newspaper.getDeviceWidth(),
                "device width doesn't match expected value");
        assertEquals(HEIGHT, newspaper.getDeviceHeight(),
                "device height doesn't match expected value");
        assertEquals(NEWSPAPER_NAME, newspaper.getNewspaperName(),
                "newspaper name doesn't match expected value");
        assertEquals(FILENAME, newspaper.getFilename(),
                "file name doesn't match expected value");
    }

    @Test
    void getById_ShouldReturnEntityWithCorrespondingId() {
        final Newspaper expectedNewspaper = Newspaper.builder()
                .id(1L)
                .build();
        when(newspaperRepository.findById(eq(ID))).thenReturn(Optional.ofNullable(expectedNewspaper));
        final Newspaper actualNewspaper = newspaperService.getById(ID);
        assertEquals(expectedNewspaper, actualNewspaper, "newspapers doesn't match");
    }

    @Test
    void getById_ShouldThrowExceptionIfNewspaperDoesNotExist() {
        when(newspaperRepository.findById(eq(ID))).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> newspaperService.getById(ID));
    }

    @Test
    void deleteById_ShouldThrowExceptionIfNewspaperDoesNotExist() {
        doThrow(new EmptyResultDataAccessException(0)).when(newspaperRepository).deleteById(eq(ID));

        assertThrows(NotFoundException.class, () -> newspaperService.deleteById(ID));
    }

}
