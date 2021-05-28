package ru.nesterov.spring.dao;

import org.springframework.stereotype.Component;
import ru.nesterov.spring.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Class.forName;

/**
 * @author Sergey Nesterov
 */

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "postgres";

    private static final String SELECT_ALL = "SELECT * FROM public.person";
    private static final String SELECT_ID = "SELECT * FROM public.person WHERE id = ?";
    private static final String SELECT_LAST_ID = "SELECT id FROM public.person ORDER BY id DESC LIMIT 1";
    private static final String INSERT_NEW = "INSERT INTO public.person (age,name,email) VALUES(?,?,?)";
    private static final String UPDATE_ID = "UPDATE public.person SET age = ?, name = ?, email = ? WHERE id = ?";
    private static final String DELETE_ID = "DELETE FROM public.person WHERE id = ?";


    private static Connection connection;


    static {
        try{
            Class.forName("org.postgresql.Driver");}
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL,LOGIN,PASSWORD);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }


    public List<Person> index(){
        try (Statement statement = connection.createStatement();){
            List<Person> people = new ArrayList<Person>();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()){
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setAge(resultSet.getInt("age"));
                person.setName(resultSet.getString("name"));
                person.setEmail(resultSet.getString("email"));
                people.add(person);
            }
            return people;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }

    }

    public Person show(int id){
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID);){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Person person = new Person();
            person.setId(resultSet.getInt("id"));
            person.setAge(resultSet.getInt("age"));
            person.setName(resultSet.getString("name"));
            person.setEmail(resultSet.getString("email"));
            return person;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }

    }
    public int create(Person person){
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW)){
            preparedStatement.setInt(1, person.getAge());
            preparedStatement.setString(2, person.getName());
            preparedStatement.setString(3, person.getEmail());
            int rows = preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return 0;
        }
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(SELECT_LAST_ID);
            resultSet.next();
            return resultSet.getInt("id");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return 0;
        }
    }

    public void update(int id, Person person) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ID)){
            preparedStatement.setInt(4, id);
            preparedStatement.setInt(1, person.getAge());
            preparedStatement.setString(2, person.getName());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void delete(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ID)){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
