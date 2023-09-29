package com.ramacciotti.crud.controller;

import com.ramacciotti.crud.dto.ClientDTO;
import com.ramacciotti.crud.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/v1")
@Tag(name = "Client Controller")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/client")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a new client")
    public ClientDTO postClient(@RequestBody ClientDTO clientDTO) {
        log.info(">> postClient()");
        ClientDTO result = clientService.createClient(clientDTO);
        log.info("<< postClient()");
        return result;
    }

    @GetMapping(path = "/client/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Uses cpf to locate an specific client at the database")
    public ClientDTO getClientByCpf(@PathVariable("cpf") String cpf) {
        log.info(">> getClientByCpf()");
        ClientDTO result = clientService.getClientByCpf(cpf);
        log.info("<< getClientByCpf()");
        return result;
    }

    @PutMapping(path = "/client/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Uses cpf to update an specific client at the database")
    public ClientDTO updateClientByCpf(@Valid @RequestBody ClientDTO clientDTO, @PathVariable("cpf") String cpf) {
        log.info(">> getClientByCpf()");
        ClientDTO result = clientService.putClientByCpf(clientDTO, cpf);
        log.info("<< getClientByCpf()");
        return result;
    }

    @DeleteMapping(path = "/client/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Uses an cpf to delete an specific client at the database")
    public void deleteClientByCpf(@PathVariable("cpf") String cpf) {
        log.info(">> deleteClientByCpf()");
        clientService.deleteClientByCpf(cpf);
        log.info("<< deleteClientByCpf()");
    }

}
