
public class TypeList {

	private static TypeList instance;

	// Hashmap to determine whether physical or special
	private String[] typeNames = {"Normal","Fighting","Flying","Poison","Ground","Rock","Bug","Ghost",
			"Steel","Fire","Water","Grass","Electric","Psychic","Ice","Dragon","Dark"};


	private boolean[] special = {false,false,false,false,false,false,false,false,false,
			true,true,true,true,true,true,true,true};
	
	private double[][] typeEffectiveNess = {
			//NORMAL
			{1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 0.0, 0.5, 
				1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
			// FIGHTING	
			{2.0, 1.0, 0.5, 0.5, 1.0, 2.0, 0.5, 0.0, 2.0, 
				1.0, 1.0, 1.0, 1.0, 0.5, 2.0, 1.0, 2.0},
			// FLYING
			{1.0, 2.0, 1.0, 1.0, 1.0, 0.5, 2.0, 1.0, 0.5, 
				1.0, 1.0, 2.0, 0.5, 1.0, 1.0, 1.0, 1.0},
			//POISON
			{1.0, 1.0, 1.0, 0.5, 0.5, 0.5, 1.0, 0.5, 0.0, 
				1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0},
			//GROUND
			{1.0, 1.0, 0.0, 2.0, 1.0, 2.0, 0.5, 1.0, 2.0, 
				2.0, 1.0, 0.5, 2.0, 1.0, 1.0, 1.0, 1.0},
			// ROCK
			{1.0, 0.5, 2.0, 1.0, 0.5, 1.0, 2.0, 1.0, 0.5, 
				2.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0},			
			// BUG
			{1.0, 0.5, 0.5, 0.5, 1.0, 1.0, 1.0, 0.5, 0.5,
				0.5, 1.0, 2.0, 1.0, 2.0, 1.0, 1.0, 0.5},				
			// GHOST
			{0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5,
				1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 0.5},			
			// STEEL
			{1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 0.5,
				0.5, 0.5, 1.0, 0.5, 1.0, 2.0, 1.0, 1.0},
			// FIRE
			{1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 2.0, 1.0, 2.0, 
				0.5, 0.5, 2.0, 1.0, 1.0, 2.0, 0.5, 1.0},
			// WATER
			{1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 1.0, 1.0, 1.0, 
				2.0, 0.5, 0.5, 1.0, 1.0, 2.0, 0.5, 1.0},
			// GRASS
			{1.0, 1.0, 0.5, 0.5, 2.0, 2.0, 0.5, 1.0, 0.5, 
				0.5, 2.0, 0.5, 1.0, 1.0, 1.0, 0.5, 1.0},
			// ELECTRIC
			{1.0, 1.0, 2.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 
				1.0, 2.0, 0.5, 0.5, 1.0, 1.0, 0.5, 1.0},
			// PSYCHIC
			{1.0, 2.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 0.5, 
				1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0, 0.0},			
			// ICE
			{1.0, 1.0, 2.0, 1.0, 2.0, 1.0, 1.0, 1.0, 0.5,
				0.5, 0.5, 2.0, 1.0, 1.0, 0.5, 2.0, 1.0},				
			// DRAGON
			{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5,
				1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0},			
			// DARK
			{1.0, 0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5,
				1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 0.5}
	};





	public static TypeList getInstance(){
		if(instance == null){
			instance = new TypeList();
		}
		return instance;
	}

	public double getTypeEffectiveNess(String type1, String type2){
		int t1 = getTypeIndex(type1);
		int t2 = getTypeIndex(type2);
		
		return this.typeEffectiveNess[t1][t2];
	}
	
	public boolean isSpecial(String type){
		return this.special[getTypeIndex(type)];
	}
	
	private int getTypeIndex(String type){
		for(int i = 0; i < this.typeNames.length; i++){
			if(this.typeNames[i].equalsIgnoreCase(type)){
				return i; 
			}
		}
		return -1;
	}
	
	private TypeList(){
		

	}
}
