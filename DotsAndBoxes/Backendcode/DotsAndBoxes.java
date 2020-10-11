import java.util.*;
public class DotsAndBoxes {
	public static void main(String[] args)
	{
		Scanner s = new Scanner(System.in);
		System.out.println("Welcome to dots and boxes.");
		int width = 4;
		int height = 4;
		int depth = 0;
		int difficulty = 0;
		Player starting;
		State root;
		int[] move = new int[3];
		String[] moveStr;
		Error result;
		MinMax obj=new MinMax();
		while(true)
		{
			System.out.println("\nPlease enter the desired difficulty. \n Enter 1 for easy level.\n Enter 2 for medium level.\n Enter 3 for hard level.");
			try {
				difficulty = Integer.parseInt(s.nextLine());
				if(difficulty == 1)
				{
					depth = 2;
					break;
				}
				else if(difficulty == 2)
				{
					depth = 4;
					break;
				}
				else if(difficulty == 3)
				{
					depth = 6;
					break;
				}
				else
					continue;
			} 
			catch(NumberFormatException e)
			{
				System.out.print("\nThat is not a valid difficulty. Please try again.");
			}
		}
		System.out.print("\n");
			starting = Player.MIN;				//set the starting move is of player 		
		root = new State(height, width, starting);
		
		System.out.println("Please enter the coordinates of your moves in row, column order."
				+ "\nCoordinates should be separated by a comma.\n");
		
		root.printNode();
		int k = 0;
		while(!root.isOver())			//for player input
		{
			k++;
			if(root.getType() == Player.MIN)
			{
				do {
					System.out.print("Player's move: ");
					moveStr = s.nextLine().split(",");
					result = Error.SUCCESS;
					if(moveStr.length < 2)
					{
						System.out.println("That move is invalid. Please enter the desired move in <row>,<column> format.");
						result = Error.INVALID_NUMBER;
					} else
					{
						for(int i = 0; i < 2; i++)
						{
							try {
								move[i] = Integer.parseInt(moveStr[i]);
							} catch (NumberFormatException e)
							{
								System.out.println("That move is invalid. Please enter the desired move in <row>,<column> format.");
								result = Error.INVALID_NUMBER;
							}
						}
					}
					if(result != Error.INVALID_NUMBER)
						result = root.makeMove(move[0], move[1]);
					if(result == Error.INVALID_SPACE)
						System.out.println("You cannot draw a line there.");
					if(result == Error.OUT_OF_BOUNDS)
						System.out.println("That coordinate is outside the boundaries of the grid.");
					if(result == Error.SPACE_FILLED)
						System.out.println("That space already has a line drawn in it.");
				} while (result != Error.SUCCESS);
				System.out.print("\n");
				root.printNode();
			}

			else
			{
				System.out.print("Computer's move: ");
				move = obj.makeMove(root, depth);		//call the minmax for finding best move
				System.out.println(move[0] + "," + move[1]);
				root.makeMove(move[0], move[1]);		//make a move to next state with return best row,col value by minmax;
				root.printNode();
			}
				if(depth ==6 && k>=20)
				{
					depth++;
				}

		}
		
		System.out.println("-------------\nFinal results\n-------------");
		System.out.println("Player score: " + root.getMinScore());
		System.out.println("Computer score: " + root.getMaxScore());
		if(root.getMaxScore() > root.getMinScore())
			System.out.println("Winner: Computer");
		else if(root.getMinScore() > root.getMaxScore())
			System.out.println("Winner: Player");
		else
			System.out.println("The game is a draw.");
		
		System.out.println("\nThank you for playing. Goodbye!");
		
		s.close();
	}
}
