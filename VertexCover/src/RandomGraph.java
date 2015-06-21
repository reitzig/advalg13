import java.util.Random;


public class RandomGraph extends Graph{
	
	public RandomGraph(int size){
		
		
		int[][] adjm = generateRandomGraph(size);
		
		generateVertices(size);
		generateEdges(adjm);
		
	}
	
	private void generateVertices(int size){
		for(int i=0; i<size; i++){
			this.addVertex(new Vertex());
		}
	}
	
	private void generateEdges(int[][] adjm){
		for(int i=0; i<adjm.length; i++){
			for(int j=0; j<i; j++){
				if(adjm[i][j] == 1){
					this.addEdge(new Edge(this.vertices.get(i), this.vertices.get(j)));
				}
			}
		}
	}
		
		
	private int[][] generateRandomGraph(int size){
//		Random r2 = new Random();
//		Integer var = r2.nextInt(Integer.MAX_VALUE);
//		System.out.println(Integer.toHexString(var));
		Random r = new Random();
		int[][] adjm = new int[size][size];
		
		for(int i=0; i<size; i++){
			for(int j=0; j<i; j++){
				if(r.nextDouble() >= 0.5){
					adjm[i][j] = 1;
				}else{
					adjm[i][j] = 0;
				}
			}
		}
		return adjm;
	}
	
	
}
