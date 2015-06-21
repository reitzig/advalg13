import java.util.ArrayList;


public class Vertex {
	
	public ArrayList<Edge> inc;

	public Vertex(){
		inc = new ArrayList<Edge>();
	}
	
	//erstellen der Adjazenzliste aufgrund der Inzidenzliste
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
	
	public int deg(){
		return inc.size();
	}

}
