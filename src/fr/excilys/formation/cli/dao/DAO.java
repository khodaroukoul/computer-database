package fr.excilys.formation.cli.dao;

import java.util.List;


public abstract class DAO<T> {

  public abstract T create(T obj);

  public abstract boolean delete(int id);

  public abstract T update(T obj);
  
  public abstract T find(int id);
  
  public abstract List<T> getList();
}
