import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.event.*;
import java.util.ArrayList;
import java.awt.*;


public class BattleGUI extends JFrame implements ActionListener{
	private static final int HEIGHT = 500;
	private static final int WIDTH = 400;

	public static final int DELAY = 1000;

	private JPanel panel;
	private JTextArea textDisplay;
	private Battle battleInstance;

	private GuiState state;

	public BattleGUI(Battle b) throws InterruptedException{

		super();
		this.battleInstance = b;
		this.state  = GuiState.SELECT_MOVE;

		setTitle("Pokemon Battle Simulator");

		setSize(WIDTH, HEIGHT);

		this.panel = new JPanel();
		add(panel);

		textDisplay = new JTextArea();
		textDisplay.setVisible(true);
		textDisplay.setPreferredSize(new Dimension((int) (WIDTH * 0.75), (int) (HEIGHT * 0.4) ));
		textDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(textDisplay);

		setVisible(true);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		textDisplay.setText(this.battleInstance.getTrainer1().getName() + " sent out " + this.battleInstance.getTrainer1().getCurrentPokemon().getName() + "!");
		textDisplay.repaint();
		Thread.sleep(DELAY);

		textDisplay.setText(this.battleInstance.getTrainer2().getName() + " sent out " + this.battleInstance.getTrainer2().getCurrentPokemon().getName() + "!");
		textDisplay.repaint();
		Thread.sleep(DELAY);

		displayMoveSelect();

	}

	public void displayPokemonSelect(){

		panel.removeAll();

		ArrayList<Pokemon> list = this.battleInstance.getCurrentTrainer().getParty();

		for(Pokemon p : list){

			JButton b = new JButton();
			b.setPreferredSize(new Dimension((int) (0.4 * WIDTH),(int) (0.15 * HEIGHT)));
			b.setText(p.getName());
			b.setActionCommand("switch " + p.getUniqueID());
			b.addActionListener(this);
			
			

			String status = p.getTypeString() + "    " + p.getHealthString() + (p.isFainted() ? "    FNT":"");
			JLabel l = new JLabel(status);
			l.setPreferredSize(new Dimension((int) (0.4 * WIDTH),(int) (0.15 * HEIGHT)));
			l.setVerticalAlignment(JLabel.CENTER);
			l.setText(status);

			panel.add(b);
			panel.add(l);
		}

		if(this.state == GuiState.CHANGE_POKEMON){
			JButton back = new JButton("Back");
			back.setPreferredSize(new Dimension((int) (0.4 * WIDTH),(int) (0.05 * HEIGHT)));
			back.addActionListener(this);
			back.setActionCommand("back");
			panel.add(back);
		}


		panel.revalidate();
		panel.repaint();

	}

	public void displayMoveSelect(){



		panel.removeAll();

		String textString = this.battleInstance.getCurrentTrainer().getName() + "'s turn!\n\n" + this.battleInstance.getCurrentTrainer().getCurrentPokemon();

		textDisplay.setText(textString);
		panel.add(textDisplay);

		createAttackButtons();


		JButton b = new JButton();
		b.setPreferredSize(new Dimension((int) (0.8 * WIDTH),(int) (0.15 * HEIGHT)));

		b.setActionCommand("switch");
		b.setText("Switch");
		b.addActionListener(this);

		panel.add(b);


		panel.revalidate();
		panel.repaint();
	}

	private void createAttackButtons(){

		Move[] moves = this.battleInstance.getCurrentTrainer().getCurrentPokemon().getMoves();



		for(int i = 0; i < moves.length; i++){

			JButton b = new JButton();
			b.setPreferredSize(new Dimension((int) (0.4 * WIDTH),(int) (0.15 * HEIGHT)));

			if(moves[i] == null){
				b.setText("");
			}
			else{
				b.setText(moves[i].getName());
				b.setActionCommand("attack " + moves[i].getName());
				b.addActionListener(this);

			}
			this.panel.add(b);
		}


	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// Handle action based on current UI state
		switch(this.state){
		case SELECT_MOVE:
			handleMoveSelectScreenAction(e.getActionCommand());
			break;
		case CHANGE_POKEMON:
		case CHANGE_POKEMON_WHEN_FAINTED:
			handlePokemonSelectScreenAction(e.getActionCommand());
			break;
		default:
			break;

		}

		// Execute 
		
	
	}
	
	private void handleMoveSelectScreenAction(String command){

		if(command.equals("switch")){
			this.state = GuiState.CHANGE_POKEMON;
			displayPokemonSelect();
			return;
		}	
		
		this.battleInstance.getCurrentTrainer().setCurrAction(command);
		
		try {
			this.battleInstance.executeTurn(this.textDisplay);
			if(this.battleInstance.battleDone()){
				this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		displayMoveSelect();

	}

	private void handlePokemonSelectScreenAction(String command){
		
		displayMoveSelect();
		
		if(command.equals("back")){
			displayMoveSelect();
			this.state = GuiState.SELECT_MOVE;
			return;
		}
		
		this.battleInstance.getCurrentTrainer().setCurrAction(command);
		
		try {
			
			this.battleInstance.executeTurn(this.textDisplay);
			if(this.battleInstance.battleDone()){
				this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
				
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


}
