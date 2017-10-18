package mazeGenerator;
/**
 *
 */
import java.io.*;
import java.util.Random;
public class Utility {
	
	private static Random random;
	private static PrintStream out;

	/**
	 * Code is taken from java.util.Collections
	 */
	public static void shuffle(int[] array) {
		if (random == null) {
			random = new Random();
		}
		for (int i = array.length; i > 1; i--) {
			swap(array, i-1, random.nextInt(i));
		}
	}

	private static void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	public static void print(String s) {
		if (out == null) {
			out = System.out;
		}
		out.printf(s);
	}

	public static int getRandom(int max) {
		if (random == null) {
			random = new Random();
		}
		int value = random.nextInt(max);
		return value;
	}

}

