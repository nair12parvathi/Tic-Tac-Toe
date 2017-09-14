import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;


public class gridClass extends JPanel implements Serializable {
	
	String temp = "Yo baby!";
	String who;
	private JPanel matrix;
	private JButton button;
	public String mySymbol;
	private Color myColor;
	JButton[] buttonList = new JButton[9];
	private JFrame gridWindow;
	int recentChange = -1;		//******************************************************************
	int gameCompleteFlag = 0;
	private gridListener listener;
	
	public gridClass(String who, String mySymbol, Color myColor, gridListener listener){
		this.who = who;
		this.mySymbol = mySymbol;
		this.myColor = myColor;
		this.listener = listener;
	}

	public void makeDisplay() {
		makePanels();
		System.out.println("making display panels");
		gridWindow = new JFrame("TicTacToe Grid for " + who);
		gridWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gridWindow.setSize(400,400);
		
		// making grid buttons
		for(int i = 0; i < 9; i++){
			button = new JButton();
			button.setName(""+i);
			buttonList[i] = button;
			
			button.addActionListener(new ButtonListener());
			button.setForeground(myColor);
			button.setBackground(Color.white);
			button.setFont(new Font("Arial", Font.BOLD, 60));
			
			button.setPreferredSize(new Dimension(100,100));
			matrix.add(button);
		}
				
		gridWindow.getContentPane().add(matrix);
		gridWindow.pack();
		gridWindow.setLocationByPlatform(true);
		gridWindow.setVisible(true);
		
	}

	private void makePanels() {
		// TODO Auto-generated method stub
		matrix = new JPanel(new GridLayout(3,3));
		matrix.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		setLayout(new BorderLayout());
	}
	
	class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent somethingHappened) {
			int didIwinFlag = 0;
			//System.out.println(somethingHappened.getActionCommand() + " got pressed");
			JButton buttonActioned = (JButton) somethingHappened.getSource();
			recentChange = Integer.parseInt(buttonActioned.getName());
			buttonActioned.setText(mySymbol);
			buttonActioned.setBackground(new Color(255, 255 ,204));
			for(int i = 0; i < 9; i++){
				buttonList[i].setEnabled(false);
			}
			
			didIwinFlag = didIwin();
			listener.onButtonClick(didIwinFlag);
			
			
		}

		private int didIwin() {
			int flag = 0;
			
			// checking if the player won
			if(buttonList[0].getText().equals(buttonList[1].getText()) && buttonList[1].getText().equals(buttonList[2].getText()) 
					&& buttonList[2].getText().equals(mySymbol)){
				flag = 1;
				return flag;
			}
			if(buttonList[0].getText().equals(buttonList[3].getText()) && buttonList[3].getText().equals(buttonList[6].getText()) 
					&& buttonList[6].getText().equals(mySymbol)){
				flag = 1;
				return flag;
			}
			if(buttonList[0].getText().equals(buttonList[4].getText()) && buttonList[4].getText().equals(buttonList[8].getText()) 
					&& buttonList[8].getText().equals(mySymbol)){
				flag = 1;
				return flag;
			}
			
			if(buttonList[2].getText().equals(buttonList[5].getText()) && buttonList[5].getText().equals(buttonList[8].getText()) 
					&& buttonList[8].getText().equals(mySymbol)){
				flag = 1;
				return flag;
			}
			if(buttonList[2].getText().equals(buttonList[4].getText()) && buttonList[4].getText().equals(buttonList[6].getText()) 
					&& buttonList[6].getText().equals(mySymbol)){
				flag = 1;
				return flag;
			}
			
			if(buttonList[8].getText().equals(buttonList[7].getText()) && buttonList[7].getText().equals(buttonList[6].getText()) 
					&& buttonList[6].getText().equals(mySymbol)){
				flag = 1;
				return flag;
			}
			
			if(buttonList[1].getText().equals(buttonList[4].getText()) && buttonList[4].getText().equals(buttonList[7].getText()) 
					&& buttonList[7].getText().equals(mySymbol)){
				flag = 1;
				return flag;
			}
			if(buttonList[3].getText().equals(buttonList[4].getText()) && buttonList[4].getText().equals(buttonList[5].getText()) 
					&& buttonList[5].getText().equals(mySymbol)){
				flag = 1;
				return flag;
			}
			
			return flag;
		}
		
	}

	public void updateDisplay() {
		// TODO Auto-generated method stub
		for(int i = 0; i < 9; i++){
			if(buttonList[i].getText().equals("")){
				buttonList[i].setEnabled(true);
			}
		}
				
	}

	public interface gridListener{
		public void onButtonClick(int didIwinFlag);
	}
}



