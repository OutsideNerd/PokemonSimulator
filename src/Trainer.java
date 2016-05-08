import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class that represents a Pokemon trainer during a battle.
 * 
 * @author Evan Dyke
 *
 */
public class Trainer {
	
	public static final int MAX_PARTY_SIZE = 6;
	
	private Pokemon currentPokemon;
	private String name;
	private ArrayList<Pokemon> party = new ArrayList<Pokemon>();
	private String currAction; // String representing action trainer is to execute during turn.
	
	/**
	 * Constructor for Trainer. 
	 * 
	 * @param name name of the trainer
	 */
	public Trainer(String name){
		this.name = name;
		this.currAction = null;
	}
	
	/**
	 * Switches out the trainer's current Pokemon for a different POkemon 
	 * 
	 * @param index position (starting at 1) in the party of the POkemon to switch in
	 * @return true
	 */
	public boolean swap(int index){
		Pokemon prevPokemon = currentPokemon;
		currentPokemon = this.party.get(index - 1);
		
		this.party.set(index - 1, prevPokemon);
		this.party.set(1, currentPokemon);
		
		return true;
	}
	/**
	 * Adds a Pokemon to the trianer's party
	 * 
	 * @param num Pokedex number of the species of the Pokemon
	 * @param level Level of the Pokemon
	 * @param EVs array of EVs (effort values) for individual Pokemon
	 * @param IVs array of IVs (individual values) for individual Pokemon 
	 * @param natureModifier array of stat modifiers for the nature of the Pokemon
	 * @return reference to Pokemon that was added to the party if successful. The method returns null
	 * if the Pokemon was unable to be added due to the party being full 
	 * @throws PokemonNotFoundException exception thrown if the species number doesn't exist
	 */
	public Pokemon addMemberToParty(int num, int level, int[] EVs, int[] IVs, double[] natureModifier) throws PokemonNotFoundException{
		Pokemon p = new Pokemon(num, this,  level, EVs, IVs, natureModifier);
		return this.addMemberToParty(p);
	}
	
	/**
	 * Adds a Pokemon to the Trainer's party.
	 * 
	 * @param p Pokemon to add to the party
	 * @return reference to Pokemon that was added to the party if successful. The method returns null
	 * if the Pokemon was unable to be added due to the party being full 
	 */
	public Pokemon addMemberToParty(Pokemon p){
		
		if(this.currentPokemon == null){
			this.party.add(p);
			this.currentPokemon = p;
			return p;
		}
		
		if(party.size() < MAX_PARTY_SIZE){
			this.party.add(p);
			return p;
			
		}
		else{
			System.out.println("Your party is full!");
			return null;
		}
		
	}
	
	/**
	 * Gets the Trainer's current Pokemon
	 * 
	 * @return
	 */
	public Pokemon getCurrentPokemon(){
		return this.currentPokemon;
	}
	
	/**
	 * Checks to see if the trainer has Pokemon that can still battle
	 * 
	 * @return boolean whether can still fight
	 */
	public boolean canFight(){
		
		for(Pokemon p : this.party){
			if(!p.isFainted()){
				return true;
			}
		}
		return false;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Pokemon> getParty() {
		return party;
	}

	public void setParty(ArrayList<Pokemon> party) {
		this.party = party;
	}

	public void setCurrentPokemon(Pokemon currentPokemon) {
		this.currentPokemon = currentPokemon;
	}

	public String getCurrAction() {
		return currAction;
	}

	public void setCurrAction(String currAction) {
		this.currAction = currAction;
	}
	
	
}
