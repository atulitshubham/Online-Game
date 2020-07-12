package com.flipkart.onlineGame.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private String name;
    private String country;
    private String email;
    private Double score;
}
