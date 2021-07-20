// Dev Josiah Anderson
// This is basically a pokemon clone class
// that is the bluprint of a "Cute Creature"
// it contains all functionality to level up
// gain experience, attack, take damage, and a method to print out its stats (toString())
public class CuteCreature{

	protected String species;
	protected String name;
	protected int level;
	protected int hitPoints;
	protected int maxHitPoints;
	protected int attackRating;
	protected int defenseRating;
	protected int experiencePoints;
	protected int experienceValue;
	protected boolean isSpecial;
	protected boolean isAlive;
	protected Attack[] attacks = new Attack[10];
	protected int attackCount = 0;

	// CuteCreature constructor setting all the attributes as the values of the arguments passed
	// with some attributes set initially with default values (level=0, hitPoints=maxhp, experiencePoints=0, isAlive=true)
	public CuteCreature(String species, int maxHitPoints, int attackRating, int defenseRating, int experienceValue, boolean isSpecial){
		
		this.species = species;
		this.maxHitPoints = maxHitPoints;
		this.attackRating = attackRating;
		this.defenseRating = defenseRating;
		this.experienceValue = experienceValue;
		this.isSpecial = isSpecial;
		this.level = 1;
		this.hitPoints = maxHitPoints;
		this.experiencePoints = 0;
		this.isAlive = true;

	}

	// a level up member method. purpose: deal with leveling up the creature
	// and the various functionality that incurs. Such as updating: max health, attack and defense. 
	// It also displays some information on the screen, in the form [previous value] --> [current value]
	protected void levelUp(){
		this.level = this.level+1;
		int prevMaxHp = this.getHitPoints();
		int prevAtk = this.getAttackRating();
		int prevDef = this.getDefenseRating();

		if(level>=2 && level<=9){
			this.maxHitPoints += 7;
			this.attackRating += 3;
			this.defenseRating += 3;
		}

		if(level>=10){
			this.maxHitPoints+=2;
			this.attackRating+=1;
			this.defenseRating+=1;
		}

		this.experienceValue+=15;
		this.hitPoints = maxHitPoints;

		System.out.format("\n|----LEVEL-UP----|");
		System.out.print("\n" + this.getName() + " is now level " + this.getLevel());
		System.out.format("%nMax Hit Points: %2d ---> %2d", prevMaxHp, this.getMaxHitPoints());
		System.out.format("%nAttack rating:  %2d ---> %2d", prevAtk, this.getAttackRating());
		System.out.format("%nDefense Rating: %d ---> %2d", prevDef, this.getDefenseRating());
		System.out.format("%nNext level:     %3dXP%n%n", (calcLevel(1,0) - this.getExperiencePoints()));


	}

	// a gain experience member method. purpose: deal with adding the correct amount of experience 
	// to the creature, as well as displaying the [previous experience held] --> [current experience held]
	public void gainExp(int exp){
		System.out.println(this.getName() + "'s XP: " + this.getExperiencePoints() + " ---> " + (this.getExperiencePoints()+exp));
		this.experiencePoints += exp;
			//parameters 1 == count, 0 is being used as the xp start point to evaluate
			//the intervals in the helper method
		if(this.getExperiencePoints() >= calcLevel(1,0)){

			this.levelUp();
		}

	}

	// a member method that deals with the creature taking damage from opponants, or possibly environment
	// possibly in the future add a parameter to signify what is damaging the creature
	// it displays how much damage the creature took, updates the hitpoint attribute, and displays
	// the final value in the form [previous hp] --> [current hp]
	// there is functionality that catches if the damage is greater than the current hp,
	// if so, the hp is set to 0, and "has been incapacitated" is displayed on the screen
	public void takeDamage(int dmg){ 
		if(hitPoints - dmg >= 0){
			System.out.print(this.getName() + " took " + dmg + " damage! \n");
			System.out.print(this.name + "'s Hit Points: " + hitPoints + " ---> " + (hitPoints-dmg) + "\n");
			hitPoints -= dmg;

		}
		else{
			System.out.print(this.getName() + " took " + dmg + " damage! \n");
			System.out.print(this.name + "'s Hit Points: " + hitPoints + " ---> " + 0 + "\n");
			hitPoints = 0;
		}
		if(hitPoints == 0){
			this.isAlive = false;
			System.out.print(this.name + " has been incapacitated.\n");
		}

	}

