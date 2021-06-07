package com.example.togepi.controller;

import com.example.togepi.service.MainService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MainController.class)
@AutoConfigureMockMvc
@EnableWebMvc
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @Qualifier("defaultMainService")
    private MainService defaultMainService;

    @MockBean
    @Qualifier("secondMainService")
    private MainService secondMainService;

    @Test
    public void whenCallingHello_thenReturnSuccess() throws Exception {
        when(defaultMainService.hello()).thenReturn("asd");
        this.mockMvc.perform(get("/hello")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenCallingHello2_thenReturnSuccess() throws Exception {
        when(secondMainService.hello()).thenReturn("asd");
        this.mockMvc.perform(get("/hello2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
