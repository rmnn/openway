
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

/**
* Program finds short cut in labyrinth between two points
*
* @author ageevd
*/
public class Main {
	private static int[][] myField = new int[64][64];
	private static int myHeight;
	private static int myWidth;
	private static final int[] myPossibleWays = { 1, -1, 0, 0, 0, 0, 1, -1 };
		
	/**
	* Get data from file and handle it
	* 
	* @throws IOExpception, if data is incorrect
	*/
	private static void getAndHandleData() throws IOException {
		Scanner in = new Scanner(System.in);
		System.out.print("Write name of file: ");
		Scanner scanner = new Scanner(new File(in.nextLine()));
		myHeight = scanner.nextInt();
		myWidth = scanner.nextInt();
		for (int i = 1; i <= myHeight; i++) {
			for (int j = 1; j <= myWidth; j++) {
				myField[i][j] = 254 + scanner.nextInt();
			}
		}
		myField[scanner.nextInt()][scanner.nextInt()] = 253;
		myField[scanner.nextInt()][scanner.nextInt()] = 0;
		in.close();
		scanner.close();
	}

	/**
	* Find short cut between two points
	* 
	* @return short cut between two points;
	* @throws IOExpception, if there is no path between points
	*/
	private static int getShortCut() throws IOException {
		int minimalSide = Math.min(myHeight, myWidth);
		int maxIterations = minimalSide * minimalSide / 2 + minimalSide - 1;
		int pathLength = 0;
		boolean isWayFound = false;
		while (!isWayFound) {
			for (int i = 1; i <= myHeight; i++) {
				for (int j = 1; j <= myWidth; j++) {
					for (int k = 0; k < 4; k++) {
						if (myField[i][j] == pathLength) {
							switch (myField[i + myPossibleWays[k]][j
									+ myPossibleWays[k + 4]]) {
							case 253:
								isWayFound = true;
							case 254:
								myField[i + myPossibleWays[k]][j
										+ myPossibleWays[k + 4]] = pathLength + 1;
							}
						}
					}
				}
			}
			if (pathLength > maxIterations) {
				throw (new RuntimeException("There is no path between points"));
			}
			pathLength++;
		}
		return pathLength;
	}

	public static void main(String[] args) {
		try {
			getAndHandleData();
			System.out.println("Short cut =  " + getShortCut());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
