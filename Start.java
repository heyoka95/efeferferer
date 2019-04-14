package rover;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class Start {

	static Random r = new Random();
	static LinkedHashMap<int[], String> marsKarte;
	static int BREITE_MARSES = 80;      		            
	static int HOEHE_MARSES = 20;			
	static int ROVER_XPOSITION = BREITE_MARSES / 2;   
	static int ROVER_YPOSITION = HOEHE_MARSES / 2;
	
	
	public static void erstelleKarteUndSetzeRoverInDieMitte() {
		marsKarte = new LinkedHashMap<>();
		int[] positionVomHindernis;
		
		for (int i = 0; i < BREITE_MARSES; i++) {
			for (int j = 0; j < HOEHE_MARSES; j++) {
				positionVomHindernis = new int[] { i, j };
				if (r.nextDouble() < 0.25 && !(ROVER_XPOSITION == i && ROVER_YPOSITION == j))
					marsKarte.put(positionVomHindernis, "#"); //Füge zur LinkedHashMap die Position auf der sich ein Hindernis befindet.
			}
		}
		marsKarte.put(new int[] {ROVER_XPOSITION , ROVER_YPOSITION }, "n"); //Erzeuge den Roboter auf den jeweiligen Positionen
	}	
	
	
	
	
	public static int[] bestimmeWidthUndHeight(Set<int[]> KoordinatenVonKarte) {
		int[] MaxGrenzeVonKoordinaten = new int[2];
		for (int[] e : KoordinatenVonKarte) {
			if (e[0] > MaxGrenzeVonKoordinaten[0])
				MaxGrenzeVonKoordinaten[0] = e[0];
			if (e[1] > MaxGrenzeVonKoordinaten[1])
				MaxGrenzeVonKoordinaten[1] = e[1];
		}
		return MaxGrenzeVonKoordinaten;
	}
	
	public static String getRoverUndHindernisse(Map<int[], String> ObjekteVonMarskarte, int[] derzeitigePosition) {
		Set<Entry<int[], String>> entrySet = ObjekteVonMarskarte.entrySet();
		for (Entry<int[], String> entry : entrySet) {
			if (entry.getKey()[0] == derzeitigePosition[0] && entry.getKey()[1] == derzeitigePosition[1])
				return entry.getValue();
		}
		return null;
	}


	
	public static void printeDasFeld() {
		// Set<int[]> keySet = mars.keySet();
		// for (int[] e : keySet) {
		// if (e[0] == 39 && e[1] == 10)
		// System.err.println(mars.get(e) + " " + e.hashCode());
		// }

		int[] GroesseUndBreite = bestimmeWidthUndHeight(marsKarte.keySet());
		for (int j = 0; j < GroesseUndBreite[1]; j++) {
			for (int i = 0; i < GroesseUndBreite[0]; i++) {
				// System.out.println(i + "," + j + ": " + get(mars, new int[] { i, j }));

				if (getRoverUndHindernisse(marsKarte, new int[] { i, j }) == null) {
					System.out.print(" ");
					continue;
				}
				if (getRoverUndHindernisse(marsKarte, new int[] { i, j }).equals("#"))
					System.out.print("#");
				else if (getRoverUndHindernisse(marsKarte, new int[] { i, j }).equals("n"))
					System.out.print("^");
				else if (getRoverUndHindernisse(marsKarte, new int[] { i, j }).equals("s"))
					System.out.print("V");
				else if (getRoverUndHindernisse(marsKarte, new int[] { i, j }).equals("e"))
					System.out.print(">");
				if (getRoverUndHindernisse(marsKarte, new int[] { i, j }).equals("w"))
					System.out.print("<");

			}
			System.out.println();
		}
		for (int i = 0; i < GroesseUndBreite[0]; i++) {
			System.out.print("=");
		}
		System.out.println();
	}

	public static void main(String[] args) {

		if (args.length > 1) {
			long seed = Long.parseLong(args[1]);
			r.setSeed(seed);
			// System.out.println("Seed: " + seed);
		}
		erstelleKarteUndSetzeRoverInDieMitte();
		String RichtungsOptionen = args[0];
		printeDasFeld();
		for (int i = 0; i < RichtungsOptionen.length(); i++) {
			bewegeRover(RichtungsOptionen.charAt(i));
			printeDasFeld();
		}
	}

	public static void bewegeRover(char c) {
		if (c == 'f') {
			int[] aktuellePosition = findeRover();
			if (getRoverUndHindernisse(marsKarte, aktuellePosition).equals("n"))
				aktuellePosition[1]--;
			else if (getRoverUndHindernisse(marsKarte, aktuellePosition).equals("s"))
				aktuellePosition[1]++;
			else if (getRoverUndHindernisse(marsKarte, aktuellePosition).equals("e"))
				aktuellePosition[0]++;
			else if (getRoverUndHindernisse(marsKarte, aktuellePosition).equals("w"))
				aktuellePosition[0]--;
		} else if (c == 'b') {
			int[] aktuellePosition = findeRover();
			if (getRoverUndHindernisse(marsKarte, aktuellePosition).equals("s"))
				aktuellePosition[1]--;
			else if (getRoverUndHindernisse(marsKarte, aktuellePosition).equals("n"))
				aktuellePosition[1]++;
			else if (getRoverUndHindernisse(marsKarte, aktuellePosition).equals("w"))
				aktuellePosition[0]++;
			else if (getRoverUndHindernisse(marsKarte, aktuellePosition).equals("e"))
				aktuellePosition[0]--;
		} else if (c == 'l') {
			int[] aktuellePosition = findeRover();
			if (getRoverUndHindernisse(marsKarte, aktuellePosition).equals("n"))
				marsKarte.put(aktuellePosition, "w");
			else if (getRoverUndHindernisse(marsKarte, aktuellePosition).equals("s"))
				marsKarte.put(aktuellePosition, "e");
			else if (getRoverUndHindernisse(marsKarte, aktuellePosition).equals("e"))
				marsKarte.put(aktuellePosition, "n");
			else if (getRoverUndHindernisse(marsKarte, aktuellePosition).equals("w"))
				marsKarte.put(aktuellePosition, "s");
		} else if (c == 'r') {
			int[] aktuellePosition = findeRover();
			if (getRoverUndHindernisse(marsKarte,aktuellePosition).equals("w"))
				marsKarte.put(aktuellePosition, "n");
			else if (getRoverUndHindernisse(marsKarte, aktuellePosition).equals("e"))
				marsKarte.put(aktuellePosition, "s");
			else if (getRoverUndHindernisse(marsKarte, aktuellePosition).equals("n"))
				marsKarte.put(aktuellePosition, "e");
			else if (getRoverUndHindernisse(marsKarte, aktuellePosition).equals("s"))
				marsKarte.put(aktuellePosition, "w");
		}

	}

	private static int[] findeRover() {
		Set<Entry<int[], String>> entrySet = marsKarte.entrySet();
		for (Entry<int[], String> entry : entrySet) {
			if (entry.getValue() != null && !entry.getValue().equals("#"))
				return entry.getKey();
		}
		throw new IllegalStateException("Rover missing in action");
	}

}
