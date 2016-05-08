
public class Move {
	
	public static int calcDamage(Move m, Pokemon p1, Pokemon p2){
		
		int damage = (int) (((2 * p1.getLevel() + 10) / 250.0) * ((double) p1.getStat("att") / p2.getStat("def")) * m.getBasePower() + 2);

		
		double modifier = 1.0;

		// STAB bonus
		if(p1.isSTAB(m)){
			modifier = modifier * 1.5;
		}
		
		modifier = modifier * p2.getTypeEffectiveness(m); 
		modifier = modifier * (Math.random() * 0.15 + 0.85);
		
		damage *= modifier;
		
		return damage;
	}
	
	private String name;
	private int basePower;
	private int accuracy;
	private String type;
	
	public Move(String name, int basePower, int accuracy, String type) {
		this.name = name;
		this.basePower = basePower;
		this.accuracy = accuracy;
		this.type = type;
	}

	public String toString(){
		return this.name + " " + this.type + " " + this.basePower + " " + this.accuracy;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBasePower() {
		return basePower;
	}

	public void setBasePower(int basePower) {
		this.basePower = basePower;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
}
