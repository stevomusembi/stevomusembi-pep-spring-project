package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("register")   
    public ResponseEntity<Account> register(@RequestBody Account account){
        accountService.register(account);
        return ResponseEntity.ok()
        .body(account);
    }
    
    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        accountService.login(account);
        return ResponseEntity.ok()
        .body(accountService.login(account));
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages(){
       return ResponseEntity.ok()
        .body(messageService.getAllMessages());
    }

    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message newMessage){
        return ResponseEntity.ok()
        .body(messageService.createMessage(newMessage));
    }

    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageByMessageId(@PathVariable int messageId){
        return ResponseEntity.ok()
        .body(messageService.getMessageById(messageId));
    }

    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccounId(@PathVariable int accountId){
       return ResponseEntity.status(200)
       .body(messageService.getMessagesByAccountId(accountId));
    }

    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> patchMessage(@PathVariable int messageId, @RequestBody Message updatedMessage){
        return ResponseEntity.ok()
        .body(messageService.patchMessage(messageId, updatedMessage));
    }

    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId){
        return ResponseEntity.ok()
        .body(messageService.deleteMessage(messageId));
    }

}
