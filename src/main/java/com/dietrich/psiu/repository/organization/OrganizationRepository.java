package com.dietrich.psiu.repository.organization;

import com.dietrich.psiu.model.organization.Organization;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "organizations", path = "orgs")
public interface OrganizationRepository extends PagingAndSortingRepository<Organization, Long> {
}
