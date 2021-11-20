package com.dietrich.psiu.repository.user;

import com.dietrich.psiu.model.user.Volunteer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "volunteers", path = "volunteers")
public interface VolunteerRepository extends PagingAndSortingRepository<Volunteer, Long> {
}
