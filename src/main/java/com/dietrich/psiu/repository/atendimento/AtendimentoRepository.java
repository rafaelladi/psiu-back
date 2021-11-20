package com.dietrich.psiu.repository.atendimento;

import com.dietrich.psiu.excerpt.atendimento.AtendimentoProjection;
import com.dietrich.psiu.model.atendimento.Atendimento;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "atendimentos", path = "atendimentos", excerptProjection = AtendimentoProjection.class)
public interface AtendimentoRepository extends PagingAndSortingRepository<Atendimento, Long> {
    @Override
    @RestResource
    @Query("select a from Atendimento a where a.id = :id and ((?#{hasAuthority('USER')} = true and a.user.id = ?#{authentication.principal.person.id}) or " +
            "(?#{hasAuthority('VOLUNTEER')} = true and a.volunteer.id = ?#{authentication.principal.person.id}) or " +
            "(?#{hasAuthority('ADMIN')} = true and a.project.organization.id = ?#{authentication.principal.person.organization.id}) or " +
            "?#{hasAuthority('SUPER_ADMIN')} = true)")
    Optional<Atendimento> findById(@Param("id") Long id);

    @Override
    @RestResource
    @Query("select a from Atendimento a where (?#{hasAuthority('USER')} = true and a.user.id = ?#{authentication.principal.person.id}) or " +
            "(?#{hasAuthority('VOLUNTEER')} = true and a.volunteer.id = ?#{authentication.principal.person.id}) or " +
            "(?#{hasAuthority('ADMIN')} = true and a.project.organization.id = ?#{authentication.principal.person.organization.id}) or " +
            "?#{hasAuthority('SUPER_ADMIN')} = true")
    Page<Atendimento> findAll(Pageable pageable);
}
