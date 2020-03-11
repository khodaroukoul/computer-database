package fr.excilys.formation.cdb.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.excilys.formation.cdb.dao.ComputerDAO;
import fr.excilys.formation.cdb.dto.ComputerDTO;
import fr.excilys.formation.cdb.mapper.ComputerMapper;
import fr.excilys.formation.cdb.models.Computer;

@Service
public class ComputerService {

	ComputerDAO pcDao;

	@Autowired
	public ComputerService(ComputerDAO pcDao) {
		this.pcDao = pcDao;
	}

	public void create(Computer computer) {
		pcDao.create(computer);
	}

	public void update(Computer computer) {
		pcDao.update(computer);
	}

	public void delete(String ids) {
		pcDao.delete(ids);
	}

	public boolean deleteFromConsole(int id) {
		return pcDao.deleteFromConsole(id);
	}

	public Optional<Computer> findById(int id) {
		return pcDao.findById(id);
	}

	public List<Computer> findByName(String name, int noPage, int nbLine, String orderBy){
		return pcDao.findByName(name, noPage, nbLine, orderBy);
	}

	public List<Computer> getList(){
		return pcDao.getList();
	}

	public List<Computer> getListPerPage(int noPage, int nbLine,String orderBy){
		return pcDao.getListPerPage(noPage, nbLine, orderBy);
	}

	public int FoundByName(String name){
		return pcDao.FoundByName(name);
	}

	public int countAll() {
		return pcDao.countAll();
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

	public List<ComputerDTO> listComputerDTO(int currentPage, int computersPerPage, String order,
			String searchPcByName) {
		List<Computer> computers;
		List<ComputerDTO> computersDTO;
		String orderBy = QuerySQL.sortSQL(order);
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
