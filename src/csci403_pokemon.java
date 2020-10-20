import java.awt.BorderLayout;
import java.io.Console;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class csci403_pokemon{
	private static String username;
	private static String password;
	private static String connection_url = "jdbc:postgresql://flowers.mines.edu/csci403";
	private static String table_name = "pokemon";
	public static Connection db;

	static final int allPoke = 801;

	private static PokeImporter importer;

	public csci403_pokemon(){

		askForStuff();
		
		PokeBaseManager Base = new PokeBaseManager();
		Base.GetCred(username, password);
		
		importer = new PokeImporter("src/pokemon.csv");
		importer.import_pokemon();

	}



	public void askForStuff(){
		System.out.println("Username: ");
		Scanner scan = new Scanner(System.in);
		username = scan.nextLine();
		username = "engreiman";

		System.out.println("Password: ");
		scan = new Scanner(System.in);
		password = scan.nextLine();
		password = "Treasure60!";

		try {
			db = DriverManager.getConnection(connection_url, username, password);
		} catch (SQLException e) {
			System.out.println("Error connecting");
			return;
		}

	}

	public ArrayList<Integer> Next(int naviOffSet) throws SQLException{
		System.out.println("Select a pokemon (0-9). Press '[' or ']' to scroll. Press 'q' to quit. Press 's' to enter SEARCH MODE0");
		String query;
		query = "SELECT * FROM pokemon ORDER BY number ASC LIMIT 10 OFFSET " + naviOffSet;
		ArrayList<Integer> PokeID = new ArrayList<Integer>();
		ArrayList<String> buttonText = new ArrayList<String>();

		try {
			PreparedStatement state = db.prepareStatement(query);
			ResultSet pokeList = state.executeQuery();

			int i = 1;
			while (pokeList.next()) {

				PokeID.add(Integer.parseInt(pokeList.getString("number")));

				System.out.println("[" + i + "] \t" + pokeList.getString("name"));

				if (i == 9) {
					i = 0;
				} else {
					i++;
				}

			}

		} catch (SQLException e) {
			System.out.println("ERROR");
		}


		return PokeID;
	}


	/*//////////////////////////////////////////////////////////////////////////////// */
	public void PokeFacts(int id){
		String queryPokemon = "SELECT * FROM " + table_name + " WHERE Number =" + id + "; ";

		try{
			PreparedStatement dex = db.prepareStatement(queryPokemon);
			ResultSet results = dex.executeQuery();

			while(results.next()){
				System.out.println();
				System.out.println();
				System.out.println("----------------------------------------------------------------------------------------");
				System.out.println("Dex-Entry #" + results.getInt("Number"));
				System.out.println(results.getString("Name") + " the " + results.getString("classification"));
				if(results.getString("type_two").isEmpty()){
					System.out.println("The " + results.getString("type_one") + " type Pokemon");
				}
				else{
					System.out.println("The " + results.getString("type_one") + " and " + results.getString("type_two") + " type Pokemon");

				}
				System.out.println("Height: " + results.getDouble("height") + "\tWeight: " + results.getDouble("weight"));

				System.out.println("First appearnce in" + " Generation "+ results.getString("generation"));

				System.out.println();
				System.out.println("Abilites: " + results.getString("abilities"));
				System.out.println();
				System.out.println("Damage Taken Modifer against type");
				System.out.println("Water\t|fire\t|Grass\t|Bug\t|Dark\t|Dragon\t|electric\t|fairy\t|fight\t|flying\t|ghost\t|ground\t|ice\t|normal\t|poison\t|psychic\t|rock\t|steel");
				System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------");
				System.out.println(results.getString("against_water") + "\t|" + results.getString("against_fire") + "\t|" + results.getString("against_grass")
				+ "\t|" + results.getString("against_bug") + "\t|" + results.getString("against_dark")+ "\t|" + results.getString("against_dragon")
				+ "\t|" + results.getString("against_electric") + "\t\t|" + results.getString("against_fairy")+ "\t|" + results.getString("against_fight") 
				+ "\t|" + results.getString("against_flying") + "\t|" + results.getString("against_ghost") + "\t|" + results.getString("against_ground")
				+ "\t|" + results.getString("against_ice") + "\t|" + results.getString("against_normal") + "\t|" + results.getString("against_poison")
				+ "\t|" + results.getString("against_psychic") + "\t\t|" + results.getString("against_rock") + "\t|" + results.getString("against_steel"));

				System.out.println();
				System.out.println("---Base Stats---");
				System.out.println("HP: " + results.getInt("hp") + "\t\t" + "Speed: " + results.getInt("speed"));
				System.out.println("Attack: " + results.getInt("attack") + "\tSpecial Attack: " + results.getInt("sp_attack"));
				System.out.println("Defense: " + results.getInt("defense") + "\tSpecial Defense: " + results.getInt("sp_defense"));

				System.out.println();

				if(results.getBoolean("legendary")){
					System.out.println("This Pokemon is Legendary!");
				}
				else{
					System.out.println("This Pokemon is not Legendary.");
				}

				System.out.println();

			}

		}
		catch (SQLException e){
			System.out.println(e);
			return;
		}

		System.out.println("Press 'c' to continue");
		boolean hold = true;
		while(hold){
			char userInput;
			Scanner scan = new Scanner(System.in);
			userInput = scan.next().charAt(0);

			if(userInput =='c' || userInput =='C'){
				hold = false;
			}

		}

	}

	public void searchPokemon(){
		boolean loop = true;

		while(loop){
			System.out.println("Select an option: ");
			System.out.println("[1] Look up by Name");
			System.out.println("[2] Look up by Type");
			System.out.println("[3] Look up by Generation");
			System.out.println("[4] Small Pokemon");
			System.out.println("[5] Large Pokemon");
			System.out.println("[q] Go Back");

			Scanner scan = new Scanner(System.in);
			char input = scan.nextLine().charAt(0);

			switch(input){
			case '1':
				String query1 = "SELECT number FROM pokemon WHERE name = ?";
				try {
					PreparedStatement stmt = db.prepareStatement(query1);
					System.out.println("Input Name: ");
					String name = scan.nextLine();
					name = name.substring(0, 1).toUpperCase() + name.substring(1);
					stmt.setString(1, name);
					ResultSet results = stmt.executeQuery();

					while (results.next()) {
						PokeFacts(results.getInt("number"));
					}
				}
				catch (SQLException e) {
					System.out.print(e);
					return;
				}

				break;
			case '2':
				System.out.println("Enter a type");
				SQLList3("SELECT name, number FROM pokemon WHERE type_one = ? OR type_two = ? LIMIT 10 OFFSET ");


				break;
			case '3': 
				System.out.println("Select a generation (1-7)");
				SQLList("SELECT * FROM pokemon WHERE generation = ? LIMIT 10 OFFSET ");
				break;
			case '4':
				SQLList2("SELECT name, number FROM pokemon WHERE height < 0.7 LIMIT 10 OFFSET ");
				break;
			case '5':
				SQLList2("SELECT name, number FROM pokemon WHERE height > 3.5 LIMIT 10 OFFSET ");
				break;
			case 'q':
			case 'Q':
				loop = false;
				break;
			default: 
				break;
			}
		}
	}
	
	public void SQLList3(String QUERY){
		Scanner scan = new Scanner(System.in);
		boolean ON = true;
		char userInput = ' ';
		scan = new Scanner(System.in);
		int naviOffSet = 0;
		ArrayList<Integer> PokeID = new ArrayList<Integer>();
		
		String user = scan.nextLine();
		user = user.substring(0,1).toLowerCase() + user.substring(1);
	
			while (ON) {
				PokeID = new ArrayList<Integer>();
				System.out.println("Select a pokemon (0-9). Press '[' or ']' to scroll. Press 'q' to quit");
				String Q = QUERY + naviOffSet;
				try{
					PreparedStatement stmt = db.prepareStatement(Q);
					stmt.setString(1, user);
					stmt.setString(2, user);
					ResultSet results = stmt.executeQuery();
					int i = 1;
					while (results.next()) {

						PokeID.add(Integer.parseInt(results.getString("number")));

						System.out.println("[" + i + "] \t" + results.getString("name"));

						if (i == 9) {
							i = 0;
						} else {
							i++;
						}

					}
				}

				catch (SQLException e) {
					System.out.print(e);
					return;
				}

				
				userInput = scan.next().charAt(0);
			

				switch (userInput) {
				//Poke choices
				case '1':
					if (PokeID.size() > 0) {
						PokeFacts(PokeID.get(0));
					}
					break;

				case '2':
					if (PokeID.size() > 1) {
						PokeFacts(PokeID.get(1));
					}
					break;

				case '3':
					if (PokeID.size() > 2) {
						PokeFacts(PokeID.get(2));
					}
					break;

				case '4':
					if (PokeID.size() > 3) {
						PokeFacts(PokeID.get(3));
					}
					break;

				case '5':
					if (PokeID.size() > 4) {
						PokeFacts(PokeID.get(4));
					}
					break;

				case '6':
					if (PokeID.size() > 5) {
						PokeFacts(PokeID.get(5));
					}
					break;

				case '7':
					if (PokeID.size() > 6) {
						PokeFacts(PokeID.get(6));
					}
					break;

				case '8':
					if (PokeID.size() > 7) {
						PokeFacts(PokeID.get(7));
					}
					break;

				case '9':
					if (PokeID.size() > 8) {
						PokeFacts(PokeID.get(8));
					}
					break;
				case '0':
					if (PokeID.size() > 9) {
						PokeFacts(PokeID.get(9));
					}
					break;

					//Next stuff
				case '[':
					if ((naviOffSet - 10 >= 0)) {
						naviOffSet -= 10;
					}
					break;
				case ']':
					//In order to get the last 801st number I had to add ten so it would grab
					//800 to 810
					if ((naviOffSet + 10 < allPoke + 10)) {
						naviOffSet += 10;
					}
					break;

					//bring us to search functionality.

					//QUIIIT
				case 'q':
				case 'Q':
					ON = false;
					break;
				default:
					break;

				}
			}
		}
	

	public void SQLList2(String QUERY){
		Scanner scan = new Scanner(System.in);
		boolean ON = true;
		char userInput = ' ';
		scan = new Scanner(System.in);
		int naviOffSet = 0;
		ArrayList<Integer> PokeID = new ArrayList<Integer>();
		
			while (ON) {
				PokeID = new ArrayList<Integer>();
				System.out.println("Select a pokemon (0-9). Press '[' or ']' to scroll. Press 'q' to quit");
				String Q = QUERY + naviOffSet;
				try{
					PreparedStatement stmt = db.prepareStatement(Q);
					ResultSet results = stmt.executeQuery();
					int i = 1;
					while (results.next()) {

						PokeID.add(Integer.parseInt(results.getString("number")));

						System.out.println("[" + i + "] \t" + results.getString("name"));

						if (i == 9) {
							i = 0;
						} else {
							i++;
						}

					}
				}

				catch (SQLException e) {
					System.out.print(e);
					return;
				}

				
				userInput = scan.next().charAt(0);
			

				switch (userInput) {
				//Poke choices
				case '1':
					if (PokeID.size() > 0) {
						PokeFacts(PokeID.get(0));
					}
					break;

				case '2':
					if (PokeID.size() > 1) {
						PokeFacts(PokeID.get(1));
					}
					break;

				case '3':
					if (PokeID.size() > 2) {
						PokeFacts(PokeID.get(2));
					}
					break;

				case '4':
					if (PokeID.size() > 3) {
						PokeFacts(PokeID.get(3));
					}
					break;

				case '5':
					if (PokeID.size() > 4) {
						PokeFacts(PokeID.get(4));
					}
					break;

				case '6':
					if (PokeID.size() > 5) {
						PokeFacts(PokeID.get(5));
					}
					break;

				case '7':
					if (PokeID.size() > 6) {
						PokeFacts(PokeID.get(6));
					}
					break;

				case '8':
					if (PokeID.size() > 7) {
						PokeFacts(PokeID.get(7));
					}
					break;

				case '9':
					if (PokeID.size() > 8) {
						PokeFacts(PokeID.get(8));
					}
					break;
				case '0':
					if (PokeID.size() > 9) {
						PokeFacts(PokeID.get(9));
					}
					break;

					//Next stuff
				case '[':
					if ((naviOffSet - 10 >= 0)) {
						naviOffSet -= 10;
					}
					break;
				case ']':
					//In order to get the last 801st number I had to add ten so it would grab
					//800 to 810
					if ((naviOffSet + 10 < allPoke + 10)) {
						naviOffSet += 10;
					}
					break;

					//bring us to search functionality.

					//QUIIIT
				case 'q':
				case 'Q':
					ON = false;
					break;
				default:
					break;

				}
			}
		
	}
	
	public void SQLList(String QUERY){
		Scanner scan = new Scanner(System.in);
		boolean ON = true;
		char userInput = ' ';
		scan = new Scanner(System.in);
		int naviOffSet = 0;
		ArrayList<Integer> PokeID = new ArrayList<Integer>();
		
		int userInt = scan.nextInt();
		if(userInt > 0 && userInt < 8){
			while (ON) {
				PokeID = new ArrayList<Integer>();
				System.out.println("Select a pokemon (0-9). Press '[' or ']' to scroll. Press 'q' to quit");
				String Q = QUERY + naviOffSet;
				try{
					PreparedStatement stmt = db.prepareStatement(Q);
					stmt.setInt(1, userInt);
					ResultSet results = stmt.executeQuery();
					int i = 1;
					while (results.next()) {

						PokeID.add(Integer.parseInt(results.getString("number")));

						System.out.println("[" + i + "] \t" + results.getString("name"));

						if (i == 9) {
							i = 0;
						} else {
							i++;
						}

					}
				}

				catch (SQLException e) {
					System.out.print(e);
					return;
				}

				
				userInput = scan.next().charAt(0);
			

				switch (userInput) {
				//Poke choices
				case '1':
					if (PokeID.size() > 0) {
						PokeFacts(PokeID.get(0));
					}
					break;

				case '2':
					if (PokeID.size() > 1) {
						PokeFacts(PokeID.get(1));
					}
					break;

				case '3':
					if (PokeID.size() > 2) {
						PokeFacts(PokeID.get(2));
					}
					break;

				case '4':
					if (PokeID.size() > 3) {
						PokeFacts(PokeID.get(3));
					}
					break;

				case '5':
					if (PokeID.size() > 4) {
						PokeFacts(PokeID.get(4));
					}
					break;

				case '6':
					if (PokeID.size() > 5) {
						PokeFacts(PokeID.get(5));
					}
					break;

				case '7':
					if (PokeID.size() > 6) {
						PokeFacts(PokeID.get(6));
					}
					break;

				case '8':
					if (PokeID.size() > 7) {
						PokeFacts(PokeID.get(7));
					}
					break;

				case '9':
					if (PokeID.size() > 8) {
						PokeFacts(PokeID.get(8));
					}
					break;
				case '0':
					if (PokeID.size() > 9) {
						PokeFacts(PokeID.get(9));
					}
					break;

					//Next stuff
				case '[':
					if ((naviOffSet - 10 >= 0)) {
						naviOffSet -= 10;
					}
					break;
				case ']':
					//In order to get the last 801st number I had to add ten so it would grab
					//800 to 810
					if ((naviOffSet + 10 < allPoke + 10)) {
						naviOffSet += 10;
					}
					break;

					//bring us to search functionality.

					//QUIIIT
				case 'q':
				case 'Q':
					ON = false;
					break;
				default:
					break;

				}
			}
		}
	}


	public static void main(String[] args)
	/*throws ClassNotFoundException*/ {

		csci403_pokemon Dex = new csci403_pokemon();
		//SELECT STUFF
		String query;

		Scanner scan = new Scanner(System.in);
		boolean ON = true;
		char userInput = ' ';
		scan = new Scanner(System.in);
		int naviOffSet = 0;
		ArrayList<Integer> PokeID = new ArrayList<Integer>();

		while (ON) {
			//UPDATE BELOW WHEN GET DATABASE

			try {
				PokeID = new ArrayList<Integer>(Dex.Next(naviOffSet));
			} catch (SQLException e) {
				e.printStackTrace();
			}

			scan = new Scanner(System.in);
			userInput = scan.next().charAt(0);

			switch (userInput) {

			//Poke choices
			case '1':
				if (PokeID.size() > 0) {
					Dex.PokeFacts(PokeID.get(0));
				}
				break;

			case '2':
				if (PokeID.size() > 1) {
					Dex.PokeFacts(PokeID.get(1));
				}
				break;

			case '3':
				if (PokeID.size() > 2) {
					Dex.PokeFacts(PokeID.get(2));
				}
				break;

			case '4':
				if (PokeID.size() > 3) {
					Dex.PokeFacts(PokeID.get(3));
				}
				break;

			case '5':
				if (PokeID.size() > 4) {
					Dex.PokeFacts(PokeID.get(4));
				}
				break;

			case '6':
				if (PokeID.size() > 5) {
					Dex.PokeFacts(PokeID.get(5));
				}
				break;

			case '7':
				if (PokeID.size() > 6) {
					Dex.PokeFacts(PokeID.get(6));
				}
				break;

			case '8':
				if (PokeID.size() > 7) {
					Dex.PokeFacts(PokeID.get(7));
				}
				break;

			case '9':
				if (PokeID.size() > 8) {
					Dex.PokeFacts(PokeID.get(8));
				}
				break;
			case '0':
				if (PokeID.size() > 9) {
					Dex.PokeFacts(PokeID.get(9));
				}
				break;

				//Next stuff
			case '[':
				if ((naviOffSet - 10 >= 0)) {
					naviOffSet -= 10;
				}
				break;
			case ']':
				//In order to get the last 801st number I had to add ten so it would grab
				//800 to 810
				if ((naviOffSet + 10 < allPoke + 10)) {
					naviOffSet += 10;
				}
				break;

				//bring us to search functionality.
			case 's':
			case 'S':
				Dex.searchPokemon();
				break;

				//QUIIIT
			case 'q':
			case 'Q':
				ON = false;
				break;
			default:
				break;

			}

		}

	}
}