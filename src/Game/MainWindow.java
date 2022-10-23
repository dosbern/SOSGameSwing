package Game;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Game.MainWindow.GameBoardCanvas;
import Game.SOSGame.CurrentTurn;
import Game.SOSGame.GamePiece;
import Game.SOSGame.GameState;

public class MainWindow {
	
	//private instance variable of type JFrame
	private static JFrame window;
	private JLabel label;
	//private JTextField textField;
	private JRadioButton radioButtonSimpleGame;
	private JRadioButton radioButtonGeneralGame;
	private JRadioButton radioButtonRedPlayerS;
	private JRadioButton radioButtonRedPlayerO;
	private JRadioButton radioButtonBluePlayerS;
	private JRadioButton radioButtonBluePlayerO;
	private JRadioButton radioButtonBluePlayerAutoPlayOn;
	private JRadioButton radioButtonBluePlayerAutoPlayOff;
	private JRadioButton radioButtonRedPlayerAutoPlayOn;
	private JRadioButton radioButtonRedPlayerAutoPlayOff;
	JButton recordButton = new JButton("Start Recording");
	//private JLabel bluePlayerScoreLabel = new JLabel("Score: ");
	//private JLabel redPlayerScoreLabel = new JLabel("Score: ");
	private JLabel turnLabel = new JLabel("Welcome to SOS Game. Click the board to begin playing, or choose a new board size and click 'New Game' to Reset the board.");
	private JTextField sizeTextField = new JTextField(2);
	private JButton newGameButton = new JButton("New Game");
	
	final int CELL_SIZE = 100; 
	final int GRID_WIDTH = 8;
	final int GRID_WIDHT_HALF = GRID_WIDTH / 2; 
	final int CELL_PADDING = CELL_SIZE / 6;
	final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2; 
	final int SYMBOL_STROKE_WIDTH = 8; 
	int CANVAS_WIDTH;
	int CANVAS_HEIGHT;
	
	private static GameBoardCanvas gameBoardCanvas; 
	private SOSGame sOSGame;
	
	public MainWindow (SOSGame sOSGame) {
	this.sOSGame = sOSGame;
	initialize();
	}
	
	//public constructor
	public MainWindow() {
		initialize();
	}
	
