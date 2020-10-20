import java.util.ArrayList;

public class Pokemon {
    private String name;
    private int id;
    private String[] types;     // SHOULD BE LENGTH 2
    private ArrayList<String> abilities;
    private Double[] against;      // SHOULD BE LENGTH 18
    private int attack;
    private int sp_attack;
    private int defense;
    private int sp_defense;
    private String classification;
    private double height;
    private int hit_points;
    private int speed;
    private double weight;
    private int generation;
    private boolean isLegendary;
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public ArrayList<String> getAbilities() {
        return abilities;
    }

    public void setAbilities(ArrayList<String> abilities) {
        this.abilities = abilities;
    }

    public Double[] getAgainst() {
        return against;
    }

    public void setAgainst(Double[] against) {
        this.against = against;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getSp_attack() {
        return sp_attack;
    }

    public void setSp_attack(int sp_attack) {
        this.sp_attack = sp_attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSp_defense() {
        return sp_defense;
    }

    public void setSp_defense(int sp_defense) {
        this.sp_defense = sp_defense;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getHit_points() {
        return hit_points;
    }

    public void setHit_points(int hit_points) {
        this.hit_points = hit_points;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public boolean isLegendary() {
        return isLegendary;
    }

    public void setLegendary(boolean legendary) {
        isLegendary = legendary;
    }
}