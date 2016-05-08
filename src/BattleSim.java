import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;


public class BattleSim {

	private static Scanner in = new Scanner(System.in);

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		/*  Read in file in the following format
		 *  <Trainer Name>
		 * 	<pokemon name>	<level> <type 1>	<type 2>
		 *  <list of base stats seperated by whitespace>
		 *  <list of EVs seperated by whitespace>
		 *  <list of IVs seperated by whitespace>
		 *  ---list of moves with the following
		 *  <name>	<base power>	<accuracy>	<type>
		 *  
		 */

		
		
		Trainer t1 = null;
		Trainer t2 = null;

		JFileChooser f = new JFileChooser();

		
		try {
			readPokemonData("pokemon_data.csv");
			t1 = readFile("trainer1.txt");
			t2 = readFile("trainer2.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Battle b = new Battle(t1,t2);
		BattleGUI gui = new BattleGUI(b);


	}

	/**
	 * Executs a turn where Pokemon p1 attacks p2, displays text, and asks for a new pokemon
	 * to be sent out if fainted. Returns a boolean if the trainer of the pokemon being 
	 * attacked (p2) hcan still fight. Will return false if all pokemon are fainted
	 * 
	 * @param p1 Pokemon doing the attacking
	 * @param p2 Pokemon being attacked
	 * @return boolean whether opposing trainer can still fight
	 */
	public static boolean attack(Pokemon p1, Pokemon p2, Move m){
		System.out.println("\n" +p1.getName() + " used " + m.getName() + "!");

		boolean hit = p1.attack(p2, m);

		if(hit){
			// Text indicatingg super effective
			double modifier = p2.getTypeEffectiveness(m);
			if(modifier == 0.5 || modifier == 0.25){
				System.out.println("It's not very effective...");
			}
			else if (modifier == 2.0 || modifier == 4.0){
				System.out.println("It's super effective");
			}
			else if(modifier == 0.0){
				System.out.println("It had no effect...");
			}
		}
		else{
			System.out.println(p1.getName() + "'s attack missed!");
		}

		// If fainted, have set out next pokemon
		if(p2.isFainted()){
			System.out.println(p2.getName() + " fainted!");
			Trainer t = p2.getTrainer();
			
			// Checks to see is any more pokemon to fight
			if(!t.canFight()){
				return false;
			}
			System.out.println("\n" + t.listTeam() + t.getName() + 
					", choose another pokemon to send out (enter 1 - 6 to select): ");
			Pokemon sendOut = null;
			while(sendOut == null){
				int i = in.nextInt();
				if(i < 1 || i > t.getParty().size()){
					System.out.println("Enter a valid number to choose the pokemon");
				}
				else if(t.getParty().get(i - 1).isFainted()){
					System.out.println("Cannot send out fainted pokemon");
				}
				else{
					sendOut = t.getParty().get(i - 1);
					t.setCurrentPokemon(sendOut);
					System.out.println(t.getName() + " sent out " + sendOut.getName() + "!");
					
				}
			}
		}
		return true;
	}
	
	public static void readPokemonData(String filename){
		File file = new File(filename);
		
		Scanner in = null;
		
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find file " + filename);
			System.exit(1);
		}
		
		in.nextLine(); // Discard first line, header data
		
		while(in.hasNext()){
			String[] commands = in.nextLine().split(",");
			try {
				int num = Integer.parseInt(commands[0]);
				String name = commands[1];
				String type1 = commands[2];
				String type2 = commands[3];
				
				int HP = Integer.parseInt(commands[4]);
				int att = Integer.parseInt(commands[5]);
				int def = Integer.parseInt(commands[6]);
				int spAtt = Integer.parseInt(commands[7]);
				int spDef = Integer.parseInt(commands[8]);
				int spd = Integer.parseInt(commands[9]);
				
				SpeciesDataList.getInstance().addPokemon(num, name, type1, type2, HP, att, def, spAtt, spDef, spd);
			} catch (NumberFormatException e) {
					System.out.println(e.getMessage());
			}
		}
		
		
	}
	
	public static Trainer readFile(String filename) throws FileNotFoundException{
		File file = new File(filename);

		Trainer t = null;
		
		Scanner in = new Scanner(file);
		while(in.hasNext()){
			String trainerName = in.nextLine();
			t = new Trainer(trainerName);

			// Loop for adding pokemon
			while(in.hasNext()){

				int dexNum = in.nextInt();
				int level = in.nextInt();
				in.nextLine(); // flush to newline
				
				int[] EVs = stringToIntArray(in.nextLine().split("\\s+"));
				int[] IVs = stringToIntArray(in.nextLine().split("\\s+"));
				double[] natureModifier = stringToDoubleArray(in.nextLine().split("\\s+"));

				Pokemon p = null;
				
				try {
					p = t.addMemberToParty(dexNum, level,EVs, IVs, natureModifier);
				} catch (PokemonNotFoundException e) {
					System.out.println("Error: " + e.getMessage());
				}
				
				for( int i = 0; i < 4; i++){
					String line = in.nextLine();
					if(line.equals("")){
						break;
					}
					String[] moveLine = line.split("\\s+");
					p.addMove(moveLine[0], Integer.parseInt(moveLine[1]), Integer.parseInt(moveLine[2]), moveLine[3]);
				}
				System.out.println(p);
			}

		}
		return t;

	}

	public static int[] stringToIntArray(String[] arr){
		int[] newArr = new int[arr.length];
		for(int i = 0; i < arr.length; i++){
			newArr[i] = Integer.parseInt(arr[i]);
		}
		return newArr;
	}

	public static double[] stringToDoubleArray(String[] arr){
		double[] newArr = new double[arr.length];
		for(int i = 0; i < arr.length; i++){
			newArr[i] = Double.parseDouble(arr[i]);
		}
		return newArr;
	}



}
