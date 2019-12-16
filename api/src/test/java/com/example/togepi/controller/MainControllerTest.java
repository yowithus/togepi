package com.example.togepi.controller;

import com.example.togepi.service.MainService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
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
