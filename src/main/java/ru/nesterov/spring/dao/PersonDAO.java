package ru.nesterov.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.nesterov.spring.model.Person;
import java.util.List;



/**
 * @author Sergey Nesterov
 */

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM public.person",new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM public.person WHERE id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public Person create(Person person){
        jdbcTemplate.update("INSERT INTO public.person (age,name,email) VALUES(?,?,?)",
                person.getAge(), person.getName(), person.getEmail());

        return jdbcTemplate.query("SELECT * FROM public.person ORDER BY id DESC LIMIT 1",
                new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public boolean isEmailAlreadyExist(String email){
        return jdbcTemplate.query("SELECT * FROM public.person WHERE email = ?",
                new Object[]{email}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null) != null;
    }

    public boolean isEmailBelongsToAnotherPerson(Person person) {
        return jdbcTemplate.query("SELECT * FROM public.person WHERE email = ? AND id != ?",
                new Object[]{person.getEmail(), person.getId()}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null) != null;
    }


    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE public.person SET age = ?, name = ?, email = ? WHERE id = ?",
                person.getAge(), person.getName(), person.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM public.person WHERE id = ?", id);
    }

}
