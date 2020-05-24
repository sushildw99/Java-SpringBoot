package com.sushil.numgen.controller;


import com.sushil.numgen.model.NumGenRequest;
import com.sushil.numgen.model.ResponseResult;
import com.sushil.numgen.service.NumgenService;
import com.sushil.numgen.util.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(NumgenController.class)
public class NumgenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NumgenService numgenService;

    @Test
    public void testGenerarteNumber() throws Exception {
        String response = "{\"Goal\":\"10\", \"Step\": \"2\"}";
        UUID uuid = UUID.randomUUID();
        //UUID uuid = spy(UUID.class);
        //when(uuid.toString()).thenReturn("911a47a6-722c-4666-9b94-90c2c6a39169");
        when(numgenService.generateNumbers(any(NumGenRequest.class))).thenReturn(uuid);

        mockMvc.perform(post("/api/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(response))
                .andExpect(status().isAccepted());

        verify(numgenService, times(1)).generateNumbers(any(NumGenRequest.class));
    }


    @Test
    public void testGetStatus() throws Exception {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setResult(Status.SUCCESS.toString());
        mockMvc.perform(get("/api/tasks/911a47a6-722c-4666-9b94-90c2c6a39169/status/"))
                .andExpect(status().isOk());

        verify(numgenService, times(1)).getStatus(anyString());
    }


    @Test
    public void testGetNumList() throws Exception {
        mockMvc.perform(get("/api/tasks/911a47a6-722c-4666-9b94-90c2c6a39169?action=get_numlist"))
                .andExpect(status().isOk());
        verify(numgenService, times(1)).getNumList(anyString());
    }

}
