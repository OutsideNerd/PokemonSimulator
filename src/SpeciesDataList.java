import java.util.HashMap;

public class SpeciesDataList {

	private static SpeciesDataList instance;
	private HashMap<Integer, SpeciesData> data = new HashMap<Integer,SpeciesData>();
	
	public static SpeciesDataList getInstance(){
		if(instance == null){
			instance = new SpeciesDataList();
		}
		return instance;
	}
	
	private SpeciesDataList(){
		this.data = new HashMap<Integer,SpeciesData>();
	}
	
	public void addPokemon(int num, String name, String type1, String type2, int HP, int att, int def, int spAtt, int spDef, int spd){
		SpeciesData s = new SpeciesData();
		s.name = name;
		s.type1 = type1;
		s.type2 = type2;
		s.baseStats = new int[6];
		s.baseStats[0] = HP;
		s.baseStats[1] = att;
		s.baseStats[2] = def;
		s.baseStats[3] = spAtt;
		s.baseStats[4] = spDef;
		s.baseStats[5] = spd;
		data.put(num, s);
	}
	
	public String getName(int num) throws PokemonNotFoundException{
		if(!this.data.containsKey(num)){
			throw new PokemonNotFoundException("Pokemon with index of " + num + " cannot be found");
		}
		return this.data.get(num).name;
	}
	
	public String getType1(int num) throws PokemonNotFoundException{
		if(!this.data.containsKey(num)){
			throw new PokemonNotFoundException("Pokemon with index of " + num + " cannot be found");
		}
		return this.data.get(num).type1;
	}
	
	public String getType2(int num) throws PokemonNotFoundException{
		if(!this.data.containsKey(num)){
			throw new PokemonNotFoundException("Pokemon with index of " + num + " cannot be found");
		}
		return this.data.get(num).type2;
	}
	
	public int[] getBaseStats(int num) throws PokemonNotFoundException{
		if(!this.data.containsKey(num)){
			throw new PokemonNotFoundException("Pokemon with index of " + num + " cannot be found");
		}
		return this.data.get(num).baseStats;
	}
	
	class SpeciesData {
		public String name;
		public String type1;
		public String type2;
		public int[] baseStats;
	}
}
