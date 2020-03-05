package fr.excilys.formation.cli.service;

import java.util.List;
import java.util.Optional;

import fr.excilys.formation.cli.dao.ComputerDAO;
import fr.excilys.formation.cli.models.Computer;

public class ComputerService {

	private static volatile ComputerService instance = null;

	private ComputerService() {
	}

	public final static ComputerService getInstance() {
		if (ComputerService.instance == null) {
			synchronized(ComputerService.class) {
				if (ComputerService.instance == null) {
					ComputerService.instance = new ComputerService();
				}
			}
		}
		return ComputerService.instance;
	}

	ComputerDAO pcDaoInstance = ComputerDAO.getInstance(); 

	public boolean create(Computer computer) {
		return pcDaoInstance.create(computer);
	}

	public boolean update(Computer computer) {
		return pcDaoInstance.update(computer);
	}

	public boolean delete(String ids) {
		return pcDaoInstance.delete(ids);
	}

	public boolean deleteFromConsole(int id) {
		return pcDaoInstance.deleteComputerFromConsole(id);
	}

	public Optional<Computer> findById(int id) {
		return pcDaoInstance.findById(id);
	}

	public List<Computer> findByName(String name, int noPage, int nbLine, String orderBy){
		return pcDaoInstance.findByName(name, noPage, nbLine, orderBy);
	}

	public List<Computer> getList(){
		return pcDaoInstance.getList();
	}

	public List<Computer> getListPerPage(int noPage, int nbLine,String orderBy){
		return pcDaoInstance.getListPerPage(noPage, nbLine, orderBy);
	}

	public int recordsFoundByName(String name){
		return pcDaoInstance.recordsFoundByName(name);
	}
	
	public int allRecords() {
		return pcDaoInstance.allRecords();
	}
}