	// a member method that deals with this creature attacking another creature c
	// displays to the screen of the form "this creature is attacking that creature"
	// this method also has hit % functionality, with 20 percent chance to miss,
	// 15 percent chance for critical hit, and 65 percent chance to land a regular hit
	// there is a helper method down below named attackRoll() that uses random
	// integers between 1 and 100 to simulate a dice roll, and returns the value to be used herein
	public void attack(CuteCreature c){ 

		System.out.println("\n" + this.getName() + " is attacking " + c.getName());
		int attack = attackRoll();
		int damage = this.attackRating - c.defenseRating;

		//attack misses, no damage dealt to creature c
		if(attack > 80){
			System.out.println("attack miss");
		}
		//attack hits, and is a critical hit, 
		else if(attack > 65){
			System.out.println("CRITICAL HIT!");
			if(damage < 2){
				damage = 2;
			}

			if(damage < 1){
				damage = 1;
			}

			c.takeDamage(damage*2);
		}
		else{
			System.out.println("Hit!");

			if(damage <= 0){
				damage = 1;
			}

			c.takeDamage(damage);
		}

		if(c.getHitPoints() == 0){
			this.gainExp(c.getExperienceValue());
		}

	}
     //uses random
	// integers between 1 and 100 to simulate a dice roll, and returns the value to be used herein
	public int attackRoll(){
		return (int) (Math.random() * 100);
	}

	// a member method designed solely to print all attributes of the creature to the screen
	// formatted nicely
	public String toString(){ 
		// commented out XP line
		// is a different version of printing out the xp
		// that sets each interval to a [0 - bracket limit for that level].
		// for example, if creature levels up to 2 with exactly 200xp (0xp --> 200xp), 
		// with the cumulative xp being 200xp
		// it would display 0/275
		// likewise, if that same creature got an additional 280xp (0-->280) added to its total
		// it would display 5xp/350xp

		if(this.getIsSpecial()){
			return "Level " + this.getLevel() + " " + this.species +
					"\n+++++++++++++++" + 
					"\n!!! Special !!!" +
					"\nNAME:   " + this.getName() +
					"\nHP:     " + this.getHitPoints() + "/" + this.getMaxHitPoints() +
					"\nATK:    " + this.getAttackRating() + 
					"\nDEF:    " + this.getDefenseRating() + 
					"\nXP:     " + this.getExperiencePoints() + "/" + calcLevel(1,0) + 
					//"\nXP: " + (this.getExperiencePoints() - calcPreviousLevel(1,0)) + "/" + (200 + ((this.getLevel()-1) * 75)) + 
					"\nXP VAL: " + this.getExperienceValue() + "\n";
					//"Attacks: " + printAttacks() + "\n";
		}
		return "Level " + this.getLevel() + " " + this.species +
				"\n+++++++++++++++" + 
				"\nNAME:   " + this.getName() +
				"\nHP:     " + this.getHitPoints() + "/" + this.getMaxHitPoints() +
				"\nATK:    " + this.getAttackRating() + 
				"\nDEF:    " + this.getDefenseRating() + 
				"\nXP:     " + this.getExperiencePoints() + "/" + calcLevel(1,0) + 
				//"\nXP: " + (this.getExperiencePoints() - calcPreviousLevel(1,0)) + "/" + (200 + ((this.getLevel()-1) * 75)) +
				"\nXP VAL: " + this.getExperienceValue() + "\n";
				//"Attacks: " + printAttacks() + "\n";



	}

	public Attack getAttack(String name){ //String name
		for(Attack atk : this.attacks){
			
			if(atk.getName().equals(name)){
				return atk;
			}
			
		}
		return null;
	}

	public Attack getAttack(){ 
		int attack = (int) Math.random() * this.attacks.length+1;
		return attacks[attack];
		
	}

	public boolean hasSpecialAttack(){
		if(this.attacks[0] == null){
			return false;
		}
		return true;
	}

	public void addAttack(String name, int baseDamage, String debuff, int hitRate, int critRate, int missRate ){
		Attack atk = new Attack(name, baseDamage, debuff, hitRate, critRate, missRate);
		this.attacks[attackCount] = atk;
		attackCount += 1;
	}

	//HONORS METHODS START
	// method designed to take in an arbitrarily large number of experience points
	// and level up the creature correctly (if 1000xp, it would level up to level 4) with 475xp 
	// to go until leveling up to the next level. It displays to the screen
	// the exact same thing that the previous gainXp method does: [prev xp] --> [current xp]
	// 
	public void gainExpHonors(int exp){
		System.out.println(this.getName() + "'s XP: " + this.getExperiencePoints() + " ---> " + (this.getExperiencePoints()+exp));

		//this.experiencePoints += exp;
			//parameters 1 == count, 0 is being used as the xp start point to evaluate
			//the intervals in the helper method
		while(exp >= calcLevel(1,0)){
			//System.out.println(calcLevel(1,0));
			this.experiencePoints = calcLevel(1,0);
			//System.out.println(this.getExperiencePoints());
			this.levelUp();
		}
		
		this.experiencePoints += (exp-this.experiencePoints);
	}

