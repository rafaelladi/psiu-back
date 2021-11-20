package com.dietrich.psiu.repository.schedule;

import com.dietrich.psiu.excerpt.schedule.ScheduleProjection;
import com.dietrich.psiu.model.schedule.Period;
import com.dietrich.psiu.model.schedule.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "schedules", path = "schedules", excerptProjection = ScheduleProjection.class)
public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, Long> {
    @Override
    @RestResource
    @Query("select s from Schedule s where (?#{hasAuthority('VOLUNTEER')} = true and s.id = ?#{authentication.principal.person.schedule.id}) or " +
            "(?#{hasAuthority('ADMIN')} = true and s.volunteer.organization.id = ?#{authentication.principal.person.organization.id}) or " +
            "?#{hasAuthority('SUPER_ADMIN')} = true")
    Page<Schedule> findAll(Pageable pageable);

    @Override
    @RestResource
    @Query("select s from Schedule s where s.id = :id and ((?#{hasAuthority('VOLUNTEER')} = true and s.id = ?#{authentication.principal.person.schedule.id}) or " +
            "(?#{hasAuthority('ADMIN')} = true and s.volunteer.organization.id = ?#{authentication.principal.person.organization.id}) or " +
            "?#{hasAuthority('SUPER_ADMIN')} = true)")
    Optional<Schedule> findById(Long id);
}
