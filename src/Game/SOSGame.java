package Game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Game.MainWindow.GameBoardCanvas;

public class SOSGame {
	
	public enum Cell {EMPTY, S, O, LOCKED}
	public enum GameState {PLAYING, DRAW, BLUE_WON, RED_WON, NOT_PLAYING, PLAYING_AUTO_BLUE, PLAYING_AUTO_RED, PLAYING_AUTO_BOTH}
	public enum CurrentTurn {BLUE, RED}
	public enum GamePiece {S, O}
	public enum GameType {simple, general}
	
	private int size = 6;
	
	private Cell[][] grid;
	private CurrentTurn turn;
	private GameState currentGameState = GameState.NOT_PLAYING;
	private GamePiece redPlayerGamePiece = GamePiece.S;
	private GamePiece bluePlayerGamePiece = GamePiece.S;
	private GameType currentGameType = GameType.simple;
	private int bluePlayerScore = 0;
	private int redPlayerScore = 0;
	private boolean bluePlayerAutoPlay = false;
	private boolean redPlayerAutoPlay = false;
	private boolean gameIsRecording = false;
	
	public void setGameIsRecordingTrue(){
		gameIsRecording = true;
	}
	
	public void setGameIsRecordingFalse(){
		gameIsRecording = false;
	}
	
	public boolean getGameIsRecording(){
		return gameIsRecording;
	}
	
	ArrayList<SOSGameLines> listOfLinesBlue = new ArrayList<SOSGameLines>();
	ArrayList<SOSGameLines> listOfLinesRed = new ArrayList<SOSGameLines>();
	
	public class SOSGameLines {
		int xOne;
		int yOne;
		int xTwo;
		int yTwo;

	}
	
	public void SOSGameLine(int x1, int y1, int x2, int y2) {
		}
	
	public SOSGame() {
		grid = new Cell[size][size];
		initBoard();
	}
	
	public SOSGame(int size) {
		this.size = size;
		grid = new Cell[size][size];
		initBoard();
	}
	
	public void initBoard() {
		for (int row = 0; row < size; row++) {
			for(int column = 0; column < size; column++) {
				grid[row][column] = Cell.EMPTY;
			}
		}
		currentGameState = GameState.PLAYING;
		turn = turn.BLUE;
	}
	
	public int getSize() {
		return size;
	}
	
	public Cell getCell(int row, int column) {
		if (row >= 0 && row < size && column >= 0 && column < size)
			return grid[row][column];
		else 
			return null;
	}
	
	public CurrentTurn getTurn() {
		return turn;
	}
	
	public void makeMove(int row, int column) {
		if (row >= 0 && row < size && column >= 0 && column < size && grid[row][column] == Cell.EMPTY) {
			if (turn == turn.BLUE) {
			grid[row][column] = (bluePlayerGamePiece == GamePiece.S) ? Cell.S : Cell.O;
			}
			else if (turn == turn.RED) {
			grid[row][column] = (redPlayerGamePiece == GamePiece.S) ? Cell.S : Cell.O;
			}
			if (gameIsRecording) {
				writeMove(row,column);
			}
			turn = (turn == turn.BLUE) ? turn.RED : turn.BLUE; 
		}
	}

	public GamePiece getBluePlayerGamePiece() {
		return bluePlayerGamePiece;
	}

	public void setBluePlayerGamePieceS(GamePiece bluePlayerGamePiece) {
		this.bluePlayerGamePiece = GamePiece.S;
	}
	
	public void setBluePlayerGamePieceO(GamePiece bluePlayerGamePiece) {
		this.bluePlayerGamePiece = GamePiece.O;
	}
	
	public GamePiece getRedPlayerGamePiece() {
		return redPlayerGamePiece;
	}
	
	public void setRedPlayerGamePieceS(GamePiece bluePlayerGamePiece) {
		this.redPlayerGamePiece = GamePiece.S;
	}

	public void setRedPlayerGamePieceO(GamePiece bluePlayerGamePiece) {
		this.redPlayerGamePiece = GamePiece.O;
	}
	
	public void setGameTypeSimple(GameType currentGameType) {
		this.currentGameType = GameType.simple;
	}
	
	public void setGameTypeGeneral(GameType currentGameType) {
		this.currentGameType = GameType.general;
	}

