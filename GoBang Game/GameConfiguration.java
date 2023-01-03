/*
 * GameConfiguration.java
 * GameConfiguration Class for Computer CPT Project
 * Ray Xu, Richard Zhou, Maxwell He
 * ICS4U1-a   
 * January 26, 2022
 */

import java.awt.Image; // Provides the classes necessary to create an applet and the classes an applet uses to communicate with its applet context. Contains all of the classes for creating user interfaces and for painting graphics and images.

import javax.swing.ImageIcon; //Swing provides a particularly useful implementation of the Icon interface, and that is the ImageIcon , which paints an icon from a GIF, JPEG, or PNG image. This is very useful in coding and will help us a great deal here to show the picture of the backchess and whitechess. 

/**
* The method below will define the interface related to the board data, save the starting point of the board, the size of the squares/grid on the board, and the number of rows and columns, etc. 
*/    
public interface GameConfiguration { // Note that like what we learned in class, an interface in java is essentially a collection of abstract methods. It is a reference type in Java, very similar to class. 
	int x=20,y=20,size=40,row=15,column=15; // set all the congiguration here for the GoBang. Size, row, colum, x, and y. 
	Image blackChecker= new ImageIcon("BlackChecker.png").getImage();	//Black Go Image linked here. 
	Image whiteChecker= new ImageIcon("WhiteChecker.png").getImage();	//White Go Image linked here.  
}