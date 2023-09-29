package com.ramacciotti.crud.service;

import com.ramacciotti.crud.dto.ClientDTO;

public interface ClientService {

    ClientDTO createClient(ClientDTO clientDTO);

    ClientDTO getClientByCpf(String cpf);

    ClientDTO putClientByCpf(ClientDTO clientDTO, String cpf);

    void deleteClientByCpf(String cpf);
}
