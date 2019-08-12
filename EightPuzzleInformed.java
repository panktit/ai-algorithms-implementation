// FOR GBFS, USE “return this.hon” in getCost METHOD
import java.util.*;

class State implements Comparable<State> {   // FOR BFS, DON'T WRITE IMPLEMENTS HERE & DON'T INCLUDE COSTS IN CLASS
	int[][] curr_state = new int[3][3];
	int level;
	int gon;
	int hon;
	int cost;

	public State(int[][] curr_state, int level, int gon, int hon) {
		this.curr_state = curr_state;
		this.level = level;
		this.gon = gon;
		this.hon = hon;
		this.cost=this.gon+this.hon;
	}

	public int getCost() {
		return this.cost;
	}

	public String toString() {
		String print = "\nState : \n";
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				print += this.curr_state[i][j] + " ";
			}
			print += "\n";
		}
		print += "\nLevel : " + this.level + "\ng(n) : " + gon + "\nh(n) : " + hon+"\nCost : "+this.cost;
		return print;
	}

	public int compareTo(State s) {   // FOR BFS, DON'T WRITE THIS METHOD
		if (this.getCost() > s.getCost())
			return 1;
		else if (this.getCost() < s.getCost())
			return -1;
		else
			return 0;
	}
}

class EightPuzzleInformed {
	static int initial_state[][] = { { 1, 0, 3 }, { 4, 2, 5 }, { 7, 8, 6 } };
	static int goal_state[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
	static ArrayList<String> visited = new ArrayList<>();
	static PriorityQueue<State> stateSpace;  // FOR BFS, USE "static Queue<State> stateSpace;"
	static boolean goalFound = false;
	static ArrayList<String> path;
	static int totalCost=0;
	static int maxLevels=5;

	public static void main(String[] args) {
		stateSpace = new PriorityQueue<>(); // FOR BFS, USE "stateSpace = new LinkedList<>();"
		path = new ArrayList<>();
		System.out.println("\nInitial state:\n");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(initial_state[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("\nGoal state:\n");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(goal_state[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("\n\n--- A* STARTS ---");
		aStar(new State(initial_state, 0, 0, findGoalCost(initial_state)));
		System.out.println("\n--- A* ENDS ---");
		if(!goalFound)
			System.out.println("\nCould not find Goal State in "+maxLevels+" levels!!!");
		else {
			System.out.println("\n\n--- A* PATH ---");
			for (String s : path)
				System.out.print(s+" -> ");
			System.out.print("goal!!");
			System.out.println("\n\nTotal cost of reaching the Goal State : "+totalCost);
		}
			
	}

	public static void aStar(State s) {
		stateSpace.add(s);
		visited.add(Arrays.deepToString(s.curr_state));
		while (!stateSpace.isEmpty()||!goalFound) {
			State temp = stateSpace.poll();
			System.out.println("\n----- Next state chosen ----" + temp);
			path.add(Arrays.deepToString(temp.curr_state));
			totalCost+=temp.getCost();
			System.out.println("\nCost till now : "+totalCost+"\n");
			if(isGoal(temp.curr_state)) {
				System.out.println("\n****** GOAL FOUND!!! ******");
				goalFound=true;
				break;
			}
			ArrayList<State> children = generateChildren(temp);
			if (children == null)
				return;
			// ADDING GENERATED CHILDREN TO STATE SPACE
			for (int i = 0; i < children.size(); i++) {
				State child = children.get(i);
				if (child.curr_state != null) {
					stateSpace.add(child);
					visited.add(Arrays.deepToString(child.curr_state));
				}
			}
			System.out.println("\nCurrent State Space : ");
			for(State st : stateSpace) 
				System.out.println(st);
		}
	}

	public static int findGoalCost(int[][] node) {
		int count = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (node[i][j] != goal_state[i][j])
					count++;
			}
		}
		return count;
	}

	public static ArrayList<State> generateChildren(State s) {
		ArrayList<State> children = new ArrayList<>();
		s.level++;		
		if (s.level == maxLevels)
			return null;
		int[][] temp1 = new int[3][3];
		int[][] temp2 = new int[3][3];
		int[][] temp3 = new int[3][3];
		int[][] temp4 = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				temp1[i][j] = s.curr_state[i][j];
				temp2[i][j] = s.curr_state[i][j];
				temp3[i][j] = s.curr_state[i][j];
				temp4[i][j] = s.curr_state[i][j];
			}
		}
		System.out.println("\nChildren generated of chosen state as : \n");
		if ((temp1 = move_left(temp1)) == null) {
			System.out.println("No child added on left!");
		} else {
			if (!visited.contains(Arrays.deepToString(temp1))) {
				children.add(new State(temp1, s.level, s.level, findGoalCost(temp1)));
				System.out.println("Child added on left!" + "\nSize of Children ArrayList : " + children.size());
			} else {
				System.out.println("Left child is already visited!!!");
			}
		}
		if ((temp2 = move_right(temp2)) == null) {
			System.out.println("No child added on right!");
		} else {
			if (!visited.contains(Arrays.deepToString(temp2))) {
				children.add(new State(temp2, s.level, s.level, findGoalCost(temp2)));
				System.out.println("Child added on right!" + "\nSize of Children ArrayList : " + children.size());
			} else {
				System.out.println("Right child is already visited!!!");
			}
		}
		if ((temp3 = move_up(temp3)) == null) {
			System.out.println("No child added on up!");
		} else {
			if (!visited.contains(Arrays.deepToString(temp3))) {
				children.add(new State(temp3, s.level, s.level, findGoalCost(temp3)));
				System.out.println("Child added on up!" + "\nSize of Children ArrayList : " + children.size());
			} else {
				System.out.println("Up child is already visited!!!");
			}
		}
		if ((temp4 = move_down(temp4)) == null) {
			System.out.println("No child added on down!");
		} else {
			if (!visited.contains(Arrays.deepToString(temp4))) {
				children.add(new State(temp4, s.level, s.level, findGoalCost(temp4)));
				System.out.println("Child added on down!" + "\nSize of Children ArrayList : " + children.size());
			} else {
				System.out.println("Down child is already visited!!!");
			}
		}
		return children;
	}

	// TO FIND CURRENT POSITION OF 0
	public static int[] findBlankTile(int temp[][]) {
		int[] position = { -1, -1 };
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (temp[i][j] == 0) {
					position[0] = i;
					position[1] = j;
					break;
				}
			}
		}
		return position;
	}

	public static int[][] move_up(int[][] temp) {
		int position[] = findBlankTile(temp);
		System.out.println("Call to move up!");
		int row = position[0];
		int col = position[1];
		if (row == 0) {
			System.out.println("Cannot go further up!");
			return null;
		}

		else {
			int t = temp[row][col];
			temp[row][col] = temp[row - 1][col];
			temp[row - 1][col] = t;
		}
		return temp;
	}

	public static int[][] move_down(int[][] temp) {
		int[] position = findBlankTile(temp);
		System.out.println("Call to move down!");
		int row = position[0];
		int col = position[1];
		if (row == 2) {
			System.out.println("Cannot go further down!");
			return null;
		} else {
			int t = temp[row][col];
			temp[row][col] = temp[row + 1][col];
			temp[row + 1][col] = t;
		}
		return temp;
	}

	public static int[][] move_left(int[][] temp) {
		int[] position = findBlankTile(temp);
		System.out.println("Call to move left!");
		int row = position[0];
		int col = position[1];
		if (col == 0) {
			System.out.println("Cannot go further left!");
			return null;
		} else {
			int t = temp[row][col];
			temp[row][col] = temp[row][col - 1];
			temp[row][col - 1] = t;
		}
		return temp;
	}

	public static int[][] move_right(int[][] temp) {
		int[] position = findBlankTile(temp);
		System.out.println("Call to move right!");
		int row = position[0];
		int col = position[1];
		if (col == 2) {
			System.out.println("Cannot go further right!");
			return null;
		} else {
			int t = temp[row][col];
			temp[row][col] = temp[row][col + 1];
			temp[row][col + 1] = t;
		}
		return temp;
	}

	public static boolean isGoal(int curr_state[][]) {
		String input = Arrays.deepToString(curr_state);
		String goal = Arrays.deepToString(goal_state);
		if (input.equals(goal))
			return true;
		else
			return false;
	}
}