	//initialize function
	public void initialize() {
		//create frame called window for entire game
		window = new JFrame();
		window.setTitle("SOS Game");
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setSize(1046,758);
		window.setLocationRelativeTo(null);
		window.setLayout(new BorderLayout(5, 5));
		
		//gameBoardPanel
		JPanel gameBoardPanel = new JPanel();
		gameBoardCanvas = new GameBoardCanvas();
		gameBoardPanel.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		//gameBoardPanel.getPreferredSize();
		gameBoardPanel.setLayout(new BorderLayout());
		gameBoardPanel.add(gameBoardCanvas);
		window.add(gameBoardCanvas, BorderLayout.CENTER);
		
		//create JPanel bluePlayerPanel
		JPanel bluePlayerPanel = new JPanel();
		bluePlayerPanel.setLayout(new BoxLayout(bluePlayerPanel, BoxLayout.Y_AXIS));
		bluePlayerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		label = new JLabel("Blue Player");
		label.setFont(new Font("Arial", Font.BOLD, 36));
		bluePlayerPanel.add(label, BorderLayout.NORTH);
		radioButtonBluePlayerS = new JRadioButton("S");
		radioButtonBluePlayerS.setFont(new Font("Arial", Font.PLAIN, 18));
		radioButtonBluePlayerS.setSelected(true);
		bluePlayerPanel.add(radioButtonBluePlayerS, BorderLayout.CENTER);
		radioButtonBluePlayerO = new JRadioButton("O");
		radioButtonBluePlayerO.setFont(new Font("Arial", Font.PLAIN, 18));
		bluePlayerPanel.add(radioButtonBluePlayerO, BorderLayout.CENTER);
		ButtonGroup buttonGroupBluePlayer = new ButtonGroup();
		buttonGroupBluePlayer.add(radioButtonBluePlayerS);
		buttonGroupBluePlayer.add(radioButtonBluePlayerO);
		radioButtonBluePlayerS.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sOSGame.setBluePlayerGamePieceS(null);
			}
		});
		radioButtonBluePlayerO.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sOSGame.setBluePlayerGamePieceO(null);
			}
		});
		label = new JLabel("Auto-Play");
		label.setFont(new Font("Arial", Font.BOLD, 24));
		bluePlayerPanel.add(label, BorderLayout.CENTER);
		radioButtonBluePlayerAutoPlayOn = new JRadioButton("Enable");
		radioButtonBluePlayerAutoPlayOn.setFont(new Font("Arial", Font.PLAIN, 18));
		bluePlayerPanel.add(radioButtonBluePlayerAutoPlayOn, BorderLayout.CENTER);
		radioButtonBluePlayerAutoPlayOff = new JRadioButton("Disable");
		radioButtonBluePlayerAutoPlayOff.setFont(new Font("Arial", Font.PLAIN, 18));
		bluePlayerPanel.add(radioButtonBluePlayerAutoPlayOff, BorderLayout.CENTER);
		ButtonGroup buttonGroupBluePlayerAutoPlay = new ButtonGroup();
		buttonGroupBluePlayerAutoPlay.add(radioButtonBluePlayerAutoPlayOn);
		buttonGroupBluePlayerAutoPlay.add(radioButtonBluePlayerAutoPlayOff);
		radioButtonBluePlayerAutoPlayOff.setSelected(true);
		radioButtonBluePlayerAutoPlayOn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sOSGame.setBluePlayerAutoPlayOn();
				sOSGame.updateGameState();
				sOSGame.checkForAutoPlay();
				window.repaint();
			}
		});
		radioButtonBluePlayerAutoPlayOff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sOSGame.setBluePlayerAutoPlayOff();
				sOSGame.updateGameState();
			}
		});
		//bluePlayerScoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
		//bluePlayerPanel.add(bluePlayerScoreLabel, BorderLayout.CENTER);
		window.add(bluePlayerPanel, BorderLayout.WEST);
		
		//create JPanel redPlayerPanel
		JPanel redPlayerPanel = new JPanel();
		redPlayerPanel.setLayout(new BoxLayout(redPlayerPanel, BoxLayout.Y_AXIS));
		redPlayerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		//redPlayer label
		label = new JLabel("Red Player");
		label.setFont(new Font("Arial", Font.BOLD, 36));
		redPlayerPanel.add(label, BorderLayout.NORTH);
		radioButtonRedPlayerS = new JRadioButton("S");
		radioButtonRedPlayerS.setFont(new Font("Arial", Font.PLAIN, 18));
		radioButtonRedPlayerS.setSelected(true);
		redPlayerPanel.add(radioButtonRedPlayerS, BorderLayout.CENTER);
		radioButtonRedPlayerO = new JRadioButton("O");
		radioButtonRedPlayerO.setFont(new Font("Arial", Font.PLAIN, 18));
		redPlayerPanel.add(radioButtonRedPlayerO, BorderLayout.CENTER);
		ButtonGroup buttonGroupRedPlayer = new ButtonGroup();
		buttonGroupRedPlayer.add(radioButtonRedPlayerS);
		buttonGroupRedPlayer.add(radioButtonRedPlayerO);
		radioButtonRedPlayerS.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sOSGame.setRedPlayerGamePieceS(null);
			}
		});
		radioButtonRedPlayerO.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sOSGame.setRedPlayerGamePieceO(null);
			}
		});
		label = new JLabel("Auto-Play");
		label.setFont(new Font("Arial", Font.BOLD, 24));
		redPlayerPanel.add(label, BorderLayout.CENTER);
		radioButtonRedPlayerAutoPlayOn = new JRadioButton("Enable");
		radioButtonRedPlayerAutoPlayOn.setFont(new Font("Arial", Font.PLAIN, 18));
		redPlayerPanel.add(radioButtonRedPlayerAutoPlayOn, BorderLayout.CENTER);
		radioButtonRedPlayerAutoPlayOff = new JRadioButton("Disable");
		radioButtonRedPlayerAutoPlayOff.setFont(new Font("Arial", Font.PLAIN, 18));
		redPlayerPanel.add(radioButtonRedPlayerAutoPlayOff, BorderLayout.CENTER);
		ButtonGroup buttonGroupRedPlayerAutoPlay = new ButtonGroup();
		buttonGroupRedPlayerAutoPlay.add(radioButtonRedPlayerAutoPlayOn);
		buttonGroupRedPlayerAutoPlay.add(radioButtonRedPlayerAutoPlayOff);
		radioButtonRedPlayerAutoPlayOff.setSelected(true);
		radioButtonRedPlayerAutoPlayOn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sOSGame.setRedPlayerAutoPlayOn();
				sOSGame.updateGameState();
				sOSGame.checkForAutoPlay();
				window.repaint();
			}
		});
		radioButtonRedPlayerAutoPlayOff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sOSGame.setRedPlayerAutoPlayOff();
				sOSGame.updateGameState();
			}
		});
		//redPlayerScoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
		//redPlayerPanel.add(redPlayerScoreLabel, BorderLayout.CENTER);
		window.add(redPlayerPanel, BorderLayout.EAST);
		
		//create JPanel optionsPanel
		JPanel optionsPanel = new JPanel();
		optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		radioButtonSimpleGame = new JRadioButton("Simple Game");
		radioButtonSimpleGame.setFont(new Font("Arial", Font.PLAIN, 18));
		radioButtonSimpleGame.setSelected(true);
		radioButtonSimpleGame.setToolTipText("The player who makes the first SOS combination wins.");
		optionsPanel.add(radioButtonSimpleGame);
		radioButtonGeneralGame = new JRadioButton("General Game");
		radioButtonGeneralGame.setFont(new Font("Arial", Font.PLAIN, 18));
		radioButtonGeneralGame.setToolTipText("The player with the most SOS combinations wins.");
		optionsPanel.add(radioButtonGeneralGame);
		/*radioButtonSimpleGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sOSGame.setGameTypeSimple(null);
			}
		});
		radioButtonGeneralGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sOSGame.setGameTypeGeneral(null);
			}
		});*/
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(radioButtonSimpleGame);
		buttonGroup.add(radioButtonGeneralGame);
		label = new JLabel("Board Size:");
		label.setFont(new Font("Arial", Font.PLAIN, 18));
		optionsPanel.add(label);
		sizeTextField = new JTextField(2);
		sizeTextField.setText("6");
		optionsPanel.add(sizeTextField);
		sizeTextField.setFont(new Font("arial", Font.PLAIN, 18));
		sizeTextField.setMargin(new Insets(0, 5, 0, 5));
		sizeTextField.setToolTipText("Enter a board size between 3 and 9.");
		sizeTextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//function
			}
		});
		JButton newGameButton = new JButton("New Game");
		newGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String sizeText = sizeTextField.getText();
				int size = Integer.parseInt(sizeText);
				if (size >= 3 && size <= 9) {
					try {
						sOSGame = new SOSGame(size);
						if (radioButtonSimpleGame.isSelected()) {
							sOSGame.setGameTypeSimple(null);
						}
						if (radioButtonGeneralGame.isSelected()) {
							sOSGame.setGameTypeGeneral(null);
						}
						radioButtonBluePlayerS.setSelected(true);
						radioButtonRedPlayerS.setSelected(true);
						radioButtonBluePlayerAutoPlayOff.setSelected(true);
						radioButtonRedPlayerAutoPlayOff.setSelected(true);
						/*
						if (sOSGame.getGameIsRecording() == true) {
							recordButton.setText("Stop Recording");
							recordButton.setBackground(Color.RED);
							sOSGame.setGameIsRecordingTrue();
						}
						else if (sOSGame.getGameIsRecording() == false) {
							recordButton.setText("Start Recording");
							recordButton.setBackground(new JButton().getBackground());
							sOSGame.setGameIsRecordingFalse();
							}
						*/
						recordButton.setText("Start Recording");
						recordButton.setBackground(new JButton().getBackground());
						updateTurnLabel();
						int width = (((size +4)*100)+46);
						int height = (((size + 1)*100)+58);
						window.setSize(width, height);
						window.repaint();
					}
					catch (NumberFormatException ex){
			            ex.printStackTrace();
			        }
				}
				else if (size < 3 || size > 9) {
					turnLabel.setText("Invalid Board Size. Choose a number between 3 and 9.");
				}
			}
			
		});
		optionsPanel.add(newGameButton);
		//recordButton.setFont(new Font("arial", Font.PLAIN, 18));
		//recordButton.setPreferredSize(new Dimension(100, 40));
		recordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//function on click
				if (recordButton.getText().equals("Start Recording")) {
					recordButton.setText("Stop Recording");
					recordButton.setBackground(Color.RED);
					sOSGame.setGameIsRecordingTrue();
				}
				else {
					recordButton.setText("Start Recording");
					recordButton.setBackground(new JButton().getBackground());
					sOSGame.setGameIsRecordingFalse();
				}
			}
		});
		optionsPanel.add(recordButton);
		window.add(optionsPanel, BorderLayout.NORTH);
		
		//createJPanel infoPanel
		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		turnLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		infoPanel.add(turnLabel);
		window.add(infoPanel, BorderLayout.SOUTH);
		
		//allows window to be visible
		window.setVisible(true);
		
	}

	/*
	//function to create button
	private JButton createButton() {
		JButton button = new JButton("O");
		button.setFocusable(false);
		button.setFont(new Font("Arial", Font.BOLD, 48));
		button.setPreferredSize(new Dimension(20, 20));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//function on click
				button.setEnabled(false);
			}
		});
		return button;
		}
		*/

		class GameBoardCanvas extends JPanel {
			GameBoardCanvas(){
				addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e) {  
							double y = e.getY() / CELL_SIZE;
							double x = e.getX() / CELL_SIZE;
							Math.floor(y);
							Math.floor(x);
							int rowSelected = (int) Math.round(y);
							int colSelected = (int) Math.round(x);
							sOSGame.makeMove(rowSelected, colSelected);
							sOSGame.checkForSOS(rowSelected, colSelected);
							sOSGame.checkForWin();
							updateTurnLabel();
							repaint(); 
							sOSGame.checkForAutoPlay();
					}
				});

			}		
					
			@Override
			public void paintComponent(Graphics g) { 
				super.paintComponent(g);
				setBackground(Color.WHITE);
				drawGridLines(g);
				drawBoard(g);
				drawSOSLines(g);
			}
			
			private void drawGridLines(Graphics g){
					CANVAS_WIDTH = CELL_SIZE * sOSGame.getSize();  
					CANVAS_HEIGHT = CELL_SIZE * sOSGame.getSize();
					g.setColor(Color.LIGHT_GRAY);
					for (int row = 1; row < sOSGame.getSize(); row++) {
						g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDHT_HALF,
								CANVAS_WIDTH-1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
					}
					for (int col = 1; col < sOSGame.getSize(); col++) {
						g.fillRoundRect(CELL_SIZE * col - GRID_WIDHT_HALF, 0,
								GRID_WIDTH, CANVAS_HEIGHT-1, GRID_WIDTH, GRID_WIDTH);
					}
					}
				}
		private void drawBoard(Graphics g){
			Graphics2D g2d = (Graphics2D)g;
			g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)); 
			for (int row = 0; row < sOSGame.getSize(); ++row) {
				for (int col = 0; col < sOSGame.getSize(); ++col) {
					int x1 = col * CELL_SIZE + CELL_PADDING;
					int y1 = row * CELL_SIZE + CELL_PADDING;
					if (sOSGame.getCell(row,col) == SOSGame.Cell.S) {
						g2d.setColor(Color.BLACK);
						int y2 = (row + 1) * CELL_SIZE - CELL_PADDING;
						g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 96));
						g2d.drawString("S", x1, y2);
					}
					if (sOSGame.getCell(row,col) == SOSGame.Cell.O) {
						g2d.setColor(Color.BLACK);
						g2d.drawOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);
					}
					}
				}
			}
		
		private void drawSOSLines(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;
			g2d.setStroke(new BasicStroke(5));
			g2d.setColor(Color.BLUE);
			for (int i=0; i<sOSGame.listOfLinesBlue.size(); i++) {
				int xONE = sOSGame.listOfLinesBlue.get(i).xOne;
				int yONE = sOSGame.listOfLinesBlue.get(i).yOne;
				int xTWO = sOSGame.listOfLinesBlue.get(i).xTwo;
				int yTWO = sOSGame.listOfLinesBlue.get(i).yTwo;
				g2d.drawLine(yONE, xONE, yTWO, xTWO);
			}
			g2d.setColor(Color.RED);
			for (int i=0; i<sOSGame.listOfLinesRed.size(); i++) {
				int xONE = sOSGame.listOfLinesRed.get(i).xOne;
				int yONE = sOSGame.listOfLinesRed.get(i).yOne;
				int xTWO = sOSGame.listOfLinesRed.get(i).xTwo;
				int yTWO = sOSGame.listOfLinesRed.get(i).yTwo;
				g2d.drawLine(yONE, xONE, yTWO, xTWO);
			}
		}
		
		private void updateTurnLabel() {
			if (sOSGame.getTurn() == CurrentTurn.BLUE) {
				turnLabel.setText("Blue Player's Turn");
			}
			if (sOSGame.getTurn() == CurrentTurn.RED) {
				turnLabel.setText("Red Player's Turn");
			}
			if (sOSGame.getGameState() == GameState.BLUE_WON) {
				turnLabel.setText("Blue Player Wins!");
			}
			if (sOSGame.getGameState() == GameState.RED_WON) {
				turnLabel.setText("Red Player Wins!");
			}
			if (sOSGame.getGameState() == GameState.DRAW) {
				turnLabel.setText("It's a Draw!");
			}
			//bluePlayerScoreLabel.setText("Score: " + Integer.toString(sOSGame.getBluePlayerScore()));
			//redPlayerScoreLabel.setText("Score: " + Integer.toString(sOSGame.getRedPlayerScore()));
		}
		
		public static void main(String[] args) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					//create instance of MainWindow
					new MainWindow(new SOSGame());
				}
			});
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
		//end
	}