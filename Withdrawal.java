public class Withdrawal extends Transaction{
	
	private int amount;
	private Keypad keypad;
	private CashDispenser cashDispenser;
	
	private final static int CANCELED = 6;
	
	public Withdrawal(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase,Keypad atmKeypad,CashDispenser atmCashDispenser) {
		
		super(userAccountNumber, atmScreen ,atmBankDatabase);
	
		keypad = atmKeypad;
		cashDispenser = atmCashDispenser;
		
	}
	
	@Override
	public void execute() {
		boolean cashDispensed = false;
		double availableBalance;
		
		BankDatabase bankDatabase = getBankDatabase();
		Screen screen = getScreen();
		
		do {
			amount = displayMenuOfAmounts();
			
			if(amount != CANCELED) {
				availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());
			}
			else {
				screen.displayMessageLine("\n Canceling transaction...");
				return;
			}
			
			if(amount <= availableBalance) {
				
				if(cashDispenser.isSufficientCashAvailable(amount)) {
					bankDatabase.debit(getAccountNumber(), amount);
					
					cashDispenser.dispenserCash(amount);
					cashDispensed = true;
					
					screen.displayMessageLine("\n your cash has been dispensed. Please take your cash now.");
				}
				else {
					screen.displayMessageLine("\nInsufficient cash available in the ATM." + "\n Please choose a smaller amount");
				}
			}
		} while(!cashDispensed);
	}
		
	private int displayMenuofAmounts()
	{
		int userChoice = 0;

		Screen screen = getScreen();

		int[] amounts = {0, 20, 40, 60, 100, 200};

		while(userChoice == 0){
			screen.displayMessageLine("\nWithdrawal Menu:");
			screen.displayMessageLine("1 - $20");
			screen.displayMessageLine("2 - $40");
			screen.displayMessageLine("3 - $60");
			screen.displayMessageLine("4 - $100");
			screen.displayMessageLine("5 - $200");
			screen.displayMessageLine("6 - Cancel transaction");
			screen.displayMessage("\n Choose a withdrawal amount: ");
		
			int input;
			input = keypad.getInput();
			
			switch(input)
			{
				case 1: // if the user chose a Withdrawal amount;
				case 2: // (i.e., chose option 1, 2, 3, 4, or 5) return the corresponding amount from array
				case 3: //
				case 4: //
				case 5:
					userChoice = amounts[input];
					break;
				case CANCELED:
					userChoice = CANCELED;
					break;
				default:
					screen.displayMessageLine("\nInvalid selection. try again");
			}
		}
		
		return userChoice;
	}
}
