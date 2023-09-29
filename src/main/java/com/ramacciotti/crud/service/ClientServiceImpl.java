package com.ramacciotti.crud.service;

import com.ramacciotti.crud.dto.ClientDTO;
import com.ramacciotti.crud.exception.ItemNotFoundException;
import com.ramacciotti.crud.exception.NullFieldException;
import com.ramacciotti.crud.model.Client;
import com.ramacciotti.crud.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ramacciotti.crud.utils.mapper.ClientMapper.convertToClient;
import static com.ramacciotti.crud.utils.mapper.ClientMapper.convertToClientDTO;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @Override
    @Transactional
    public ClientDTO createClient(ClientDTO clientDTO) {

        verifyFields(clientDTO);

        Client client = convertToClient(clientDTO);

        try {

            log.info("** Trying to save this client on database...");

            client = clientRepository.save(client);

            log.info("** Saved successfully!");

        } catch (Exception exception) {

            log.info("## Ops! There was an error: {}", exception.getMessage());

            throw new RuntimeException(exception.getMessage());

        }

        return convertToClientDTO(client);

    }

    @Override
    @Transactional
    public ClientDTO getClientByCpf(Long cpf) {

        Client client = clientRepository.findClientByCpf(cpf);

        log.info("** Trying to find this client on database...");

        if (client == null) {

            log.error("## Ops! Client not found!");

            throw new ItemNotFoundException("client_not_found");

        } else {

            log.info("** Client found!");

            return convertToClientDTO(client);

        }

    }

    @Override
    @Transactional
    public ClientDTO putClientByCpf(ClientDTO clientDTO, Long cpf) {

        log.info("** Trying to find this client on database...");

        Client client = clientRepository.findClientByCpf(cpf);

        if (client == null) {

            log.error("## Ops! Client not found!");

            throw new ItemNotFoundException("client_not_found");

        } else {

            log.info("** Client found!");

            try {

                log.info("** Trying to update this client...");

                updateFields(client, clientDTO);

                clientRepository.save(client);

                log.info("** Updated successfully!");

            } catch (Exception exception) {

                log.error("## Ops! Error: {}", exception.getMessage());

                throw new RuntimeException(exception.getMessage());

            }

            return convertToClientDTO(client);

        }

    }

    @Override
    @Transactional
    public void deleteClientByCpf(Long cpf) {

        log.info("** Trying to find this client on database...");

        Client client = clientRepository.findClientByCpf(cpf);

        if (client == null) {

            log.error("## Ops! Client not found!");

            throw new ItemNotFoundException("client_not_found");

        } else {

            log.info("** Client found!");

            log.info("** Deleting this client...");

            clientRepository.deleteByCpf(client.getCpf());

            log.info("** Client deleted!");

        }

    }


    private void verifyFields(ClientDTO clientDTO) {

        if (clientDTO.getName() == null) {
            log.error("# Ops! 'Name' must not be null!");
            throw new NullFieldException("name_must_not_be_null");
        }

        if (clientDTO.getAge() == null) {
            log.error("# Ops! 'Age' must not be null!");
            throw new NullFieldException("age_must_not_be_null");
        }

        if (clientDTO.getPhone() == null) {
            log.error("# Ops! 'Phone' must not be null!");
            throw new NullFieldException("phone_must_not_be_null");
        }

        if (clientDTO.getCpf() == null) {
            log.error("# Ops! 'Cpf' must not be null!");
            throw new NullFieldException("cpf_must_not_be_null");
        }

    }

    private void updateFields(Client client, ClientDTO clientDTO) {

        if (clientDTO.getName() != null) {
            client.setName(clientDTO.getName());
        } else {
            client.setName(client.getName());
        }

        if (clientDTO.getCpf() != null) {
            client.setCpf(clientDTO.getCpf());
        } else {
            client.setCpf(client.getCpf());
        }

        if (clientDTO.getAge() != null) {
            client.setAge(clientDTO.getAge());
        } else {
            client.setAge(client.getAge());
        }

        if (clientDTO.getPhone() != null) {
            client.setPhone(clientDTO.getPhone());
        } else {
            client.setPhone(client.getPhone());
        }

    }

}
