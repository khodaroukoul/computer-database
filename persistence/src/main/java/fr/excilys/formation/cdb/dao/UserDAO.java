package fr.excilys.formation.cdb.dao;

import fr.excilys.formation.cdb.model.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class UserDAO {

    private final SessionFactory sessionFactory;

    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Users findByUserName(String username) {
        String sqlCommand = SQLCommands.ADD_USER.getSqlCommands();

        Session session = this.sessionFactory.getCurrentSession();
        List<Users> users = session.createQuery(sqlCommand).setParameter("username", username)
                .getResultList();

        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public void addNewUser(Users user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(user);
    }
}