package com.waris.insurance.service.impl;

import com.waris.insurance.dao.ClientDao;
import com.waris.insurance.entity.Client;
import com.waris.insurance.exception.NoSuchClientExistException;
import com.waris.insurance.repository.ClientRepository;
import com.waris.insurance.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<ClientDao> saveClients(List<ClientDao> clientDao) {
        log.info("Adding Clients");
        if (clientDao.isEmpty()) {throw new NoSuchClientExistException("No Client Available");
        }
        List<Client> clients = new ArrayList<Client>();
        for (ClientDao clientDaos : clientDao) {
            Client client = new Client();
            BeanUtils.copyProperties(clientDaos, client);
            clients.add(client);
        }
        clientRepository.saveAll(clients);
        return clientDao;
    }

    public List<ClientDao> getClients() {
        log.info("Getting Clients");
        List<Client> contacts = clientRepository.findAll();
        if (contacts.isEmpty()) { throw new NoSuchClientExistException("Clients not Found :" + contacts);
        }
        else {
            List<ClientDao> clientDaos = new ArrayList<ClientDao>();
            for (Client client : contacts) {
                ClientDao contactDao = new ClientDao();
                BeanUtils.copyProperties(client, contactDao);
                clientDaos.add(contactDao);
            }
            return clientDaos;
        }

    }


    public ClientDao getClientsById(String id) {
            Client client = clientRepository.findById(id).orElseThrow(() -> new NoSuchClientExistException("No Client Found for ID:" + id));
            log.info("Client Fetched By id:" + id);
            ClientDao clientDao = new ClientDao();
            BeanUtils.copyProperties(client, clientDao);
            return clientDao;
    }

    public ClientDao updateClientById(String id, ClientDao clientDao) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new NoSuchClientExistException("No Client Found for ID:" + id));
        log.info("Client Fetched By id:" + id);
        BeanUtils.copyProperties(clientDao, client);
        clientRepository.save(client);
        return clientDao;
    }

    public void deleteClientById(String id) {
        clientRepository.findById(id).orElseThrow(() -> new NoSuchClientExistException("No Client Found for ID:" + id));
        log.error("Deleting Client By ID" + id);
        clientRepository.deleteById(id);

    }
}
