package com.ramacciotti.crud.utils.mapper;

import com.ramacciotti.crud.dto.ClientDTO;
import com.ramacciotti.crud.model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientMapper {

    public static List<ClientDTO> convertToClientDTO(List<Client> clientList) {

        List<ClientDTO> clientDTOList = new ArrayList<>();

        for (Client client : clientList) {

            ClientDTO clientDTO = ClientDTO.builder()
                    .name(client.getName())
                    .cpf(client.getCpf())
                    .age(client.getAge())
                    .phone(client.getPhone())
                    .build();

            clientDTOList.add(clientDTO);

        }

        return clientDTOList;

    }

    public static ClientDTO convertToClientDTO(Client client) {

        return ClientDTO.builder()
                .name(client.getName())
                .cpf(client.getCpf())
                .age(client.getAge())
                .phone(client.getPhone())
                .build();

    }
    
    public static Client convertToClient(ClientDTO clientDTO) {

        return Client.builder()
                .name(clientDTO.getName())
                .cpf(clientDTO.getCpf())
                .age(clientDTO.getAge())
                .phone(clientDTO.getPhone())
                .build();

    }

}
