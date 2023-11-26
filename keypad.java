package Midterm;
import java.util.*;

public class keypad {
	private Scanner input;
	
	public keypad() {
		input = new Scanner(System.in);
		
	}
	
	public int getInput() {
		return input.nextInt();
	}
}
