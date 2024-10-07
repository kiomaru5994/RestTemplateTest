package ru.kiomaru.resttemplatetest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class IndexController {

    final private UserService userService;

    public static String answer = "";
    public static String url = "http://94.198.50.185:7081/api/users";
    private final RestTemplate restTemplate;

    public IndexController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String index() {
        System.out.println(userService.getUsers());
        userService.performRequestWithSession(url, userService.createUser(), HttpMethod.POST);
        userService.performRequestWithSession(url, userService.updateUser(), HttpMethod.PUT);
        userService.performRequestWithSession(url + "/3", HttpMethod.DELETE);
        System.out.println(answer);
        return "index";
    }
}
