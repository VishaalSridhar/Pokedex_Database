import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/* First database "pokemon.csv" from https://www.kaggle.com/rounakbanik/pokemon/data */

public class PokeImporter {
    private static final int DATA_SIZE = 41;

    private String mFileName;
    private FileReader mPokeFile;
    public PokeBaseManager pokeBase;
    private Connection db;

    public PokeImporter(String filename) {
        mFileName = filename;

        try {
			mPokeFile = new FileReader(mFileName);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
    }
    
    public void import_pokemon() {

        pokeBase = new PokeBaseManager();
        pokeBase.connect();
        //pokeBase.TableConnect();

        Scanner scanner = new Scanner(mPokeFile);
		scanner.useDelimiter(",");
		while (scanner.hasNext()) {
		    Pokemon pokemon = new Pokemon();
		    String line = scanner.nextLine();

		    int[] abilIndex = new int[] {line.indexOf('['), line.indexOf(']')};
		    String abilString = line.substring(abilIndex[0] + 1, abilIndex[1]);
		    abilString = abilString.replace('\'', '*');
		    String[] abilities = abilString.split(",");
		    pokemon.setAbilities(new ArrayList<String>(Arrays.asList(abilities)));

		    line = line.substring(abilIndex[1] + 3, line.length());

		    String[] stats = line.split(",");

		    Double[] againstArray = new Double[18];
		    for (int i = 0; i < 18; i++) {
		        if (stats[i].equals("")) { againstArray[i] = 0.00; continue;}
		        againstArray[i] = Double.parseDouble(stats[i]);
		    }
		    pokemon.setAgainst(againstArray);

		    pokemon.setAttack(Integer.parseInt(stats[18]));

		    pokemon.setClassification(stats[23]);

		    pokemon.setDefense(Integer.parseInt(stats[24]));

		    if (stats[26].equals("")) { pokemon.setHeight(0.00); }
		    else { pokemon.setHeight(Double.parseDouble(stats[26])); }

		    pokemon.setHit_points(Integer.parseInt(stats[27]));

		    pokemon.setName(stats[29]);

		    pokemon.setId(Integer.parseInt(stats[31]));

		    pokemon.setSp_attack(Integer.parseInt(stats[32]));

		    pokemon.setSp_defense(Integer.parseInt(stats[33]));

		    pokemon.setSpeed(Integer.parseInt(stats[34]));

		    pokemon.setTypes(new String[]{stats[35], stats[36]});

		    if (stats[37].equals("")) { pokemon.setWeight(0.00); }
		    else { pokemon.setWeight(Double.parseDouble(stats[37])); }

		    pokemon.setGeneration(Integer.parseInt(stats[38]));

		    pokemon.setLegendary(Boolean.parseBoolean(stats[39]));


		    /*list.add(pokemon);*/
		    pokeBase.addPokemon(pokemon);
		}

		scanner.close();

        /*return list;*/
    }

}
