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
        people = new ArrayList<>();
        people.add(new Person(++PEOPLE_COUNT, "Any", 23, "any@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT,"Tony", 45, "tony@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT,"Johny", 35, "johny@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT,"Ruby", 15, "ruby@mail.ru"));

    }
    public List<Person> index(){
        return people;
    }

    public Person show(final int id){
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }
    public void create(Person person){
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person person) {
        Person personToBeUpdated = this.show(id);
        personToBeUpdated.setName(person.getName());
        personToBeUpdated.setAge(person.getAge());
        personToBeUpdated.setEmail(person.getEmail());
    }

    public void delete(int id) {
        people.removeIf(person -> person.getId() == id);
    }
}
