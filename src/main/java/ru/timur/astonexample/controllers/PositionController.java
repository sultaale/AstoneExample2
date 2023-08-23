package ru.timur.astonexample.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.timur.astonexample.dao.PositionDAO;
import ru.timur.astonexample.models.Position;

@Controller
@RequestMapping("/position")
public class PositionController {
    private final PositionDAO positionDAO;

    @Autowired
    public PositionController(PositionDAO positionDAO) {
        this.positionDAO = positionDAO;
    }

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("positions",positionDAO.getAllPositions());
        return "position/index";
    }

    @GetMapping("/create")
    public String newPosition(@ModelAttribute("position") Position position){
        return "position/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("position") Position position){
        positionDAO.save(position);
        return "redirect:/position/index";
    }

    @GetMapping("/employees/{id}")
    public String getEmployees(Model model, @PathVariable("id") int id){
        model.addAttribute("employees",positionDAO.getAllEmployeeOnPosition(id));
        return "position/employees";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id){
        positionDAO.delete(id);
        return "redirect:/position/index";
    }
}
