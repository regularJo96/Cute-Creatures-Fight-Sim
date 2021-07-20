// Dev Josiah Anderson
// this is an attack class
// it allows the Cute Creature to have multiple different attacks, each varying
// in hit chance, critical hit chance and miss chance
// as well as different damage 
// I plan to add a debuff modifier later on
// the framework for one is in place, kind of

public class Attack {
	private String name; //attribute name
	private int baseDamage; // attribute base damage
	private String debuff; // attribute to keep track of the debuff
	private int hitRate; // hit chance (can be a percentage out of 100 like 65/100)
	private int critRate; // critical hit chance
	private int missRate; // miss rate
	private int critModifier; // an attribute to modify the base damage to the set modifier


	//the attack constructor
	public Attack(String name, int baseDamage, String debuff, int hitRate, int critRate, int missRate){
		this.name = name;
		this.baseDamage = baseDamage;
		this.debuff = debuff;
		this.hitRate = hitRate;
		this.critRate = critRate;
		this.missRate = missRate;
		this.critModifier = 3;


	}

	// this method is the start of a debuff "causer"
	// it will change the state of the enemy's attack or defense
	// later I will add a time/turn variable to 
	// remove the modifier after the time alloted
	public void debuff(CuteCreature c){
		if(this.debuff.equals("defense")){
			c.setDefense(c.getDefenseRating()-1);
		}

		else if(this.debuff.equals("attack")){
			c.setAttack(c.getAttackRating()-1);
		}
		else{
			System.out.println("This attack has no debuff");
		}

	}

	public String toString(){
		return "[ Attack name:         " + this.name + "\n" +
			   "  Attack damage:       " + this.baseDamage + "\n" +
			   "  Attack debuff:       " + this.debuff + "\n" +
			   "  Attack hit rate:     " + this.hitRate + "% \n" +
			   "  Attack miss rate:    " + this.missRate + "% \n" +
			   "  Critical hit chance: " + this.critRate + "% ]\n";
	}

	//hitchance, critical hitchance, missChance 
	public int attackRoll(){
		return (int) (Math.random() * (hitRate + critRate + missRate));
	}

	//a getter for the damage
	public int getBaseDamage(){
		return this.baseDamage;
	}

	// a getter for the hit chance
	public int getHitRate(){
		return this.hitRate;
	}

	// a getter for the miss chance
	public int getMissRate(){
		return this.missRate;
	}

	// a getter for the critical hit chance
	public int getCritRate(){
		return this.critRate;
	}

	// a getter for the rate at which the critical hit raises the base damage
	public int getCritModifier(){
		return this.critModifier;
	}
	// a getter for the name
	public String getName(){
		return this.name;
	}

}