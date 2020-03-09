package fr.excilys.formation.cli.daoTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.excilys.formation.cli.service.ComputerService;

public class ComputerDAOTest {
	ComputerService pcServiceInstance = ComputerService.getInstance();
	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}
	
	@Test
	public void testFindComputer() {
		assertTrue(pcServiceInstance.findById(5).isPresent());
		assertFalse(pcServiceInstance.findById(2).isPresent());
	}

	@Test
	public void testDeleteComputer() {
		assertTrue(pcServiceInstance.deleteFromConsole(2));
		assertFalse(pcServiceInstance.deleteFromConsole(0));
	}
}
