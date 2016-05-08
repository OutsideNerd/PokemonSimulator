;


public class Pokemon {
	
	private static int uniqueIDGenerator = 1;
	
	private int uniqueID;
	
	private int dexNum;
	private String name;
	private int level;
	public static final String[] STAT_NAMES = {"hp","att","def","spAtt","spDef","spd"};
	
	// Holds pokemons stats in the following order: hp, att, def, spatt, spdef, speed
	
	private int [] baseStats;
	private int[] IVs = new int[6];
	private int[] EVs = new int[6];
	private double[] natureModifier = new double[6];
	private int[] finalStats = new int[6];
	private int currHealth;
	private Trainer trainer;
	
	private String type1;
	private String type2;
	private Move[] moves = new Move[4];
	private int numMoves = 0;
	
	private boolean fainted;
	
	public Pokemon(int num, Trainer t, int level, int[] EVs, int[] IVs, double[] natureModifier) throws PokemonNotFoundException{
		
		// Generate a unique id
		this.uniqueID = Pokemon.uniqueIDGenerator;
		Pokemon.uniqueIDGenerator++;
		
		// Read id data from constructor
		this.dexNum = num;
		this.trainer = t;
		this.level = level;
		this.EVs = EVs;
		this.IVs = IVs;
		this.natureModifier = natureModifier;
		
		
		// Lookup species info
		this.name = SpeciesDataList.getInstance().getName(this.dexNum);
		this.type1 = SpeciesDataList.getInstance().getType1(this.dexNum);
		this.type2 = SpeciesDataList.getInstance().getType2(this.dexNum);
		this.baseStats = SpeciesDataList.getInstance().getBaseStats(this.dexNum);
		
		
		calculateStats();
		this.currHealth = this.finalStats[0]; // set HP to max
	}
	
	public Pokemon(String name, Trainer t, int level, String type1, int[] baseStats, int[] EVs, int[] IVs, double[] natureModifier) {
		this(name, t, level, type1, "none", baseStats, EVs, IVs, natureModifier);
	}
	
	public Pokemon(String name, Trainer t, int level, String type1, String type2, int[] baseStats, int[] EVs, int[] IVs, double[] natureModifier) {
		this.name = name;
		this.trainer = t;
		this.level = level;
		this.type1 = type1;
		this.type2 = type2;
		this.baseStats = baseStats;
		this.EVs = EVs;
		this.IVs = IVs;
		this.natureModifier = natureModifier;
		calculateStats();
		this.currHealth = this.finalStats[0];
		this.fainted = false;
	}
	
	public String getBattleStatus(){
		return this.name + " " + this.currHealth + "/" + this.finalStats[0];
	}
	
	public boolean equals(Object o){
		if(o instanceof Pokemon){
			Pokemon p = (Pokemon) o;
			return (this.uniqueID == p.getUniqueID());
		}
		return false;
	}
	
	public String toString(){
		String s = this.name + " " + this.type1;
		if(!this.type2.equals("none")){
			s+= "/" + this.type2;
		}
		s += "\nLevel: " + this.level + "\n";
		for(int i = 0; i < Pokemon.STAT_NAMES.length; i++){
			if(Pokemon.STAT_NAMES[i].equals("hp")){
				s += Pokemon.STAT_NAMES[i] + ": " + this.currHealth + "/" + this.finalStats[i] + "\n";
			}
			else{
				s += Pokemon.STAT_NAMES[i] + ": " + this.finalStats[i] + "\n";
			}
		}
		return s;
	}
	
	public Move[] getMoves(){
		return this.moves;
	}
	
	public boolean addMove(String name, int basePower, int accuracy, String type){
		if(numMoves < moves.length){
			this.moves[numMoves] = new Move(name, basePower, accuracy, type);
			numMoves++;
			return true;
		}
		else{
			return false;
		}
	}
	
	public Move getMove(String s){
		for(int i = 0; i < this.numMoves; i++){
			if(s.equalsIgnoreCase(this.moves[i].getName())){
				return this.moves[i];
			}
		}
		System.out.println("Invalid move");
		return null;
	}
	
	public String listMoves(){
		String s = "";
		for(int i = 0; i < this.moves.length; i++){
			s += this.moves[i] + "\n";
		}
		return s;
	}
	
	public int getStat(String stat){
		for(int i = 0; i < Pokemon.STAT_NAMES.length; i++){
			if(stat.equalsIgnoreCase(Pokemon.STAT_NAMES[i])){
				return this.finalStats[i];
			}
		}
		return -1;
		
	}

	/**
	 * Attacks an enemy pokemon with a move
	 * 
	 * @param enemy Pokemon to attack
	 * @param m move to use
	 * @return boolean whether attack hit (didn't miss)
	 */
	public boolean attack(Pokemon enemy, Move m){
		// Check for accuracy
		if(Math.random() <= (double)(m.getAccuracy() / 100.0)){
			int damage = Move.calcDamage(m, this, enemy);
			enemy.damage(damage);
			return true;
		}
		else{
			return false;
		}
	}
	
	public void damage(int damage){
		this.currHealth = Math.max(0, this.currHealth - damage);
		if(this.currHealth == 0){
			this.fainted = true;
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Trainer getTrainer() {
		return trainer;
	}

	public boolean isFainted() {
		return fainted;
	}

	public double getTypeEffectiveness(Move m){
		double modifier = TypeList.getInstance().getTypeEffectiveNess(m.getType(), this.type1);
		if(!this.type2.equals("none")){
			modifier = modifier * TypeList.getInstance().getTypeEffectiveNess(m.getType(), this.type2);
		}
		return modifier;
		
	}
	
	public boolean isSTAB(Move m){
		if(m.getType().equalsIgnoreCase(this.type1)){
			return true;
		}
		else if(!this.type2.equals("none") && m.getType().equalsIgnoreCase(this.type1)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void setFainted(boolean fainted) {
		this.fainted = fainted;
	}
	
	private void calculateStats(){
		this.finalStats[0] = (2 * this.baseStats[0] + this.IVs[0] + (int) (this.EVs[0]/4)) * this.level / 100 + this.level + 10;
		for(int i = 1; i < this.finalStats.length; i++){
			this.finalStats[i] = (2 * this.baseStats[i] + this.IVs[i] + (int) (this.EVs[i]/4)) * this.level / 100 + 5;
			this.finalStats[i] *= this.natureModifier[i];
		}
	}

	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public String getType2() {
		return type2;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}

	public int getCurrHealth() {
		return currHealth;
	}

	public int getUniqueID() {
		return uniqueID;
	}
	
}
