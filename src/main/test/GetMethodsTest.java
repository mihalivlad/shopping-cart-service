import com.example.shoppingCart.model.order.Order;
import com.example.shoppingCart.model.transaction.Transaction;
import org.junit.jupiter.api.Test;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource(locations = "classpath:application-test.properties")
public class GetMethodsTest {

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void getAllHappyCase() {

        //Given
        int numberOfProducts = new Random().nextInt(10) + 1;
        double sum = new Random().nextDouble();
        String randomName = randomWord(7);

        List<Integer> productsId = new ArrayList<>();
        for (int j = 0; j < numberOfProducts; j++) {
            productsId.add(new Random().nextInt(100) + 1);
        }

        Transaction transaction = new Transaction(randomName, productsId, sum);
        Order order = new Order(randomName, productsId);

        //When
        ResponseEntity<Order> responseEntity = restTemplate.postForEntity("http://localhost:8082/saveOrderWithoutCheck", order, Order.class);
        List<Transaction> transactions = Arrays.asList(restTemplate.getForObject("http://localhost:8082/getAll", Transaction[].class));

        //Then
        assertEquals(transactions.size(), transactions.get(transactions.size()-1).getId());
    }

    @Test
    public void getAllByDateHappyCase() {

        //Given
        int numberOfProducts = new Random().nextInt(10) + 1;
        double sum = new Random().nextDouble();
        String randomName = randomWord(7);

        List<Integer> productsId = new ArrayList<>();
        for (int j = 0; j < numberOfProducts; j++) {
            productsId.add(new Random().nextInt(100) + 1);
        }

        Transaction transaction = new Transaction(randomName, productsId, sum);
        Order order = new Order(randomName, productsId);

        //When
        ResponseEntity<Order> responseEntity = restTemplate.postForEntity("http://localhost:8082/saveOrderWithoutCheck", order, Order.class);
        List<Transaction> transactions = Arrays.asList(restTemplate.getForObject("http://localhost:8082/getAllByDate", Transaction[].class));

        //Then
        assertEquals(transactions.size(), transactions.get(0).getId());
    }

    @Test
    public void getAllUnhappyCase(){

        try{
            List<Transaction> transactions = Arrays.asList(restTemplate.getForObject("http://localhost:8082/getAll", Transaction[].class));
        } catch (HttpServerErrorException e){

            assertEquals(e.getStatusCode(), NOT_FOUND);
        }
    }

    @Test
    public void getAllByDateUnhappyCase(){
        try{
            List<Transaction> transactions = Arrays.asList(restTemplate.getForObject("http://localhost:8082/getAll", Transaction[].class));
        } catch (HttpServerErrorException e){

            assertEquals(e.getStatusCode(), NOT_FOUND);
        }
    }

    @Test
    public void getAllByUsernameTestHappyCase() {

        //Given
        int numberOfProducts = new Random().nextInt(10) + 1;
        double sum = new Random().nextDouble();
        String name = randomWord(7);

        List<Integer> productsId = new ArrayList<>();
        for (int j = 0; j < numberOfProducts; j++) {
            productsId.add(new Random().nextInt(100) + 1);
        }

        Transaction transaction = new Transaction(name, productsId, sum);
        Order order = new Order(name, productsId);

        // Given

        // When
        ResponseEntity<Order> responseEntity = restTemplate.postForEntity("http://localhost:8082/saveOrderWithoutCheck", order, Order.class);
        List<Transaction> transactions = restTemplate.getForObject("http://localhost:8082/getAllByUsername/"+name,List.class);

        // Then
        assertEquals(transactions.size(), 1);

    }

    @Test
    public void getAllByUsernameTestUnhappyCase() {

        // Given
        String name = "Ion123467";

        // When
        List<Transaction> transactions = restTemplate.getForObject("http://localhost:8082/getAllByUsername/"+name,List.class);

        // Then
        assertEquals(transactions.size(), 0);

    }

    private String randomWord(int targetStringLength) {
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
