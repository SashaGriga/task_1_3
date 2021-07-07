package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService users = new UserServiceImpl();

        users.createUsersTable();

        users.saveUser("firstname1", "lastname1", (byte) 20);
        users.saveUser("firstname2", "lastname2", (byte) 25);
        users.saveUser("firstname3", "lastname3", (byte) 30);
        users.saveUser("firstname4", "lastname4", (byte) 35);

        List<User> usersList = users.getAllUsers();
        for (User user: usersList) {
            System.out.println(user);
        }

        users.cleanUsersTable();

        users.dropUsersTable();
    }
}
