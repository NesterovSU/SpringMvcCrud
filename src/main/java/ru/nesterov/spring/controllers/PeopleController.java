package ru.nesterov.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nesterov.spring.dao.PersonDAO;
import ru.nesterov.spring.model.Person;

/**
 * @author Sergey Nesterov
 */

@Controller
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PersonDAO personDAO;

    @GetMapping("")
    public String index(Model model){
//        Получение всех людей
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
//        Получение одного человека по id
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
//        Получение формы для создания человека
        return "people/create";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model){
//        Получение формы для редактирования человека
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PostMapping("")
    public String createPerson(@ModelAttribute("person") Person person, Model model){
//        Создание одного человека в DAO
        personDAO.create(person);
        int id = person.getId();
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") Person person,@PathVariable("id") int id, Model model){
//        Редактирование одного человека в DAO
        personDAO.update( id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";
    }
}
