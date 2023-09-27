package com.kiyak.eindopdracht_backend_kiyak.service;

import com.kiyak.eindopdracht_backend_kiyak.EindopdrachtBackendKiyakApplication;
import com.kiyak.eindopdracht_backend_kiyak.domain.Demo;
import com.kiyak.eindopdracht_backend_kiyak.domain.User;
import com.kiyak.eindopdracht_backend_kiyak.repository.DemoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;


@SpringBootTest
@ContextConfiguration(classes = EindopdrachtBackendKiyakApplication.class)
public class DemoServiceImplTest {

    @InjectMocks
    private DemoServiceImpl demoService;

    @Mock
    private DemoRepository demoRepository;
    Demo demo;

    @Test
    public void getDemoByIdShouldReturnDemo1() {
        User user = new User();
        Demo d = new Demo();
        d.setId(1L);

        Demo demo1 = new Demo(1L, "name", "message", "", 1L, "message");
        Mockito
                .when(demoRepository.existsById(anyLong()))
                .thenReturn(true);
        Mockito
                .when(demoRepository.findById(anyLong()))
                .thenReturn(Optional.of(d));

        Demo demoThroughService = demoService.getFileById(1);

        assertEquals(1L, demoThroughService.getId());
    }

    @Test
    void updateDemoShouldReturnOK() {
        User user = new User();
        Demo d = new Demo();
        d.setId(1L);

        Demo demo1 = new Demo(1L, "name", "message", "demo", 1L, "feedback");
        Mockito
                .when(demoRepository.existsById(anyLong()))
                .thenReturn(true);
        Mockito
                .when(demoRepository.findById(anyLong()))
                .thenReturn(Optional.of(d));

        ResponseEntity demoThroughService = demoService.updateFile( 1 ,"this is the feedback now");

        assertEquals(HttpStatus.OK, demoThroughService.getStatusCode());
    }
}

