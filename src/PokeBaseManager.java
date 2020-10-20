  import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PokeBaseManager {
    private static String username;
    private static String password;
    private static String connection_url = "jdbc:postgresql://flowers.mines.edu/csci403";
    private static String table_name = "pokemon";
    public static String[] columns = {
            "abilities",
            "against_bug",
            "against_dark",
            "against_dragon",
            "against_electric",
            "against_fairy",
            "against_fight",
            "against_fire",
            "against_flying",
            "against_ghost",
            "against_grass",
            "against_ground",
            "against_ice",
            "against_normal",
            "against_poison",
            "against_psychic",
            "against_rock",
            "against_steel",
            "against_water",
            "attack",
            "classification",
            "defense",
            "height",
            "HP",
            "name",
            "number",
            "sp_attack",
            "sp_defense",
            "speed",
            "type_one",
            "type_two",
            "weight",
            "generation",
            "legendary"
    };

    private static String select_string;

    private Connection database_connection;
    
    public void GetCred(String u, String p){
    	username = u;
    	password = p;
    }
    
    public void TableConnect(){
    	String start = "DROP TABLE IF EXISTS pokemon; "
    			+ "CREATE TABLE pokemon ("
    			+ "abilities varchar(100), "
    			+ "against_bug DECIMAL(2,1), "
    			+ "against_dark DECIMAL(2,1), "
    			+ "against_dragon DECIMAL(2,1), "
    			+ "against_electric DECIMAL(2,1), "
    			+ "against_fairy DECIMAL(2,1), "
    			+ "against_fight DECIMAL(2,1), "
    			+ "against_fire DECIMAL(2,1), "
    			+ "against_flying DECIMAL(2,1) , "
    			+ "against_ghost DECIMAL(2,1), "
    			+ "against_grass DECIMAL(2,1), "
    			+ "against_ground DECIMAL(2,1), "
    			+ "against_ice DECIMAL(2,1), "
    			+ "against_normal DECIMAL(2,1), "
    			+ "against_poison DECIMAL(2,1), "
    			+ "against_psychic DECIMAL(2,1), "
    			+ "against_rock DECIMAL(2,1), "
    			+ "against_steel DECIMAL(2,1), "
    			+ "against_water DECIMAL(2,1), "
    			+ "attack INTEGER, "
    			+ "classification varchar(100), "
    			+ "defense INTEGER, "
    			+ "height DECIMAL(5,2), "
    			+ "hp INTEGER, "
    			+ "name varchar(50), "
    			+ "number INTEGER PRIMARY KEY, "
    			+ "sp_attack INTEGER, "
    			+ "sp_defense INTEGER, "
    			+ "speed INTEGER, "
    			+ "type_one varchar(50), "
    			+ "type_two varchar(50), "
    			+ "weight DECIMAL (7,2), "
    			+ "generation INTEGER, "
    			+ "legendary BOOLEAN "
    			+ ");";

        
        try{
        	Statement statement = database_connection.createStatement();
            statement.execute(start);
        	
        }
        catch(SQLException e){
        	System.out.println("ISSUE WITH TABLE CREATION");
        }
    }

    public Connection connect() {
   
        database_connection = null;
        try {
            database_connection = DriverManager.getConnection(connection_url, username, password);
            System.out.println("Connected to database server");
        }
        catch(SQLException sqlE) {
            System.out.println("Failed to connect to database");
            System.out.println(sqlE.getMessage());
        }

        return database_connection;
    }

    public void disconnect() {
        try {
            database_connection.close();
        }
        catch (SQLException sql) {
            System.out.println("PokeBaseManager.disconnect() failed");
            System.out.println(sql.getMessage());
        }
    }


    public void addPokemon(Pokemon pokemon) {
        String template = "INSERT INTO " + table_name + " ";

        /** add in the column names to the statement */
        template = template + "(";
        for (String col : columns) {
            if (col.equals("legendary")) {
                 template = template + col;
                 break;
            }
            template = template + col + ", ";
        }
        template = template + ") ";

        /** add in the values from the pokemon */
        template = template + "\nVALUES (";
        template = formatPokemon(template, pokemon);
        template = template + ");";

        try {
            Statement statement = database_connection.createStatement();
            statement.execute(template);
            System.out.println("Added " + pokemon.getName());
        }
        catch (SQLException sqlE) {
            System.out.println("PokeBaseManager.addPokemon() failed sql createStatement()");
            System.out.println(sqlE.getMessage());
        }

    }

    private String formatPokemon(String string, Pokemon pokemon) {

        String abils = "'";
        for (String abil : pokemon.getAbilities()) { abils = abils + abil + " "; }
        string = string + abils + "', ";

        /** add in the against values */
        for (Double against : pokemon.getAgainst()) { string = string + Double.toString(against) + ", "; }

        // ATTACK
        string = string + Integer.toString(pokemon.getAttack()) + ", ";
        // CLASSIFICATION
        string = string + "'" + pokemon.getClassification() + "', ";
        // DEFENSE
        string = string + Integer.toString(pokemon.getDefense()) + ", ";
        // HEIGHT
        string = string + Double.toString(pokemon.getHeight()) + ", ";
        // HP
        string = string + Integer.toString(pokemon.getHit_points()) + ", ";
        // NAME
        if (pokemon.getName().equals("Farfetch'd")) { string = string + "'Farfetch''d', "; }
        else { string = string + "'" + pokemon.getName() + "', "; }
        // NUMBER
        string = string + Integer.toString(pokemon.getId()) + ", ";
        // SP_ATTACK
        string = string + Integer.toString(pokemon.getSp_attack()) + ", ";
        // SP_DEFENSE
        string = string + Integer.toString(pokemon.getSp_defense()) + ", ";
        // SPEED
        string = string + Integer.toString(pokemon.getSpeed()) + ", ";
        // TYPES
        string = string + "'" + pokemon.getTypes()[0] + "', '" + pokemon.getTypes()[1] + "', ";
        // WEIGHT
        string = string + Double.toString(pokemon.getWeight()) + ", ";
        // GENERATION
        string = string + Integer.toString(pokemon.getGeneration()) + ", ";
        // LEGENDARY
        string = string + Boolean.toString(pokemon.isLegendary());

        return string;
    }

}
