package Test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Game.SOSGame;
import Game.SOSGame.Cell;

public class BoardTest {

	private SOSGame sOSGame = new SOSGame();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNewBoard() {
		for (int row = 0; row<sOSGame.getSize(); row++) {
			for (int column = 0; column<sOSGame.getSize(); column++) {
				assertTrue(sOSGame.getCell(row, column) == Cell.EMPTY);
			}
		}
	}
	
	@Test
	public void testInvalidRow() {
		assertEquals("", sOSGame.getCell(10, 0), null); 
	}

	@Test
	public void testInvalidColumn() {
		assertEquals("", sOSGame.getCell(10, 3), null); 
	}
}