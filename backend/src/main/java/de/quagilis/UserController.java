package de.quagilis;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Arrays.asList;


@RestController
@RequestMapping("/user")
@CrossOrigin()
public class UserController {

    @GetMapping(value = "/", produces = "application/json")
    public User get() {
        return new User();
    }
}
