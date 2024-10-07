package ru.kiomaru.resttemplatetest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    final private UserService userService;

    public static String answer = "";

    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        System.out.println(userService.getUsers());
        return "index";
    }
}
