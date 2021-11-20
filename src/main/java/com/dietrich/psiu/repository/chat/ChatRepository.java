package com.dietrich.psiu.repository.chat;

import com.dietrich.psiu.excerpt.chat.ChatProjection;
import com.dietrich.psiu.model.chat.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "chats", path = "chats", excerptProjection = ChatProjection.class)
public interface ChatRepository extends PagingAndSortingRepository<Chat, Long> {
    @Override
    @RestResource
    @Query("select c from Chat c where (?#{hasAuthority('USER')} = true and c.user.id = ?#{authentication.principal.person.id}) or" +
            "(?#{hasAuthority('VOLUNTEER')} = true and c.volunteer.id = ?#{authentication.principal.person.id})")
    Page<Chat> findAll(Pageable pageable);

    @Override
    @RestResource
    @Query("select c from Chat c where c.id = :id and ((?#{hasAuthority('USER')} = true and c.user.id = ?#{authentication.principal.person.id}) or" +
            "(?#{hasAuthority('VOLUNTEER')} = true and c.volunteer.id = ?#{authentication.principal.person.id}))")
    Optional<Chat> findById(@Param("id") Long id);
}
