//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package name.motok.webapptest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import name.motok.webapptest.dao.PersonDAO;

@Controller
@RequestMapping({"/test-batch-update"})
public class BatchController {
    private final PersonDAO personDAO;

    public BatchController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping
    public String index() {
        return "batch/index";
    }

    @GetMapping({"/without"})
    public String withoutBatch() {
        this.personDAO.testMultiUpdate();
        return "redirect:/people";
    }

    @GetMapping({"/with"})
    public String withBatch() {
        this.personDAO.testBatchUpdate();
        return "redirect:/people";
    }
}
