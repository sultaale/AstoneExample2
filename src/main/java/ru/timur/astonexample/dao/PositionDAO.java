package ru.timur.astonexample.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.timur.astonexample.models.Employee;
import ru.timur.astonexample.models.Position;
import javax.persistence.Query;
import java.util.List;

@Component
public class PositionDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PositionDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Position> getAllPositions(){
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from Position p",Position.class).getResultList();
    }

    @Transactional
    public void save(Position position){
        Session session = sessionFactory.getCurrentSession();
        session.save(position);
    }

    @Transactional(readOnly = true)
    public List<Employee> getAllEmployeeOnPosition(int id){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select e from Employee e where e.position.id = :positionId");
        query.setParameter("positionId", id);

        List<Employee> employees = query.getResultList();

        return employees;
    }

    @Transactional
    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Position.class, id));
    }
}
