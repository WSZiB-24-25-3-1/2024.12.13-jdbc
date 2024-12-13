package pl.edu.wszib.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class App {
    public static Connection connection;

    public static void main(String[] args) {
        connect();
        /*Optional<User> userBox = getUserById(3);
        userBox.ifPresentOrElse(System.out::println,
                () -> System.out.println("Nic takiego nie ma w bazie !!"));*/

        /*User user = new User(2, "user", "user", "mateusz");
        persistUser(user);*/

        /*List<User> users = getAllUsers();
        System.out.println(users);*/

        /*deleteUser(1);*/

        /*User user = getUserById(2).get();
        user.setName("wiesiek");

        updateUser(user);*/

        disconnect();
    }

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            App.connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            //Connection connection = DriverManager.getConnection("jdbc:mysql://123.123.123.123:3306/baza", "", "");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Zepsute !!!");
        }
    }

    public static void persistUser(User user) {
        try {
            String sql = "INSERT INTO tuser (id, login, password, name) VALUES (?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Nie dziala (persistUser)");
        }
    }

    public static void deleteUser(int id) {
        try {
            String sql = "DELETE FROM tuser WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Nie dziala (deleteUser)");
        }
    }

    public static Optional<User> getUserById(int id) {
        try {
            String sql = "SELECT * FROM tuser WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();


            if(rs.next()) {
                return Optional.of(new User(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println("Zepsulo sie (getUserById)");
        }
        return Optional.empty();
    }

    public static List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM tuser;";
            ResultSet rs = connection.prepareStatement(sql).executeQuery();

            while (rs.next()) {
                result.add(new User(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println("Zepsulo sie (getAllUsers)");
        }
        return result;
    }

    public static void updateUser(User user) {
        try {
            String sql = "UPDATE tuser SET login = ?, password = ?, name = ? WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setInt(4, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Zepsulo sie (updateUser)");
        }
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Zepsulo sie (disconnect)");
        }
    }
}
