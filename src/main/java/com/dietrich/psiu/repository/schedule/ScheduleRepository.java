package com.dietrich.psiu.repository.schedule;

import com.dietrich.psiu.model.schedule.Schedule;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, Long> {

}
