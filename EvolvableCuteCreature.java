// Dev Josiah Anderson

// this class is a child class or subclass of CuteCreature
// it basically implements a way for the creature to 
// "evolve" into a stronger creature and gain a special attack
// not all cutecreatures evolve, but all EvolvableCuteCreaturs 
// may evolve at one point

public class EvolvableCuteCreature extends CuteCreature{
	private String evolveSpecies;
	private int evolveLevel;
	private String type;
	private String resistantTo;
	private String vulnerableTo;
	private Boolean evolved;
	//private String[] evolveables = {"", "", "", "", "", ""};

	// this is a constructor for the EvolvableCuteCreature class. It 
	// takes in the species, Health, Attack, Defense, XP value, if the creature is special and 2 extra 
	// exclusive arguments/parameters: the level at which the creature will evolve, and the species the 
	// creature will morph into. ( evolveLevel and evolveSpecies)
	public EvolvableCuteCreature(String species, int maxHitPoints, 
								int attackRating, int defenseRating, 
								int experienceValue, boolean isSpecial, 
								int evolveLevel, String evolveSpecies){
		
		super(species, maxHitPoints, attackRating, defenseRating, experienceValue, isSpecial);
		this.evolveSpecies = evolveSpecies;
		this.evolveLevel = evolveLevel;
		this.evolved = false;
		this.type = "none";
	}

	// this is an overriden levelUp() method with purpose to 
	// level up the creature and make it evolve IF it reaches the proper 
	// level. I just have it use the parent's levelUp() method until it reaches the evolve
	// level it also sets isEvolved to true, and calls setSpecies with the evolveSpecies argument, 
	// atunement and setRandV methods
	@Override
	protected void levelUp(){
		
		super.levelUp();

		if(this.level == getEvolveLevel()){
			int prevMaxHp = this.getMaxHitPoints();
			int prevAtk = this.getAttackRating();
			int prevDef = this.getDefenseRating();

			this.maxHitPoints = this.getMaxHitPoints() + 9;
			this.attackRating = this.getAttackRating() + 4;
			this.defenseRating = this.getDefenseRating() + 4;
			this.hitPoints = this.getMaxHitPoints();

			this.setSpecies(evolveSpecies);
			this.attunement();
			this.setRandV();
			this.evolved = true;

			System.out.format("\n****!!! %s is EVOLVING into species %s !!!****", this.getName().toUpperCase(), this.getSpecies().toUpperCase());
			System.out.format("%nMax Hit Points: %2d ---> %2d", prevMaxHp, this.getMaxHitPoints());
			System.out.format("%nAttack rating:  %2d ---> %2d", prevAtk, this.getAttackRating());
			System.out.format("%nDefense Rating: %d ---> %2d\n\n", prevDef, this.getDefenseRating());
		}
	}

	// this is a method specialized to have teh creature use their special attack
	// there are "checkers" in place to see if teh creature being attacked resists or
	// is vulnerable to the attacker's type
	// there is also a check to see if the creature is an instance of the\
	// evolvable class, and if so it is able to use the special attack. Otherwise it
	// has no special attack

	public void specialAttack(CuteCreature c){
		int a = this.attackRating;
		int d = c.defenseRating;
		int damage = a-d;

		if( !(c instanceof EvolvableCuteCreature) ){
			System.out.print("\n" + this.getName() + " is attacking " + c.getName() + "\n");
			c.takeDamage(damage);

		}
		else if(c instanceof EvolvableCuteCreature && ((EvolvableCuteCreature)c).isEvolved()){

			EvolvableCuteCreature newCreature = (EvolvableCuteCreature)c;
			
			if(newCreature.getType().equals(this.type)){
				this.attackingWithSpecial(newCreature);
				System.out.print("Oh, " + newCreature.getName() + " is the same SPECIAL TPYE! The attack is neglible\n");
				newCreature.takeDamage(1);
			}
			else if(newCreature.getResistanceTo().equals(this.type)){
				
				damage = a-(5*d);

				if (damage <= 0){
					damage = 0;
				}
				this.attackingWithSpecial(newCreature);
				System.out.print("Uh oh, " + newCreature.getName() + " is resisting the attack\n");
				newCreature.takeDamage(damage);
			}
			else if(newCreature.getVulnerabilityTo().equals(this.type)){
				
				damage = (5*a)-d;

				if (damage <= 10){
					damage = 10;
				}
				this.attackingWithSpecial(newCreature);
				System.out.print("WOW! " + c.getName() + " is vulnerable to this type of attack (" + this.getType() + ")\n");
				newCreature.takeDamage(damage);
			}

			else {
				if (damage <= 0){
					damage = 1;
				}
				this.attackingWithSpecial(newCreature);
				newCreature.takeDamage(damage);

			}
		}

	}


