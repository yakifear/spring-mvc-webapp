//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package name.motok.webapptest.utill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import name.motok.webapptest.dao.PersonDAO;
import name.motok.webapptest.models.Person;

@Component
public class AddPersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public AddPersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        Person person = (Person)target;
        if (this.personDAO.show(person.getName()) != null) {
            errors.rejectValue("name", "", "Name is already exist");
        }

    }
}
