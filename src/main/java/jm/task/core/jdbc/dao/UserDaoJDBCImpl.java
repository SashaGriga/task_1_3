package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Connection con = getConnection();Statement st = con.createStatement()) {
            st.execute("create table if not exists Users (" +
                    "ID bigint primary key auto_increment," +
                    "Name varchar(15) not null," +
                    "LastName varchar(15)," +
                    "Age tinyint)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Connection con = getConnection();Statement st = con.createStatement()) {
            st.execute("drop table if exists Users");
            try {
                st.close();
                con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into Users (Name, LastName, Age) values (?, ?, ?)";
        try (Connection con = getConnection();PreparedStatement prepst = con.prepareStatement(sql)) {
            prepst.setString(1, name);
            prepst.setString(2, lastName);
            prepst.setByte(3, age);
            prepst.executeUpdate();
            System.out.println("Пользователь с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "delete from Users where ID = ?";
        try (Connection con = getConnection();PreparedStatement prepst = con.prepareStatement(sql)) {
            prepst.setLong(1, id);
            prepst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        try (Connection con = getConnection();Statement st = con.createStatement();ResultSet resultSet = st.executeQuery("select * from Users")) {

            while (resultSet.next()) {
                User tUser = new User();
                tUser.setId(resultSet.getLong("ID"));
                tUser.setName(resultSet.getString("Name"));
                tUser.setLastName(resultSet.getString("LastName"));
                tUser.setAge(resultSet.getByte("Age"));
                list.add(tUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        String DELETE_ALL = "TRUNCATE TABLE Users";

        try (Connection con = getConnection();Statement st = con.createStatement()) {
            st.executeUpdate(DELETE_ALL);
            System.out.println("Все пользователи удалены!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}