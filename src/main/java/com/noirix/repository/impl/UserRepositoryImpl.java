package com.noirix.repository.impl;

import com.noirix.domain.User;
import com.noirix.exception.NoSuchEntityException;
import com.noirix.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserRepositoryImpl implements UserRepository {

    public static final String POSTRGES_DRIVER_NAME = "org.postgresql.Driver";
    public static final String DATABASE_URL = "jdbc:postgresql://localhost:";
    public static final int DATABASE_PORT = 5432;
    public static final String DATABASE_NAME = "/student_demo";
    public static final String DATABASE_LOGIN = "postgres";
    public static final String DATABASE_PASSWORD = "postgres";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String BIRTH_DATE = "birth_date";
    public static final String LOGIN = "login";
    public static final String WEIGHT = "weight";

    @Override
    public List<User> findAll() {
        final String findAllQuery = "select * from users order by id desc";

        List<User> result = new ArrayList<>();

        Connection connection;
        Statement statement;
        ResultSet rs;

        try {
            Class.forName(POSTRGES_DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded!");
        }

        String jdbcURL = StringUtils.join(DATABASE_URL, DATABASE_PORT, DATABASE_NAME);

        try {
            connection = DriverManager.getConnection(jdbcURL, DATABASE_LOGIN, DATABASE_PASSWORD);
            statement = connection.createStatement();
            rs = statement.executeQuery(findAllQuery);

            //Row mapping
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong(ID));
                user.setName(rs.getString(NAME));
                user.setSurname(rs.getString(SURNAME));
                user.setLogin(rs.getString(LOGIN));
                user.setBirthDate(rs.getDate(BIRTH_DATE));
                user.setWeight(rs.getFloat(WEIGHT));

                result.add(user);
            }

            return result;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public User findOne(Long id) {
        final String findById = "select * from users where id = ?";

        Connection connection;
        PreparedStatement statement;
        ResultSet rs;

        try {
            Class.forName(POSTRGES_DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded!");
        }

        String jdbcURL = StringUtils.join(DATABASE_URL, DATABASE_PORT, DATABASE_NAME);

        try {
            connection = DriverManager.getConnection(jdbcURL, DATABASE_LOGIN, DATABASE_PASSWORD);
            statement = connection.prepareStatement(findById);
            statement.setLong(1, id);
            rs = statement.executeQuery();
            //Row mapping
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong(ID));
                user.setName(rs.getString(NAME));
                user.setSurname(rs.getString(SURNAME));
                user.setLogin(rs.getString(LOGIN));
                user.setBirthDate(rs.getDate(BIRTH_DATE));
                user.setWeight(rs.getFloat(WEIGHT));

                return user;
            }

            throw new NoSuchEntityException("No such user with id:" + id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public User save(User user) {
        final String insertQuery = "insert into users (name, surname, birth_date, login, weight) " +
                "values (?,?,?,?,?)";

        Connection connection;
        PreparedStatement statement;

        try {
            Class.forName(POSTRGES_DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded!");
        }

        String jdbcURL = StringUtils.join(DATABASE_URL, DATABASE_PORT, DATABASE_NAME);

        try {
            connection = DriverManager.getConnection(jdbcURL, DATABASE_LOGIN, DATABASE_PASSWORD);
            statement = connection.prepareStatement(insertQuery);

            PreparedStatement lastInsertId = connection.prepareStatement("SELECT currval('users_id_seq') as last_insert_id;");

            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setDate(3, new Date(user.getBirthDate().getTime()));
            statement.setString(4, user.getLogin());
            statement.setFloat(5, user.getWeight());

            statement.executeUpdate();

            Long insertedId;
            ResultSet lastIdResultSet = lastInsertId.executeQuery();
            if (lastIdResultSet.next()) {
                insertedId = lastIdResultSet.getLong("last_insert_id");
            } else {
                throw new RuntimeException("We cannot read sequence last value during User creation!");
            }

            return findOne(insertedId);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<User> findUsersByQuery(String query) {
        return null;
    }


    @Override
    public Double getUserExpensiveCarPrice(Integer userId) {
        final String findPriceFunction = "select get_user_expensive_car(?)";

        Connection connection;
        PreparedStatement statement;
        ResultSet rs;

        try {
            Class.forName(POSTRGES_DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded!");
        }

        String jdbcURL = StringUtils.join(DATABASE_URL, DATABASE_PORT, DATABASE_NAME);

        try {
            connection = DriverManager.getConnection(jdbcURL, DATABASE_LOGIN, DATABASE_PASSWORD);
            statement = connection.prepareStatement(findPriceFunction);
            statement.setInt(1, userId);
            rs = statement.executeQuery();

            //Row mapping
            rs.next();
            return rs.getDouble("get_user_expensive_car");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }
}
