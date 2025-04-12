package com.example.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    List <Message> findAllByPostedBy(int accountId);

    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.messageText = :newMessageText WHERE m.id = :messageId")
    int updateMessageTextByMessageId(@Param("messageId") int messageId, @Param("newMessageText") String newMessageText);


    @Modifying
    @Transactional
    @Query("DELETE FROM Message m WHERE m.id = :messageId")
    int deleteByMessageId(@Param("messageId") int messageId);

}