	public GameType getCurrentGameType() {
		return currentGameType;
	}
	
	public void checkForSOS(int row, int column) {
		if (grid[row][column] == Cell.S) {
			//N
			try {
				if (grid[row-1][column] == Cell.O && grid[row-2][column] == Cell.S) {
					//draw line
					int rowOne = row;
					int columnOne = column;
					int rowTwo = (row-2);
					int columnTwo = column;
					addLineToList(rowOne, columnOne, rowTwo, columnTwo);
				}
			}
			catch (ArrayIndexOutOfBoundsException ex) {
			}
			//NE
			try {
				if (grid[row-1][column+1] == Cell.O && grid[row-2][column+2] == Cell.S) {
					//draw line
					int rowOne = row;
					int columnOne = column;
					int rowTwo = (row-2);
					int columnTwo = (column+2);
					addLineToList(rowOne, columnOne, rowTwo, columnTwo);
				}
			}
			catch (ArrayIndexOutOfBoundsException ex) {
			}
			//E
			try {
				if (grid[row][column+1] == Cell.O && grid[row][column+2] == Cell.S) {
					//draw line
					int rowOne = row;
					int columnOne = column;
					int rowTwo = row;
					int columnTwo = (column+2);
					addLineToList(rowOne, columnOne, rowTwo, columnTwo);
				}
			}
			catch (ArrayIndexOutOfBoundsException ex) {
			}
			//SE
			try {
				if (grid[row+1][column+1] == Cell.O && grid[row+2][column+2] == Cell.S) {
					//draw line
					int rowOne = row;
					int columnOne = column;
					int rowTwo = (row+2);
					int columnTwo = (column+2);
					addLineToList(rowOne, columnOne, rowTwo, columnTwo);
				}
			}
			catch (ArrayIndexOutOfBoundsException ex) {
			}
			//S
			try {
				if (grid[row+1][column] == Cell.O && grid[row+2][column] == Cell.S) {
					//draw line
					int rowOne = row;
					int columnOne = column;
					int rowTwo = (row+2);
					int columnTwo = column;
					addLineToList(rowOne, columnOne, rowTwo, columnTwo);
				}
			}
			catch (ArrayIndexOutOfBoundsException ex) {
			}
			//SW
			try {
				if (grid[row+1][column-1] == Cell.O && grid[row+2][column-2] == Cell.S) {
					//draw line
					int rowOne = row;
					int columnOne = column;
					int rowTwo = (row+2);
					int columnTwo = (column-2);
					addLineToList(rowOne, columnOne, rowTwo, columnTwo);
				}
			}
			catch (ArrayIndexOutOfBoundsException ex) {
			}
			//W
			try {
			if (grid[row][column-1] == Cell.O && grid[row][column-2] == Cell.S) {
				//draw line
				int rowOne = row;
				int columnOne = column;
				int rowTwo = row;
				int columnTwo = (column-2);
				addLineToList(rowOne, columnOne, rowTwo, columnTwo);
			}
			}
			catch (ArrayIndexOutOfBoundsException ex) {
			}
			//NW
			try {
				if (grid[row-1][column-1] == Cell.O && grid[row-2][column-2] == Cell.S) {
					//draw line
					int rowOne = row;
					int columnOne = column;
					int rowTwo = (row-2);
					int columnTwo = (column-2);
					addLineToList(rowOne, columnOne, rowTwo, columnTwo);
				}
			}
			catch (ArrayIndexOutOfBoundsException ex) {
			}
		}
		if (grid[row][column] == Cell.O) {
			//N&S
			try {
				if (grid[row-1][column] == Cell.S && grid[row+1][column] == Cell.S) {
					//draw line
					int rowOne = (row-1);
					int columnOne = column;
					int rowTwo = (row+1);
					int columnTwo = column;
					addLineToList(rowOne, columnOne, rowTwo, columnTwo);
				}
			}
			catch (ArrayIndexOutOfBoundsException ex) {
			}
			//NE&SW
			try {
				if (grid[row-1][column+1] == Cell.S && grid[row+1][column-1] == Cell.S) {
					//draw line
					int rowOne = (row-1);
					int columnOne = (column+1);
					int rowTwo = (row+1);
					int columnTwo = (column-1);
					addLineToList(rowOne, columnOne, rowTwo, columnTwo);
				}
			}
			catch (ArrayIndexOutOfBoundsException ex) {
			}
			//E&W
			try {
				if (grid[row][column-1] == Cell.S && grid[row][column+1] == Cell.S) {
					//draw line
					int rowOne = row;
					int columnOne = (column-1);
					int rowTwo = row;
					int columnTwo = (column+1);
					addLineToList(rowOne, columnOne, rowTwo, columnTwo);
				}
			}
			catch (ArrayIndexOutOfBoundsException ex) {
			}
			//SE&NW
			try {
				if (grid[row+1][column+1] == Cell.S && grid[row-1][column-1] == Cell.S) {
					//draw line
					int rowOne = (row+1);
					int columnOne = (column+1);
					int rowTwo = (row-1);
					int columnTwo = (column-1);
					addLineToList(rowOne, columnOne, rowTwo, columnTwo);
				}
			}
			catch (ArrayIndexOutOfBoundsException ex) {
			}
		}
	}
	
