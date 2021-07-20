import java.util.ArrayList;

//Dev Josiah Anderson

// this client program is merely to test the evolvable creatures abilities
// attacking each other. There should be all of the tests EXCEPT 
// when a evolvable creature tries to attack a creature who has not evolved yet. It 
// would not work for me so I commented it out. its on line 62


//the output prints teh two "fighters" stats, and then has them both attack each other one
// time, outputting whether or not it resisted, vulnerable or the same type

public class CreatureClient{
	public static void main(String[] args){
    // String species, int maxHitPoints, int attackRating, 
    // int defenseRating, int experienceValue, boolean isSpecial, 
    // int evolveLevel, String evolveSpecies
		// all four of these creatures have different types when evolved
		CuteCreature one = new EvolvableCuteCreature("AI", 45, 15, 4, 200, true, 3, "Robot");
		CuteCreature two = new EvolvableCuteCreature("heroine", 40, 11, 3, 200, true, 3, "Scarlet-witch");
		CuteCreature three = new EvolvableCuteCreature("soldier", 40, 11, 3, 200, true, 3, "Captain-America");
		CuteCreature four = new EvolvableCuteCreature("millionaire", 40, 11, 3, 200, true, 3, "Iron-Man");

		
		//this creature has the same type when evolved as creature two
		CuteCreature five = new EvolvableCuteCreature("clawman", 45, 15, 4, 200, true, 3, "Wolverine");

		//this creature is not evolved yet
		CuteCreature six = new EvolvableCuteCreature("bowman", 45, 15, 4, 200, true, 3, "hawkeye");

		//this cr5eature cant evolve
		CuteCreature seven = new CuteCreature("scientist", 40, 11, 3, 200, true);

		ArrayList<CuteCreature> creatures = new ArrayList<CuteCreature>();
		creatures.add(one);
		creatures.add(two);
		creatures.add(three);
		creatures.add(four);
		creatures.add(five);
		creatures.add(six);
		creatures.add(seven);

		one.setName("Vision"); 
		two.setName("Wanda");
		three.setName("Steve");
		four.setName("Tony");
		five.setName("Hugh");
		six.setName("CLint");
		seven.setName("Bruce");

		// levelling up the first 5 creatures to an evolveable level.
		for(int i = 0; i < 5; i++){
			levelUpToEvolvable(creatures.get(i));
		}

		//a loop to make all combinations of the first 4 fight each other, 
		// the reason why a call to fight(one,two) should also be called as fight(two,one)
		// is because the fight starts with the creature attacking with a special attack, and in most cases, 
		// the creature being attacked is incapacitated immediately
		for(int c1 = 0; c1 < creatures.size(); c1++){
			for(int c2 = 0; c2 < creatures.size(); c2++){
				if(c1!=c2){
					fight(creatures.get(c1),creatures.get(c2));
				}
			}
		}
	}
	public static void levelUpToEvolvable(CuteCreature creature){
		creature.gainExpHonors(475);
	}

	// method that takes 2 creatures and simulates 1 attack each, if the second has not fainted yet,
	// at least.
	public static void fight(CuteCreature one, CuteCreature two){

		System.out.print("FIGHTERS: \n");
		System.out.print(one);
		System.out.print(two);

		if(one instanceof EvolvableCuteCreature){
			((EvolvableCuteCreature)one).specialAttack(two);

			if(two.isAlive() && two instanceof EvolvableCuteCreature){
				((EvolvableCuteCreature)two).specialAttack(one);
			}

			one.revive();
			two.revive();
		}
		else{
			System.out.println(one.getName() + " is not evolvable, cannot attack with a special attack");
		}

	}

}