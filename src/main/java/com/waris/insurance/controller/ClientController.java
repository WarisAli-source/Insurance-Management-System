package com.waris.insurance.controller;

import com.waris.insurance.dao.ClientDao;
import com.waris.insurance.exception.NoSuchClientExistException;
import com.waris.insurance.service.impl.ClientServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientServiceImpl clientService;

    @PostMapping("/clients")
    public ResponseEntity<List<ClientDao>> addClientDetail(@RequestBody List<ClientDao> clientDaos) {

        List<ClientDao> contactDao = clientService.saveClients(clientDaos);
        log.info("Contact Added");
        return new ResponseEntity<>(contactDao, HttpStatus.CREATED);
    }

    @GetMapping("/clients")
    public ResponseEntity<List<ClientDao>> getClientDetails() {
        log.info("Contact Fetching");
        List<ClientDao> parts = clientService.getClients();
        if (parts.isEmpty()) {
            throw new NoSuchClientExistException("Contacts Not Found");
        }
        return new ResponseEntity<>(parts, HttpStatus.OK);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<ClientDao> getClientDetailsById(@PathVariable String id) {

        ClientDao clientDaoList = clientService.getClientsById(id);
        if (clientDaoList == null) {
            throw new NoSuchClientExistException("Client Not Found");
        }
        return new ResponseEntity<>(clientDaoList, HttpStatus.OK);
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<ClientDao> updateClientDetails(@PathVariable String id, @RequestBody ClientDao clientDao) {
        ClientDao clientDaoList = clientService.updateClientById(id, clientDao);
        if (clientDaoList == null) {
            throw new NoSuchClientExistException("Client Not Found");
        }
        return new ResponseEntity<>(clientDaoList, HttpStatus.OK);
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Void> deleteClientDetails(@PathVariable String id) {
        clientService.deleteClientById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
