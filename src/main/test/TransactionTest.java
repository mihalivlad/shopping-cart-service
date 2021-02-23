import com.example.shoppingCart.model.order.Order;
import com.example.shoppingCart.model.transaction.Transaction;
import org.junit.jupiter.api.Test;
import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionTest {

    private String urlSaveOrderWithoutCheck ="http://localhost:8082/saveOrderWithoutCheck";
    private String urlUpdate ="http://localhost:8082/update/";
    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void saveOrderWithoutCheckHappyCase() {

        // Given
        String createdUser = randomWord(5);
        List<Integer> productsId = new ArrayList<>();
        productsId.add(1);
        Order order = new Order(createdUser, productsId);

        // When
        ResponseEntity<Order> responseEntity = restTemplate.postForEntity(urlSaveOrderWithoutCheck,order, Order.class);

        // Then
        assertEquals(responseEntity.getStatusCode(), OK);

    }

    @Test
    public void saveOrderWithoutCheckExceptionCase() {

        // Given
        String given = randomWord(10);

        // When
        try {
            ResponseEntity<Order> responseEntity = restTemplate.postForEntity(urlSaveOrderWithoutCheck, given, Order.class);
        }catch (HttpClientErrorException e) {
            // Then
            assertEquals(e.getStatusCode(), UNSUPPORTED_MEDIA_TYPE);
        }
    }

    @Test
    public void updateHappyCase() {

        // Given
        String createdUser = randomWord(5);
        String updateUser = randomWord(5);

        List<Integer> productsId = new ArrayList<>();
        productsId.add(1);
        Order order = new Order(createdUser, productsId);
        restTemplate.postForEntity(urlSaveOrderWithoutCheck,order, Order.class);
        Transaction transaction = new Transaction();
        transaction.setUsername(updateUser);
        HttpEntity<Transaction> transactionHttpEntity = new HttpEntity<>(transaction);
        // When
        ResponseEntity<Transaction> responseEntity = restTemplate
                .exchange(urlUpdate+1, HttpMethod.PUT
                        , transactionHttpEntity, Transaction.class);

        // Then
        assertEquals(responseEntity.getBody().getUsername(), updateUser);

    }

    @Test
    public void updateExceptionCase() {

        // Given
        String createdUser = randomWord(5);
        String updateUser = randomWord(5);

        List<Integer> productsId = new ArrayList<>();
        productsId.add(1);
        Order order = new Order(createdUser, productsId);
        restTemplate.postForEntity(urlSaveOrderWithoutCheck,order, Order.class);
        Transaction transaction = new Transaction();
        transaction.setUsername(updateUser);
        HttpEntity<Transaction> transactionHttpEntity = new HttpEntity<>(transaction);
        // When

        try {
            ResponseEntity<Transaction> responseEntity = restTemplate
                    .exchange(urlUpdate+0, HttpMethod.PUT
                            , transactionHttpEntity, Transaction.class);
        }catch (HttpServerErrorException e) {
            // Then
            assertEquals(e.getStatusCode(), INTERNAL_SERVER_ERROR);
        }
    }

    private String randomWord(int targetStringLength){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

}
