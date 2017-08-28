package io.dehli.transactions.controllers;

import io.dehli.transactions.models.Transaction;
import io.dehli.transactions.repos.TransactionRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@EnableAutoConfiguration
@RefreshScope
@RestController
@RequestMapping("/")
public class TransactionController {

    @Value("${transactions.max}")
    private Double maxAmount;

    @Value("${transactions.min}")
    private Double minAmount;

    private Random random = new Random();

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Iterable<Transaction>> getAccounts() {
        System.out.println(maxAmount);
        System.out.println(minAmount);
        return new ResponseEntity<>(transactionRepository.findAll(), HttpStatus.OK);
    }

    // TODO: Change this to a POST & use cents
    @RequestMapping("/create")
    public ResponseEntity<String> createAccount(@RequestParam(name="id") Integer accountId,
                                                @RequestParam(name="amount") Double amount) {

        if (amount < minAmount || amount > maxAmount) {
            return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
        }

        // TODO: Figure out a better way to send this message
        rabbitTemplate.convertAndSend( "accounts", accountId.toString() + ":" + amount.toString());

        transactionRepository.save(new Transaction(accountId, amount));
        return new ResponseEntity<>("ok", HttpStatus.CREATED);
    }
}
