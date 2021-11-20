package com.dietrich.psiu.repository.schedule;

import com.dietrich.psiu.model.schedule.Period;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "periods", path = "periods")
public interface PeriodRepository extends PagingAndSortingRepository<Period, Long> {
}
