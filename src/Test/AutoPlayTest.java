package Test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Game.SOSGame;
import Game.SOSGame.GameState;
import Game.SOSGame.CurrentTurn;

public class AutoPlayTest {
	
	private SOSGame sOSGame;

	@Before
	public void setUp() throws Exception {
		sOSGame = new SOSGame();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void redPlayerAutoMovetest() {
		sOSGame.setRedPlayerAutoPlayOn();
		sOSGame.updateGameState();
		assertTrue(sOSGame.getGameState() == GameState.PLAYING_AUTO_RED);
		assertTrue(sOSGame.getTurn() == CurrentTurn.BLUE);
		sOSGame.makeMove(0, 0);
		sOSGame.makeMoveAutoPlay();
		sOSGame.updateGameState();
		assertTrue(sOSGame.getGameState() == GameState.PLAYING_AUTO_RED);
		assertTrue(sOSGame.getTurn() == CurrentTurn.BLUE);
	}

	@Test
	public void bluePlayerAutoMovetest() {
		sOSGame.setBluePlayerAutoPlayOn();
		sOSGame.updateGameState();
		assertTrue(sOSGame.getGameState() == GameState.PLAYING_AUTO_BLUE);
		assertTrue(sOSGame.getTurn() == CurrentTurn.BLUE);
		sOSGame.makeMoveAutoPlay();
		sOSGame.updateGameState();
		assertTrue(sOSGame.getGameState() == GameState.PLAYING_AUTO_BLUE);
		assertTrue(sOSGame.getTurn() == CurrentTurn.RED);
	}
	
	@Test
	public void blueAndRedPlayerAutoMoveTest() {
		sOSGame.setBluePlayerAutoPlayOn();
		sOSGame.setRedPlayerAutoPlayOn();
		sOSGame.updateGameState();
		assertTrue(sOSGame.getGameState() == GameState.PLAYING_AUTO_BOTH);
	}
}
