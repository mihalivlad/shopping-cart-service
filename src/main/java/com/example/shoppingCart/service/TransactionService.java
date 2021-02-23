package com.example.shoppingCart.service;

import com.example.shoppingCart.model.order.Order;
import com.example.shoppingCart.model.order.Product;
import com.example.shoppingCart.model.transaction.Transaction;
import com.example.shoppingCart.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class TransactionService {
    private TransactionRepo transactionRepo;
    private RestTemplate restTemplate;
    private Environment env;
    @Autowired
    public TransactionService(TransactionRepo transactionRepo, RestTemplate restTemplate, Environment env) {
        this.transactionRepo = transactionRepo;
        this.restTemplate = restTemplate;
        this.env = env;
    }

    public boolean checkUserExistance(String username) {
        String url = env.getProperty("app.url.checkUsername");
        url += username;
        System.out.println(url);

        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        System.out.println(responseEntity);
        //String response = responseEntity.getBody();
        HttpStatus httpStatus = responseEntity.getStatusCode();
        if(httpStatus.compareTo(OK) == 0){
            return true;
        }
        return false;
    }

    public List<Product> checkProductsExistance(List<Integer> prodIds) {
        String url = env.getProperty("app.url.checkProducts");
        ResponseEntity<Product[]> responseEntityProducts = restTemplate.postForEntity(url, prodIds, Product[].class);
        if(responseEntityProducts.getStatusCode().compareTo(OK) == 0) {
            List<Product> productList = Arrays.asList(responseEntityProducts.getBody().clone());
             return productList;
        }
        return null;
    }

    private Double getSum(List<Product> products) {
        Double sum = 0.0;
        for(Product product : products)
            sum += product.getPrice();
        return sum;
    }

    public Order saveOrder(Order order) {
        if(checkUserExistance(order.getUsername())) {
            List<Product> products = checkProductsExistance(order.getProductsId());
            // Todo duplicates????
            if (products != null) {
                Double sum = this.getSum(products);
                Transaction transaction = new Transaction(order.getUsername(), order.getProductsId(), sum);
                transactionRepo.save(transaction);
                return order;
            }
        }
        return null;
    }

    public Order saveOrderWithoutCheck(Order order) {
        Transaction transaction = new Transaction(order.getUsername(), order.getProductsId(), 10.0);
        transactionRepo.save(transaction);
        return order;
    }
    public List<Transaction> listTransactions(){
        return transactionRepo.findAll();
    }

    public List<Transaction> listTransactionsByDate(){
        return transactionRepo.findAllByOrderByTransactionDateDesc();
    }

    public List<Transaction> listTransactionsByUsername(String username) {
        return transactionRepo.findAllByUsername(username);
    }

    public void deleteTransaction(Integer id) {
        transactionRepo.deleteById(id);
    }

    public Transaction updateTransaction(Integer id, Transaction update){
        Transaction transaction = transactionRepo.getOne(id);
        if(update.getUsername() != null){ //verify username
            transaction.setUsername(update.getUsername());
        }
        if(update.getProductIds() != null){ //verify products
            transaction.setProductIds(update.getProductIds());
        }
        if(update.getTotal() != null){ //calculate sum
            transaction.setTotal(update.getTotal());
        }
        return transactionRepo.save(transaction);
    }

    public List<Transaction> listFromList(){
        Transaction[] transactionsArray = restTemplate.getForObject("http://localhost:8082/getAll", Transaction[].class);
        return Arrays.asList(transactionsArray.clone());
    }
}
