package com.ramacciotti.crud.service;

import com.ramacciotti.crud.dto.ClientDTO;

public interface ClientService {

    ClientDTO createClient(ClientDTO clientDTO);

    ClientDTO getClientByCpf(Long cpf);

    ClientDTO putClientByCpf(ClientDTO clientDTO, Long cpf);

    void deleteClientByCpf(Long cpf);
}
