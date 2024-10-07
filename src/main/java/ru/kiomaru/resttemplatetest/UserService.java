package ru.kiomaru.resttemplatetest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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
    public static String sessionId;


    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getUsers() {
        ResponseEntity<User[]> responseEntity = restTemplate.exchange(
                IndexController.url,
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

    public String performRequestWithSession(String uri, Object body, HttpMethod method) {
        if (sessionId == null) {
            throw new IllegalStateException("SessionId not set");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, sessionId);
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpEntity<User> entity = new HttpEntity<>((User) body ,headers);
        ResponseEntity<String> response = restTemplate.exchange(uri, method, entity, String.class);
        System.out.println(response.getHeaders());
        System.out.println(response.getBody());
        IndexController.answer+=response.getBody();
        return response.getBody();
    }

    public String performRequestWithSession(String uri, HttpMethod method) {
        if (sessionId == null) {
            throw new IllegalStateException("SessionId not set");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, sessionId);
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpEntity<User> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(uri, method, entity, String.class);
        System.out.println(response.getHeaders());
        System.out.println(response.getBody());
        IndexController.answer+=response.getBody();
        return response.getBody();
    }

    public User createUser() {
        User user = new User();
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte) 20);
        return user;
    }

    public Object updateUser() {
        User user = createUser();
        user.setName("Thomas");
        user.setLastName("Shelby");
        return user;
    }
}
