package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.hibernate.AccidentHibernateService;
import ru.job4j.accidents.service.hibernate.AccidentTypeHibernateService;
import ru.job4j.accidents.service.hibernate.RuleHibernateService;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
@RequestMapping("/accidents")
public class AccidentController {
    private final AccidentHibernateService accidents;
    private final AccidentTypeHibernateService typeService;
    private final RuleHibernateService ruleService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", typeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "/accidents/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        accidents.create(accident, ids);
        return "redirect:/index";
    }

    @GetMapping("/editAccident")
    public String viewUpdateAccident(@RequestParam("id") int id, Model model) {
        var accidentOptional = accidents.findById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("message", "Заявление с указанным идентификатором не найдено");
            return "errors/404";
        }
        model.addAttribute("accident", accidentOptional.get());
        model.addAttribute("types", typeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "/accidents/editAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        accidents.update(accident, ids);
        return "redirect:/index";
    }
}