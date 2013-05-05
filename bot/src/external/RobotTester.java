package external;

import java.io.*;
import java.lang.Runtime;
import pso.Particle;

/**
 * Trieda sluziaca ako adapter medzi testovacimi skriptami a vypoctovym modulom.
 * @author Marek Hlav·Ë <mark.hlavac@gmail.com>
 *
 */
public class RobotTester {
	
	private static String TEST_SCRIPT_PATH;
	
	/**
	 * Pripravenie cesty pre spustenie testovacieho skriptu.
	 * Priklad cesty: "C:/Users/mark/dp/g-swarm/bot/script/robot_tester.py"
	 */
	public static void loadTestScriptPath(){
		File file = new File("conf/path.conf");
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
			// cesta v konfiguraku je definovane v hodnote TEST_SCRIPT
			TEST_SCRIPT_PATH = br.readLine().replaceAll("TEST_SCRIPT=", "");
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		} catch (java.lang.NullPointerException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Spustenie testu s pozadovanymi parametrami.
	 * Program caka na ukoncenie spusteneho externeho skriptu.
	 * @param particlesNum			pocet robotov vo vypoctovej iteracii (1 robot = 1 castica)
	 * @param iteration				cislo iteracie
	 * @param testPath				nazov adresaru obsahujuci aktualny test
	 * @param swarmName				nazov aktualne vyvijaneho modulu (podstatne len pre paralelne gramaticke roje)
	 */
	public static void startTest(int particlesNum, int iteration, String testPath, String swarmName){
		try {
			String command = "python " + TEST_SCRIPT_PATH + " " + particlesNum + " " + iteration + " "  + testPath + " " + swarmName;
			System.out.println(command);
			Process p = Runtime.getRuntime().exec(command);
			
			// prepojenie chyboveho a regularneho vystupu s Java regularnym vystupom
			StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");
			StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT");

			// start gobblers
			outputGobbler.start();
			errorGobbler.start();
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			System.err.println("Error: " + e.getMessage());
		}	
	}
	
	/**
	 * Zisti ziskane skore v subojoch pre konkretneho robota.
	 * @param particle				castica reprezentujuca robota
	 * @return skore
	 */
	public static double loadTotalScore(Particle particle){
		// kazdy validny a otestovany robot by mal mat vo vlastnom adresari vygenerovany subor score.log obsahujuci nadobudnute skore
		File file = new File(particle.getDir() + "/score.log");
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
			String line = br.readLine();
			return Double.parseDouble(line);
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		} catch (java.lang.NullPointerException e) {
			System.err.println("Error: " + e.getMessage());
		}
		
	    return 0;
	}
}
