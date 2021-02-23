package com.example.shoppingCart.controller;

import com.example.shoppingCart.model.transaction.Transaction;
import com.example.shoppingCart.service.TransactionService;
import com.example.shoppingCart.model.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {
    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(path = "/saveOrder")
    public ResponseEntity<String> addOrder(@RequestBody Order order) {
        //List<Integer> prodIds = new ArrayList<>();
        //prodIds.add(1);
        //RestTemplate restTemplate = new RestTemplate();
        //Order order = restTemplate.getForObject("http://localhost:8080/getOrder",Order.class);
        System.out.println(order);
        Order verifyOrder = transactionService.saveOrder(order);
        if(verifyOrder != null) {
            return new ResponseEntity<>("ok", OK);
        }
        return new ResponseEntity<>("not ok:(", BAD_REQUEST);
    }

    @PostMapping(path = "/saveOrderWithoutCheck")
    public ResponseEntity<Order> addOrderWithoutCheck(@RequestBody Order order) {
        Order verifyOrder = transactionService.saveOrderWithoutCheck(order);
        if(verifyOrder != null) {
            return new ResponseEntity<>(verifyOrder, OK);
        }
        return new ResponseEntity<>(verifyOrder, BAD_REQUEST);
    }

    @GetMapping("/getAll")
    public List<Transaction> getAll(){
      return transactionService.listTransactions();
    }

    @GetMapping("/getAllByDate")
    public List<Transaction> getAllByDate(){
        return transactionService.listTransactionsByDate();
    }

    @GetMapping("/getAllByUsername/{username}")
    public List<Transaction> getAllByUsername(@PathVariable(value = "username") String username) {
        return transactionService.listTransactionsByUsername(username);
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteTransactionById(@PathVariable(value = "id") Integer id) {
        transactionService.deleteTransaction(id);
    }

    @PutMapping("/update/{id}")
    public Transaction update(@PathVariable("id") Integer id, @RequestBody Transaction transaction){
        return transactionService.updateTransaction(id,transaction);
    }

}
