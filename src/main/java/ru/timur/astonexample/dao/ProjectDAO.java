package ru.timur.astonexample.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.timur.astonexample.models.Employee;
import ru.timur.astonexample.models.Project;
import javax.persistence.Query;
import java.util.List;

@Component
public class ProjectDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public ProjectDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Project> getAllProjects(){
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from Project p", Project.class)
                .getResultList();
    }


    @Transactional
    public void save(Project project){
        Session session = sessionFactory.getCurrentSession();
        session.save(project);
    }

    @Transactional(readOnly = true)
    public List<Employee> getAllEmployeeOnProject(int id){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT e FROM Employee e JOIN e.projects p WHERE p.id IN (:projectId)");
        query.setParameter("projectId", id);

        List<Employee> employees = query.getResultList();
        return employees;
    }

    @Transactional
    public void deleteEmp(int projectId,int empId){
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            Employee employee = session.get(Employee.class, empId);
            Project project = session.get(Project.class, projectId);

            if (employee != null && project != null) {
                employee.getProjects().remove(project);
                project.getEmployees().remove(employee);

                session.saveOrUpdate(employee);
                session.saveOrUpdate(project);

                tx.commit();
            }
        }
    }

    @Transactional
    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Project.class, id));
    }
}
