package fr.excilys.formation.cdb.dao;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import fr.excilys.formation.cdb.mapper.ComputerMapper;
import fr.excilys.formation.cdb.model.Computer;

import javax.persistence.TypedQuery;

@Repository
public class ComputerDAO {

    private SessionFactory sessionFactory;
    ComputerMapper computerMapper;

    public ComputerDAO(SessionFactory sessionFactory, ComputerMapper computerMapper) {
        this.sessionFactory = sessionFactory;
        this.computerMapper = computerMapper;
    }

    public void create(Computer computer) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(computer);
    }

    public void delete(String ids) {
        Pattern pattern = Pattern.compile(",");
        List<Integer> idList = pattern.splitAsStream(ids).map(Integer :: valueOf).collect(Collectors.toList());

        String sqlCommand = SQLCommands.DELETE_COMPUTERS.getSqlCommands();

        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery query = session.createQuery(sqlCommand);

        query.setParameter("computerIds", idList);

        query.executeUpdate();
    }

    public boolean deleteFromConsole(int computerId) {
        boolean isDeleted = false;

        if (!findById(computerId).isEmpty()) {
            String sqlCommand = SQLCommands.DELETE_COMPUTER.getSqlCommands();

            Session session = this.sessionFactory.getCurrentSession();
            TypedQuery query = session.createQuery(sqlCommand);

            query.setParameter("computerId", computerId);

            query.executeUpdate();

            isDeleted = true;
        }

        return isDeleted;
    }

    public void update(Computer computer) {
        String sqlCommand = SQLCommands.UPDATE_COMPUTER.getSqlCommands();

        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery query = session.createQuery(sqlCommand);

        query.setParameter("computerId", computer.getId())
                .setParameter("computerName", computer.getName())
                .setParameter("introduced", computer.getIntroduced())
                .setParameter("discontinued", computer.getDiscontinued());

        setCompanyId(computer, query);

        query.executeUpdate();
    }

    public List<Computer> findById(int computerId) {
        String sqlCommand = SQLCommands.FIND_COMPUTER.getSqlCommands();

        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery<Computer> query = session.createQuery(sqlCommand);

        query.setParameter("computerId", computerId);

        return query.getResultList();
    }

    public List<Computer> findByName(String name, int noPage, int nbLine, String orderBy) {
        String sqlCommand = SQLCommands.FIND_COMPUTERS_BY_NAME.getSqlCommands() +
                SQLCommands.ORDER_BY.getSqlCommands() + orderBy;

        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery<Computer> query = session.createQuery(sqlCommand);
        query.setParameter("computerName", '%' + name + '%')
                .setFirstResult((noPage - 1) * nbLine).setMaxResults(nbLine);

        return query.getResultList();
    }

    public List<Computer> getList() {
        String sqlCommand = SQLCommands.FIND_COMPUTERS.getSqlCommands();

        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery<Computer> query = session.createQuery(sqlCommand);

        return query.getResultList();
    }

    public List<Computer> getListPerPage(int noPage, int nbLine, String orderBy) {
        String sqlCommand = SQLCommands.FIND_COMPUTERS.getSqlCommands() +
                SQLCommands.ORDER_BY.getSqlCommands() + orderBy;

        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery<Computer> query = session.createQuery(sqlCommand);

        query.setFirstResult((noPage - 1) * nbLine);
        query.setMaxResults(nbLine);

        return query.getResultList();
    }

    public int countAll() {
        String sqlCommand = SQLCommands.COUNT_COMPUTERS.getSqlCommands();

        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery(sqlCommand);

        return Math.toIntExact((Long) query.getSingleResult());
    }

    public int foundByName(String name) {
        String sqlCommand = SQLCommands.COUNT_COMPUTERS_FOUND_BY_NAME.getSqlCommands();

        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery(sqlCommand);

        query.setParameter("computerName", '%' + name + '%');

        return Math.toIntExact((Long) query.getSingleResult());
    }

    private void setCompanyId(Computer computer, TypedQuery query) {
        if (computer.getCompany().getId() > 0) {
            query.setParameter("companyId", computer.getCompany().getId());
        } else {
            query.setParameter("companyId", null);
        }
    }
}