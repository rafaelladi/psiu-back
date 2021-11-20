package com.dietrich.psiu.repository.schedule;

import com.dietrich.psiu.excerpt.schedule.PeriodProjection;
import com.dietrich.psiu.model.organization.Project;
import com.dietrich.psiu.model.schedule.Period;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "periods", path = "periods", excerptProjection = PeriodProjection.class)
public interface PeriodRepository extends PagingAndSortingRepository<Period, Long> {
    @Override
    @RestResource
    @Query("select p from Period p where (?#{hasAuthority('VOLUNTEER')} = true and p.schedule.id = ?#{authentication.principal.person.schedule.id}) or " +
            "(?#{hasAuthority('ADMIN')} = true and p.schedule.volunteer.organization.id = ?#{authentication.principal.person.organization.id}) or " +
            "?#{hasAuthority('SUPER_ADMIN')} = true")
    Page<Period> findAll(Pageable pageable);

    @Override
    @RestResource
    @Query("select p from Period p where p.id = :id and ((?#{hasAuthority('VOLUNTEER')} = true and p.schedule.id = ?#{authentication.principal.person.schedule.id}) or " +
            "(?#{hasAuthority('ADMIN')} = true and p.schedule.volunteer.organization.id = ?#{authentication.principal.person.organization.id}) or " +
            "?#{hasAuthority('SUPER_ADMIN')} = true)")
    Optional<Period> findById(Long id);
}
