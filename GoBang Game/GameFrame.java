/*
 * GameFrame.java
 * GameFrame Class for Computer CPT Project
 * Ray Xu, Richard Zhou, Maxwell He
 * ICS4U1-a
 * January 26, 2022
 */

import javax.swing.JFrame; // import from library
import javax.swing.JOptionPane; // import from library
import javax.swing.JPanel; // import from library
import javax.swing.ImageIcon; // import from library
import javax.swing.JButton; // import from library
import javax.swing.JComboBox; // import from library
import java.awt.Dimension; // import from library
import java.awt.BorderLayout; // import from library
import java.awt.Color; // import from library
import java.awt.FlowLayout; // import from library
import java.awt.Graphics; // import from library
import java.awt.Image; // import from library
import java.util.ArrayList; // import from library
import java.util.HashMap; // import from library

/**
* This is also a very important class for the game. As you can probably tell from its name, this class is responsible for handling the whole interface of the game. It had sevreal important job such as creating the panel, set the color for the panel, create buttons and format the buttons on the interface, etc.   
*/

public class GameFrame extends JPanel implements GameConfiguration{ // note that this is a class that both extends and implements. This is allowed in OOP coding. In this case, it extends on JPanel and implements GameConfiguration. 
	public Graphics brush;//define a brush for the paint method. 
	public int[][] isAvail=new int [column][row];// Define a two-dimensional array to store the board's movement/moves.
	public ArrayList<CheckerPosition>checkerPositionList=new ArrayList<CheckerPosition>();//Save each step of checker movement. We do so by calling the CheckerPosition class we created and created a new arrarylist called checkerPositionList. 
	public int turn=0;// Cannot play if turn equals 0. turn = 1 is black player's turn, turn = 2 is white player's turn. 
	public int chooseType=0;//0 means Human vs. Human, 1 means Human vs. AI, default human vs. human. This will be changed in the Button.java file under the Mode selection section. Note the public keyword here.    
	
  /**
  The below part is the most important part of the code. It is the actual AI algorithem that we created after researching on GoBang books. Below are the strategy that the GoBank mentioned. Therefore, in a very real sense, the player is not playing againest a simple algorithem but againest an AI who actually learned the entire GoBang book. 
  */     

	public static HashMap<String,Integer> board = new HashMap<String,Integer>();// Set an array of different drop cases and corresponding weights (drop is a chekcer move. the definition weights can be found in our package under algorithem section. This is a very technical term and it's based on the math formula we used)
	static {//check connection situation classification(major part of AI algorithem, check created documentary to understand following term such as Sleep 1 connect. They are all GoBang terms to describe certain strategy)

		//Blocked (Block a certain point on the GameBoard by drop checkers) Below are all strategies for Block
		board.put("01", 25);//Sleep 1 connect
		board.put("02", 22);//Sleep 1 connect
		board.put("001", 17);//Sleep 1 connect
		board.put("002", 12);//Sleep 1 connect
		board.put("0001", 17);//Sleep 1 connect
		board.put("0002", 12);//Sleep 1 connect
		
		board.put("0102",25);//Sleep 1 connect，15
		board.put("0201",22);//Sleep 1 connect，10
		board.put("0012",15);//Sleep 1 connect，15
		board.put("0021",10);//Sleep 1 connect，10
		board.put("01002",25);//Sleep 1 connect，15
		board.put("02001",22);//Sleep 1 connect，10
		board.put("00102",17);//Sleep 1 connect，15
		board.put("00201",12);//Sleep 1 connect，10
		board.put("00012",15);//Sleep 1 connect，15
		board.put("00021",10);//Sleep 1 connect，10

		board.put("01000",25);//Live 1 connect，15
		board.put("02000",22);//Live 1 connect，10
		board.put("00100",19);//Live 1 connect，15
		board.put("00200",14);//Live 1 connect，10
		board.put("00010",17);//Live 1 connect，15
		board.put("00020",12);//Live 1 connect，10
		board.put("00001",15);//Live 1 connect，15
		board.put("00002",10);//Live 1 connect，10

		//Blocked (Block a certain point on the GameBoard by drop checkers)Below are all strategies for Block
		board.put("0101",65);//Sleep 2 connect，40
		board.put("0202",60);//Sleep 2 connect，30
		board.put("0110",80);//Sleep 2 connect，40
		board.put("0220",76);//Sleep 2 connect，30
		board.put("011",80);//Sleep 2 connect，40
		board.put("022",76);//Sleep 2 connect，30
		board.put("0011",65);//Sleep 2 connect，40
		board.put("0022",60);//Sleep 2 connect，30
		
		board.put("01012",65);//Sleep 2 connect，40
		board.put("02021",60);//Sleep 2 connect，30
		board.put("01102",80);//Sleep 2 connect，40
		board.put("02201",76);//Sleep 2 connect，30
		board.put("01120",80);//Sleep 2 connect，40
		board.put("02210",76);//Sleep 2 connect，30
		board.put("00112",65);//Sleep 2 connect，40
		board.put("00221",60);//Sleep 2 connect，30

		board.put("01100",80);//Live 2 connect，40
		board.put("02200",76);//Live 2 connect，30
		board.put("01010",75);//Live 2 connect，40
		board.put("02020",70);//Live 2 connect，30
		board.put("00110",75);//Live 2 connect，40
		board.put("00220",70);//Live 2 connect，30
		board.put("00011",75);//Live 2 connect，40
		board.put("00022",70);//Live 2 connect，30
		
		//Blocked (Block a certain point on the GameBoard by drop checkers)Below are all strategies for Block
		board.put("0111",150);//Sleep 3 connect，100
		board.put("0222",140);//Sleep 3 connect，80
		
		board.put("01112",150);//Sleep 3 connect，100
		board.put("02221",140);//Sleep 3 connect，80
	
		board.put("01110", 1100);//Live 3 connect
		board.put("02220", 1050);//Live 3 connect
		board.put("01101",1000);//Live 3 connect，130
		board.put("02202",800);//Live 3 connect，110
		board.put("01011",1000);//Live 3 connect，130
		board.put("02022",800);//Live 3 connect，110
		
		board.put("01111",3000);//4 connect，300
		board.put("02222",3500);//4 connect，280
	}
	public int[][] weightArray=new int[column][row];// Define a two-dimensional array that holds the weights of each point (More definition of weights can be find in our group's package)
	

