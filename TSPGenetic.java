import java.util.*;
class Chromosome {
	int [] path;
	int cost;
	public Chromosome(int [] path, int cost) {
		this.path=path;
		this.cost=cost;
	}
	public String toString() {
		String temp="";
		for(int i=0;i<5;i++)
			temp+=this.path[i]+1+" -> ";
		temp=temp.substring(0,temp.length()-4);
		temp+="\tCost : "+this.cost;
		return temp;
	}
	public int getCost() {
		return this.cost;
	}
}
class TSPGenetic {
	static int [][] graph={{0,10,15,20},{10,0,35,25},{15,35,0,30},{20,25,30,0}};
	static ArrayList<Chromosome> population;
	static ArrayList<Chromosome> selectedPopulation;
	static int threshold;
	public static void main(String[] args) {
		System.out.println("\nGraph : \n");
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				System.out.println("Node ["+(i+1)+"] ["+(j+1)+"] : "+graph[i][j]);
			}
			System.out.println();
		}
		population=new ArrayList<>();
		selectedPopulation=new ArrayList<>();
		for(int i=0;i<8;i++) {
			int path[]=generatePopulation();
				population.add(new Chromosome(path,getCost(path)));
        }			
		System.out.println("Initial Population : \n");
		for(Chromosome c: population)
			System.out.println(c);
		selection();
		System.out.println("\nSelected Population : \n");
		for(Chromosome c: selectedPopulation)
			System.out.println(c);
		selectedPopulation.sort(Comparator.comparingInt(Chromosome :: getCost));
		crossOver();
		// UPDATE COST
		for(Chromosome c: selectedPopulation)
			c.cost=getCost(c.path);
		selectedPopulation.sort(Comparator.comparingInt(Chromosome :: getCost));
		System.out.println("\nCrossover Population : \n");
		for(Chromosome c: selectedPopulation)
			System.out.println(c);
		mutate();
		// UPDATE COST
		for(Chromosome c: selectedPopulation)
			c.cost=getCost(c.path);
		System.out.println("\nMutated Population : \n");
		for(Chromosome c: selectedPopulation)
			System.out.println(c);
		selectedPopulation.sort(Comparator.comparingInt(Chromosome :: getCost));
		System.out.println("\nResult at the end of first generation : \n\n"+selectedPopulation.get(0));	
	}
	public static int[] generatePopulation() {
		Random r=new Random();
		int [] path=new int[5];
		int count=0;
		ArrayList<Integer> random=new ArrayList<>();
		int temp=r.nextInt(4);
		int prev=temp;
		random.add(temp);
		path[0]=temp;		
		while(count<3)  {
			temp=r.nextInt(4);
			while(random.contains(temp))
				temp=r.nextInt(4);
			random.add(temp);
			path[count+1]=temp;
			prev=temp;
			count++;
		}
		path[4]=path[0];
		return path;
	}
	public static int getCost(int [] path) {
        int totalCost=0;
        for(int j=0;j<4;j++)
            totalCost+=graph[path[j]][path[j+1]];
        return totalCost;
	}

	public static void selection() {
		Scanner sc=new Scanner(System.in);
		System.out.println("\nEnter threshold value : ");
		threshold=sc.nextInt();
		for(Chromosome c: population) {
			if(c.cost<=threshold)
				selectedPopulation.add(c);
		}
	}

	public static void crossOver () {
		Random r=new Random();
		if(selectedPopulation.size()>1) {
			if(selectedPopulation.size()%2==0) {
				for(int i=0;i<selectedPopulation.size();i++) {
					// TO GENERATE RANDOM NUMBERS IN A RANGE FROM 1-4  r.nextInt((max - min) + 1) + min;
					int ratio=r.nextInt((4 - 1) + 1) + 1; 
					System.out.println("Cross-over Ration : "+ratio+" : "+(5-ratio));
					for(int j=0;j<selectedPopulation.get(i).path.length-1;j++) {
						if(j<ratio) {
							continue;
						} else {
							int temp=selectedPopulation.get(i).path[j];
							selectedPopulation.get(i).path[j]=selectedPopulation.get(i+1).path[j];
							selectedPopulation.get(i+1).path[j]=temp;
						}
					}
					selectedPopulation.get(i).path[4]=selectedPopulation.get(i).path[0];
					selectedPopulation.get(i+1).path[4]=selectedPopulation.get(i+1).path[0];
					i++;
				}
			} else {
				for(int i=0;i<selectedPopulation.size()-1;i++) {
					// TO GENERATE RANDOM NUMBERS IN A RANGE FROM 1-4  r.nextInt((max - min) + 1) + min;
					int ratio=r.nextInt((4 - 1) + 1) + 1; 
					System.out.println("Cross-over Ration : "+ratio+" : "+(5-ratio));
					for(int j=0;j<selectedPopulation.get(i).path.length-1;j++) {
						if(j<ratio) {
							continue;
						} else {
							int temp=selectedPopulation.get(i).path[j];
							selectedPopulation.get(i).path[j]=selectedPopulation.get(i+1).path[j];
							selectedPopulation.get(i+1).path[j]=temp;
						}
					}
					selectedPopulation.get(i).path[4]=selectedPopulation.get(i).path[0];
					selectedPopulation.get(i+1).path[4]=selectedPopulation.get(i+1).path[0];
					i++;
				}
				// FOR LAST SET CROSS OVER WITH SECOND-LAST SET IN CASE OF ODD NUMBER OF SELECTED POPULATION
				int size=selectedPopulation.size();
				int ratio=r.nextInt((4 - 1) + 1) + 1;
				System.out.println("Cross-over Ration : "+ratio+" : "+(5-ratio)); 
				for(int j=0;j<selectedPopulation.get(size-1).path.length-1;j++) {
					if(j<ratio) {
						continue;
					} else {
						int temp=selectedPopulation.get(size-2).path[j];
						selectedPopulation.get(size-2).path[j]=selectedPopulation.get(size-1).path[j];
						selectedPopulation.get(size-1).path[j]=temp;
					}
				}
			}
		} else {
			System.out.println("Nothing to crossover!!");
		}
	}
	public static void mutate() {
		System.out.println();
		for(Chromosome c : selectedPopulation) {
			Random r = new Random();
			int pos=r.nextInt((3) + 1);
			int node=r.nextInt((3) + 1);
			System.out.println("Mutation at : "+(pos+1)+" and mutated value : "+(node+1));
			c.path[pos]=node;
			c.path[4]=c.path[0];
		}
	}
}