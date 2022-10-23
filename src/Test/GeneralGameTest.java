package Test;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Game.SOSGame;

public class GeneralGameTest {
	
	private SOSGame sOSGame = new SOSGame(3);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGeneralGameBlueWins() {
		sOSGame.makeMove(0, 0);
		sOSGame.setRedPlayerGamePieceO(null);
		sOSGame.makeMove(0, 1);
		sOSGame.setRedPlayerGamePieceS(null);
		sOSGame.makeMove(0, 2);
		sOSGame.checkForSOS(0, 2);
		sOSGame.makeMove(1, 0);
		sOSGame.makeMove(1, 1);
		sOSGame.makeMove(1, 2);
		sOSGame.makeMove(2, 0);
		sOSGame.makeMove(2, 1);
		sOSGame.makeMove(2, 2);
		sOSGame.checkForWin();
		assertTrue(sOSGame.getGameState() == SOSGame.GameState.BLUE_WON);
	}
	
	@Test
	public void testGeneralGameRedWins() {
		sOSGame.makeMove(2, 2);
		sOSGame.makeMove(0, 0);
		sOSGame.setBluePlayerGamePieceO(null);
		sOSGame.makeMove(0, 1);
		sOSGame.setBluePlayerGamePieceS(null);
		sOSGame.makeMove(0, 2);
		sOSGame.checkForSOS(0, 2);
		sOSGame.makeMove(1, 0);
		sOSGame.makeMove(1, 1);
		sOSGame.makeMove(1, 2);
		sOSGame.makeMove(2, 0);
		sOSGame.makeMove(2, 1);
		sOSGame.checkForWin();
		assertTrue(sOSGame.getGameState() == SOSGame.GameState.RED_WON);
	}
	
	@Test
	public void testGeneralGameDraw() {
		sOSGame.makeMove(0, 0);
		sOSGame.makeMove(0, 1);
		sOSGame.makeMove(0, 2);
		sOSGame.makeMove(1, 0);
		sOSGame.makeMove(1, 1);
		sOSGame.makeMove(1, 2);
		sOSGame.makeMove(2, 0);
		sOSGame.makeMove(2, 1);
		sOSGame.makeMove(2, 2);
		sOSGame.checkForWin();
		assertTrue(sOSGame.getGameState() == SOSGame.GameState.DRAW);
	}

}