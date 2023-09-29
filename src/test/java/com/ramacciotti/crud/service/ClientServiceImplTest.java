package com.ramacciotti.crud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramacciotti.crud.dto.ClientDTO;
import com.ramacciotti.crud.exception.ItemNotFoundException;
import com.ramacciotti.crud.exception.NullFieldException;
import com.ramacciotti.crud.model.Client;
import com.ramacciotti.crud.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ClientServiceImplTest {

    private final ClassLoader classLoader = getClass().getClassLoader();

    @Autowired
    ObjectMapper mapper;

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientServiceImpl clientService;

    @Test
    void createClient() throws Exception {

        Client repositoryResponse = getJsonFileAsObject("client.json", Client.class);
        when(clientRepository.findClientByCpf(any())).thenReturn(null);
        when(clientRepository.save(any())).thenReturn(repositoryResponse);

        ClientDTO userRequest = getJsonFileAsObject("clientDTO.json", ClientDTO.class);
        ClientDTO actualResponse = clientService.createClient(userRequest);

        verify(clientRepository, times(1)).save(any());

        Assertions.assertEquals("Mariana Ramacciotti", actualResponse.getName());
        Assertions.assertEquals(11L, actualResponse.getAge());

    }

    @Test
    void createClientDuplicatedError() throws Exception {

        Client repositoryResponse = getJsonFileAsObject("client.json", Client.class);
        when(clientRepository.findClientByCpf(any())).thenReturn(repositoryResponse);

        ClientDTO userRequest = getJsonFileAsObject("clientDTO.json", ClientDTO.class);

        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> {
            clientService.createClient(userRequest);
        });

        Assertions.assertEquals(runtimeException.getMessage(), "client_already_exists");

    }

    @Test
    void createClientSavingError() throws Exception {

        ClientDTO userRequest = getJsonFileAsObject("clientDTO.json", ClientDTO.class);

        when(clientRepository.findClientByCpf(any())).thenReturn(null);

        doThrow(new RuntimeException("exception")).when(clientRepository).save(any());

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            clientService.createClient(userRequest);
        });

        Assertions.assertEquals(exception.getMessage(), "exception");

    }

    @Test
    void createClientNullNameError() throws Exception {

        ClientDTO userRequest = getJsonFileAsObject("clientDTO.json", ClientDTO.class);
        userRequest.setName(null);

        NullFieldException exception = Assertions.assertThrows(NullFieldException.class, () -> {
            clientService.createClient(userRequest);
        });

        Assertions.assertEquals(exception.getMessage(), "name_must_not_be_null");

    }

    @Test
    void createClientNullCpfError() throws Exception {

        ClientDTO userRequest = getJsonFileAsObject("clientDTO.json", ClientDTO.class);
        userRequest.setCpf(null);

        NullFieldException exception = Assertions.assertThrows(NullFieldException.class, () -> {
            clientService.createClient(userRequest);
        });

        Assertions.assertEquals(exception.getMessage(), "cpf_must_not_be_null");

    }

    @Test
    void createClientNullAgeError() throws Exception {

        ClientDTO userRequest = getJsonFileAsObject("clientDTO.json", ClientDTO.class);
        userRequest.setAge(null);

        NullFieldException exception = Assertions.assertThrows(NullFieldException.class, () -> {
            clientService.createClient(userRequest);
        });

        Assertions.assertEquals(exception.getMessage(), "age_must_not_be_null");

    }

    @Test
    void createClientNullPhoneError() throws Exception {

        ClientDTO userRequest = getJsonFileAsObject("clientDTO.json", ClientDTO.class);
        userRequest.setPhone(null);

        NullFieldException exception = Assertions.assertThrows(NullFieldException.class, () -> {
            clientService.createClient(userRequest);
        });

        Assertions.assertEquals(exception.getMessage(), "phone_must_not_be_null");

    }


    @Test
    void getClientByCpf() throws IOException {

        Client client = getJsonFileAsObject("client.json", Client.class);
        when(clientRepository.findClientByCpf(11111111111L)).thenReturn(client);

        ClientDTO clientDTO = clientService.getClientByCpf(11111111111L);

        verify(clientRepository, times(1)).findClientByCpf(11111111111L);

        Assertions.assertEquals(11111111111L, clientDTO.getCpf());
        Assertions.assertEquals("Mariana Ramacciotti", clientDTO.getName());
        Assertions.assertEquals(11L, clientDTO.getAge());
        Assertions.assertEquals(11111111111L, clientDTO.getPhone());

    }

    @Test
    void getClientByCpfNotFound() {

        when(clientRepository.findClientByCpf(11111111111L)).thenReturn(null);

        ItemNotFoundException runtimeException = Assertions.assertThrows(ItemNotFoundException.class, () -> {
            clientService.getClientByCpf(11111111111L);
        });

        Assertions.assertEquals(runtimeException.getMessage(), "client_not_found");

    }


    @Test
    void putClientByCpf() throws IOException {

        ClientDTO userRequest = getJsonFileAsObject("clientDTO.json", ClientDTO.class);

        Client repositoryResponse = getJsonFileAsObject("client.json", Client.class);
        when(clientRepository.findClientByCpf(11111111111L)).thenReturn(repositoryResponse);

        ClientDTO actualResponse = clientService.putClientByCpf(userRequest, 11111111111L);

        verify(clientRepository, times(1)).findClientByCpf(11111111111L);
        verify(clientRepository, times(1)).save(any());

        Assertions.assertEquals("Mariana Ramacciotti", actualResponse.getName());
        Assertions.assertEquals(11L, actualResponse.getAge());

    }

    @Test
    void putClientByCpfNotFound() throws IOException {

        ClientDTO userRequest = getJsonFileAsObject("clientDTO.json", ClientDTO.class);

        when(clientRepository.findClientByCpf(11111111111L)).thenReturn(null);

        ItemNotFoundException runtimeException = Assertions.assertThrows(ItemNotFoundException.class, () -> {
            clientService.putClientByCpf(userRequest, 11111111111L);
        });

        Assertions.assertEquals(runtimeException.getMessage(), "client_not_found");

    }

    @Test
    void putClientByCpfUpdatingError() throws IOException {

        ClientDTO userRequest = getJsonFileAsObject("clientDTO.json", ClientDTO.class);

        Client repositoryResponse = getJsonFileAsObject("client.json", Client.class);
        when(clientRepository.findClientByCpf(11111111111L)).thenReturn(repositoryResponse);

        doThrow(new RuntimeException("exception")).when(clientRepository).save(any());

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            clientService.putClientByCpf(userRequest, 11111111111L);
        });

        Assertions.assertEquals(exception.getMessage(), "exception");

    }


    @Test
    void deleteClientByCpf() throws IOException {

        Client repositoryResponse = getJsonFileAsObject("client.json", Client.class);
        when(clientRepository.findClientByCpf(11111111111L)).thenReturn(repositoryResponse);

        clientService.deleteClientByCpf(11111111111L);

        verify(clientRepository, times(1)).findClientByCpf(11111111111L);
        verify(clientRepository, times(1)).deleteClientByCpf(11111111111L);

    }

    @Test
    void deleteClientByCpfNotFound() {

        when(clientRepository.findClientByCpf(11111111111L)).thenReturn(null);

        ItemNotFoundException runtimeException = Assertions.assertThrows(ItemNotFoundException.class, () -> {
            clientService.deleteClientByCpf(11111111111L);
        });

        Assertions.assertEquals(runtimeException.getMessage(), "client_not_found");

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