import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Battle {

	private int turnCounter;

	private Trainer trainer1;
	private Trainer trainer2;

	private Trainer currentTrainer;

	private Trainer firstAttacker;
	private Trainer secondAttacker;

	public Battle(Trainer t1, Trainer t2){
		this.trainer1 = t1;
		this.trainer2 = t2;
		this.turnCounter = 1;

		this.currentTrainer = this.trainer1;

	}

	public boolean battleDone(){
		return !(this.trainer1.canFight() && this.trainer2.canFight());
	}

	public void newTurn(){
		this.trainer1.setCurrAction(null);
		this.trainer2.setCurrAction(null);

		this.turnCounter++;
	}

	public void executeTurn(JTextArea display) throws InterruptedException{

		// Checks to see if turn is ready to be executed and both players have selected a move
		if(this.trainer1.getCurrAction() == null || this.trainer2.getCurrAction() == null || this.currentTrainer == this.trainer1){

			// If first trainer selected action and second trainer hasnt, switch the new current trainer
			if(this.trainer1.getCurrAction() != null && this.trainer2.getCurrAction() == null && this.currentTrainer == this.trainer1){
				this.currentTrainer = this.trainer2;
			}
			return;
		}

		// Determine who goes first
		this.currentTrainer = this.trainer1;
		determinePriority();

		// Have the first attacker execute their move
		String[] commandArgs = this.firstAttacker.getCurrAction().split("\\s+");
		
		// If attacking
		if(commandArgs[0].equals("attack")){

			String moveString1 = commandArgs[1];
			Move m1 = this.firstAttacker.getCurrentPokemon().getMove(moveString1);

			if(this.attack(display, firstAttacker,secondAttacker, m1)){
				return;
			}
		}

		// If switching out pokemon
		else if(commandArgs[0].equals("switch")){
			this.switchPokemon(display, firstAttacker);
		}


		// Second trainer moves
		commandArgs = this.secondAttacker.getCurrAction().split("\\s+");
		
		// If attacking
		if(commandArgs[0].equals("attack")){

			String moveString2 = commandArgs[1];
			Move m2 = this.secondAttacker.getCurrentPokemon().getMove(moveString2);

			if(this.attack(display, secondAttacker,firstAttacker,m2)){
				return;
			}
		}

		// If switching out pokemon
		else if(commandArgs[0].equals("switch")){
			this.switchPokemon(display, secondAttacker);
		}

		


		this.currentTrainer = this.trainer1;
		newTurn();


	}

	public void determinePriority(){

		String[] command1 = this.trainer1.getCurrAction().split("\\s+");
		String[] command2 = this.trainer2.getCurrAction().split("\\s+");


		if(this.trainer1.getCurrentPokemon().getStat("spd") > this.trainer2.getCurrentPokemon().getStat("spd") || command1[0].equals("switch")){
			this.firstAttacker = this.trainer1;
			this.secondAttacker = this.trainer2;
		}
		else if(this.trainer1.getCurrentPokemon().getStat("spd") < this.trainer2.getCurrentPokemon().getStat("spd") || command1[1].equals("switch")){
			this.firstAttacker = this.trainer2;
			this.secondAttacker = this.trainer1;
		}
		else{
			if(Math.random() <= 0.5){
				this.firstAttacker = this.trainer1;
				this.secondAttacker = this.trainer2;
			}
			else{
				this.firstAttacker = this.trainer2;
				this.secondAttacker = this.trainer1;
			}
		}
	}
	/**
	 * 
	 * 
	 * @param text
	 * @param t1
	 * @param t2
	 * @param m
	 * @return boolean if attack caused other pokemon to faint
	 * @throws InterruptedException
	 */

	
	public void switchPokemon(JTextArea display, Trainer t) throws InterruptedException{
		
		int id = Integer.parseInt(t.getCurrAction().split("\\s+")[1]);
		
		
		display.setText(t.getName() + " withrew " + t.getCurrentPokemon() + "!");
		display.update(display.getGraphics());
		Thread.sleep(BattleGUI.DELAY);
		
		// Looks for the pokemon in the party matching the unique identifier
		for(Pokemon p : t.getParty()){
			if(p.getUniqueID() == id){
				t.setCurrentPokemon(p);
			}
		}
		
		display.setText(t.getName() + " sent out " + t.getCurrentPokemon() + "!");
		display.update(display.getGraphics());
		Thread.sleep(BattleGUI.DELAY);
		
	}

	public boolean attack(JTextArea text, Trainer t1, Trainer t2, Move m) throws InterruptedException{

		Pokemon p1 = t1.getCurrentPokemon();
		Pokemon p2 = t2.getCurrentPokemon();

		text.setText(p1.getName() + " used " + m.getName() + "!");
		text.update(text.getGraphics());
		Thread.sleep(BattleGUI.DELAY);


		boolean hit = p1.attack(p2, m);

		if(hit){
			// Text indicatingg super effective
			double modifier = p2.getTypeEffectiveness(m);
			if(modifier == 0.5 || modifier == 0.25){
				text.setText("It's not very effective...");
				text.update(text.getGraphics());
				Thread.sleep(BattleGUI.DELAY);
			}
			else if (modifier == 2.0 || modifier == 4.0){
				text.setText("It's super effective!");
				text.update(text.getGraphics());
				Thread.sleep(BattleGUI.DELAY);
			}
			else if(modifier == 0.0){
				text.setText("It had no effect...");
				text.update(text.getGraphics());
				Thread.sleep(BattleGUI.DELAY);
			}
		}
		else{
			text.setText(p1.getName() + "'s attack missed!");
			text.update(text.getGraphics());
			Thread.sleep(BattleGUI.DELAY);
		}

		// If fainted, have set out next pokemon
		if(p2.isFainted()){
			text.setText(p2.getName() + " fainted!");
			text.update(text.getGraphics());
			Thread.sleep(BattleGUI.DELAY);

			if(!t2.canFight()){
				text.setText(t1.getName() + " won!");
				text.update(text.getGraphics());
				Thread.sleep(BattleGUI.DELAY);
				return true;
			}

			Pokemon sendOut = t2.getParty().get(t2.getParty().indexOf(t2.getCurrentPokemon()) + 1);
			t2.setCurrentPokemon(sendOut);

			text.setText(t2.getName() + " sent out " + sendOut.getName() + "!");
			text.update(text.getGraphics());
			Thread.sleep(BattleGUI.DELAY);
			newTurn();
			return true;
		}

		return false;
	}

	public Trainer getTrainer1(){
		return this.trainer1;
	}

	public Trainer getTrainer2(){
		return this.trainer2;
	}

	public Trainer getCurrentTrainer(){
		return this.currentTrainer;
	}


	public int getTurnCounter() {
		return turnCounter;
	}

}
