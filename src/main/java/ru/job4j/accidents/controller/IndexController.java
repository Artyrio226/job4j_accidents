package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.accidents.service.hibernate.AccidentHibernateService;

@Controller
@AllArgsConstructor
@RequestMapping({"/", "index"})
public class IndexController {
    private final AccidentHibernateService accidentService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("user", "Petr Arsentev");
        model.addAttribute("accidents", accidentService.findAll());
        return "index";
    }
}
