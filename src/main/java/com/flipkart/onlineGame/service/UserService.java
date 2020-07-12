package com.flipkart.onlineGame.service;

import com.flipkart.onlineGame.model.User;

import java.util.*;
import java.util.stream.Collectors;

public class UserService {

    static Map<String, User> map = new HashMap<>();

    public static void upsertUser(String[] instructions) {
        User newUser = Optional.ofNullable(map.get(instructions[3])).map(user -> {
            user.setName(instructions[1]);
            user.setCountry(instructions[2]);
            user.setEmail(instructions[3]);
            return user;
        }).orElse(new User(instructions[1], instructions[2], instructions[3], (double) 0.0));
        map.put(instructions[3], newUser);
        System.out.println(map);
    }

    public static void upsertScore(String[] instructions) {
        String message = Optional.ofNullable(map.get(instructions[1])).map(user -> {
            user.setScore(Double.parseDouble(instructions[2]));
            return "Score updated successfully";
        }).orElse("No user found");
        System.out.println(message);
        System.out.println(map);
    }

    public static void getTop(String[] instructions) {
        List<String> res = new ArrayList<>();
        if (instructions.length > 2) {
            res = getTopBasedOnScoreAndLocation(Integer.parseInt(instructions[1]), instructions[2]);
        }
        if (instructions.length == 2) {
            res = getOverAllTop(Integer.parseInt(instructions[1]));
        }
        System.out.println(res);
    }

    private static List<String> getOverAllTop(Integer k) {
        List<String> res = new ArrayList<>();
        PriorityQueue<User> pq = new PriorityQueue<>((user1, user2) -> (int) (user1.getScore() - user2.getScore()));
        for (String email : map.keySet()) {
            pq.add(map.get(email));
            if (pq.size() > k) {
                pq.poll();
            }
        }
        while (!pq.isEmpty()) {
            res.add(pq.poll().getEmail());
        }
        Collections.reverse(res);
        return res;
    }

    private static List<String> getTopBasedOnScoreAndLocation(Integer k, String location) {
        List<String> res = new ArrayList<>();
        List<User> userList = new ArrayList<>(map.values());
        userList = userList.stream().filter(user -> user.getCountry().equals(location)).collect(Collectors.toList());
        PriorityQueue<User> pq = new PriorityQueue<>((user1, user2) -> (int) (user1.getScore() - user2.getScore()));
        for (User user : userList) {
            pq.add(user);
            if (pq.size() > k) {
                pq.poll();
            }
        }
        while (!pq.isEmpty()) {
            res.add(pq.poll().getEmail());
        }
        Collections.reverse(res);
        return res;
    }

    public static void getUsersWithScore(String[] instructions) {
        map.values().stream().filter(user -> user.getScore() == Double.parseDouble(instructions[1]))
                .collect(Collectors.toList()).forEach(user -> System.out.println(user.getEmail()));
    }

    public static void search(String[] instructions) {
        if (instructions[1].equals("null")) instructions[1] = null;
        if (instructions[2].equals("null")) instructions[2] = null;
        if (instructions[3].equals("null")) instructions[3] = null;

        System.out.println(map.values().stream()
                .filter(user -> Optional.ofNullable(instructions[1]).map(ins -> user.getName().equals(ins)).orElse(true))
                .filter(user -> Optional.ofNullable(instructions[2]).map(ins -> user.getScore() == Double.parseDouble(ins)).orElse(true))
                .filter(user -> Optional.ofNullable(instructions[3]).map(ins -> user.getCountry().equals(ins)).orElse(true))
                .collect(Collectors.toList()));
    }

    public static void getRange(String[] instructions) {
        Integer lowRank = Integer.parseInt(instructions[1]);
        Integer highRank = Integer.parseInt(instructions[2]);
        List<String> res = getOverAllTop(highRank);
        for (int i = lowRank - 1; i < res.size() && i < highRank; i++) {
            System.out.println(res.get(i));
        }
    }

    public static void searchName(String[] instructions) {
        map.values().stream().filter(user -> user.getName().contains(instructions[1])).forEach(user -> System.out.println(user.getEmail()));
    }
}
