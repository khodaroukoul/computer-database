package fr.excilys.formation.cdb.dao;

import fr.excilys.formation.cdb.model.Computer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Repository
@SuppressWarnings("unchecked")
public class ComputerDAO {

    private final SessionFactory sessionFactory;

    public ComputerDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(Computer computer) {
        String sqlCommand = SQLCommands.NEW_COMPUTER.getSqlCommands();

        Session session = this.sessionFactory.getCurrentSession();

        TypedQuery<?> query = session.createSQLQuery(sqlCommand);
        query.setParameter("computerId", getLastId(session) + 1)
                .setParameter("computerName", computer.getName())
                .setParameter("introduced", computer.getIntroduced())
                .setParameter("discontinued", computer.getDiscontinued());
        setCompanyId(computer, query);

        query.executeUpdate();
    }

    public void update(Computer computer) {
        String sqlCommand = SQLCommands.UPDATE_COMPUTER.getSqlCommands();

        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery<?> query = session.createQuery(sqlCommand);
        query.setParameter("computerId", computer.getId())
                .setParameter("computerName", computer.getName())
                .setParameter("introduced", computer.getIntroduced())
                .setParameter("discontinued", computer.getDiscontinued());
        setCompanyId(computer, query);

        query.executeUpdate();
    }

    public void delete(String ids) {
        Pattern pattern = Pattern.compile(",");
        List<Integer> idList = pattern.splitAsStream(ids).map(Integer::valueOf)
                .collect(Collectors.toList());

        String sqlCommand = SQLCommands.DELETE_COMPUTERS.getSqlCommands();

        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery<?> query = session.createQuery(sqlCommand);
        query.setParameter("computerIds", idList);

        query.executeUpdate();
    }

    public int deleteFromConsole(int computerId) {
        int isDeleted = 0;

        if (findById(computerId).isPresent()) {
            String sqlCommand = SQLCommands.DELETE_COMPUTER.getSqlCommands();

            Session session = this.sessionFactory.getCurrentSession();
            TypedQuery<?> query = session.createQuery(sqlCommand);
            query.setParameter("computerId", computerId);

            isDeleted = query.executeUpdate();
        }

        return isDeleted;
    }

    public Optional<Computer> findById(int computerId) {
        Session session = this.sessionFactory.getCurrentSession();

        return Optional.ofNullable(session.get(Computer.class, computerId));
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
        TypedQuery<?> query = session.createQuery(sqlCommand);

        return Math.toIntExact((Long) query.getSingleResult());
    }

    public int foundByName(String name) {
        String sqlCommand = SQLCommands.COUNT_COMPUTERS_FOUND_BY_NAME.getSqlCommands();

        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery<?> query = session.createQuery(sqlCommand);
        query.setParameter("computerName", '%' + name + '%');

        return Math.toIntExact((Long) query.getSingleResult());
    }

    private void setCompanyId(Computer computer, TypedQuery<?> query) {
        if (computer.getCompany().getId() > 0) {
            query.setParameter("companyId", computer.getCompany().getId());
        } else {
            query.setParameter("companyId", null);
        }
    }

    private int getLastId(Session session) {
        String lastIdSql = SQLCommands.LAST_ID.getSqlCommands();
        TypedQuery<?> lastIdQuery = session.createSQLQuery(lastIdSql);
        Number lastIdDb = (Number) lastIdQuery.getSingleResult();

        int lastId = 0;

        if (lastIdDb != null) {
            lastId = lastIdDb.intValue();
        }

        return lastId;
    }
}