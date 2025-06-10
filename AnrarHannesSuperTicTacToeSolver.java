import java.util.Scanner;

public class AnrarHannesSuperTicTacToeSolver {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		int numRounds = sc.nextInt();
		String gameLog = sc.next();
		
		int hPoint = 0, aPoint = 0;
		
		for( char c : gameLog.toCharArray()) {
			
			if((hPoint == numRounds) ){
				break;
			}
			if((aPoint == numRounds)) {
				break;
			}
			
			if( c == 'H') {
				hPoint++;
			}else if(c == 'A') {
				aPoint++;
			}	
		}
		
		if(hPoint > aPoint) {
			System.out.println("Arnar");

		}else {
			System.out.println("Hannes");

		}
		
		sc.close();
	}
	
}
