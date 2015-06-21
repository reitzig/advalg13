import java.util.ArrayList;


public class Graph {

	ArrayList<Vertex> vertices;
	ArrayList<Edge> edges;
	
	public Graph(){
		vertices = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
	}
	
	public Graph(int size){
		for(int i = 0; i < size; i++){
			addVertex(new Vertex());
		}
	}
		
	public Graph(ArrayList<Vertex> vertices, ArrayList<Edge> edges){
		this.vertices = vertices;
		this.edges = edges;
	}
	
	
	public void addEdge(Edge e){
		//check whether it's already there
		boolean check = true;
		for(Edge ed: edges){
			if(ed.v.equals(e.v) && ed.w.equals(e.w)){
				check = false;
			}
			if(ed.w.equals(e.v) && ed.v.equals(e.w)){
				check = false;
			}
		}
		if(check){
			edges.add(e);
			e.v.inc.add(e);
			e.w.inc.add(e);
		}
	}
	
	public void removeEdge(Edge e){
		edges.remove(e);
		e.v.inc.remove(e);
		e.w.inc.remove(e);
	}
	
	public void addVertex(Vertex v){
		vertices.add(v);
	}
	
	public void removeVertex(Vertex v){
		vertices.remove(v);
		ArrayList<Edge> inc = copyList(v.inc);
		for(Edge e: inc){
			removeEdge(e);
		}
	}
	
	private ArrayList<Edge> copyList(ArrayList<Edge> inc){
		ArrayList<Edge> inc2 = new ArrayList<Edge>();
		for(Edge e: inc){
			inc2.add(e);
		}
		return inc2;
	}
	
	ArrayList<Vertex> copyVertices(){
		ArrayList<Vertex> verts = new ArrayList<Vertex>();
		for(Vertex v: vertices){
			verts.add(v);
		}
		return verts;
	}
	
}
