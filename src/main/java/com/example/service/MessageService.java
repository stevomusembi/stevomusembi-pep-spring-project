package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public List<Message> getAllMessages(){
        return (List<Message>)messageRepository.findAll();
    }

    public Message createMessage(Message newMessage){
        String messageText = newMessage.getMessageText();
        Integer postedBy = newMessage.getPostedBy();
        if(messageText!= "" && messageText.length() <= 255 && accountRepository.findById(postedBy).isPresent()){
            return messageRepository.save(newMessage);

        } else {
            throw new ResourceNotFoundException("Invalid message or user");
        }
    }

    public Message getMessageById(Integer messageId){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);  
       if(optionalMessage.isPresent()){
        return optionalMessage.get();
       };
       return null;
    }

    public Integer patchMessage(int messageId, Message newMessage){
        String messageText = newMessage.getMessageText();
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        
        if(messageText!= "" && messageText.length() <= 255 && optionalMessage.isPresent()){
            return messageRepository.updateMessageTextByMessageId(messageId,messageText);

        } else {
            throw new ResourceNotFoundException("Invalid message");
        }
        
    }

    public Integer deleteMessage(int messageId){
        int rowsAffected = messageRepository.deleteByMessageId(messageId);
        if(rowsAffected == 0){
            return null;
        }
        return rowsAffected;
    }

    public List<Message> getMessagesByAccountId(int accountId){
        Optional<Account> account = accountRepository.findById(accountId);
        if(account.isPresent()){
            return (List<Message>)messageRepository.findAllByPostedBy(accountId);
        }
        return null;
    }
}
