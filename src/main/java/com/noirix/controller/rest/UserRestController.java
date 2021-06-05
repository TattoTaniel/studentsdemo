package com.noirix.controller.rest;

import com.noirix.beans.SecurityConfig;
import com.noirix.controller.requests.UserCreateRequest;
import com.noirix.domain.User;
import com.noirix.repository.UserRepository;
import com.noirix.util.UserGenerator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/rest/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserRepository userRepository;
    private final UserGenerator userGenerator;
    private final SecurityConfig config;

    @GetMapping
    public List<User> findAll() {
        System.out.println("In rest controller");
        return userRepository.findAll();
    }

    @GetMapping("/hello")
    public List<User> securedFindAll(HttpServletRequest request) {
        String secretKey = request.getHeader("Secret-Key");

        if (StringUtils.isNotBlank(secretKey) && secretKey.equals(config.getSecretKey())) {
            return userRepository.findAll();
        } else {
            //throw new UnauthorizedException();
            return Collections.emptyList();
        }
    }

    @GetMapping("/search")
    public List<User> userSearch(@RequestParam Integer limit, @RequestParam String query) {
        return userRepository.findUsersByQuery(limit, query);
    }

    @PostMapping
    public User createUser(@ModelAttribute UserCreateRequest createRequest) {
        User generatedUser = userGenerator.generate();
        generatedUser.setWeight(createRequest.getWeight());
        generatedUser.setLogin(createRequest.getLogin());

        return userRepository.save(generatedUser);
    }

    @PostMapping("/generate/{usersCount}")
    public List<User> generateUsers(@PathVariable("usersCount") Integer count) {
        throw new RuntimeException("Haha!");
//        List<User> generateUsers = userGenerator.generate(count);
//        userRepository.batchInsert(generateUsers);
//
//        return userRepository.findAll();
    }

}