package Graphs;
//import java.util.ArrayList;


public class GraphAdj {
	
	private int[][] adjmat;
	
	public GraphAdj(int size){
		adjmat = new int[size][size];
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				adjmat[i][j] = 0;
			}
 		}
	}
	
	public void addEdge(int v, int w){
		adjmat[v][w] = 1;
		adjmat[w][v] = 1;
	}
	
	public void removeEdge(int v, int w){
		adjmat[v][w] = 0;
		adjmat[w][v] = 0;
	}
	
	
}

