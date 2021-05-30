package ru.nesterov.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nesterov.spring.dao.PersonDAO;
import ru.nesterov.spring.model.Person;

import javax.validation.Valid;

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
//        Получение всех людей из базы
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
//        Получение одного человека по id из базы
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
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, Model model){
//        Создание одного человека в базе

        if(bindingResult.hasErrors()){
            return "people/create";
        }

        model.addAttribute("person", personDAO.create(person));
        return "people/show";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id){
//        Редактирование одного человека в базе

        if(bindingResult.hasErrors()){
            return "people/edit";
        }

        personDAO.update( id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
//        Удаление одного человека из базы
        personDAO.delete(id);
        return "redirect:/people";
    }
}
