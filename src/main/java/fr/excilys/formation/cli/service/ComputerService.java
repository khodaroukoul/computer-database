package fr.excilys.formation.cli.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import fr.excilys.formation.cli.dao.ComputerDAO;
import fr.excilys.formation.cli.dto.ComputerDTO;
import fr.excilys.formation.cli.mapper.ComputerMapper;
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

	public void create(Computer computer) {
		pcDaoInstance.create(computer);
	}

	public void update(Computer computer) {
		pcDaoInstance.update(computer);
	}

	public void delete(String ids) {
		pcDaoInstance.delete(ids);
	}

	public boolean deleteFromConsole(int id) {
		return pcDaoInstance.deleteFromConsole(id);
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

	public int FoundByName(String name){
		return pcDaoInstance.FoundByName(name);
	}
	
	public int countAll() {
		return pcDaoInstance.countAll();
	}
	
	public int noOfComputers(String searchPcByName) {
		int noOfComputers;
		if(searchPcByName!=null && !searchPcByName.isBlank()) {
			noOfComputers = FoundByName(searchPcByName);
		} else {
			noOfComputers = countAll();
		}
		return noOfComputers;
	}

	public List<ComputerDTO> listComputerDTO(int currentPage, int computersPerPage, String orderBy,
			String searchPcByName) {
		List<Computer> computers;
		List<ComputerDTO> computersDTO;
		if(searchPcByName!=null && !searchPcByName.isBlank()) {
			computers = findByName(searchPcByName,currentPage,computersPerPage,orderBy);
			computersDTO = computers.stream().map(s -> ComputerMapper.FromComputerToComputerDTO(s)).
					collect(Collectors.toList());
		} else {
			computers = getListPerPage(currentPage,computersPerPage,orderBy);
			computersDTO = computers.stream().map(s -> ComputerMapper.FromComputerToComputerDTO(s)).
					collect(Collectors.toList());
		}
		return computersDTO;
	}
}
