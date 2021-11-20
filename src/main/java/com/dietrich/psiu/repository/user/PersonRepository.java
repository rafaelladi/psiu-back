package com.dietrich.psiu.repository.user;

import com.dietrich.psiu.model.user.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByEmail(String email);
    boolean existsByEmail(String email);
}
