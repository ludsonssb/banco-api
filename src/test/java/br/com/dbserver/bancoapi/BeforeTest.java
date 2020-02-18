package br.com.dbserver.bancoapi;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

public class BeforeTest {

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
}
