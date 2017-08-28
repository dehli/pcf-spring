package io.dehli.accounts.controllers;

import io.dehli.accounts.models.Account;
import io.dehli.accounts.repos.AccountRepository;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RabbitListener(queues = "accounts")
@RequestMapping("/")
public class AccountController {

    private Random random = new Random();

    @Autowired
    private AccountRepository accountRepository;

    @Bean
    public Queue accountsQueue() {
        return new Queue("accounts");
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Iterable<Account>> getAccounts() {
        return new ResponseEntity<>(accountRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping("/create")
    public ResponseEntity<String> createAccount() {
        String name = "";
        for (int i = 0; i < 10; ++i) {
            name += random.nextInt(10);
        }

        accountRepository.save(new Account(name));
        return new ResponseEntity<>(name, HttpStatus.CREATED);
    }

    @RabbitHandler
    public void onMessage(@Payload String message) {
        // TODO: Figure out a better way to handle Payload
        String[] parts = message.split(":");

        Account account = accountRepository.findOne(Integer.parseInt(parts[0]));
        account.addToBalance(Double.parseDouble(parts[1]));
        accountRepository.save(account);
    }
}