	// this method is in chharge of setting the attune type of the creature. It is called when the creature 
	// finally evolves.
	private void attunement(){
		if(this.species.charAt(0) >= 'A' && this.species.charAt(0) <= 'F'){
			this.type = "light";
		}
		if(this.species.charAt(0) >= 'G' && this.species.charAt(0) <= 'L'){
			this.type = "dark";
		}
		if(this.species.charAt(0) >= 'M' && this.species.charAt(0) <= 'R'){
			this.type = "nature";
		}
		if(this.species.charAt(0) >= 'S' && this.species.charAt(0) <= 'Z'){
			this.type = "tech";
		}
	}

	// this method is in charge of setting the ResistantTo and VulnerableTo traits/attributes. 
	// all evolved creatures have types they are resistant and vulnerable to
	private void setRandV(){
		if(this.type.equals("light")){
			this.vulnerableTo = "dark";
			this.resistantTo = "tech";
		}
		if(this.type.equals("dark")){
			this.vulnerableTo = "light";
			this.resistantTo = "nature";
		}
		if(this.type.equals("nature")){
			this.vulnerableTo = "tech";
			this.resistantTo = "dark";
		}
		if(this.type.equals("tech")){
			this.vulnerableTo = "nature";
			this.resistantTo = "light";
		}
	}

	//this is an overriden toString method that is in charge of outputting the
	// stats of this cute creature. It also checks if the creature has evolved and 
	// if it has not, it has no type yet. It also makes sure to output the correct species at the top

	@Override
	public String toString(){
		if(this.evolved){
			return "Level " + this.getLevel() + " " + this.species +
					"\n+++++++++++++++" + 
					"\n!!! Special !!!" +
					"\nNAME:   " + this.getName() +
					"\nHP:     " + this.getHitPoints() + "/" + this.getMaxHitPoints() +
					"\nATK:    " + this.getAttackRating() + 
					"\nDEF:    " + this.getDefenseRating() + 
					"\nXP:     " + this.getExperiencePoints() + "/" + calcLevel(1,0) + 
					//"\nXP: " + (this.getExperiencePoints() - calcPreviousLevel(1,0)) + "/" + (200 + ((this.getLevel()-1) * 75)) + 
					"\nXP VAL: " + this.getExperienceValue() +
					"\nType: " + this.getType() + "\n\n";
		}

		return super.toString() + this.getType() + "\n\n";

	}

	//this is just a method to display the string "[creature] is attacking [enemy] " 
	// I just got tired of writing it out
	private void attacking(EvolvableCuteCreature c){
		System.out.print("\n" + this.getName() + " is attacking " + c.getName() + "\n");
	}

	private void attackingWithSpecial(EvolvableCuteCreature c){
		System.out.print("\n" + this.getName() + " attacks " + c.getName() + " with a special attack! "+"\n");
	}


	// all of these are just 'getters' that return the value of the attribute
	//  type, evolveLevel,resistantTo, vulnerableTo, isEvolved
	private String getType(){
		return this.type;
	}

	protected int getEvolveLevel(){
		return this.evolveLevel;
	}

	protected String getResistanceTo(){
		return this.resistantTo;
	}
	protected String getVulnerabilityTo(){
		return this.vulnerableTo;
	}

	protected boolean isEvolved(){
		return this.evolved;
	}

}