package ru.timur.astonexample.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.timur.astonexample.dao.EmployeeDAO;
import ru.timur.astonexample.dao.PositionDAO;
import ru.timur.astonexample.dao.ProjectDAO;
import ru.timur.astonexample.models.Employee;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeDAO employeeDAO;
    private final PositionDAO positionDAO;
    private final ProjectDAO projectDAO;

    @Autowired
    public EmployeeController(EmployeeDAO employeeDAO, PositionDAO positionDAO, ProjectDAO projectDAO) {
        this.employeeDAO = employeeDAO;
        this.positionDAO = positionDAO;
        this.projectDAO = projectDAO;
    }

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("employees", employeeDAO.getAllEmployee());
        return "employee/index";
    }

    @GetMapping("/create")
    public String newEmployee(@ModelAttribute("employee") Employee employee, Model model){
        model.addAttribute("positions",positionDAO.getAllPositions());
        return "employee/create";
    }


    @PostMapping("/create")
    public String create(@ModelAttribute("employee") Employee employee){
        employeeDAO.save(employee);
        return "redirect:/employee/index";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("employee", employeeDAO.show(id));
        model.addAttribute("positions",positionDAO.getAllPositions());
        model.addAttribute("projects",projectDAO.getAllProjects());
        return "employee/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute("employee") Employee employee, @PathVariable("id") int id){
        employeeDAO.update(employee,id);
        return "redirect:/employee/index";
    }

      @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id){
        employeeDAO.delete(id);
        return "redirect:/employee/index";
      }
}
