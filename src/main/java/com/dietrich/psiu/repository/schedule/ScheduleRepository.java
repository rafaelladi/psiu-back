package com.dietrich.psiu.repository.schedule;

import com.dietrich.psiu.model.schedule.Schedule;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "schedules", path = "schedules")
public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, Long> {
}
