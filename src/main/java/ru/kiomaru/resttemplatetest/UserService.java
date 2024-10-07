package ru.kiomaru.resttemplatetest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class UserService {

    final RestTemplate restTemplate;
    private String sessionId;


    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getUsers() {
        String url = "http://94.198.50.185:7081/api/users";
        ResponseEntity<User[]> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                User[].class
        );
        HttpHeaders headers = responseEntity.getHeaders();
        sessionId = headers.getFirst(HttpHeaders.SET_COOKIE);
        if (sessionId == null) {
            System.out.println("Пусто");
        } else {
            System.out.println("Получил = " + sessionId);
        }
        return Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));
    }

}
