package com.se.bankapp.controllers;

import com.se.bankapp.services.RecommendationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecommendationController.class)
class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RecommendationService service;

    @Test
    void getRecommendations_returnsString() throws Exception {
        when(service.generateRecommendation(1L)).thenReturn("Save more, spend less!");

        mockMvc.perform(get("/recommendations")
                        .param("accountId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Save more, spend less!"));
    }
}
