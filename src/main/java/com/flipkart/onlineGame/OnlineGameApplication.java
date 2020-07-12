package com.flipkart.onlineGame;

import com.flipkart.onlineGame.model.User;
import com.flipkart.onlineGame.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class OnlineGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineGameApplication.class, args);
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter first instruction");
		String[] instructions = sc.nextLine().split(",");
		while(!instructions[0].equals("EXIT")) {
			switch (instructions[0]) {
				case "UPSERT_USER":
					UserService.upsertUser(instructions);
					break;
				case "UPSERT_SCORE":
					UserService.upsertScore(instructions);
					break;
				case "GET_TOP":
					UserService.getTop(instructions);
					break;
				case "GET_USERS_WITH_SCORE":
					UserService.getUsersWithScore(instructions);
					break;
				case "SEARCH":
					UserService.search(instructions);
					break;
				case "GET_RANGE":
					UserService.getRange(instructions);
					break;
				case "SEARCH_NAME":
					UserService.searchName(instructions);
					break;
				default:
					System.out.println("Non of the choices matched.");
					break;
			}
			System.out.println("Enter next instruction");
			instructions = sc.nextLine().split(",");
		}
		sc.close();
	}

}
