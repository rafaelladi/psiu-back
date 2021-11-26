package com.dietrich.psiu.repository.chat;

import com.dietrich.psiu.model.chat.Message;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {

}
