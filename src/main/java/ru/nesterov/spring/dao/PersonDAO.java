package ru.nesterov.spring.dao;

import org.springframework.stereotype.Component;
import ru.nesterov.spring.model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Nesterov
 */

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private List<Person> people;
    {
        people = new ArrayList<Person>();
        people.add(new Person(++PEOPLE_COUNT,"Any"));
        people.add(new Person(++PEOPLE_COUNT,"Tony"));
        people.add(new Person(++PEOPLE_COUNT,"Johny"));
        people.add(new Person(++PEOPLE_COUNT,"Ruby"));

    }
    public List<Person> index(){
        return people;
    }

    public Person show(final int id){
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }
}
