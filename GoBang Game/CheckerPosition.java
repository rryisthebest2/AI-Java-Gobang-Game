/*
 * CheckerPosition.java
 * CheckerPosition Class for Computer CPT Project
 * Ray Xu, Richard Zhou, Maxwell He
 * ICS4U1-a
 * January 26, 2022
 */

/**
* The class below is another very important class for the code. It will store the information of every checker's position data for each move. Without this class, the game will not be able to keep track of each checker's position.
*/
public class CheckerPosition { // At here, we created a ChessPosition class. Description see above.    
	public int list1,list2; // create two public integer here. The list1 and list2 are just my personal preference. No specific meaning here. you could do listk or listf. It doesn't matter what you name it as. They will help with storing information.

  /**This method is mandatory here in order for the code to work. It's an internal logic we created. More information can be found in documents. The method do not require any paraemters as can be seen below. 
  */
  public CheckerPosition(){
   
  }
  /** 
  * This is a very important method for the code, it keeps all the information into the public integer we created. The reason why we can use integer here is because the checker are going to be place on the intersection of the vertical and horizontal line of the gameboard which indicates that it must be integer value. This method requires two integer type paraemters list1 and list2 which are declared above. 
  */
	public CheckerPosition(int list1,int list2) {
		this.list1=list1;// this keyword here. It refers to the current object in a method or constructor. 
		this.list2=list2;// this keyword here. It refers to the current object in a method or constructor.
	}
} // end of CheckerPosition class.   
