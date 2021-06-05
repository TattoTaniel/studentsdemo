package com.noirix;

import com.noirix.domain.User;
import com.noirix.repository.UserRepository;
import com.noirix.util.UserGenerator;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class SpringContextTester {
    public static void main(String[] args) {


//        ClassPathXmlApplicationContext xmlContext = new ClassPathXmlApplicationContext("classpath:application-context.xml");
//
//        User user1 = xmlContext.getBean("user1", User.class);
//        User user2 = (User) xmlContext.getBean("user2");
//        System.out.println(user1);
//        System.out.println(user2);

        Logger log = Logger.getLogger(SpringContextTester.class);
        log.info("I am working!");

        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext("com.noirix");

        //UserRepository userRepository = annotationConfigApplicationContext.getBean(UserRepository.class);

//        System.out.println(annotationConfigApplicationContext.getBean("getStringUtils", StringUtils.class).concat("First", "second"));
//
        UserRepository userRepository = annotationConfigApplicationContext.getBean(UserRepository.class);

//        userRepository.findAll();
//        userRepository.findAll();
//        userRepository.findAll();
//        userRepository.findAll();
//        userRepository.findAll();

//        for (User user : all) {
//            System.out.println(user);
//        }

        UserGenerator userGenerator = annotationConfigApplicationContext.getBean(UserGenerator.class);

        List<User> generatedUsers = userGenerator.generate(10);
        List<User> generatedUsers1 = userGenerator.generate(10);

        //TODO: check speed of executing
        userRepository.save(generatedUsers1);
        userRepository.batchInsert(generatedUsers);
    }
}