	private void addLineToList(int rowOne, int columnOne, int rowTwo, int columnTwo) {
		int x1 = (rowOne*100)+50;
		int y1 = (columnOne*100)+50;
		int x2 = (rowTwo*100)+50;
		int y2 = (columnTwo*100)+50;
		
		SOSGameLines newLine = new SOSGameLines();
		newLine.xOne = x1;
		newLine.yOne = y1;
		newLine.xTwo = x2;
		newLine.yTwo = y2;
		
		boolean lineAlreadyExists = false;
		for (SOSGameLines x : listOfLinesBlue) {
			if (newLine.equals(x)) {
				lineAlreadyExists = true;
			}
		}
		for (SOSGameLines x : listOfLinesRed) {
			if (newLine.equals(x)) {
				lineAlreadyExists = true;
			}
		}
		
		if (lineAlreadyExists == false) {
			if (turn == CurrentTurn.RED) {
				listOfLinesBlue.add(newLine);
				bluePlayerScore++;
			}
			if (turn == CurrentTurn.BLUE) {
				listOfLinesRed.add(newLine);
				redPlayerScore++;
			}
		}
	}
	
	public void updateGameState() {
		if (bluePlayerAutoPlay == true && redPlayerAutoPlay == false) {
			currentGameState = GameState.PLAYING_AUTO_BLUE;
		}
		else if (bluePlayerAutoPlay == false && redPlayerAutoPlay == true) {
			currentGameState = GameState.PLAYING_AUTO_RED;
		}
		else if (bluePlayerAutoPlay == true && redPlayerAutoPlay == true) {
			currentGameState = GameState.PLAYING_AUTO_BOTH;
		}
		else if (bluePlayerAutoPlay == false && redPlayerAutoPlay == false) {
			currentGameState = GameState.PLAYING;
		}
		//if gamestate == playing
		//if gamestate == draw
		//if gamestate == bluewon
		//if gamestate == redwon
	}
	
	public void checkForWin() {
		if (currentGameType == GameType.simple && listOfLinesBlue.size() == 1
				|| currentGameType == GameType.simple && listOfLinesRed.size() == 1) {
			lockBoard();
			if (listOfLinesBlue.size() > listOfLinesRed.size()) {
				currentGameState = GameState.BLUE_WON;
			}
			if (listOfLinesBlue.size() < listOfLinesRed.size()) {
				currentGameState = GameState.RED_WON;
			}
		}
		if (currentGameType == GameType.simple && boardIsFull()) {
			if (listOfLinesBlue.size() == 0 && listOfLinesRed.size() == 0) {
				currentGameState = GameState.DRAW;
			}
		}
		if (currentGameType == GameType.general) {
			//game board is full, highest score wins
			if (boardIsFull()) {
				if (listOfLinesBlue.size() > listOfLinesRed.size()) {
					currentGameState = GameState.BLUE_WON;
				}
				if (listOfLinesBlue.size() < listOfLinesRed.size()) {
					currentGameState = GameState.RED_WON;
				}
				if (listOfLinesBlue.size() == listOfLinesRed.size()) {
					currentGameState = GameState.DRAW;
				}
			}
		}
		else if (currentGameState == GameState.PLAYING_AUTO_BLUE || currentGameState == GameState.PLAYING_AUTO_RED ||currentGameState == GameState.PLAYING_AUTO_BOTH) {
			//wait(10000);
		}
		if (gameIsRecording) {
		writeGameOver();
		}
	}
	
