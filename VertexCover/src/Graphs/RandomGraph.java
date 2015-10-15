package Graphs;
import java.util.Random;


public class RandomGraph extends Graph{
	
	static double edgeProbability = 0.05;
	
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
					this.addEdge(new Edge(this.getVertices().get(i), this.getVertices().get(j)));
				}
			}
		}
	}
		
		
	private int[][] generateRandomGraph(int size){
		Random r = new Random();
		int[][] adjm = new int[size][size];
		
		for(int i=0; i<size; i++){
			for(int j=0; j<i; j++){
				if(r.nextDouble() <= edgeProbability){
					adjm[i][j] = 1;
				}else{
					adjm[i][j] = 0;
				}
			}
		}
		return adjm;
	}
	
	public static Double getEdgeProbability(){
		return edgeProbability;
	}
	
}
