package com.dietrich.psiu.repository.schedule;

import com.dietrich.psiu.model.schedule.Period;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodRepository extends PagingAndSortingRepository<Period, Long> {

}
