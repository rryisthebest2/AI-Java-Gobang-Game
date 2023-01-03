/*
 * FrameListener.java
 * FrameListener Class for Computer CPT Project
 * Ray Xu, Richard Zhou, Maxwell He
 * ICS4U1-a
 * January 26, 2022
 */

import java.awt.event.MouseListener; // The Java MouseListener is notified whenever you change the state of mouse. It is used in our case to track mouse drag or click. When people try to place Go down the board or move their cursor, we have to track that.   
import java.awt.Graphics; // This is perhaps one of the most important package we need to import for this game. It is the abstract base class for all graphics contexts that allow an application to draw onto components that are realized on various devices, as well as onto off-screen images. 

/** 
* This method implements the monitoring interface processing of the GameFrame chess interface. 
*/

public class FrameListener implements GameConfiguration,MouseListener{ // Note that we used the keyword implements here. This is very diffferent from the keyword extends. Implements indicates that we are using the elements of a Java Interface in your class.In this case, it will be GoBangconfig.

	public GameFrame gameFrame;//Note the public keyword here. 
	GameConfiguration checker; // create a new GoBangconfig object called go here. 
	
  /** 
  A simple setGraphics method here. Note the this keyword here. 
  */
	public void setGraphics(GameFrame gameFrame) {
		this.gameFrame=gameFrame;
	}
 
