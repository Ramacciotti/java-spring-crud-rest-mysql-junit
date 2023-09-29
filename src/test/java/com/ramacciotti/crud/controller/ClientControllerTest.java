package com.ramacciotti.crud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramacciotti.crud.dto.ClientDTO;
import com.ramacciotti.crud.repository.ClientRepository;
import com.ramacciotti.crud.service.ClientServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

    private final ClassLoader classLoader = getClass().getClassLoader();

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ClientServiceImpl clientService;

    @MockBean
    ClientRepository clientRepository;

    @InjectMocks
    ClientController clientController;

    @Test
    void postClient() throws Exception {

        String userRequest = getJsonFileAsString("clientDTO.json");
        ClientDTO clientDTO = getJsonFileAsObject("clientDTO.json", ClientDTO.class);

        when(clientService.createClient(any())).thenReturn(clientDTO);

        var response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/client")
                        .content(userRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        var actualResponse = mapper.readValue(response.getResponse().getContentAsString(), ClientDTO.class);

        Assertions.assertEquals("Mariana Ramacciotti", actualResponse.getName());
        Assertions.assertEquals("11111111111", actualResponse.getCpf());
        Assertions.assertEquals(11L, actualResponse.getAge());
        Assertions.assertEquals("11111111111", actualResponse.getPhone());

    }

    @Test
    void getClientByCpf() throws Exception {

        ClientDTO clientDTO = getJsonFileAsObject("clientDTO.json", ClientDTO.class);
        when(clientService.getClientByCpf("11111111111")).thenReturn(clientDTO);

        var response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/client/11111111111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var actualResponse = mapper.readValue(response.getResponse().getContentAsString(), ClientDTO.class);

        Assertions.assertEquals("Mariana Ramacciotti", actualResponse.getName());
        Assertions.assertEquals("11111111111", actualResponse.getCpf());
        Assertions.assertEquals(11L, actualResponse.getAge());
        Assertions.assertEquals("11111111111", actualResponse.getPhone());

    }

    @Test
    void updateClientByCpf() throws Exception {

        String userRequest = getJsonFileAsString("request_to_update_client.json");

        ClientDTO clientDTO = getJsonFileAsObject("response_updated_client.json", ClientDTO.class);
        when(clientService.putClientByCpf(any(), any())).thenReturn(clientDTO);

        var response = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/client/" + "11111111111")
                        .content(userRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var actualResponse = mapper.readValue(response.getResponse().getContentAsString(), ClientDTO.class);

        Assertions.assertEquals("Mariana Ramacciotti", actualResponse.getName());
        Assertions.assertEquals("11111111111", actualResponse.getCpf());
        Assertions.assertEquals(22L, actualResponse.getAge());
        Assertions.assertEquals("11111111111", actualResponse.getPhone());

    }

    @Test
    void deleteClientByCpf() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/client/11111111111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

    }


    private String getJsonFileAsString(String filename) throws IOException {
        File file = new File(((URL) Objects.requireNonNull(classLoader.getResource(filename))).getFile());
        return new String(Files.readAllBytes(file.toPath()));
    }

    private <T> T getJsonFileAsObject(String filename, Class<T> typeKey) throws IOException {
        var json = getJsonFileAsString(filename);
        return mapper.readValue(json, typeKey);
    }

}