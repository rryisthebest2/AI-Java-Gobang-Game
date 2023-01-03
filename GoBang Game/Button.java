/*
 * Button.java
 * Button Class for Computer CPT Project
 * Ray Xu, Richard Zhou, Maxwell He
 * ICS4U1-a
 * January 26, 2022
 */

import java.awt.event.ActionListener; // ActionListener in Java is a class that is responsible for handling all action events such as when the user clicks on a component.
import java.awt.event.ActionEvent;// This is a  semantic event which indicates that a component-defined action occurred.The ActionEvent is generated when button is clicked or the item of a list is double clicked.
import javax.swing.JOptionPane; // used for creating window-based applications. We believe this is the most efficent way to do it so we used it. Powerful function like display message using windows etc. We will use this for pop up message to communciate with users. 
import javax.swing.JComboBox; //JComboBox inherits JComponent class . JComboBox shows a popup menu that shows a list and the user can select a option from that specified list .

/** 
* This class is one of the most important class for the code. It implements both GameConfiguration and ActionListener. The majority of this class contains the Buttons that shows up on our interface. For example, the New Game button, the Game Rule button, and the Exit Button, etc. In a very real sense, this is the class where all the Buttons on the interface are coded. At the end of the code, it also includes our Game Mode selection. 
*/

public class Button implements GameConfiguration,ActionListener{ // We named this class Button, note the public key word and the implements keyword. This is very diffferent from the keyword extends. Implements indicates that we are using the elements of a Java Interface in your class.In this case, it will be GameConfiguration and ActionListener. 
	public GameFrame gameFrame; // notice the public keyword here. 
	public JComboBox<String> box; // notice the public Keyword here. We don't necessary have to put the <String> here. It's just because since Java 7, it will say a unsafe Warning if we don't put it. But it will still work. 

