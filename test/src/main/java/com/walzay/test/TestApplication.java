package com.walzay.test;

import com.walzay.test.service.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class TestApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

    @Autowired
    GiftlovService giftlovService;
    @Autowired
    SignInService signInService;
    @Override
    public void run(String... args) throws Exception {

      //  System.out.println(giftlovService.postRequestChekcToekn("coding_challenge_1","coding_challenge_1"));
       // System.out.println(signInService.generateToken("coding_challenge_1","coding_challenge_1"));
    }
}
