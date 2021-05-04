package com.noirix;

import com.noirix.repository.UserRepository;
import com.noirix.repository.impl.UserRepositoryImpl;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepositoryImpl();
// Find all users
//        for (User user : userRepository.findAll()) {
//            System.out.println(user);
//        }

        //Find one
//        try {
//            System.out.println(userRepository.findOne(100L));
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }

        //Save
//        User user = new User();
//        user.setName("Test");
//        user.setSurname("Save");
//        user.setLogin("test_save_2");
//        user.setWeight(110f);
//        user.setBirthDate(new Date(12000000L));
//
//        System.out.println(userRepository.save(user));

        //Check function call
        // System.out.println(userRepository.getUserExpensiveCarPrice(1));
    }

}