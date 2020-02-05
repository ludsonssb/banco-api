package br.com.dbserver.bancoapi.controller;

import br.com.dbserver.bancoapi.model.LogTransacao;
import br.com.dbserver.bancoapi.service.LogTransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("logTransacao")
public class LogTransacaoController {

    @Autowired
    private LogTransacaoService logTransacaoService;

    @GetMapping("/log")
    List<LogTransacao> logs(@RequestParam String dataDe, @RequestParam String dataAte, @RequestParam long conta){
        return logTransacaoService.recuperaLog(dataDe,dataAte,conta);
    }
}
