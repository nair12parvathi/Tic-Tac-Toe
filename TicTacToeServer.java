import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


/**
 * This program implements a networked Tic Tac Toe game. (Server Side)
 * @author Pranit Meher (pxm3417@rit.edu)
 * @author Neha Upadhyay (nxu3128@rit.edu)
 * @author Parvathi Nair(pan7447@rit.edu)
 *
 */
public class TicTacToeServer implements gridClass.gridListener {
	private static   int listeningPort;
	private static String ipAddress;
	private static InetAddress IP;
	// main class file of the program
	private static int portNumber;
	private ServerSocket serverSocket;
	private static Socket socket;
	private gridClass ServerGrid;
	private static int myTurn;
	private static String opponentChar;
	private static String myChar;
	//private TicTacToeServer selfObj;
	
	

	public TicTacToeServer(){
		myChar = "X";
		opponentChar = "O";
		ServerGrid = new gridClass("Server", myChar, Color.blue, this);
		myTurn = 0;
		
	}
	
	public void makeSocket(){
		Socket sendSocket = new Socket();
		
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException{
		TicTacToeServer selfObj = new TicTacToeServer();
		// reading the input
		selfObj.readInput();
		// displaying the grid
		selfObj.ServerGrid.makeDisplay();
		selfObj.makeSockets();	
	}
	
	private void play() {
		
		//gridClass tempGrid = null;
		
		while(/*ServerGrid.gameCompleteFlag == 0*/ true){
			
			
			// receive grid
			ObjectInputStream inputStream;
			try {
				System.out.println("Server Receiving");
				//socket = serverSocket.accept();
				inputStream = new ObjectInputStream(socket.getInputStream());
				ServerGrid.recentChange = (int) inputStream.readObject();
				System.out.println("Received");
			} 
			catch (IOException | ClassNotFoundException e) {
				System.out.println("Lost connection with Client");
				System.exit(0);
			}
			if(ServerGrid.recentChange == 10){
				System.out.println("You lose");
				System.exit(0);
			}
			
			ServerGrid.buttonList[ServerGrid.recentChange].setText(opponentChar);
			ServerGrid.buttonList[ServerGrid.recentChange].setEnabled(false);
			
			ServerGrid.updateDisplay();	// make buttons active again
			
			// sends grid with onButtonClickEvent
			
		}
		
	}
	private void makeSockets()  {

		System.out.println("Making sockets");
		try {
			serverSocket = new ServerSocket(listeningPort);
			socket = serverSocket.accept();
			System.out.println("Server waiting for client at port: " + listeningPort);

			while(true){
				System.out.println("Connection Established");
				play();
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Lost connection with Client");
			System.exit(0);
		}
		
	}
	private void readInput() throws UnknownHostException {
		Scanner readInput = new Scanner(System.in);
		
		// reading Server's listening port
		/*System.out.println("Please enter the Port number to listen on: ");
		listeningPort = readInput.nextInt();*/
		
		listeningPort = 12345;	// REMOVE THIS LINE***********************************************************************

	}
	@Override
	public void onButtonClick(int didIwinFlag) {
		// send grid
		try {
			if(didIwinFlag == 1){
				ServerGrid.recentChange = 10; // for win
			}
			System.out.println("Server Sending");
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(ServerGrid.recentChange);
			if(didIwinFlag == 1){
				System.out.println("Congratulations! You won");
				System.exit(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	

}
