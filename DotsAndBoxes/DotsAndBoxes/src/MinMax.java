public class MinMax {
	int type;
	int changedmaxscore;
	int changedminscore;
	int[] makeMove(State node, int depth)
	{
		int coords[] = {0, 0, 0};			//this array store the best move(row,col,value) which is return to main function for taking next move for AI
		
		int[][] grida = new int[node.length1()][node.length2()];		//In this array we copy the current state 
		node.copy(grida);
		type = 1;														//current type =max;
		changedmaxscore = node.getMaxScore();
		changedminscore = node.getMinScore(); 
		coords = minMax(grida,changedminscore,changedmaxscore,type, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);	//call the mimax to decide the best move
		return coords;			//this will return the row,col and utility value for the best move

	}
	//pmove functions calculate the possible moves we can take at given state 
	int pmove(int [][]grid)		
	{	int count =0;
		for(int row = 0; row < grid.length; row++)
		{
			if(row % 2 == 0)
			{
				for(int col = 1; col < grid[row].length; col += 2)
				{
					if(grid[row][col] < 1)
						count++;
				}
			}
			else
			{
				for(int col = 0; col < grid[row].length; col += 2)
				{
					if(grid[row][col] < 1)
					{
						count++;
					}
				}
			}
		}
		return count;
	}
	//changedmove function make a move for calculating the score which is used to calculate utility value
	void changedmove(int [][]grid,int minscore,int maxscore,int type,int row,int col)
	{
		changedminscore=minscore;
		changedmaxscore=maxscore;
		// Check above and below
		if(row % 2 == 0)
		{
			int up = row - 1;
			int down =row + 1;
			if(isSurrounded(grid,up, col))
			{
				if(type == 1)
					changedmaxscore += 1;
				else
					changedminscore += 1;
			}
			if(isSurrounded(grid,down, col))
			{
				if(type == 1)
					changedmaxscore += 1;
				else
					changedminscore += 1;
			}
		}
		// Check left and right
		else
		{
			int left = col - 1;
			int right = col + 1;
			if(isSurrounded(grid,row, left))
			{
				if(type == 1)
					changedmaxscore += 1;
				else
					changedminscore += 1;
			}
			if(isSurrounded(grid,row, right))
			{
				if(type == 1)
					changedmaxscore += 1;
				else
					changedminscore += 1;
			}
		}
	}
	//check that the given row,col is surrounded or not
	boolean isSurrounded(int [][]grid,int row, int col)
	{
		if(	row > 0 && row < grid.length && col > 0 && col < grid[row].length)
		{
			if( grid[row - 1][col] > 0 && grid[row + 1][col] > 0 && grid[row][col - 1] > 0 && grid[row][col + 1] > 0)
			{
				return true;
			}
			
		}
		
		return false;
	}
 
	int[] minMax(int[][] grid,int minscore,int maxscore,int type,int depth, int alpha, int beta)
	{
		int[] best = {0,0,0};			//this is for storing best row,best column and value which used to make final move
		int[] tbest = {0,0,0};			//this is for storing row,col and value for temprory 
		int tempmin;					//tempmin,tempmax,ttype is for storing minimum and maximum score,player type temprory
		int tempmax;
		int ttype;

		int count = pmove(grid);
		if( depth==0 || count == 0)				//if we go the particular depth or no more moves is available 
		{
			best[0] = 0;
			best[1] = 0;
			best[2] = maxscore-minscore;		//then return the utility value .
			return best;
		}
		if(type == 1)			//if player is max;
		{			

			int bestVal = Integer.MIN_VALUE;		//initalize the best value as the integer.min value
			int value;
			int flag = 0;			//if(alpha<=beta) then for breaking from  inner loop we use flag value;
				for(int row = 0;flag == 0 && row < grid.length; row++)	//run for all possible moves 
				{
					if(row % 2 == 0)
					{
						for(int col = 1; flag == 0 &&col < grid[row].length; col += 2)
						{
							if(grid[row][col] < 1)		//here place is empty we can draw a line
							{	
								grid[row][col]=1;			//so fill that space
								tempmin=minscore;
								tempmax=maxscore;
								ttype = type;
								changedmove(grid,minscore,maxscore,type,row,col);		//change the move
							
								if(maxscore<changedmaxscore)
								{
										maxscore=changedmaxscore;			//check that score is changed or not if changed then definitely
																			//that move makes a box so,the same player also takes the next move
								
								}
								else
								{		//if score is not changed then next move is of opponent so we cahnge the player;
										type = 0;
								}
										//recursive call for next level ;
										tbest = minMax(grid,minscore,maxscore,type, depth - 1, alpha, beta);
										value = tbest[2];
										//after returning we again set the state, minscore, maxscore and the player as it is in previous 
										//before taking that move so that we can check for next possiblities
										grid[row][col]=0;
										minscore=tempmin;			//also reser the minscore, maxscore and the player type
										maxscore=tempmax;
										type = ttype;
										if(value > bestVal)			//because this is max player so it select max value
										{	
											best[0]=row;
											best[1]=col;
											bestVal = value;
										}
										if(bestVal > alpha)		//here we do the alph beta pruning
											alpha = bestVal;
										if(beta <= alpha)
											flag=1;			//break;
										
							}


						}
					}
					else
					{
						for(int col = 0; flag==0 && col < grid[row].length; col += 2)
						{
							if(grid[row][col] < 1)
							{
								grid[row][col]=1;
								tempmin=minscore;
								tempmax=maxscore;
								ttype = type;																	
								changedmove(grid,minscore,maxscore,type,row,col);
								if(maxscore<changedmaxscore)
								{
										maxscore=changedmaxscore;
										
								}
								else
								{
										type = 0;
								}
										tbest = minMax(grid,minscore,maxscore,type, depth - 1, alpha, beta);
										value = tbest[2];
										grid[row][col]=0;
										minscore=tempmin;
										maxscore=tempmax;
										type = ttype;
										if(value > bestVal)
										{
											best[0]=row;
											best[1]=col;
											bestVal = value;
										}
										if(bestVal > alpha)
											alpha = bestVal;
										if(beta <= alpha)
											flag=1;
							}
						}
					}
					
				}
			best[2] = bestVal;				//best[0],best[1] contains the move and best[2] contains the best value
			return best;
		}
		else
		{								//this make moves when the player in min(human) and select best value
			int bestVal = Integer.MAX_VALUE;
			int value;
			int flag=0;
				for(int row = 0;flag==0 &&  row < grid.length; row++)
				{
					if(row % 2 == 0)
					{
						for(int col = 1;flag==0 &&  col < grid[row].length; col += 2)
						{
							if(grid[row][col] < 1)
							{
								grid[row][col]=1;
								tempmin=minscore;
								tempmax=maxscore;	
								ttype = type;
								changedmove(grid,minscore,maxscore,type,row,col);
								if(minscore<changedminscore)
								{
										minscore=changedminscore;
										
								}
								else
								{
										type = 1;
								}
										tbest = minMax(grid,minscore,maxscore,type, depth - 1, alpha, beta);
										value = tbest[2];
									grid[row][col]=0;
										minscore=tempmin;
										maxscore=tempmax;
										type = ttype;
									if(value < bestVal)
									{
										bestVal = value;
										best[0] = row;
										best[1] = col;
									}
									if(bestVal < beta)
										beta = bestVal;
									if(beta <= alpha)
										flag=1;
							}

						}
					}
					else
					{
						for(int col = 0; flag==0 && col < grid[row].length; col += 2)
						{
							if(grid[row][col] < 1)
							{
								grid[row][col]=1;
								tempmin=minscore;
								tempmax=maxscore;	
								ttype = type;
								changedmove(grid,minscore,maxscore,type,row,col);
								if(minscore<changedminscore)
								{
										minscore=changedminscore;
										
								}
								else
								{
										type = 1;
								}
										tbest = minMax(grid,minscore,maxscore,type, depth - 1, alpha, beta);
										value = tbest[2];
									grid[row][col]=0;
										minscore=tempmin;
										maxscore=tempmax;
										type = ttype;
									if(value < bestVal)
									{
										bestVal = value;
										best[0]=row;
										best[1]=col;
									}
									if(bestVal < beta)
										beta = bestVal;
									if(beta <= alpha)
										flag=1;

							}
						}
					}
					
				}		
				best[2]=bestVal;
			return best;
		}
	}
}

