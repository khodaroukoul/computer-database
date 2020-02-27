package fr.excilys.formation.cli.daoTest;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.excilys.formation.cli.dao.ComputerDAO;

public class ComputerDAOTest {

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}
	@Test
	public void testFindComputer() {
		assertTrue(ComputerDAO.getInstance().find(5).isPresent());
		assertFalse(ComputerDAO.getInstance().find(0).isPresent());
	}
	@Test
	public void testDeleteComputer() {
		assertTrue(ComputerDAO.getInstance().delete(2));
		assertFalse(ComputerDAO.getInstance().delete(0));
	}
	
	

}
