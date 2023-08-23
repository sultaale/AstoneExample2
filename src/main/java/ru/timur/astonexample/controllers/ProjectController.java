package ru.timur.astonexample.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.timur.astonexample.dao.ProjectDAO;
import ru.timur.astonexample.models.Project;

@Controller
@RequestMapping("/project")
public class ProjectController {
    private final ProjectDAO projectDAO;

    @Autowired
    public ProjectController(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("projects",projectDAO.getAllProjects());
        return "project/index";
    }

    @GetMapping("/create")
    public String newProject(@ModelAttribute("project") Project project){
        return "project/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("project") Project project){
        projectDAO.save(project);
        return "redirect:/project/index";
    }

    @GetMapping("/employees/{id}")
    public String getEmployees(Model model, @PathVariable("id") int id){
        model.addAttribute("employees",projectDAO.getAllEmployeeOnProject(id));
        model.addAttribute("projectId",id);
        return "project/employees";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id){
        projectDAO.delete(id);
        return "redirect:/project/index";
    }

    @PostMapping("/deleteEmp/{project_id}/{emp_id}")
    public String deleteEmp(@PathVariable("project_id") int projectId,@PathVariable("emp_id") int empId){
        projectDAO.deleteEmp(projectId,empId);
        return "redirect:/project/index";
    }
}
