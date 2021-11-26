package com.dietrich.psiu.repository.atendimento;

import com.dietrich.psiu.model.atendimento.Atendimento;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtendimentoRepository extends PagingAndSortingRepository<Atendimento, Long> {

}
