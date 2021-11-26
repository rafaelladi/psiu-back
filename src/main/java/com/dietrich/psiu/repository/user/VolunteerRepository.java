package com.dietrich.psiu.repository.user;

import com.dietrich.psiu.model.organization.Organization;
import com.dietrich.psiu.model.user.Volunteer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerRepository extends PagingAndSortingRepository<Volunteer, Long> {
    boolean existsByEmail(String email);
    Volunteer findByEmail(String email);
    List<Volunteer> findAllByOrganizationId(Long organizationId);
    Volunteer findByIdAndOrganizationId(Long id, Long organizationId);

    @Query(value = "select * from person v, project_volunteers pv where pv.volunteer_id = v.id and pv.project_id = :projectId and v.organization_id = :organizationId and v.dtype = 'Volunteer'", nativeQuery = true)
    List<Volunteer> findAllByProjectIdAndOrganizationId(Long projectId, Long organizationId);

    @Query(value = "select count(v.name) > 0 from person v, project_volunteers pv where v.id = :id and pv.project_id = :projectId and v.organization_id = :organizationId and v.dtype = 'Volunteer'", nativeQuery = true)
    boolean existsByIdAndProjectIdAndOrganizationId(Long id, Long projectId, Long organizationId);
}
