package fr.excilys.formation.cdb.daoTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.excilys.formation.cdb.configuration.SpringConfig;
import fr.excilys.formation.cdb.service.ComputerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class ComputerDAOTest {
	
	@Autowired
	ComputerService computerService;
	
	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}
	
	@Test
	public void testFindComputer() {
		assertFalse(computerService.findById(5).isEmpty());
		assertTrue(computerService.findById(0).isEmpty());
	}

	@Test
	public void testDeleteComputer() {
		assertTrue(computerService.deleteFromConsole(2));
		assertFalse(computerService.deleteFromConsole(0));
	}
	
	@Test
	public void testcountComputer() {
		assertEquals(50,computerService.countAll());
	}
}
