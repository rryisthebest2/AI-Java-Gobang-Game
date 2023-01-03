/*
 * Main.java
 * Main Class for Computer CPT Project
 * Ray Xu, Richard Zhou, Maxwell He
 * ICS4U1-a
 * January 26, 2022
 */

/**
* Main Class for initializing the object for the Gobang interface. This the main method of the code. It's going to be s a short class because we use OOP in coding.
*/
class Main {
  // Main function entry
	public static void main(String args[]) {
		GameFrame gameFrame=new GameFrame();// Here, we Initialize an object for the GoBang Game interface. 
		gameFrame.initializeUI();//Here, we call method for interface initialization
	}
}