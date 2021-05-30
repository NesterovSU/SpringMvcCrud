package ru.nesterov.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL = "SELECT * FROM public.person";
    private static final String SELECT_ID = "SELECT * FROM public.person WHERE id = ?";
    private static final String SELECT_LAST = "SELECT * FROM public.person ORDER BY id DESC LIMIT 1";
    private static final String INSERT_NEW = "INSERT INTO public.person (age,name,email) VALUES(?,?,?)";
    private static final String UPDATE_ID = "UPDATE public.person SET age = ?, name = ?, email = ? WHERE id = ?";
    private static final String DELETE_ID = "DELETE FROM public.person WHERE id = ?";


    private static Connection connection;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> index(){
        return jdbcTemplate.query(SELECT_ALL, new PersonMapper());
    }

    public Person show(int id){
        return jdbcTemplate.<Person>query(SELECT_ID, new Object[]{id}, new PersonMapper())
                .stream().findAny().orElse(null);
    }

    public Person create(Person person){
        jdbcTemplate.update(INSERT_NEW, person.getAge(), person.getName(), person.getEmail());
        return jdbcTemplate.query(SELECT_LAST, new PersonMapper())
                .stream().findAny().orElse(null);
    }

    public void update(int id, Person person) {
        jdbcTemplate.update(UPDATE_ID, person.getAge(), person.getName(), person.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update(DELETE_ID, id);
    }

    public static class PersonMapper implements RowMapper<Person>{

        @Override
        public Person mapRow(ResultSet resultSet, int i) throws SQLException {
            Person person = new Person();
            person.setId(resultSet.getInt("id"));
            person.setAge(resultSet.getInt("age"));
            person.setName(resultSet.getString("name"));
            person.setEmail(resultSet.getString("email"));
            return person;
        }
    }
}