 /** 
 * Other than the part in  the GameFrame section, the below section is also an extremely important section for the AI algorithm. This is where the math equation is used and caculation is made. 
 */
	public Integer unionWeight(Integer a,Integer b ) { // AI joint algorithm. More description please see extra document submitted with the code. It includes a clear description of our logic with our algorithm and code.   
		if((a==null)||(b==null)) return 0; // First, we must determine whether or not the two values ​​​​a and b are null. null is assigned to any variable of a reference type to indicate that the variable does not refer to any object or array.

		//one one:101/202 
	    else if((a>=22)&&(a<=25)&&(b>=22)&&(b<=25)) return 60;
		//one two、two one:1011/2022
		else if(((a>=22)&&(a<=25)&&(b>=76)&&(b<=80))||((a>=76)&&(a<=80)&&(b>=22)&&(b<=25))) return 800;
		//one three, three one, two, two:10111/20222
		else if(((a>=10)&&(a<=25)&&(b>=1050)&&(b<=1100))||((a>=1050)&&(a<=1100)&&(b>=10)&&(b<=25))||((a>=76)&&(a<=80)&&(b>=76)&&(b<=80)))
			return 3000;
		//one three, three one
		else if(((a>=22)&&(a<=25)&&(b>=140)&&(b<=150))||((a>=140)&&(a<=150)&&(b>=22)&&(b<=25))) return 3000;
		//two three, three two:110111
		else if(((a>=76)&&(a<=80)&&(b>=1050)&&(b<=1100))||((a>=1050)&&(a<=1100)&&(b>=76)&&(b<=80))) return 3000;
		else return 0;
	}
	  /**
    This method is responsible whenever the user left click their mouse on the gameboard. The method will caculate the intersection to determine the exact spot it land on. We need this because the gameboard has grid so we must use this to caculate the position. Note that all checker in the game will only land on intersetions.  
    */
	  public void mouseClicked(java.awt.event.MouseEvent e) {
		  int x=e.getX();// get the x 
		  int y=e.getY(); // get the y
		  int countX=(x/40)*40+20; // Calculates which intersection of the chessboard the pawn is going to land on
		  int countY=(y/40)*40+20; // Calculates which intersection of the chessboard the pawn is going to land on

		  Graphics brush=gameFrame.getGraphics();
		  int arrayJ=(countX-20)/40;
		  int arrayI=(countY-20)/40; // Calculates the corresponding position of the pieces on the chessboard in the array

		  
		  if(gameFrame.turn!=0) // Determines if the game can be played. 0 means can't play. 
		  if(gameFrame.isAvail[arrayI][arrayJ]!=0) {//if not equal 0, it means something is there. Either black or white checker. 
			  gameFrame.popUp("Error message","There are already checkers here, please place your piece elsewhere"); // Error messages show when placing a piece in an illegal positions
		  }
		  else {
			  if(gameFrame.chooseType==0) {//if the game mode is Human vs Human. 0=H vs H, 1= H vs AI. 
				  if(gameFrame.turn==1) { // if it is black player's turn. 1=black, 2= white. 
					  brush.drawImage(blackChecker,countX-size/2, countY-size/2, size, size,null); // Places the piece where the user wants it to be, black checker in this case. 
					  gameFrame.isAvail[arrayI][arrayJ]=1; // Set the current position to have a piece, the piece is a black piece as indicated by 1. 
					  gameFrame.checkerPositionList.add(new CheckerPosition(arrayI,arrayJ)); // Save the current position of the chess piece in an array
					  gameFrame.turn++; // add, so that now it became 2 which is white player's turn now. 
					  
					  int iMin=arrayI-4,iMax=arrayI+4; // Determine whether there are five pieces of chess pieces
					  if(iMin<0) iMin=0; // Determines the column size
					  if(iMax>14) iMax=14; // Defines the range of the array to prevent the user from placing their piece out of bounds 
					  int count1=0; // Determine the number of connected pieces on the board
					  for(int i=iMin;i<=iMax;i++) {
						  if(gameFrame.isAvail[i][arrayJ]==1) count1++;
						  else count1=0; // If another piece of different color appears, or if there is no piece(empty space), restart the count. 
						  if(count1==5) {//if black player got 5 connected. 
							  System.out.println("Black wins"); // prints out the winner is black 
							  gameFrame.popUp("Game result: ","Black wins");
							  gameFrame.turn=0;// end the game by set to 0, lock the board. 
							  return;
						  }
					  }
            // same process but for jmin instead which is arrary j not arrary i. 
					  int jMin=arrayJ-4,jMax=arrayJ+4; // Determine whether there are five pieces of chess pieces
					  if(jMin<0) jMin=0; // Determines the column size
					  if(jMax>14) jMax=14; // Defines the range of the array to prevent the user from placing their piece out of bounds 
					  int count2=0; // Determine the number of connected pieces on the board
					  for(int j=jMin;j<=jMax;j++) {
						  if(gameFrame.isAvail[arrayI][j]==1) count2++;
						  else count2=0; // If another piece appears, or if there is no piece, restart the count
						  if(count2==5) { 
							  System.out.println("Black wins"); // prints out the winner if black 
							  gameFrame.popUp("Game result: ","Black wins");
							  gameFrame.turn=0;// end the game by set to 0, lock the board. 
							  return;
						  }
						  
					  }

					  // Defines the range of the array to prevent the user from placing their piece out of bounds 
					  int count3=0; // Determine the number of connected pieces on the board
					  for(int i=-4;i<=4;i++) {
						  if((arrayI+i>=0)&&(arrayJ+i>=0)&&(arrayI+i<=14)&&(arrayJ+i<=14)) {
							  if(gameFrame.isAvail[arrayI+i][arrayJ+i]==1) count3++;
							  else count3=0; // If another piece appears, or if there is no piece, restart the count
							  if(count3==5) {
								  System.out.println("Black wins"); // prints out the winner if black 
								  gameFrame.popUp("Game result: ","Black wins");
								  gameFrame.turn=0; // end the game by set to 0, lock the board. 
								  return;
							  }
						  }
					  }
					  int count4=0; // Determine the number of connected pieces on the board
					  for(int i=-4;i<=4;i++) {
						  if((arrayI+i>=0)&&(arrayJ-i>=0)&&(arrayI+i<=14)&&(arrayJ-i<=14)) {
							  //System.out.print("count4:"+count4); 
							  if(gameFrame.isAvail[arrayI+i][arrayJ-i]==1) count4++;
							  else count4=0; // If another piece appears, or if there is no piece, restart the count
							  if(count4==5) {
								  System.out.println("Black wins"); // prints out the winner if black 
								  gameFrame.popUp("Game result: ","Black wins");
								  gameFrame.turn=0; // end the game by set to 0, lock the board. 
								  return;
							  }
						  }
					  }
				  }
				  else if(gameFrame.turn==2){ //if it is white player's turn. 1= black, 2 = white. 
					  brush.drawImage(whiteChecker,countX-size/2, countY-size/2, size, size,null); // Places the piece where the user wants it to be, white checker in this case. 
					  gameFrame.checkerPositionList.add(new CheckerPosition(arrayI,arrayJ)); // Save the current position of the chess piece in an array 
					  gameFrame.isAvail[arrayI][arrayJ]=2; // Set the current position to have a piece, the piece is a white piece
					  gameFrame.turn--; //minus, so that now it became 2 which is back to black player's turn now. 
					 
					  int iMin=arrayI-4,iMax=arrayI+4; // Determine whether there are five pieces of chess pieces
					  if(iMin<0) iMin=0; // Determines the column size
					  if(iMax>14) iMax=14; // Defines the range of the array to prevent the user from placing their piece out of bounds 
					  int count1=0; // Determine the number of connected pieces on the board
					  for(int i=iMin;i<=iMax;i++) {
						  if(gameFrame.isAvail[i][arrayJ]==2) count1++;
						  else count1=0; // If another piece appears, or if there is no piece, restart the count
						  if(count1==5) {
							  System.out.println("White wins"); // prints out the winner if white 
							  gameFrame.popUp("Game result: ","White wins"); 
							  gameFrame.turn=0;// same as above logic for black.   
							  return;
						  }
					  }

					  int jMin=arrayJ-4,jMax=arrayJ+4; // Determine whether there are five pieces of chess pieces
					  if(jMin<0) jMin=0; // Determines the column size
					  if(jMax>14) jMax=14; // Defines the range of the array to prevent the user from placing their piece out of bounds 
					  int count2=0; // Determine the number of connected pieces on the board
					  for(int j=jMin;j<=jMax;j++) {
						  if(gameFrame.isAvail[arrayI][j]==2) count2++;
						  else count2=0; // If another piece appears, or if there is no piece, restart the count
						  if(count2==5) {
							  System.out.println("White wins"); // prints out the winner if white 
							  gameFrame.popUp("Game result: ","White wins");
							  gameFrame.turn=0; //same logic as above for black.
							  return;
						  }
						  
					  }
					  
            // Defines the range of the array to prevent the user from placing their piece out of bounds
					  int count3=0; // Determine the number of connected pieces on the board
					  for(int i=-4;i<=4;i++) {
						  if((arrayI+i>=0)&&(arrayJ+i>=0)&&(arrayI+i<=14)&&(arrayJ+i<=14)) {
							  if(gameFrame.isAvail[arrayI+i][arrayJ+i]==2) count3++;
							  else count3=0; // If another piece appears, or if there is no piece, restart the count
							  if(count3==5) {
								  System.out.println("White wins"); // prints out the winner if white 
								  gameFrame.popUp("Game result: ","White wins");
								  gameFrame.turn=0;//same logic as above
								  return;
							  }
						  }
					  }
					  int count4=0; // Determine the number of connected pieces on the board
					  for(int i=-4;i<=4;i++) {
						  if((arrayI+i>=0)&&(arrayJ-i>=0)&&(arrayI+i<=14)&&(arrayJ-i<=14)) {
							  if(gameFrame.isAvail[arrayI+i][arrayJ-i]==2) count4++;
							  else count4=0; // If another piece appears, or if there is no piece, restart the count
							  if(count4==5) {
								  System.out.println("White wins"); // prints out the winner if white 
								  gameFrame.popUp("Game result: ","White wins");
								  gameFrame.turn=0;// same logic as above
								  return;
							  }
						  }
					  }
				  }
			  }

			  else { // If you choose a Human vs AI battle
				  if(gameFrame.turn==1) { //if it is black player's turn. 1= black, 2= white. 
					  
					  //Situation where player drops the checker first
					  //We need to get the place where the player lands the checker.
					  brush.drawImage(blackChecker,countX-size/2, countY-size/2, size, size,null);
					  //Set the current position to already have a checker, the checker is a black checker
					  gameFrame.isAvail[arrayI][arrayJ]=1;
					  //Save the position of the currently played checkers in a dynamic array
					  gameFrame.checkerPositionList.add(new CheckerPosition(arrayI,arrayJ));
					  gameFrame.turn++;
					  
					  int blackImin=arrayI-4,blackImax=arrayI+4; // Determine whether there are five pieces of checker
					  if(blackImin<0) blackImin=0; // Determines the column size
					  if(blackImax>14) blackImax=14; // Defines the range of the array to prevent the user from placing their piece out of bounds 
					  int count1=0; // Determine the number of connected checker on the board 
					  for(int i=blackImin;i<=blackImax;i++) {
						  if(gameFrame.isAvail[i][arrayJ]==1) count1++;
						  else count1=0; // Determine the number of connected checker on the board
						  if(count1==5) { 
							  System.out.println("Black wins"); // prints out the winner if black 
							  gameFrame.popUp("Game result: ","Black wins");
							  return;
						  }
					  }

					  int blackJmin=arrayJ-4,blackJmax=arrayJ+4; // Determine whether there are five pieces of check
					  if(blackJmin<0) blackJmin=0; // Determines the column size
					  if(blackJmax>14) blackJmax=14; // Defines the range of the array to prevent the user from placing their piece out of bounds 
					  int count2=0; // Determine the number of connected checker on the board 
					  for(int j=blackJmin;j<=blackJmax;j++) {
						  if(gameFrame.isAvail[arrayI][j]==1) count2++;
						  else count2=0; // If another checker appears, or if there are no checkers, start counting again
						  if(count2==5) {
							  System.out.println("Black wins"); // prints out the winner if black 
							  gameFrame.popUp("Game result: ","Black wins");
							  return;
						  }
					  }
           
            // Defines the range of the array to prevent the user from placing their piece out of bounds
					  int count3=0; // Determine the number of connected checker on the board
					  for(int i=-4;i<=4;i++) {
						  if((arrayI+i>=0)&&(arrayJ+i>=0)&&(arrayI+i<=14)&&(arrayJ+i<=14)) {
							  if(gameFrame.isAvail[arrayI+i][arrayJ+i]==1) count3++;
							  else count3=0; // If another checker appears, or if there are no checkers, start counting again
							  if(count3==5) {
							  System.out.println("Black wins"); // prints out the winner if black 
							  gameFrame.popUp("Game result: ","Black wins");
								  return;
							  }
						  }
					  }
					  int count4=0; // Determine the number of connected checker on the board
					  for(int i=-4;i<=4;i++) {
						  if((arrayI+i>=0)&&(arrayJ-i>=0)&&(arrayI+i<=14)&&(arrayJ-i<=14)) {
							  //System.out.print("count4:"+count4);
							  if(gameFrame.isAvail[arrayI+i][arrayJ-i]==1) count4++;
							  else count4=0; // If another checker appears, or if there are no checkers, start counting again
							  if(count4==5) {
								  System.out.println("Black side wins"); // prints out the winner if black 
								  gameFrame.popUp("Game Result","Black side wins");
								  return;
							  }
						  }
					  }
					  
					  //Robot Lands the checker
					  //we must first calculate the weight of each position on the board for the algorithem to work properly
					  for(int i=0;i<gameFrame.isAvail.length;i++) {
						  for(int j=0;j<gameFrame.isAvail[i].length;j++) {
							  // first, we have to determine if the current position is empty
							  if(gameFrame.isAvail[i][j]==0) {
								  // Extend to the left
								  String connectCase="0";
								  int jMin=Math.max(0, j-4);
								  for(int positionj=j-1;positionj>=jMin;positionj--) {
									  // Here, we add the previous checker in order
									  connectCase=connectCase+gameFrame.isAvail[i][positionj];
								  }
								  // Here, we take the corresponding weights from the array and add them to the current position of the weights array
								  Integer valLeft=gameFrame.board.get(connectCase);
								  if(valLeft!=null) gameFrame.weightArray[i][j]+=valLeft;
								  
								  // Extend to the right
								  connectCase="0";
								  int jMax=Math.min(14, j+4);
								  for(int positionj=j+1;positionj<=jMax;positionj++) {
									  // Add the previous checkers in order
									  connectCase=connectCase+gameFrame.isAvail[i][positionj];
								  }
								  // Same logic as above, we take the corresponding weights from the array and add them to the current position of the weights array
								  Integer valRight=gameFrame.board.get(connectCase);
								  if(valRight!=null) gameFrame.weightArray[i][j]+=valRight;
								  
								  // Here is the Joint judgment, the judgment for rows
								  gameFrame.weightArray[i][j]+=unionWeight(valLeft,valRight);
								  
								  // Extending upwards
								  connectCase="0";
								  int iMin=Math.max(0, i-4);
								  for(int positioni=i-1;positioni>=iMin;positioni--) {
									  // Add the previous checkers in order
									  connectCase=connectCase+gameFrame.isAvail[positioni][j];
								  }
								  // Take the corresponding weights from the array and add them to the current position of the weights array
								  Integer valueUp=gameFrame.board.get(connectCase);
								  if(valueUp!=null) gameFrame.weightArray[i][j]+=valueUp;
								  
								  // Extend downwards
								  connectCase="0";
								  int iMax=Math.min(14, i+4);
								  for(int positioni=i+1;positioni<=iMax;positioni++) {
									  // Add the previous checkers in order
									  connectCase=connectCase+gameFrame.isAvail[positioni][j];
								  }
								  // Take the corresponding weights from the array and add them to the current position of the weights array
								  Integer valueDown=gameFrame.board.get(connectCase);
								  if(valueDown!=null) gameFrame.weightArray[i][j]+=valueDown;
								  
								  // Here is the Joint judgment, the judgment for columns
								  gameFrame.weightArray[i][j]+=unionWeight(valueUp,valueDown);
								  
								  //extend to the top left, i, j, both minus the same number
								  connectCase="0";
								  for(int position=-1;position>=-4;position--) {
									  if((i+position>=0)&&(i+position<=14)&&(j+position>=0)&&(j+position<=14))
									  connectCase=connectCase+gameFrame.isAvail[i+position][j+position];
								  }
								  
                  // Take the corresponding value from the array and add it to the current position of the weight array
								  Integer valLeftUp=gameFrame.board.get(connectCase);
								  if(valLeftUp!=null) gameFrame.weightArray[i][j]+=valLeftUp;
								  
								 //extend to the bottom right, i, j, both add the same number
								  connectCase="0";
								  for(int position=1;position<=4;position++) {
									  if((i+position>=0)&&(i+position<=14)&&(j+position>=0)&&(j+position<=14))
									  connectCase=connectCase+gameFrame.isAvail[i+position][j+position];
								  }
								  // Take the corresponding weights from the array and add them to the current position of the weights array
								  Integer valRightDown=gameFrame.board.get(connectCase);
								  if(valRightDown!=null) gameFrame.weightArray[i][j]+=valRightDown;
								  
								  // Here is the Joint judgment statement for the algorthim
								  gameFrame.weightArray[i][j]+=unionWeight(valLeftUp,valRightDown);
								  
								  //extend to the lower left, i plus, j minus
								  connectCase="0";
								  for(int position=1;position<=4;position++) {
									  if((i+position>=0)&&(i+position<=14)&&(j-position>=0)&&(j-position<=14))
									  connectCase=connectCase+gameFrame.isAvail[i+position][j-position];
								  }
								  // Take the corresponding weights from the array and add them to the current position of the weights array
								  Integer valueLeftDown=gameFrame.board.get(connectCase);
								  if(valueLeftDown!=null) gameFrame.weightArray[i][j]+=valueLeftDown;
								  
								  //extend to the upper right, i minus, j plus
								  connectCase="0";
								  for(int position=1;position<=4;position++) {
									  if((i-position>=0)&&(i-position<=14)&&(j+position>=0)&&(j+position<=14))
									  connectCase=connectCase+gameFrame.isAvail[i-position][j+position];
								  }
								  // Take the corresponding weights from the array and add them to the current position of the weights array
								  Integer valueRightUp=gameFrame.board.get(connectCase);
								  if(valueRightUp!=null) gameFrame.weightArray[i][j]+=valueRightUp;
								  
								  // Here is the Joint judgment statement for the algorthim
								  gameFrame.weightArray[i][j]+=unionWeight(valueLeftDown,valueRightUp);
							  }
						  }
					  }
					  
					  // Take out the largest weight value
					  int roboti=0,robotj=0;
					  int weightmax=0;
					  for(int i=0;i<checker.row;i++) {
						  for(int j=0;j<checker.column;j++) {
							  if(weightmax<gameFrame.weightArray[i][j]) {
								  weightmax=gameFrame.weightArray[i][j];
								  roboti=i;
								  robotj=j;
							  }
						  }
					  }
					  
					  //Determine the position and drop the checker
					  //i corresponds to y, j corresponds to x
					  countX=20+robotj*40;
					  countY=20+roboti*40;
					  brush.drawImage(whiteChecker,countX-size/2, countY-size/2, size, size,null);
					  // set the current position to already have a checker, the checker is white
					  gameFrame.checkerPositionList.add(new CheckerPosition(roboti,robotj));
					  gameFrame.isAvail[roboti][robotj]=2;
					  gameFrame.turn--;
					 
					  // reset the weight array weightArray after the the checker is placed
					  for(int i=0;i<checker.column;i++) 
						  for(int j=0;j<checker.row;j++)
							  gameFrame.weightArray[i][j]=0;
					  
					  //column judgment
					  // first define the array scope to prevent overstepping
					  int iMin=roboti-4,iMax=roboti+4;
					  if(iMin<0) iMin=0;
					  if(iMax>14) iMax=14;
					  count1=0; // Determine the number of connected checkers
					  for(int i=iMin;i<=iMax;i++) {
						  if(gameFrame.isAvail[i][robotj]==2) count1++;

						  else count1=0; // If another checker appears, or if there are no checkers, start counting again
						  if(count1==5) {
							  System.out.println("White side wins"); // prints out the winner if white 
							  gameFrame.popUp("Game Result","White side wins");
							  gameFrame.turn=0;
							  return;
						  }
					  }

					  int jMin=robotj-4,jMax=robotj+4; // Determine whether there are five pieces of chess pieces
					  if(jMin<0) jMin=0; // Determines the column size
					  if(jMax>14) jMax=14; // Defines the range of the array to prevent the user from placing their piece out of bounds 
					  count2=0; //Determine the number of connected checkers
					  for(int j=jMin;j<=jMax;j++) {
						  if(gameFrame.isAvail[roboti][j]==2) count2++;
						  else count2=0; // If another checker appears, or if there are no checkers, start counting again
						  if(count2==5) {
							  System.out.println("White side wins"); // prints out the winner if white 
							  gameFrame.popUp("Game Result","White side wins");
							  gameFrame.turn=0;
							  return;
						  }
						  
					  }
            // Defines the range of the array to prevent the user from placing their piece out of bounds
					  count3=0;  // Determine the number of connected pieces on the board
					  for(int i=-4;i<=4;i++) {
						  if((roboti+i>=0)&&(robotj+i>=0)&&(roboti+i<=14)&&(robotj+i<=14)) {
							  if(gameFrame.isAvail[roboti+i][robotj+i]==2) count3++;
							  else count3=0; // If another checker appears, or if there are no checkers, start counting again
							  if(count3==5) {
							  System.out.println("White side wins"); // prints out the winner if white 
							  gameFrame.popUp("Game Result","White side wins");
								  gameFrame.turn=0;
								  return;
							  }
						  }
					  }
					  count4=0; // Determine the number of connected pieces on the board
					  for(int i=-4;i<=4;i++) {
						  if((roboti+i>=0)&&(robotj-i>=0)&&(roboti+i<=14)&&(robotj-i<=14)) {
							  if(gameFrame.isAvail[roboti+i][robotj-i]==2) count4++; 
							  else count4=0; // If another checker appears, or if there are no checkers, start counting again
							  if(count4==5) {
							  System.out.println("White side wins"); // prints out the winner if white 
							  gameFrame.popUp("Game Result","White side wins");
								  gameFrame.turn=0;
								  return;
							  }
						  }
					  }
				  }
			  }
		  }
	  }
	  
	  
    /**
    Method for mousePressed. Note the public keyword
    */
    
	  public void mousePressed(java.awt.event.MouseEvent e) {
		  
	  }
	  
	  
    /**
    Method for mouseReleased. Note the public keyword
    */
    
	  public void mouseReleased(java.awt.event.MouseEvent e) {
		  
	  }
	  
	  
    /**
    Method for mouseEntered. Note the public keyword
    */
   
	  public void mouseEntered(java.awt.event.MouseEvent e) {
		  
	  }
	  
	  
    /**
    Method for mouseExited. Note the public keyword
    */
    
	  public void mouseExited(java.awt.event.MouseEvent e) {
		  
	  }
}// end of FrameListener class. 