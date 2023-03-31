package com.waris.insurance.service;

import com.waris.insurance.dao.ClientDao;

import java.util.List;

public interface ClientService {
    public List<ClientDao> saveClients(List<ClientDao> clientDaoList);
    public List<ClientDao> getClients();
    public ClientDao getClientsById(String id);
    public ClientDao updateClientById(String id, ClientDao clientDao);
    public void deleteClientById(String id);
}