	public void attack(CuteCreature c, Attack atk){
		int hitRate = atk.getHitRate();
		int missRate = atk.getMissRate();
		int critRate = atk.getCritRate();
		int criticalModifier = atk.getCritModifier();
		int damage = atk.getBaseDamage();
		int attack = attackRoll(hitRate, missRate, critRate);
		int total = hitRate + missRate + critRate; //in case the numbers given are a sum > 100
		//I get the total of all three to work with

		System.out.println("\n" + this.getName() + " is attacking " + c.getName() + " with his special attack " +
			atk.getName());
		if(attack > total-missRate){
			System.out.println("attack miss");
		}
		else if(attack > total-critRate){
			//critical hit
			System.out.println("CRITICAL HIT!");
			if(damage < 2){
				damage = 2;
			}

			if(damage < 1){
				damage = 1;
			}

			c.takeDamage(damage*criticalModifier);
		}
		else{
			//hit
			if(damage <= 0){
				damage = 1;
			}
			System.out.println("Hit!");

			c.takeDamage(damage);
		}

		if(c.getHitPoints() == 0){
			this.gainExp(c.getExperienceValue());
		}
		
	}
	//overloaded attack diceroll for the overloaded attack class
	public int attackRoll(int hitRate, int missRate, int critRate){
		return (int) (Math.random() * (hitRate + critRate + missRate));
	}

	//HONORS METHODS END

	//extra methods
	// recursive helper method to help determine what experience points are needed 
	// to level up to the next level. (or calculate next level points)
	// using the trait of the levelling up: 
	// level   |   xp needed    |    cumulative xp needed
	//   1     |      200       |            200
	//   2     |      275       |            475
	//   3     |      350       |            825
	//   4     |      425       |           1250

	// an function I came up with is: f(level) = 200 + (75 * (level-1)) + previous experience points
	// f(1) = 200 + (75*0) + 0 (because there is no previous experience points nor level 0)
	// f(2) = 200 + (75*1) + f(1)
	// f(3) = 200 + (75*2) + f(2)

	// the reason why I chose to go with forward recursion instead of ending at 0, is because
	// I do not know how many levels the creature will end up levelling to, so 
	// it instead of using calls to the calcLevel() method in the equation, it uses a value passed
	// along that contains the value of that equation. It 'iterates' until the counter
	// is equal to the creature's current level. it returns the highest 'tier' of cumulative xp 
	// that it reaches. Perhaps even this could have been achieved with a loop
	// but it felt more natural to use recursion, and I wanted the extra practice

	public int calcLevel(int count, int previous){
		int value = 200 + (75*(count-1));
		int next = value + previous;

		if(count == this.getLevel()){
			return next;
		}
		else{
			return calcLevel(count+1, next);
		}
	}
	
	// I wanted to use this recursive helper method to help determine what the previous amount of xp points
	// were needed in order for a cleaner output in the toString() method
	// I wanted the method to display the points in the current bracket like so:
	// 0 / points (to next level). For example, if CuteCreature is level 3, they might have 
	// 578 cumulative experience points, but I wanted it to output:
	// 247/350 instead of 578/825. I still ended up using it but i could
	//not figure out how to do it my prefered way

	// public int calcPreviousLevel(int count, int previous){
	// 	int value = 200 + (75*(count-1));
	// 	int next = value + previous;

	// 	if(count == this.getLevel()){
	// 		return previous;
	// 	}
	// 	else{
	// 		return calcPreviousLevel(count+1, next);
	// 	}
	// }

	public String printAttacks(){
		String attackString = "";
		for(Attack atk : this.attacks){
			attackString += atk.toString();
		}
		return attackString;
	}
	public void setDefense(int def){
		this.defenseRating = def;
	}

	public void setAttack(int atk){
		this.attackRating = atk;
	}

	public int getHitPoints(){
		return this.hitPoints;
	}

	public int getExperienceValue(){
		return this.experienceValue;
	}

	public String getSpecies(){
		return this.species;

	}

	public String getName(){
		return this.name;

	}

	public int getLevel(){
		return this.level;

	}

	public int getMaxHitPoints(){
		return this.maxHitPoints;

	}

	public int getAttackRating(){
		return this.attackRating;

	}

	public int getDefenseRating(){
		return this.defenseRating;

	}

	public int getExperiencePoints(){
		return this.experiencePoints;

	}

	public boolean getIsSpecial(){
		return this.isSpecial;

	}


	public void setName(String name){
		this.name = name;
	}

	public void revive(){
		this.isAlive = true;
		this.hitPoints = getMaxHitPoints();
		System.out.println("\n...Reviving " + this.getName() + "...\n");
	}

	public boolean isAlive(){
		return this.isAlive;
	}

	public void setSpecies(String species){
		this.species = species;

	}

}