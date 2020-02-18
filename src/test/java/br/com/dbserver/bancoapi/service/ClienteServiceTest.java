package br.com.dbserver.bancoapi.service;

import br.com.dbserver.bancoapi.BeforeTest;
import br.com.dbserver.bancoapi.controller.dto.AlteraClienteDTO;
import br.com.dbserver.bancoapi.controller.dto.ClienteResponse;
import br.com.dbserver.bancoapi.exceptions.ClienteNaoEncontradoException;
import br.com.dbserver.bancoapi.model.Cliente;
import br.com.dbserver.bancoapi.repository.ClienteRepository;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;


public class ClienteServiceTest extends BeforeTest {

    @InjectMocks
    ClienteService clienteService;

    @Mock
    ClienteRepository clienteRepository;

    @Mock
    @Autowired
    AlteraClienteDTO alteraClienteDTO;

    private static final long UM = 1;
    private static final long SEIS = 6;

    @Test(expected = ClienteNaoEncontradoException.class)
    public void buscaPorIdClienteNull()  {
        clienteService.buscarClientePeloCodigo(null);
    }

    @Test
    public void buscaPorIdClientePreenchido(){
        Cliente cliente = new Cliente();
        Mockito.when(clienteRepository.findById(UM)).thenReturn(Optional.of(cliente));
        ClienteResponse result = clienteService.buscarClientePeloCodigo(UM);
        assertNotNull(result);
        assertNotSame(cliente, result);
        assertEquals(cliente.getId(), result.getId());
    }

    @Test
    public void atualizaCliente() throws InterruptedException {
        Cliente cliente = new Cliente();
        Mockito.when(clienteRepository.findById(SEIS)).thenReturn(Optional.of(cliente));
    }
}