	private boolean boardIsFull() {
		Boolean isFull = true;
		for (int row = 0; row < size; ++row) {
			for (int col = 0; col < size; ++col) {
				if (grid[row][col] == Cell.EMPTY) {
					isFull = false;
					}
				}
			}
		if (isFull == true) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private void lockBoard() {
		for (int row = 0; row < size; row++) {
			for(int column = 0; column < size; column++) {
				if (grid[row][column] == Cell.EMPTY) {
				grid[row][column] = Cell.LOCKED;
				}
			}
		}
	}
	
	public GameState getGameState() {
		return currentGameState;
	}
	
	public void makeMoveAutoPlay() {
		int currentSOSCount = 0;
		int bestMoveSOSCountForS = 0;
		int bestMoveSOSCountForO = 0;
		int bestSMoveRow = 0;
		int bestSMoveColumn = 0;
		int bestOMoveRow = 0;
		int bestOMoveColumn = 0;
		for (int row = 0; row < size; row++) {
			for(int column = 0; column < size; column++) {
				if (grid[row][column] == Cell.EMPTY) {
					//N
					try {
						if (grid[row-1][column] == Cell.O && grid[row-2][column] == Cell.S) {
							currentSOSCount++;
						}
					}
					catch (ArrayIndexOutOfBoundsException ex) {
					}
					//NE
					try {
						if (grid[row-1][column+1] == Cell.O && grid[row-2][column+2] == Cell.S) {
							currentSOSCount++;
						}
					}
					catch (ArrayIndexOutOfBoundsException ex) {
					}
					//E
					try {
						if (grid[row][column+1] == Cell.O && grid[row][column+2] == Cell.S) {
							currentSOSCount++;
						}
					}
					catch (ArrayIndexOutOfBoundsException ex) {
					}
					//SE
					try {
						if (grid[row+1][column+1] == Cell.O && grid[row+2][column+2] == Cell.S) {
							currentSOSCount++;
						}
					}
					catch (ArrayIndexOutOfBoundsException ex) {
					}
					//S
					try {
						if (grid[row+1][column] == Cell.O && grid[row+2][column] == Cell.S) {
							currentSOSCount++;
						}
					}
					catch (ArrayIndexOutOfBoundsException ex) {
					}
					//SW
					try {
						if (grid[row+1][column-1] == Cell.O && grid[row+2][column-2] == Cell.S) {
							currentSOSCount++;
						}
					}
					catch (ArrayIndexOutOfBoundsException ex) {
					}
					//W
					try {
						if (grid[row][column-1] == Cell.O && grid[row][column-2] == Cell.S) {
							currentSOSCount++;
						}
					}
					catch (ArrayIndexOutOfBoundsException ex) {
					}
					//NW
					try {
						if (grid[row-1][column-1] == Cell.O && grid[row-2][column-2] == Cell.S) {
							currentSOSCount++;
						}
					}
					catch (ArrayIndexOutOfBoundsException ex) {
					}
					if (currentSOSCount > bestMoveSOSCountForS) {
						bestMoveSOSCountForS = currentSOSCount;
						bestSMoveRow = row;
						bestSMoveColumn = column;
						
					}
					currentSOSCount = 0;
				}
			}
		}
		for (int row = 0; row < size; row++) {
			for(int column = 0; column < size; column++) {
				if (grid[row][column] == Cell.EMPTY) {
					//N&S
					try {
						if (grid[row-1][column] == Cell.S && grid[row+1][column] == Cell.S) {
							currentSOSCount++;
						}
					}
					catch (ArrayIndexOutOfBoundsException ex) {
					}
					//NE&SW
					try {
						if (grid[row-1][column+1] == Cell.S && grid[row+1][column-1] == Cell.S) {
							currentSOSCount++;
						}
					}
					catch (ArrayIndexOutOfBoundsException ex) {
					}
					//E&W
					try {
						if (grid[row][column-1] == Cell.S && grid[row][column+1] == Cell.S) {
							currentSOSCount++;
						}
					}
					catch (ArrayIndexOutOfBoundsException ex) {
					}
					//SE&NW
					try {
						if (grid[row+1][column+1] == Cell.S && grid[row-1][column-1] == Cell.S) {
							currentSOSCount++;
						}
					}
					catch (ArrayIndexOutOfBoundsException ex) {
					}
					if (currentSOSCount > bestMoveSOSCountForO) {
						bestMoveSOSCountForO = currentSOSCount;
						bestOMoveRow = row;
						bestOMoveColumn = column;
						
					}
					currentSOSCount = 0;
				}
			}
	}
		double randChoiceForMove = (Math.random() * 1);
		int randChoiceForMoveInt = (int) randChoiceForMove;
		char randChoiceForMoveChar = 'A';
		if (bestMoveSOSCountForS == bestMoveSOSCountForO && bestMoveSOSCountForS != 0) {
			if (randChoiceForMoveInt == 0) {
				randChoiceForMoveChar = 'S';
			}
			else if (randChoiceForMoveInt == 1) {
				randChoiceForMoveChar = 'O';
			}
		}
		if (bestMoveSOSCountForS > bestMoveSOSCountForO || randChoiceForMoveChar == 'S') {
			if (turn == turn.BLUE) {
				setBluePlayerGamePieceS(bluePlayerGamePiece);
				grid[bestSMoveRow][bestSMoveColumn] = Cell.S;
				if (gameIsRecording) {
					writeMove(bestSMoveRow,bestSMoveColumn);
				}
			}
			if (turn == turn.RED) {
				setRedPlayerGamePieceS(redPlayerGamePiece);
				grid[bestSMoveRow][bestSMoveColumn] = Cell.S;
				if (gameIsRecording) {
					writeMove(bestSMoveRow,bestSMoveColumn);
				}
			}
			turn = (turn == turn.BLUE) ? turn.RED : turn.BLUE;
			checkForSOS(bestSMoveRow,bestSMoveColumn);
			checkForWin();
		}
		else if (bestMoveSOSCountForS < bestMoveSOSCountForO || randChoiceForMoveChar == 'O') {
			if (turn == turn.BLUE) {
				setBluePlayerGamePieceO(bluePlayerGamePiece);
				grid[bestOMoveRow][bestOMoveColumn] = Cell.O;
				if (gameIsRecording) {
					writeMove(bestOMoveRow,bestOMoveColumn);
				}
			}
			else if (turn == turn.RED) {
				setRedPlayerGamePieceO(redPlayerGamePiece);
				grid[bestOMoveRow][bestOMoveColumn] = Cell.O;
				if (gameIsRecording) {
					writeMove(bestOMoveRow,bestOMoveColumn);
				}
			}
			turn = (turn == turn.BLUE) ? turn.RED : turn.BLUE;
			checkForSOS(bestOMoveRow,bestOMoveColumn);
			checkForWin();
		}
		else if (bestMoveSOSCountForS == 0 && bestMoveSOSCountForO == 0) {
			//no moves available to make SOS, check for block and not setting up move
			//for now make random move
			boolean validMove = false;
			int randRow = 0;
			int randColumn = 0;
			double randGamePiece = (Math.random() * 1);
			int randGamePieceInt = (int) randGamePiece;
			
			while (!validMove) {
			double randRowCoordinate = (Math.random() * size);  
	        double randColumnCoordinate = (Math.random() * size);
	        int randRowCoordinateInt = (int) randRowCoordinate;
	        int randColumnCoordinateInt = (int) randColumnCoordinate;
	        if (grid[randRowCoordinateInt][randColumnCoordinateInt] == Cell.EMPTY) {
	        	randRow = randRowCoordinateInt;
	        	randColumn = randColumnCoordinateInt;
	        	validMove = true;
	        }
			}
	        
	        if (randGamePieceInt == 0) {
	        	if (turn == turn.BLUE) {
	        		setBluePlayerGamePieceS(bluePlayerGamePiece);
	        		grid[randRow][randColumn] = Cell.S;
	        	}
	        	else if (turn == turn.RED) {
	        		setRedPlayerGamePieceS(redPlayerGamePiece);
	        		grid[randRow][randColumn] = Cell.S;
	        	}
	        }
	        if (randGamePieceInt == 1) {
	        	if (turn == turn.BLUE) {
	        		setBluePlayerGamePieceO(bluePlayerGamePiece);
	        		grid[randRow][randColumn] = Cell.O;
	        	}
	        	else if (turn == turn.RED) {
	        		setRedPlayerGamePieceO(redPlayerGamePiece);
	        		grid[randRow][randColumn] = Cell.O;
	        	}
	        }
	        if (gameIsRecording) {
	        	writeMove(randRow, randColumn);
	        }
	        turn = (turn == turn.BLUE) ? turn.RED : turn.BLUE;
	        checkForSOS(randRow,randColumn);
	        checkForWin();
		}
	}
	
	public void setBluePlayerAutoPlayOn() {
		bluePlayerAutoPlay = true;
	}
	
	public void setBluePlayerAutoPlayOff() {
		bluePlayerAutoPlay = false;
	}
	
	public void setRedPlayerAutoPlayOn() {
		redPlayerAutoPlay = true;
	}
	
	public void setRedPlayerAutoPlayOff() {
		redPlayerAutoPlay = false;
	}
	
	public boolean getBluePlayerAutoPlay() {
		return bluePlayerAutoPlay;
	}
	
	public boolean getRedPlayerAutoPlay() {
		return redPlayerAutoPlay;
	}
	
	public void checkForAutoPlay() {
		if (currentGameState == GameState.PLAYING_AUTO_BOTH) {
			while (currentGameState == GameState.PLAYING_AUTO_BOTH) {
				makeMoveAutoPlay();
			}
		}
		if (currentGameState == GameState.PLAYING_AUTO_BLUE) {
			if (turn == turn.BLUE) {
			makeMoveAutoPlay();
			}
		}
		if (currentGameState == GameState.PLAYING_AUTO_RED) {
			if (turn == turn.RED) {
			makeMoveAutoPlay();
			}
		}
	}
	
	public static void wait(int ms)
	{
	    try
	    {
	        Thread.sleep(ms);
	    }
	    catch(InterruptedException ex)
	    {
	        Thread.currentThread().interrupt();
	    }
	}
	
	public int getBluePlayerScore() {
		return bluePlayerScore;
	}
	
	public int getRedPlayerScore() {
		return redPlayerScore;
	}
	
	public void writeMove(int row, int column) {
		if (getTurn() == turn.BLUE) {
			try {
				FileWriter file = new FileWriter("SOS_Game_Log.txt", true);
				PrintWriter output = new PrintWriter(file, true);
				output.write("Blue Player placed " + getBluePlayerGamePiece() + " at (" + row + "," + column + ")\n");
				output.close();
			} catch (IOException e) {
				e.getStackTrace();
			}
		}
		else if (getTurn() == turn.RED) {
			try {
				FileWriter file = new FileWriter("SOS_Game_Log.txt", true);
				PrintWriter output = new PrintWriter(file, true);
				output.write("Red Player placed " + getRedPlayerGamePiece() + " at (" + row + "," + column + ")\n");
				output.close();
			} catch (IOException e) {
				e.getStackTrace();
			}
		}
	}
	
	public void writeGameOver() {
		if (currentGameState == GameState.BLUE_WON) {
			try {
				FileWriter file = new FileWriter("SOS_Game_Log.txt", true);
				PrintWriter output = new PrintWriter(file, true);
				output.write("Blue Player Wins.\n\n");
				output.close();
			} catch (IOException e) {
				e.getStackTrace();
			}
		}
		else if (currentGameState == GameState.RED_WON) {
			try {
				FileWriter file = new FileWriter("SOS_Game_Log.txt", true);
				PrintWriter output = new PrintWriter(file, true);
				output.write("Red Player Wins.\n\n");
				output.close();
			} catch (IOException e) {
				e.getStackTrace();
			}
		}
		else if (currentGameState == GameState.DRAW) {
			try {
				FileWriter file = new FileWriter("SOS_Game_Log.txt", true);
				PrintWriter output = new PrintWriter(file, true);
				output.write("The game is a draw.\n\n");
				output.close();
			} catch (IOException e) {
				e.getStackTrace();
			}
		}
	}
	//end
}