	/**
   Initialize an interface and set the title size and other properties such as color, size, layout, etc. It also responsible for the creation of buttons on the interface. 
   */    
	public void initializeUI() {
		JFrame frame=new JFrame(); // create a new JFrame object here called frame. Responsible for the whole interface of the game. 
		frame.setTitle("GoBang"); // set the title of the frame as GoBang. This can be seen at the top of our interface. 
		frame.setSize(765,635); // set the size of the frame. 
		frame.setLocationRelativeTo(null); // set location relative to. null indicates. null in general indicate that the variable does not refer to any object.     
		frame.setDefaultCloseOperation(3); // set default close operation here. 
		
		frame.setLayout(new BorderLayout());// Set the top container JFrame as the frame layout
		
		Dimension dim1=new Dimension(150,0);//Setting the size of the right half
		Dimension dim3=new Dimension(550,0);//Setting the size of the left half
		Dimension dim2=new Dimension(140,40);// Set the size of the right button component. This will be used later for creating those buttons. 
		
		//Implement the interface on the left, add the GoBangframe object to the middle part of the frame layout
		//There is already a GoBangframe object, indicating that the current class object is 'this'
		this.setPreferredSize(dim3);//Set the size of the GoBang playing screen
		this.setBackground(Color.LIGHT_GRAY);//Set the color of the GoBang playing screen. We choosed light gray. 
		// Here, add the left panel directly here, indicating that it is in the middle section of the frame layout
		// If placed in other sections, as tested by Richard, there will be some minor problems*
		frame.add(this,BorderLayout.CENTER);//Add to the middle part of the frame layout
		
		// Implement the JPanel container interface on the right
		JPanel panel=new JPanel();
		panel.setPreferredSize(dim1);// Set the size of JPanel
		panel.setBackground(Color.white);// Set the right interface color to white
		frame.add(panel,BorderLayout.EAST);// Add to the east part of the frame layout
		panel.setLayout(new FlowLayout());// Set JPanel to flow layout
		
		// Next we need to add the buttons and other components to the JPanel in turn
		// Set the button array
		String[] butName= {"New Game","Undo","Surrender","User Guide","Game Rules","Exit"};
		JButton[] button=new JButton[6];
		
		// Add the three button components in turn, it will loop until the for loop is satisy
		for(int i=0;i<butName.length;i++) { // notice the < sign, not <= sign. 
			button[i]=new JButton(butName[i]);
			button[i].setPreferredSize(dim2); // use dim2 in this case for buttons.
			panel.add(button[i]); // add it. 
		}
		
		//Set Options button
		String[] boxName= {"Human Vs. Human","Human Vs. AI"};
		JComboBox<String> box=new JComboBox<>(boxName);
		panel.add(box);// add box. 
		
		//button monitoring section 
		Button butListen=new Button(this,box);
		// Add a mechanism for listening to status events for each button
		for(int i=0;i<butName.length;i++) { // notice the < sign, not <= sign. 
			button[i].addActionListener(butListen);// Add a listener method for the occurrence of the operation
		}
		
		// Add event listening mechanism for checkable boxes
		box.addActionListener(butListen);
		
		FrameListener fl=new FrameListener();
		fl.setGraphics(this);//Get the brush object
		this.addMouseListener(fl);
		
		frame.setVisible(true);

    JOptionPane.showMessageDialog(this,"Welcome to GoBang!! Click 'New Game' to start the game. Please Read \nthe Game Rules and 'user Guide' before starting the game");
	}
	
  /**
  This method is responsible for the message pop up functionality. We will use this to pop up message windows to communicate with the users. For example, show them the result of the game etc. 
  */
	public void popUp(String top,String result) {
		JOptionPane jo=new JOptionPane();
		jo.showMessageDialog(null, result, top, JOptionPane.PLAIN_MESSAGE);
	}
	
	/**
  This is the redraw method. It's really important for the game for both the upcoming Undo functionality and also the restart game functionality. 
  */
	public void paint(Graphics brush) {
		super.paint(brush);//Draw the white box
		
		Image icon= new ImageIcon("GameBoard.jpg").getImage();	// ImageIcon cannot be used here(Ray Xu debugs, tested)
		brush.drawImage(icon, 0, 0, row*size, column*size, null);
		
		// Redraw the checkers here
		for(int i=0;i<row;i++) {
			for(int j=0;j<column;j++) {
				if(isAvail[i][j]==1) { // if black is true, use black checker. 1 is black. 
					int countX=size*j+20;
					int countY=size*i+20;
					brush.drawImage(blackChecker,countX-size/2, countY-size/2, size, size,null); // draw it 
				}
				else if(isAvail[i][j]==2) { // if white is true, use white checker. 2 is white.  
					int countX=size*j+20;
					int countY=size*i+20;
					brush.drawImage(whiteChecker,countX-size/2, countY-size/2, size, size,null); // draw it
				}
			}
		}
	}
	
}//end of GameFrame class