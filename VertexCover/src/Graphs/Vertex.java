package Graphs;
import java.util.ArrayList;


public class Vertex {
	
	public ArrayList<Edge> inc;
	public boolean visited = false;
	public int dist = 0;

	public Vertex(){
		inc = new ArrayList<Edge>();
	}
	
	public Vertex(boolean visited){
		inc = new ArrayList<Edge>();
		this.visited = visited;
	}
	
	//create adjacency list on the base of incidence list
	public ArrayList<Vertex> adj(){
		ArrayList<Vertex> adj = new ArrayList<Vertex>();
		for(Edge e: inc){
			if(!e.v.equals(this)){
				adj.add(e.v);
			}
			if(!e.w.equals(this)){
				adj.add(e.w);
			}
		}
		return adj;
	}	
	
	// return the degree (being the size of the incidence list)
	public int deg(){
		return inc.size();
	}

}
