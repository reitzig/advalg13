package Graphs;
import java.util.ArrayList;


public class Graph {

	private ArrayList<Vertex> vertices;
	private ArrayList<Edge> edges;
	
	public Graph(){
		setVertices(new ArrayList<Vertex>());
		setEdges(new ArrayList<Edge>());
	}
	
	public Graph(int size){
		for(int i = 0; i < size; i++){
			addVertex(new Vertex());
		}
	}
		
	public Graph(ArrayList<Vertex> vertices, ArrayList<Edge> edges){
		this.setVertices(vertices);
		this.setEdges(edges);
	}
	
	
	public void addEdge(Edge e){
		//check whether it's already there
		boolean check = true;
		for(Edge ed: getEdges()){
			if(ed.v.equals(e.v) && ed.w.equals(e.w)){
				check = false;
			}
			if(ed.w.equals(e.v) && ed.v.equals(e.w)){
				check = false;
			}
		}
		if(check){
			getEdges().add(e);
			e.v.inc.add(e);
			e.w.inc.add(e);
		}
	}
	
	public void removeEdge(Edge e){
		getEdges().remove(e);
		e.v.inc.remove(e);
		e.w.inc.remove(e);
	}
	
	public void addVertex(Vertex v){
		getVertices().add(v);
	}
	
	public void removeVertex(Vertex v){
		getVertices().remove(v);
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
	
	public ArrayList<Vertex> copyVertices(){
		ArrayList<Vertex> verts = new ArrayList<Vertex>();
		for(Vertex v: getVertices()){
			verts.add(v);
		}
		return verts;
	}

	public void reset() {
		for(Vertex v: getVertices()){
			v.dist = 0;
			v.visited = false;
		}
		
	}
	
	public void print() {
		int x = 0;
		for(Vertex v: getVertices()){
			System.out.println(x);
			x++;
		}
		for(Edge e: getEdges()){
			int v = getVertices().indexOf(e.v);
			int w = getVertices().indexOf(e.w);
			System.out.println(v + "-" + w);
		}
		
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}

	public ArrayList<Vertex> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<Vertex> vertices) {
		this.vertices = vertices;
	}
	
}
