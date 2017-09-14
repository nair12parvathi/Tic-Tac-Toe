import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;



/**
 * This program implements a networked Tic Tac Toe game. (Client Side)
 * @author Pranit Meher (pxm3417@rit.edu)
 * @author Neha Upadhyay (nxu3128@rit.edu)
 * @author Parvathi Nair(pan7447@rit.edu)
 *
 */
public class TicTacToeClient implements gridClass.gridListener{

	private static String ipAddress;
	private static InetAddress IP;
	private static int portNumber;
	private static Socket clientSocket;
	private static int myTurn = 1;
	private static String myChar;
	private static String opponentChar;
	private gridClass clientGrid;

	public TicTacToeClient(){
		myTurn = 1;
		myChar = "O";
		opponentChar = "X";
		clientGrid = new gridClass("Client", myChar, Color.red, this);

	}
	
	
	public static void main(String[] args) {
		TicTacToeClient selfObj = new TicTacToeClient();
		selfObj.readInput();
		selfObj.clientGrid.makeDisplay();
		selfObj.makeSockets();
		//sendObject(clientGrid);
	}


	private void makeSockets() {
		System.out.println("Making client sockets");
		try {
			clientSocket = new Socket(ipAddress,portNumber);
			play();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Lost connection with Server");
			System.exit(0);
			
		}
	}

	private void play() {		
		while(clientGrid.gameCompleteFlag == 0){
			
			// receive grid
			System.out.println("Client Receiving");
			ObjectInputStream inputStream;
			try {
				inputStream = new ObjectInputStream(clientSocket.getInputStream());
				clientGrid.recentChange =  (int) inputStream.readObject();
				
				if(clientGrid.recentChange == 10){
					System.out.println("You lose");
					System.exit(0);
				}
				// update button
				clientGrid.buttonList[clientGrid.recentChange].setText(opponentChar);
				clientGrid.buttonList[clientGrid.recentChange].setEnabled(false);
				clientGrid.updateDisplay();	// make buttons active again
				
				
			} 
			catch (IOException | ClassNotFoundException e) {
				System.out.println("Lost connection with Server");
				System.exit(0);
			}
		
			//send grid from function
		}
		
	}

	private void readInput() {
		Scanner readInput = new Scanner(System.in);
		try {
			/*// reading Client's ip and port from the user
			System.out.println("Please enter the Server's IP address");
			ipAddress = readInput.nextLine();*/
			
			ipAddress = "localhost";	// REMOVE THIS LINE***********************************************************************
			
			IP = InetAddress.getByName(ipAddress);
			
			/*System.out.println("Please enter the Server's port number");
			portNumber = readInput.nextInt();*/
			portNumber = 12345;		// REMOVE THIS LINE***********************************************************************
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("invalid IP");
			System.exit(0);
		}
		
		
	}
	
	@Override
	public void onButtonClick(int didIwinFlag) {
		// send grid
		try {
			if(didIwinFlag == 1){
				clientGrid.recentChange = 10; // for win
			}
			System.out.println("Client Sending");
			ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			outputStream.writeObject(clientGrid.recentChange);
			if(didIwinFlag == 1){
				System.out.println("Congratulations! You won");
				System.exit(0);
			}
		} catch (IOException e) {
			System.out.println("Lost connection with Server");
			System.exit(0);
		}
		
	}

}
