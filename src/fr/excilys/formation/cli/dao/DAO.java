package fr.excilys.formation.cli.dao;

import java.sql.Connection;
import java.util.List;

import fr.excilys.formation.cli.jdbc.ConnectionMySQL;

public abstract class DAO<T> {
  
  public Connection connect =  ConnectionMySQL.getInstance().getConnection();
  
  
  public abstract T create(T obj);

  public abstract void delete(T obj);

  public abstract T update(T obj);
  
  public abstract List<T> getList();
}
