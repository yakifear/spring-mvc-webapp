//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package name.motok.webapptest.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import name.motok.webapptest.dao.PersonDAO;
import name.motok.webapptest.models.Person;
import name.motok.webapptest.utill.AddPersonValidator;
import name.motok.webapptest.utill.EditPersonValidator;

@Controller
@RequestMapping({"/people"})
public class PeopleController {
    private final PersonDAO personDAO;
    private final AddPersonValidator addPersonValidator;
    private final EditPersonValidator editPersonValidator;
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public PeopleController(PersonDAO personDAO, AddPersonValidator addPersonValidator, EditPersonValidator editPersonValidator) {
        this.personDAO = personDAO;
        this.addPersonValidator = addPersonValidator;
        this.editPersonValidator = editPersonValidator;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", this.personDAO.index());
        return "people/index";
    }

    @GetMapping({"/{id}"})
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", this.personDAO.show(id));
        String url = "https://api.chucknorris.io/jokes/random";
        String response = (String)this.restTemplate.getForObject(url, String.class, new Object[0]);
        model.addAttribute("joke", response);
        return "people/show";
    }

    @GetMapping({"/new"})
    public String newPerson() {
        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, Model model) {
        this.addPersonValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult);
            return "people/new";
        } else {
            this.personDAO.save(person);
            return "redirect:/people";
        }
    }

    @GetMapping({"/{id}/edit"})
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", this.personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping({"/{id}"})
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id) {
        this.editPersonValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/edit";
        } else {
            this.personDAO.update(id, person);
            return "redirect:/people";
        }
    }

    @DeleteMapping({"/{id}"})
    public String delete(@PathVariable("id") int id) {
        this.personDAO.delete(id);
        return "redirect:/people";
    }
}
