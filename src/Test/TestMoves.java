package Test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Game.SOSGame;

public class TestMoves {
	
	private SOSGame sOSGame;
	
	@Before
	public void setUp() throws Exception {
		sOSGame = new SOSGame();
		sOSGame.makeMove(1, 1); //Blue Player's Move
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMakeMove() {
		sOSGame.makeMove(0, 0); //Red Player's Move
		assertEquals("", sOSGame.getCell(0, 0), SOSGame.Cell.S);
		assertEquals("", sOSGame.getTurn(), SOSGame.CurrentTurn.BLUE);
	}
	
	@Test
	public void testMoveNonVacantCell() {
		sOSGame.makeMove(0, 0);
		sOSGame.makeMove(1, 0);
		assertEquals("", sOSGame.getTurn(), SOSGame.CurrentTurn.RED);
		sOSGame.makeMove(1, 0); // invalid move
		assertEquals("", sOSGame.getTurn(), SOSGame.CurrentTurn.RED);
	}

	@Test
	public void testTurnInvalidRowMove() {
		sOSGame.makeMove(10, 0); 
		assertEquals("", sOSGame.getTurn(), SOSGame.CurrentTurn.RED);
	}
}