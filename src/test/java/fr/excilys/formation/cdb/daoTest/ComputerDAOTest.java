package fr.excilys.formation.cdb.daoTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.excilys.formation.cdb.service.ComputerService;
import fr.excilys.formation.cdb.spring.config.SpringContextConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringContextConfig.class})
public class ComputerDAOTest {
	
	@Autowired
	ComputerService pcServiceInstance;
	
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
