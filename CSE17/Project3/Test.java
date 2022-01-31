import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		
		System.out.println("Testing put(key, value) - Total number of collision");
		System.out.printf("%-20s %-20s %-20s\n", "Size", "HashMapSC", "HashMapLP");
		for(int i = 10000; i <= 100000; i += 10000) {
			HashMapSC<String, String> sc = new HashMapSC<>(i);
			readFromFile(sc);
			HashMapLP<String, String> lp = new HashMapLP<>(i);
			readFromFile(lp);
			System.out.printf("%-20d %-20d %-20d\n", i, HashMapSC.collisions, HashMapLP.collisions);
		}
		
		HashMapSC<String, String> sc = new HashMapSC<>(50000);
		readFromFile(sc);
		HashMapLP<String, String> lp = new HashMapLP<>(50000);
		readFromFile(lp);
		ArrayList<String> words = new ArrayList<>(550000);
		readFromFile(words);
		
		System.out.println("\nTesting get(key) - Number of Iterations");
		
		int lpTotalIter = 0, scTotalIter = 0;
		System.out.printf("%-20s %-20s %-20s\n", 
				"Word","HashMapSC", "HashMapLP");
		for (int i = 0; i < 1000; i++) {
			String target = words.get((int)(Math.random()*50000));
			lp.get(target);
			sc.get(target);
			int lpiter = HashMapLP.iterations;
			int sciter = HashMapSC.iterations;
			lpTotalIter += lpiter;
			scTotalIter += sciter;
			if (i % 50 == 0) { 
				System.out.printf("%-20s %-20d %-20d\n", 
						target, lpiter, sciter);
			}
		}
		System.out.printf("\n%-20s %-20d %-20d\n", "Average", 
				lpTotalIter/1000, scTotalIter/1000);
		
	}
	
	public static void readFromFile(HashMapSC<String, String> map) {
		try {
			Scanner readFile = new Scanner(new File("dictionary.txt"));
			while (readFile.hasNextLine()) {
				String line = readFile.nextLine();
				String word = line.substring(0, line.indexOf("|"));
				String definition = line.substring(line.indexOf("|")+1);
				map.put(word, definition);
			}
			readFile.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Couldn't find dictionary.txt");
			System.exit(0);
		}
	}
	
	public static void readFromFile(HashMapLP<String, String> map) {
		try {
			Scanner readFile = new Scanner(new File("dictionary.txt"));
			while (readFile.hasNextLine()) {
				String line = readFile.nextLine();
				String word = line.substring(0, line.indexOf("|"));
				String definition = line.substring(line.indexOf("|")+1);
				map.put(word, definition);
			}
			readFile.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Couldn't find dictionary.txt");
			System.exit(0);
		}
	}
	
	public static void readFromFile(ArrayList<String> map) {
		try {
			Scanner readFile = new Scanner(new File("dictionary.txt"));
			while (readFile.hasNextLine()) {
				String line = readFile.nextLine();
				String word = line.substring(0, line.indexOf("|"));
				map.add(word);
			}
			readFile.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Couldn't find dictionary.txt");
			System.exit(0);
		}
	}
	
	/*
	 * Output: 
	 * 
	 * Testing put(key, value) - Total number of collision
Size                 HashMapSC            HashMapLP           
10000                22796                36753               
20000                17768                18801               
30000                17768                18801               
40000                7668                 9027                
50000                7668                 9027                
60000                7668                 9027                
70000                4181                 4550                
80000                4181                 4550                
90000                4181                 4550                
100000               4181                 4550                

Testing get(key) - Number of Iterations
Word                 HashMapSC            HashMapLP           
"Acrisy"             1                    1                   
"Salience"           2                    2                   
"Read"               1                    1                   
"Histophyly"         1                    1                   
"Saline"             1                    1                   
"Greenfinch"         1                    1                   
"Lidless"            1                    1                   
"Laguay"             4                    2                   
"Foresighted"        1                    1                   
"Undulary"           1                    1                   
"Heathenish"         1                    1                   
"Ney"                1                    1                   
"Relique"            3                    2                   
"Farfow"             1                    1                   
"Painted"            1                    1                   
"Forebrace"          1                    1                   
"Haplostemonous"     1                    1                   
"Unbutton"           3                    3                   
"Riffle"             1                    1                   
"Horripilation"      2                    2                   

Average              1                    1                   

	*
	*	The numbers for collisions for put are often the same from
	*	one size to the next, because when we create it and rehash it
	*	we change it to a power of 2, meaning a lot of the sizes get 
	*	changed to the same power of 2 for size. Both decrease as the size
	*	increases, which makes sense. As there's more open indexes, there are
	*	less collisions. Linear Probing consistently had more collisions, probably
	*	because LP takes up more individual indices of the hashTable than
	*	SC does. Basically for LP, one collision means you take up another index
	*	, increasing your odds of having another collision.
	*
	*	As expected, the iterations for get() were an average of 1 for
	*	each HashMap. This should really always be the case, because we
	*	rehash when the HashMap gets too full.
	*/
	
}
