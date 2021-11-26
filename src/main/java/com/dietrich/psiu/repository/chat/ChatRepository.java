package com.dietrich.psiu.repository.chat;

import com.dietrich.psiu.model.chat.Chat;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends PagingAndSortingRepository<Chat, Long> {
    Chat findByIdAndUserId(Long id, Long userId);
    Chat findByIdAndVolunteerId(Long id, Long volunteerId);
}
