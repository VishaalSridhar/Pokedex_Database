import java.util.ArrayList;

public class PokeBuilder {
    private String name;
    private int id;
    private String[] types;
    private ArrayList<String> abilities;
    private Double[] against;
    private int attack;
    private int sp_attak;
    private int defense;
    private int sp_defense;
    private String classification;
    private double height;
    private int hp;
    private int speed;
    private double weight;
    private int generation;
    private boolean legendary;

    public PokeBuilder() {
    }

    public PokeBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public PokeBuilder setID(int id) {
        this.id = id;
        return this;
    }

    public PokeBuilder setTypes(String[] types) {
        this.types = types;
        return this;
    }

    public PokeBuilder setAbilities(ArrayList<String> abilities) {
        this.abilities = abilities;
        return this;
    }

    public PokeBuilder setAgainst(Double[] against) {
        this.against = against;
        return this;
    }

    public PokeBuilder setAttack(int attack) {
        this.attack = attack;
        return this;
    }

    public PokeBuilder setSPAttack(int sp_attack) {
        this.sp_attak = sp_attack;
        return this;
    }

    public PokeBuilder setDefense(int defense) {
        this.defense = defense;
        return this;
    }

    public PokeBuilder setSPDefense(int sp_defense) {
        this.sp_defense = sp_defense;
        return this;
    }

    public PokeBuilder setClassification(String classif) {
        this.classification = classif;
        return this;
    }

    public PokeBuilder setHeight(Double height) {
        this.height = height;
        return this;
    }

    public PokeBuilder setHP(int hp) {
        this.hp = hp;
        return this;
    }

    public PokeBuilder setSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    public PokeBuilder setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public PokeBuilder setGeneration(int gen) {
        this.generation = gen;
        return this;
    }

    public PokeBuilder setLegendary(boolean legendary) {
        this.legendary = legendary;
        return this;
    }

    public Pokemon build() {
        Pokemon pokemon = new Pokemon();

        pokemon.setName(name);
        pokemon.setId(id);
        pokemon.setTypes(types);
        pokemon.setAbilities(abilities);
        pokemon.setAgainst(against);
        pokemon.setAttack(attack);
        pokemon.setSp_attack(sp_attak);
        pokemon.setDefense(defense);
        pokemon.setSp_defense(sp_defense);
        pokemon.setClassification(classification);
        pokemon.setHeight(height);
        pokemon.setHit_points(hp);
        pokemon.setSpeed(speed);
        pokemon.setWeight(weight);
        pokemon.setGeneration(generation);
        pokemon.setLegendary(legendary);

        return pokemon;
    }
}