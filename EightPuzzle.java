import java.util.*;
class State {
	int [][] curr_state=new int[3][3];
	int level;
	public State(int[][]curr_state,int level) {
		this.curr_state=curr_state;
		this.level=level;
	}
	public String toString() {
	String print="\nState : \n";
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				print+=this.curr_state[i][j]+" ";
			}
			print+="\n";
		}
		print+="\nLevel : "+this.level+"\t Done printing!";
		return print;
	}
}
public class EightPuzzle {

	static int initial_state[][]={{1,0,3},{4,2,5},{7,8,6}};
	static int goal_state[][]={{1,2,3},{4,5,6},{7,8,0}};
	static ArrayList<String> visited=new ArrayList<>();
	static boolean goalFound=false;
	public static void main(String args[]) {
		System.out.println("\nInitial state:\n");
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				System.out.print(initial_state[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println("\nGoal state:\n");
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				System.out.print(goal_state[i][j]+" ");
			}
			System.out.println();
		}
		if(isGoal(initial_state))
			System.out.println("Goal found!!");
		else 
			System.out.println("Goal not found!!");
		System.out.println("\n\n---DFS STARTS---");
		dfs(new State(initial_state,0));
		visited.clear();
		goalFound=false;
		System.out.println("\n\n---IDS LEVEL 0 STARTS---");
		ids(new State(initial_state,0),0);
		visited.clear();
		goalFound=false;
		System.out.println("\n\n---IDS LEVEL 1 STARTS---");
		ids(new State(initial_state,0),1);
		visited.clear();
		goalFound=false;
		System.out.println("\n\n---IDS LEVEL 2 STARTS---");
		ids(new State(initial_state,0),2);
		visited.clear();
		goalFound=false;
		System.out.println("\n\n---IDS LEVEL 3 STARTS---");
		ids(new State(initial_state,0),3);
	}
	public static void dfs(State s) {
		if(isGoal(s.curr_state)) {
			System.out.println("\n****** DFS called for state ******"+s);
			System.out.println("***************** Goal Found!! ***************************");
			goalFound=true;
			return;
		}
		else if (!goalFound) {
			System.out.println("\n****** DFS called for state ******"+s);
			ArrayList<State> children=generateChildren(s);
			if(children==null)
				return; 
			System.out.println("Number of children generated for this state : "+children.size());
			visited.add(Arrays.deepToString(s.curr_state));		
			for (int i = 0; i < children.size(); i++) {
				State temp=children.get(i);
				if(temp.curr_state!=null && temp.level<4) {
					dfs(temp);
				}
			}
		}
	}
	public static void ids(State s,int level) {
		if(isGoal(s.curr_state)) {
			System.out.println("\n****** IDS called for state ******"+s);
			System.out.println("***************** Goal Found!! ***************************");
			goalFound=true;
			return;
		}
		else if (!goalFound) {
			System.out.println("\n****** IDS called for state ******"+s);
			ArrayList<State> children=generateChildren(s);
			if(children==null)
				return;
			System.out.println("Number of children generated for this state : "+children.size());
			visited.add(Arrays.deepToString(s.curr_state));		
			for (int i = 0; i < children.size(); i++) {
				State temp=children.get(i);
				if(temp.curr_state!=null && temp.level<=level) {
					ids(temp,level);
				}
			}
		}
	}
	public static ArrayList<State> generateChildren(State s) {
		ArrayList<State> children=new ArrayList<>();
		s.level++;
		if(s.level==4)
			return null;
		int [][] temp1=new int[3][3];
		int [][] temp2=new int[3][3];
		int [][] temp3=new int[3][3];
		int [][] temp4=new int[3][3];
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				temp1[i][j]=s.curr_state[i][j];
				temp2[i][j]=s.curr_state[i][j];
				temp3[i][j]=s.curr_state[i][j];
				temp4[i][j]=s.curr_state[i][j];
			}
		}

		if((temp1=move_left(temp1))==null) {
			System.out.println("No child added on left!");
		} else {
			if(!visited.contains(Arrays.deepToString(temp1))) {
				children.add(new State(temp1,s.level));
				System.out.println("Child added on left!"+"\nSize of Children ArrayList : "+children.size());
			}
			else {
				System.out.println("Left child is already visited!!!");
			}
		}		
		if((temp2=move_right(temp2))==null) {
			System.out.println("No child added on right!");
		} else {
			if(!visited.contains(Arrays.deepToString(temp2))) {
				children.add(new State(temp2,s.level));
				System.out.println("Child added on right!"+"\nSize of Children ArrayList : "+children.size());
			}
			else {
				System.out.println("Right child is already visited!!!");
			}
		}
		if((temp3=move_up(temp3))==null) {
			System.out.println("No child added on up!");
		} else {
			if(!visited.contains(Arrays.deepToString(temp3))) {
				children.add(new State(temp3,s.level));
				System.out.println("Child added on up!"+"\nSize of Children ArrayList : "+children.size());
			}
			else {
				System.out.println("Up child is already visited!!!");
			}
		}
		if((temp4=move_down(temp4))==null){
			System.out.println("No child added on down!");
		} else {
			if(!visited.contains(Arrays.deepToString(temp4))) {
				children.add(new State(temp4,s.level));
				System.out.println("Child added on down!"+"\nSize of Children ArrayList : "+children.size());
			}
			else {
				System.out.println("Down child is already visited!!!");
			}
		}
		System.out.println("Children generated : ");
		for(State c : children) {
			System.out.println(c);
		}
		return children;		
	}
	// TO FIND CURRENT POSITION OF 0
	public static int[] findBlankTile(int temp[][]) {
		System.out.println("Call to find blank tile!\nInput matrix : ");
		for(int i=0;i<temp.length;i++) {
			for(int j=0;j<temp[i].length;j++) {
				System.out.print(temp[i][j]+" ");
			}
			System.out.println();
		}
		int [] position = {-1,-1};
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				if(temp[i][j]==0) {
					position[0]=i;
					position[1]=j;
					break;
				}	
			}			
		}
		System.out.println("Position of blank tile : "+position[0]+position[1]);
		return position;
	}
	public static int [][] move_up(int [][] temp) {		
		int position[]=findBlankTile(temp);
		System.out.println("Call to move up!\nInput matrix : ");
		for(int i=0;i<temp.length;i++) {
			for(int j=0;j<temp[i].length;j++) {
				System.out.print(temp[i][j]+" ");
			}
			System.out.println();
		}
		int row=position[0];
		int col=position[1];
		if(row==0) {
			System.out.println("Cannot go further up!");
			return null;
		}
			
		else {
			int t=temp[row][col];
			temp[row][col]=temp[row-1][col];
			temp[row-1][col]=t;
		}
		System.out.println("Ouput matrix : ");
		for(int i=0;i<temp.length;i++) {
			for(int j=0;j<temp[i].length;j++) {
				System.out.print(temp[i][j]+" ");
			}
			System.out.println();
		}
		return temp;
	}

	public static int[][] move_down(int [][] temp) {
		int [] position=findBlankTile(temp);
		System.out.println("Call to move down!\nInput matrix : ");
		for(int i=0;i<temp.length;i++) {
			for(int j=0;j<temp[i].length;j++) {
				System.out.print(temp[i][j]+" ");
			}
			System.out.println();
		}
		int row=position[0];
		int col=position[1];
		if(row==2) {
			System.out.println("Cannot go further down!");
			return null;
		}
		else {
			int t=temp[row][col];
			temp[row][col]=temp[row+1][col];
			temp[row+1][col]=t;
		}
		System.out.println("Ouput matrix : ");
		for(int i=0;i<temp.length;i++) {
			for(int j=0;j<temp[i].length;j++) {
				System.out.print(temp[i][j]+" ");
			}
			System.out.println();
		}
		return temp;
	}

	public static int[][] move_left(int [][] temp) {
		int [] position=findBlankTile(temp);
		System.out.println("Call to move left!\nInput matrix : ");
		for(int i=0;i<temp.length;i++) {
			for(int j=0;j<temp[i].length;j++) {
				System.out.print(temp[i][j]+" ");
			}
			System.out.println();
		}
		int row=position[0];
		int col=position[1];
		if(col==0) {
			System.out.println("Cannot go further left!");
			return null;
		}
		else {
			int t=temp[row][col];
			temp[row][col]=temp[row][col-1];
			temp[row][col-1]=t;
		}
		System.out.println("Ouput matrix : ");
		for(int i=0;i<temp.length;i++) {
			for(int j=0;j<temp[i].length;j++) {
				System.out.print(temp[i][j]+" ");
			}
			System.out.println();
		}
		return temp;
	}

	public static int [][] move_right(int [][] temp) {
		int [] position=findBlankTile(temp);
		System.out.println("Call to move right!\nInput matrix : ");
		for(int i=0;i<temp.length;i++) {
			for(int j=0;j<temp[i].length;j++) {
				System.out.print(temp[i][j]+" ");
			}
			System.out.println();
		}
		int row=position[0];
		int col=position[1];
		if(col==2) {
			System.out.println("Cannot go further right!");
			return null;
		}
		else {
			int t=temp[row][col];
			temp[row][col]=temp[row][col+1];
			temp[row][col+1]=t;
		}
		System.out.println("Ouput matrix : ");
		for(int i=0;i<temp.length;i++) {
			for(int j=0;j<temp[i].length;j++) {
				System.out.print(temp[i][j]+" ");
			}
			System.out.println();
		}
		return temp;
	}

	public static boolean isGoal(int curr_state[][]) {
		String input=Arrays.deepToString(curr_state);
		String goal=Arrays.deepToString(goal_state);
		if(input.equals(goal))
			return true;
		else
			return false;
	}
}