  /** This method is responsible for the initial status setting here. It has two parameter, gameFrame and box. 
  */
	public Button(GameFrame gameFrame,JComboBox<String> box) {
		this.gameFrame=gameFrame;// Get the left half of the drawing board. Notice the this keyword here. 
		this.box=box;//Get the checkbox object. Notice the this keyword here.  
	}

	
  /** This method handles the interface when an operation occurs. This is where user's click to button are handled in the program. Whenever they click a button on the interface, it will be handled by this method. Notice the public and void key word here. Void indicates no return value.  
  */
	public void actionPerformed(ActionEvent e) { //ActionEvenet package is described above. 
		
		// At here we have to use else if, because without else if, every time we click on the right side of the screen, it will first get the information regarding the Mode selection, and then it will reset the gameboard data. However, else if will not let this happen. It will check each one.  

		// Get the content of the current clicked button and determine if it is the "Start New Game" button.
		if(e.getActionCommand().equals("New Game")) {
			//Redrawing the board here
		    for(int i=0;i<gameFrame.isAvail.length;i++)
			   for(int j=0;j<gameFrame.isAvail[i].length;j++)
			    	 gameFrame.isAvail[i][j]=0;// i and j are just personal preferenc. No specific meaning. Just satisy the for loop requirment here. 
		    gameFrame.repaint();// use the repaint function we coded in GameFrame. 
			// If it is the button to start a new game, then set the listening method for the left half
			gameFrame.turn=1; // turn is an important variable. It indicates the Color. 1 = black, 2 = white. 
		}
		// Determine if the currently clicked button is Undo. If it is Undo and the user is in human vs human mode then we would prompt a message to give the User's opponent a choice to accept the Undo request or decline the Undo request. I did this by using JOptionPane.showOptionDialog. If the user agree the request then we will proceed the Undo. 
		else if(e.getActionCommand().equals("Undo") && gameFrame.chooseType ==0) {// check game Mode and if it's regret. 
      String[] options = new String[] {"Yes", "No"}; // the option give to the user. Accept or Decline. 
      int response = JOptionPane.showOptionDialog(null, "Do you want to allow your opponent to Undo?", "Undo Request", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);// prompt message window. 
      if((response==0)&&(gameFrame.checkerPositionList.size()>1)&&(gameFrame.turn!=0)){// check if there is at least 2 checker on the board, check to see game is not over.(Game over turn would equal 0, and also check if the response to request is Accept.)
				// set the corresponding position of the pawn array to 0.
				CheckerPosition lastChecker=new CheckerPosition();
				// Get the object information of the last checker
				lastChecker=gameFrame.checkerPositionList.remove(gameFrame.checkerPositionList.size()-1);
				// Set the corresponding array position to 0, which means empty. Note i and j again are just personal preference.   
				gameFrame.isAvail[lastChecker.list1][lastChecker.list2]=0;
				// Restore the player data to the player data from the previous rounds.  
				if(gameFrame.turn==1) gameFrame.turn++; // This is just trying to determine which user is next. If turn ==1 which means black user just put a checker, it will add and turn to 2 which would be white checker player next. 
				else gameFrame.turn--;// vice versa, we minus so it's black player's turn.   
				
			  // Call gameFrame's repaint method directly. Note that the brush for the repaint method is fetched before the board page is generated so that it will work. This is acutally very important.  

				// Note that calling repaint will automatically call the paint method without giving parameters
				gameFrame.repaint();

			  } 
      
			else {
				gameFrame.popUp("Error Warning","You can not Undo!");
			}
		}
    else if(e.getActionCommand().equals("Undo") && gameFrame.chooseType ==1){// This is the case when the game is in Human vs AI mode. In this case, Users are not permitted to Undo for fairness concern.  
        gameFrame.popUp("Error Warning", "No Undo is allowed in Human vs AI");
    }// prompt message.  
		else if(e.getActionCommand().equals("Surrender")) { // check if it is surrender case, if it is we straight declare the other side win. Which side wins is decided by the variable turn. 
			if(gameFrame.turn==1) gameFrame.popUp("Game Result","White side wins"); // message prompt
			else gameFrame.popUp("Game Resukt","Black side wins"); // message prompt. 
		    // Resetting the board to inoperable
		    gameFrame.turn=0; // reset the board. turn=0 will lock the board and wait for next round.  
		}
    else if(e.getActionCommand().equals("Game Rules")){ // if user decided to click Game Rule and check the rule for the game. 
      gameFrame.popUp("Game Rules","Players alternate turns placing a checker of \ntheir colour on an empty intersection. Black \nplays first. The first player to form to \nan unbroken chain of five stones vertically, \nhorizontally, or diagonally wins the game");
      //This is the game rule. I decided to use \n to format the sentence so it will look better on the screen. This is not necessary. 
    }
    else if(e.getActionCommand().equals("User Guide")){ // If user wants to check out the Interface Guide. 
      gameFrame.popUp("User Guide", "Instruction: \n1. Click the mode selection button at the bottom right to select a game mode. \n2. Please read the ‘Game Rules’ before you start the game \n3. Click ‘New Name’ to start a game. \n4. Drag your mouse and click the position you would like to place a checker on (left click). \n5. In ‘Human vs. Human’ mode,  you are allowed to undo if your opponent agrees. \n6. The ‘Undo’ button is not allowed in ‘Human vs. AI’ mode. \n7. To surrender the game, simply click the ‘Surrender’ button.\n8. Click ‘Exit’ or the tool car at the top right to end the game."); // Once again, we use the functionwe already coded in gameFrame to pop out the message window. I used \n to format it so it will looks very clean and easier for the user to see.   
    }
    else if(e.getActionCommand().equals("Exit")){ // If the user decided to Exit the code. There is two way to exit the code. User can exit by click the tool bar at the top. But to make their job easier, I decided to create an Exit button as well.      
      gameFrame.popUp("Game Over", "Thank you for your use"); // messgae prompt. 
      System.exit(0); // Exit here.  
    }
		else if(box.getSelectedItem().equals("Human Vs. AI")) { // If the Game mode is Human vs AI and that is selected by user. It will change Type to 1. 1 = Human vs AI. 0 = Human vs Human. 
			 gameFrame.chooseType=1;
			 gameFrame.turn=0;// Resetting the board to inoperable
		}
		else if(box.getSelectedItem().equals("Human Vs. Human")){ // If the Game mode is Human vs Human and that is selected by user. It will change Type to 0. 1 = Human vs AI. 0 = Human vs Human. 
			 gameFrame.chooseType=0;
			 gameFrame.turn=0;// Resetting the board to inoperable
		}
	}
	
} // end of Button Class. 