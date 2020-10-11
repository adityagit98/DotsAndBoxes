import java.util.*;
public class State {
	private int[][] grid;
	private Player type;
	private int maxScore;
	private int minScore;
	//take rows, col from main function and form the grid
	public State(int rows, int cols, Player inType)
	{
		int rDim = (rows * 2) + 1;
		int cDim = (cols * 2) + 1;
		grid = new int[rDim][cDim];
		type = inType;
		maxScore = 0;
		minScore = 0;
		initialize();			//initialize all the value of grid to maintain dots,boxes and lines
	}
	public int getMaxScore() { return maxScore; }		//return maxscore at the current state
	public int getMinScore() { return minScore; }		//return minscore at the current state
	public Player getType() { return type; }
	public int length1(){return grid.length;}
	public int length2(){return grid[0].length;}
	public void copy(int array[][])					//copy the current state of board into taken argument array
	{
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j < grid[i].length; j++)
			{
				array[i][j] = grid[i][j];
			}
		}
	}
	//reset function reset the board to play from begining
	public void reset()	
	{
		type = Player.MIN;
		maxScore = 0;
		minScore = 0;
		initialize();
	}
	//makeMove function check for errors if there is no error then make move and change the player accordingly
	public Error makeMove(int row, int col)
	{
		if( row < 0 || row > grid.length || col < 0 || col > grid[0].length)
	    {
			return Error.OUT_OF_BOUNDS;
		}
		if(!(row % 2 == 0 ^ col % 2 == 0))
			return Error.INVALID_SPACE;
		
		if(grid[row][col] > 0)
			return Error.SPACE_FILLED;
		
		grid[row][col] = 1;
		int x = maxScore;
		int y = minScore;
		updateScore(row, col);
		if(type == Player.MAX)
		{
			if(x<maxScore)
			{
				return Error.SUCCESS;
			}
			type = Player.MIN;
		}
		else
		{
			if(y<minScore)
			{
				return Error.SUCCESS;
			}
			type = Player.MAX;
		}
		return Error.SUCCESS;
	}
	//update function update the score when player or AI make a move;
	private void updateScore(int row, int col)		
	{
		// Check above and below
		if(row % 2 == 0)
		{
			int up = row - 1;
			int down = row + 1;
			if(isSurrounded(up, col))
			{
				if(type == Player.MAX)
					maxScore += 1;
				else
					minScore += 1;
			}
			if(isSurrounded(down, col))
			{
				if(type == Player.MAX)
					maxScore += 1;
				else
					minScore += 1;
			}
		}
		// Check left and right
		else
		{
			int left = col - 1;
			int right = col + 1;
			if(isSurrounded(row, left))
			{
				if(type == Player.MAX)
					maxScore += 1;
				else
					minScore += 1;
			}
			if(isSurrounded(row, right))
			{
				if(type == Player.MAX)
					maxScore += 1;
				else
					minScore += 1;
			}
		}
	}
	//check whether given place is surrounded or not
	private boolean isSurrounded(int row, int col)		
	{
		if(row > 0 && row < grid.length && col > 0 && col < grid[row].length)
		{
			return grid[row - 1][col] > 0 && grid[row + 1][col] > 0 && grid[row][col - 1] > 0 &&  grid[row][col + 1] > 0;
		}
		
		return false;
	}
	//here is the initialization part where we set the grid according to there box size;
	private void initialize()
	{
		
		for(int row = 0; row < grid.length; row++)
		{
			if(row % 2 == 0)
			{
				for(int col = 0; col < grid[row].length; col++)
					grid[row][col] = 0;
			}
			else
			{
				for(int col = 0; col < grid[row].length; col++)
				{
					if(col % 2 == 0)
						grid[row][col] = 0;
					else
						grid[row][col] = 25;
				}
			}
		}
	}
	//check that game is over or not
	public boolean isOver()
	{
		for(int row = 0; row < grid.length; row++)
		{
			if(row % 2 == 0)
			{
				for(int col = 1; col < grid[row].length; col += 2)
				{
					if(grid[row][col] == 0)
						return false;
				}
			}
			else
			{
				for(int col = 0; col < grid[row].length; col += 2)
					if(grid[row][col] == 0)
						return false;
			}
		}
		return true;
	}
	
	//print tghe current state of board;
	public void printNode()
	{
		System.out.print(" ");
		for(int col = 0; col < grid[0].length; col++)
		{
			System.out.print(" " + col);
		}
		System.out.print("\n");
		for(int row = 0; row < grid.length; row++)
		{
			System.out.print(row + " ");
			if(row % 2 == 0)
			{
				for(int col = 0; col < grid[row].length; col++)
				{
					if(col % 2 == 0)
						System.out.print("*");
					else if(grid[row][col] > 0)
						System.out.print("---");
					else
						System.out.print("   ");
				}
			}
			else
			{
				for(int col = 0; col < grid[row].length; col++)
				{
					if(col % 2 != 0)
					{
						if(grid[row][col]== 25)
						{
							System.out.print("   ");
						}
						else
						{
							System.out.print(" " + grid[row][col] + " ");
						}
					}
					else if(grid[row][col] > 0)
						System.out.print("|");
					else
						System.out.print(" ");
				}
			}
			System.out.print("\n");
		}
		System.out.println("-----\nScore\n-----");
		System.out.println("Player: " + minScore);
		System.out.println("Computer: " + maxScore + "\n");
	}
}
