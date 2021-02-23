import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.ACCEPTED;

public class SystemTest {

    @Test
    public void addToCartTest() throws IOException, ParseException {
        //given
        JSONParser jsonParser = new JSONParser();
        Object jsonUser = jsonParser.parse(new FileReader("src\\main\\resources\\static\\create_user.json"));
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject("http://localhost:8080/", jsonUser, Object.class);
        Object jsonProduct = jsonParser.parse(new FileReader("src\\main\\resources\\static\\create_product.json"));
        Object jsonCategory = jsonParser.parse(new FileReader("src\\main\\resources\\static\\create_category.json"));
        restTemplate.postForObject("http://localhost:8081/category/create", jsonCategory, Object.class);
        restTemplate.postForObject("http://localhost:8081/addProduct", jsonProduct, Object.class);

        //when
        ResponseEntity<String> responseEntity =  restTemplate.getForEntity("http://localhost:8080/addToCart/?username="+"Ion2",String.class);

        //then
        HttpStatus httpStatus = responseEntity.getStatusCode();
        assertEquals(httpStatus, ACCEPTED);

    }
}
