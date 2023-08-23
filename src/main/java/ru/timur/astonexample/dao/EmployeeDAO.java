package ru.timur.astonexample.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.timur.astonexample.models.Employee;
import java.util.List;

@Component
public class EmployeeDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public EmployeeDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Employee> getAllEmployee(){
        Session session = sessionFactory.getCurrentSession();

        System.out.println(session.createQuery("select e from Employee e", Employee.class)
                .getResultList());
        return session.createQuery("select e from Employee e", Employee.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public Employee show(int id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(Employee.class, id);
    }

    @Transactional
    public void save(Employee employee){
        Session session = sessionFactory.getCurrentSession();
        session.save(employee);
    }

    @Transactional
    public void update(Employee updatedEmp,int id){
        Session session = sessionFactory.getCurrentSession();
        Employee employeeToBeUpdated = session.get(Employee.class, id);

        employeeToBeUpdated.setFirstName(updatedEmp.getFirstName());
        employeeToBeUpdated.setLastName(updatedEmp.getLastName());
        employeeToBeUpdated.setPosition(updatedEmp.getPosition());
        employeeToBeUpdated.setProjects(updatedEmp.getProjects());
    }

    @Transactional
    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Employee.class, id));
    }
}
