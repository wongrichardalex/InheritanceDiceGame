package application;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class FinalProjectController {
	

	Dice player1 = new Dice();
	Dice player2 = new Dice();
	
	@FXML 
	private TextField tfPlayerRoll;
	@FXML
	private TextArea taScoreboard;
	@FXML
	private TextField tfRoll;
	
	@FXML
	private ToggleButton tgbtnDie1;
	@FXML
	private ToggleButton tgbtnDie2;
	@FXML
	private ToggleButton tgbtnDie3;
	@FXML
	private ToggleGroup dieGroup1;
	@FXML
	private ToggleGroup dieGroup2;
	@FXML
	private ToggleGroup dieGroup3;
	
	@FXML
	public Button btnRoll;
	@FXML
	private Button btnEndTurn;
	
	int round = 0;
	String winner = "Player1";


	
	@FXML
	private void initialize()  {
		
		tgbtnDie1.setToggleGroup(dieGroup1);
		tgbtnDie1.setToggleGroup(dieGroup2);
		tgbtnDie1.setToggleGroup(dieGroup3);
		
		tfPlayerRoll.setText("Player1");
		tfRoll.setText("1");	
		taScoreboard.setText("Player1    Player 2     Score");
	}
	
	public void buttonRoll() {
		roll2(player1, player2);
		}
		
	private void roll2(Dice player1, Dice player2) {
		int random;	
		int roundChanger = 0;
		
		// after game resets, reset the scoreboard headers
		if (round == 0) {
			taScoreboard.setText("Player1    Player 2     Score");
		}
		
		// find what roll # we are on
		String rollWeAreOn = tfRoll.getText();
		
		// if all three die are slected, jump to turn 3
		if (tgbtnDie1.isSelected() == true && tgbtnDie1.isSelected() == true &&
				tgbtnDie1.isSelected() == true) {
			tfRoll.setText("3");
		}
		
		// determines who has the current roll
		String currentPlayer = tfPlayerRoll.getText();
		String stringPlayer1 = "Player1";
	
		String stringNumber1 = "1";
		String stringNumber2 = "2";
		String stringNumber3 = "3";
		
	
		// only get new values for dice that haven't been kept		
		if (tgbtnDie1.isSelected() == false) {
			random = generateRandom();
			// send the value to the GUI guys so they can display
			// the correct picture
			// displayDie()
			if (currentPlayer.equals(stringPlayer1)) {
				player1.setDie1(random);
			} else {
				player2.setDie1(random);
			}
		} 
		if (tgbtnDie2.isSelected() == false) {
			random = generateRandom();
			// send the value to the GUI guys so they can display
			// the correct picture
			// displayDie()
			if (currentPlayer.equals(stringPlayer1)) {
				player1.setDie2(random);
			} else {
				player2.setDie2(random);
			}
		} 
		if (tgbtnDie3.isSelected() == false) {
			random = generateRandom();
			// send the value to the GUI guys so they can display
			// the correct picture
			// displayDie()
			if (currentPlayer.equals(stringPlayer1)) {
				player1.setDie3(random);
			} else {
				player2.setDie3(random);
			}
		} 
		
		System.out.println(tgbtnDie1.isSelected());
		System.out.println(player1.getDie1() + " " +
				player1.getDie2() + " " +
				player1.getDie3() + " ");
	
		
		if (rollWeAreOn.equals(stringNumber1)) {  // if first roll set to 2nd
			tfRoll.setText("2");
		} else if (rollWeAreOn.equals(stringNumber2)) { // if 2nd, to 3rd
			tfRoll.setText("3");
		} else {                             // if 3rd reset and take score
			tgbtnDie1.setSelected(false);
			tgbtnDie2.setSelected(false);
			tgbtnDie3.setSelected(false);
			if (round % 2 == 1) {
				String oldText = taScoreboard.getText();
				String winningPlayer = calculateScore(player1, player2);
//				tfPlayerRoll.setText(winner);
				tfPlayerRoll.setText(winningPlayer);
				roundChanger = 1;
				taScoreboard.setText(oldText + "\n" + player1.getDie1() + 
						player1.getDie2() + player1.getDie3() + 
						"           "  + player2.getDie1() + 
						player2.getDie2() + player2.getDie3() + 
						"             " + player1.getPoints() +
						"  " + player2.getPoints());
			}
			round = round + 1;
			tfRoll.setText("1");
			
			// more logic to keep track of turn / round
			if (roundChanger != 1) {
			if (currentPlayer.equals(stringPlayer1)) {
				tfPlayerRoll.setText("Player2");
			} else {
				tfPlayerRoll.setText("Player1");
			} 
			roundChanger = 0;
			}
			
			// if 11 rounds are over, calculate winner
			if (round == 22) {
				String oldText = taScoreboard.getText();
				if (player1.getPoints() > player2.getPoints()) {
					taScoreboard.setText( oldText + 
							"\nGame Over!! Player 1 wins!!");
					round = 0;
				} else {
					taScoreboard.setText(oldText + 
							"\nGame Over!! Player 2 Wins!!");
				round = 0;
				}
			}
			
		
	
		}
		
	}
	
	
	private String calculateScore(Dice Player1, Dice Player2) {
		int p1d1 = Player1.getDie1();
		int p1d2 = Player1.getDie2();
		int p1d3 = Player1.getDie3();
		
		int p2d1 = Player2.getDie1();
		int p2d2 = Player2.getDie2();
		int p2d3 = Player2.getDie3();
		
		int p1P = Player1.getPoints();
		int p2P = Player2.getPoints();
	
		
		int impPartsP1;
		int impPartsP2;
		
		int sumP1 = (p1d1 + p1d2 + p1d3);
		int sumP2 = (p2d1 + p2d2 + p2d3);
		
		int rankP1 = getRank(Player1);
		int rankP2 = getRank(Player2);

		
		// now that ranks have been gotten, calculate winner
		// if P1 had higher rank, he wins. the end.
		if (rankP1 > rankP2) {
			winner = "Player1";
		} else if (rankP2 > rankP1) {  // if player2 has higher rank
			winner = "Player2";
		} else if (rankP1 == rankP2) { // if ranks are same
			if (rankP1 == 4) {			// if player 1 rolled 421
				if (winner.equals("Player1")) {
					winner = "Player1";
				} else {
					winner = "Player2";
				}
				winner = "Player1";
			} else if (rankP1 == 3) {  // if they are triples;
				if (sumP1 >= sumP2) {  // highest wins
					winner = "Player1";
				} else if (sumP2 > sumP1) {
					winner = "Player2";
				} else {
					if (winner.equals("Player1")) {
						winner = "Player1";
						} else {
							winner = "Player2";
						}
				}
			} else if (rankP1 == 2) {  // if they are doubles
				if (p1d1 == p1d2) {
					impPartsP1 = p1d1; // set the important part to the pair
				} else {
					impPartsP1 = p1d3;
				}
				if (p2d1 == p2d2) {  // do it for player 2 also
					impPartsP2 = p2d1;
				} else {
					impPartsP2 = p2d3;
				}
				if (impPartsP1 > impPartsP2) {  //if player1 pair is better
					winner = "Player1";
				} else if (impPartsP2 > impPartsP1) { // or p2's > p1's
					winner = "Player2";
				} else if (impPartsP1 == impPartsP2) { // if same pair
					if (sumP1 >= sumP2) {				// add them up
						winner = "Player1";
					} else if (sumP2 > sumP1) {
						winner = "Player2";
					} else {
						if (winner.equals("Player1")) {
							winner = "Player1";
							} else {
								winner = "Player2";
							}
					}
				}
			} else {
				if (sumP1 >= sumP2) {  // if no pairs or better, take sums
					winner = "Player1";
				} else if (sumP2 > sumP1){
					winner = "Player2";
				} else {
					if (winner.equals("Player1")) {
						winner = "Player1";
						} else {
							winner = "Player2";
						}
				}
			}
		} 		
		if (winner.equals("Player1")) {
			player1.setPoints(p1P + 1);
		} else {
			player2.setPoints(p2P + 1);
		}
		return winner;
	}
	
	private int getRank(Dice playerToRank) {
		int rank = 1;
		int ph;    // placeholder
		int d1 = playerToRank.getDie1();
		int d2 = playerToRank.getDie2();
		int d3 = playerToRank.getDie3();
		
		// sorting them in ascending order
		// switch the 2nd number into first position if it is bigger
		if (d2 > d1) {
			ph = d1;
			d1 = d2;
			d2 = ph;
		}  // switch the 3rd number into second position if it is bigger
		if (d3 > d2) {
			ph = d2;
			d2 = d3;
			d3 = ph;
		} // switch the 2nd number into first position if it is bigger
		if (d2 > d1) {
			ph = d1;
			d1 = d2;
			d2 = ph;
		}
		
		// now to get their ranking
		// check for 4,2,1
		if (d1 == 4 && d2 == 2 && d3 == 1) {
			rank = 4;
		} else if (d1 == d2 && d1 == d3) {  // checks for triples
			rank = 3;
		} else if ((d1 == d2 && d1 != d3) || d2 == d3) { // pairs
			rank = 2;
		} else {
			rank = 1;
		}
		
		return rank;
	}
	
	public int generateRandom() {
		int random = (int)(Math.random()*((6-1)+1))+1;
		return random;
	}

	
	
	
	
	
	
	
}