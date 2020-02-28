package br.com.dbserver.bancoapi.service;

import br.com.dbserver.bancoapi.BeforeTest;
import br.com.dbserver.bancoapi.model.Conta;
import br.com.dbserver.bancoapi.model.LogTransacao;
import br.com.dbserver.bancoapi.repository.LogTransacaoRepository;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;

public class LogTransacaoServiceTest extends BeforeTest {

    @InjectMocks
    LogTransacaoService logTransacaoService;

    @Mock
    LogTransacaoRepository logTransacaoRepository;

}
