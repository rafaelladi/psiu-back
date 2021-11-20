package com.dietrich.psiu.controller;

import com.dietrich.psiu.model.atendimento.Atendimento;
import com.dietrich.psiu.model.atendimento.StatusAtendimento;
import com.dietrich.psiu.repository.atendimento.AtendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@RepositoryRestController
@ResponseBody
public class AtendimentoController {
    private final AtendimentoRepository atendimentoRepository;

    @Autowired
    public AtendimentoController(AtendimentoRepository atendimentoRepository) {
        this.atendimentoRepository = atendimentoRepository;
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/atendimentos")
    public ResponseEntity<?> save() {
        Atendimento atendimento = new Atendimento();
        atendimento.setNote("test");
        atendimento.setCreationDateTime(new Date());
        atendimento.setScheduledDate(new Date());
        atendimento.setStatus(StatusAtendimento.REQUESTED);
        return ResponseEntity.ok(atendimentoRepository.save(atendimento));
    }
}
