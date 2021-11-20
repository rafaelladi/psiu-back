package com.dietrich.psiu.repository.chat;

import com.dietrich.psiu.excerpt.chat.MessageProjection;
import com.dietrich.psiu.model.chat.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "messages", path = "messages", excerptProjection = MessageProjection.class)
public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
    @Override
    @RestResource
    @Query("select m from Message m where m.id = :id and m.person.id = ?#{authentication.principal.person.id}")
    Optional<Message> findById(Long id);

    @Override
    @RestResource
    @Query("select m from Message m where m.person.id = ?#{authentication.principal.person.id}")
    Page<Message> findAll(Pageable pageable);
}
