package com.waris.insurance.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.waris.insurance.dao.ClientDao;
import com.waris.insurance.entity.Client;
import com.waris.insurance.exception.NoSuchClientExistException;
import com.waris.insurance.repository.ClientRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ClientServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ClientServiceImplTest {
    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    private ClientServiceImpl clientServiceImpl;

    @Test
    void testSaveClients() {
        assertThrows(NoSuchClientExistException.class, () -> clientServiceImpl.saveClients(new ArrayList<>()));
    }


    @Test
    void testSaveClients2() {
        when(clientRepository.saveAll((Iterable<Client>) any())).thenReturn(new ArrayList<>());

        ArrayList<ClientDao> clientDaoList = new ArrayList<>();
        clientDaoList.add(new ClientDao());
        List<ClientDao> actualSaveClientsResult = clientServiceImpl.saveClients(clientDaoList);
        assertSame(clientDaoList, actualSaveClientsResult);
        assertEquals(1, actualSaveClientsResult.size());
        verify(clientRepository).saveAll((Iterable<Client>) any());
    }




    @Test
    void testGetClients() {
        when(clientRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(NoSuchClientExistException.class, () -> clientServiceImpl.getClients());
        verify(clientRepository).findAll();
    }

    @Test
    void testGetClients2() {
        Client client = new Client();
        client.setAddress("42 Main St");
        client.setContactInformation("Getting Clients");
        client.setDateOfBirth(LocalDate.ofEpochDay(1L));
        client.setId("42");
        client.setName("Getting Clients");

        ArrayList<Client> clientList = new ArrayList<>();
        clientList.add(client);
        when(clientRepository.findAll()).thenReturn(clientList);
        List<ClientDao> actualClients = clientServiceImpl.getClients();
        assertEquals(1, actualClients.size());
        ClientDao getResult = actualClients.get(0);
        assertEquals("42 Main St", getResult.getAddress());
        assertEquals("Getting Clients", getResult.getName());
        assertEquals("42", getResult.getId());
        assertEquals("1970-01-02", getResult.getDateOfBirth().toString());
        assertEquals("Getting Clients", getResult.getContactInformation());
        verify(clientRepository).findAll();
    }



    @Test
    void testGetClientsById() {
        Client client = new Client();
        client.setAddress("42 Main St");
        client.setContactInformation("Contact Information");
        client.setDateOfBirth(LocalDate.ofEpochDay(1L));
        client.setId("42");
        client.setName("Name");
        Optional<Client> ofResult = Optional.of(client);
        when(clientRepository.findById((String) any())).thenReturn(ofResult);
        ClientDao actualClientsById = clientServiceImpl.getClientsById("42");
        assertEquals("42 Main St", actualClientsById.getAddress());
        assertEquals("Name", actualClientsById.getName());
        assertEquals("42", actualClientsById.getId());
        assertEquals("1970-01-02", actualClientsById.getDateOfBirth().toString());
        assertEquals("Contact Information", actualClientsById.getContactInformation());
        verify(clientRepository).findById((String) any());
    }

    @Test
    void testGetClientsById2() {
        when(clientRepository.findById((String) any())).thenReturn(Optional.empty());
        assertThrows(NoSuchClientExistException.class, () -> clientServiceImpl.getClientsById("42"));
        verify(clientRepository).findById((String) any());
    }



    @Test
    void testUpdateClientById() {
        Client client = new Client();
        client.setAddress("42 Main St");
        client.setContactInformation("Contact Information");
        client.setDateOfBirth(LocalDate.ofEpochDay(1L));
        client.setId("42");
        client.setName("Name");
        Optional<Client> ofResult = Optional.of(client);

        Client client1 = new Client();
        client1.setAddress("42 Main St");
        client1.setContactInformation("Contact Information");
        client1.setDateOfBirth(LocalDate.ofEpochDay(1L));
        client1.setId("42");
        client1.setName("Name");
        when(clientRepository.save((Client) any())).thenReturn(client1);
        when(clientRepository.findById((String) any())).thenReturn(ofResult);
        ClientDao clientDao = new ClientDao();
        assertSame(clientDao, clientServiceImpl.updateClientById("42", clientDao));
        verify(clientRepository).save((Client) any());
        verify(clientRepository).findById((String) any());
    }


    @Test
    void testUpdateClientById2() {
        Client client = new Client();
        client.setAddress("42 Main St");
        client.setContactInformation("Contact Information");
        client.setDateOfBirth(LocalDate.ofEpochDay(1L));
        client.setId("42");
        client.setName("Name");
        Optional<Client> ofResult = Optional.of(client);
        when(clientRepository.save((Client) any())).thenThrow(new NoSuchClientExistException("Msg"));
        when(clientRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(NoSuchClientExistException.class, () -> clientServiceImpl.updateClientById("42", new ClientDao()));
        verify(clientRepository).save((Client) any());
        verify(clientRepository).findById((String) any());
    }


    @Test
    void testDeleteClientById() {
        Client client = new Client();
        client.setAddress("42 Main St");
        client.setContactInformation("Contact Information");
        client.setDateOfBirth(LocalDate.ofEpochDay(1L));
        client.setId("42");
        client.setName("Name");
        Optional<Client> ofResult = Optional.of(client);
        doNothing().when(clientRepository).deleteById((String) any());
        when(clientRepository.findById((String) any())).thenReturn(ofResult);
        clientServiceImpl.deleteClientById("42");
        verify(clientRepository).findById((String) any());
        verify(clientRepository).deleteById((String) any());
    }


    @Test
    void testDeleteClientById2() {
        Client client = new Client();
        client.setAddress("42 Main St");
        client.setContactInformation("Contact Information");
        client.setDateOfBirth(LocalDate.ofEpochDay(1L));
        client.setId("42");
        client.setName("Name");
        Optional<Client> ofResult = Optional.of(client);
        doThrow(new NoSuchClientExistException("Msg")).when(clientRepository).deleteById((String) any());
        when(clientRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(NoSuchClientExistException.class, () -> clientServiceImpl.deleteClientById("42"));
        verify(clientRepository).findById((String) any());
        verify(clientRepository).deleteById((String) any());
    }


}

