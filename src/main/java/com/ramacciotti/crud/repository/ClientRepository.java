package com.ramacciotti.crud.repository;

import com.ramacciotti.crud.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findClientByCpf(Long cpf);

    void deleteClientByCpf(Long cpf);

}
