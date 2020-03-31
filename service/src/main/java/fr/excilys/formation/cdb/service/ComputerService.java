package fr.excilys.formation.cdb.service;

import fr.excilys.formation.cdb.dao.ComputerDAO;
import fr.excilys.formation.cdb.dto.ComputerDTO;
import fr.excilys.formation.cdb.mapper.ComputerMapper;
import fr.excilys.formation.cdb.model.Computer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComputerService {

    private final ComputerDAO computerDao;
    private final ComputerMapper computerMapper;

    public ComputerService(ComputerDAO computerDao, ComputerMapper computerMapper) {
        this.computerDao = computerDao;
        this.computerMapper = computerMapper;
    }

    @Transactional
    public void create(Computer computer) {
        computerDao.create(computer);
    }

    @Transactional
    public void update(Computer computer) {
        computerDao.update(computer);
    }

    @Transactional
    public void delete(String ids) {
        computerDao.delete(ids);
    }

    @Transactional
    public int deleteFromConsole(int id) {
        return computerDao.deleteFromConsole(id);
    }

    @Transactional
    public Optional<Computer> findById(int id) {
        return computerDao.findById(id);
    }

    @Transactional
    public List<Computer> findByName(String name, int noPage, int nbLine, String orderBy) {
        return computerDao.findByName(name, noPage, nbLine, orderBy);
    }

    @Transactional
    public List<Computer> getListPerPage(int noPage, int nbLine, String orderBy) {
        return computerDao.getListPerPage(noPage, nbLine, orderBy);
    }

    @Transactional
    public int foundByName(String name) {
        return computerDao.foundByName(name);
    }

    @Transactional
    public int countAll() {
        return computerDao.countAll();
    }

    @Transactional
    public int noOfComputers(String searchPcByName) {
        int noOfComputers;
        if (searchPcByName != null && !searchPcByName.isBlank()) {
            noOfComputers = foundByName(searchPcByName);
        } else {
            noOfComputers = countAll();
        }

        return noOfComputers;
    }

    @Transactional
    public List<ComputerDTO> listComputerDTO(int currentPage, int computersPerPage, String order, String searchByName) {
        List<Computer> computers;
        List<ComputerDTO> computersDTO;
        String orderBy = QuerySQL.sortSQL(order);

        if (searchByName != null && !searchByName.isBlank()) {
            computers = findByName(searchByName, currentPage, computersPerPage, orderBy);
        } else {
            computers = getListPerPage(currentPage, computersPerPage, orderBy);
        }
        computersDTO = computers.stream().map(computerMapper::fromComputerToComputerDTO)
                .collect(Collectors.toList());

        return computersDTO;
    }
}