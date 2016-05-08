import java.util.ArrayList;
import java.util.Iterator;


public class Trainer {
	
	public static final int MAX_PARTY_SIZE = 6;
	
	private Pokemon currentPokemon;
	private String name;
	private ArrayList<Pokemon> party = new ArrayList<Pokemon>();
	private String currAction;
	
	public Trainer(String name){
		this.name = name;
		this.currAction = null;
	}
	
	public boolean swap(int index){
		Pokemon prevPokemon = currentPokemon;
		currentPokemon = this.party.get(index - 1);
		
		this.party.set(index - 1, prevPokemon);
		this.party.set(1, currentPokemon);
		
		return true;
	}
	
	public Pokemon addMemberToParty(int num, int level, int[] EVs, int[] IVs, double[] natureModifier) throws PokemonNotFoundException{
		Pokemon p = new Pokemon( num, this,  level, EVs, IVs, natureModifier);
		return this.addMemberToParty(p);
	}
	
	public Pokemon addMemberToParty(String name, int level, String type1, int[] baseStats, int[] EVs, int[] IVs, double[] natureModifier){
		Pokemon p = new Pokemon(name, this, level, type1, baseStats, EVs, IVs, natureModifier);
		return this.addMemberToParty(p);
	}
	
	public Pokemon addMemberToParty(String name, int level, String type1, String type2, int[] baseStats, int[] EVs, int[] IVs, double[] natureModifier){
		Pokemon p = new Pokemon(name, this, level, type1, type2, baseStats, EVs, IVs, natureModifier);
		return this.addMemberToParty(p);
	}
	
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
	
	public Pokemon getCurrentPokemon(){
		return this.currentPokemon;
	}
	
	public boolean canFight(){
		
		for(Pokemon p : this.party){
			if(!p.isFainted()){
				return true;
			}
		}
		return false;
	}

	public String listTeam(){
		String s = "";
		for(Pokemon p : this.party){
			s += p.getBattleStatus() + (p.isFainted() ? " FNT":"") + "\n";
		}
		return s;
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
