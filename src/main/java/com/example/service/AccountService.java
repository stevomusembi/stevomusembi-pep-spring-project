package com.example.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.ConflictException;
import com.example.exception.UnauthorizedException;
import com.example.repository.AccountRepository;

@Transactional
@Service
public class AccountService {

    private AccountRepository accountRepository;
    

    @Autowired
    public AccountService (AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public void register(Account newAccount){
        String username = newAccount.getUsername();
        String password = newAccount.getPassword();

        if(accountRepository.existsByUsername(username)){
            throw new ConflictException("username already exists");
        }
        if (username != null && password != null && password.length() > 4 && username.length() > 0) {
            accountRepository.save(newAccount);
        }
    }

    public Account login(Account account){
        String username = account.getUsername();
        String password = account.getPassword();
        Optional<Account> optionalAccount = accountRepository.findByUsernameAndPassword(username, password);
        if(optionalAccount.isPresent()){
            return optionalAccount.get();
        }else{
            throw  new UnauthorizedException("check username and password and try again");
        }
    }


    

}
