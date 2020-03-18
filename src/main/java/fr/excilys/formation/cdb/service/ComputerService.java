package fr.excilys.formation.cdb.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import fr.excilys.formation.cdb.dao.ComputerDAO;
import fr.excilys.formation.cdb.dto.ComputerDTO;
import fr.excilys.formation.cdb.mapper.ComputerMapper;
import fr.excilys.formation.cdb.model.Computer;

@Service
public class ComputerService {

    private ComputerDAO computerDao;
    private ComputerMapper computerMapper;

    public ComputerService(ComputerDAO computerDao, ComputerMapper computerMapper) {
        this.computerDao = computerDao;
        this.computerMapper = computerMapper;
    }

    public void create(Computer computer) {
        computerDao.create(computer);
    }

    public void update(Computer computer) {
        computerDao.update(computer);
    }

    public void delete(String ids) {
        computerDao.delete(ids);
    }

    public boolean deleteFromConsole(int id) {
        return computerDao.deleteFromConsole(id);
    }

    public List<Computer> findById(int id) {
        return computerDao.findById(id);
    }

    public List<Computer> findByName(String name, int noPage, int nbLine, String orderBy) {
        return computerDao.findByName(name, noPage, nbLine, orderBy);
    }

    public List<Computer> getList() {
        return computerDao.getList();
    }

    public List<Computer> getListPerPage(int noPage, int nbLine, String orderBy) {
        return computerDao.getListPerPage(noPage, nbLine, orderBy);
    }

    public int foundByName(String name) {
        return computerDao.foundByName(name);
    }

    public int countAll() {
        return computerDao.countAll();
    }

    public int noOfComputers(String searchPcByName) {
        int noOfComputers;
        if (searchPcByName != null && !searchPcByName.isBlank()) {
            noOfComputers = foundByName(searchPcByName);
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

        if (searchPcByName != null && !searchPcByName.isBlank()) {
            computers = findByName(searchPcByName, currentPage, computersPerPage, orderBy);
            computersDTO = computers.stream().map(computerMapper::FromComputerToComputerDTO).
                    collect(Collectors.toList());
        } else {
            computers = getListPerPage(currentPage, computersPerPage, orderBy);
            computersDTO = computers.stream().map(computerMapper::FromComputerToComputerDTO).
                    collect(Collectors.toList());
        }

        return computersDTO;
    }
}
