package VCAlgs;
import java.util.ArrayList;
import java.util.HashMap;

import Graphs.Edge;
import Graphs.Graph;
import Graphs.RandomGraph;
import Graphs.Vertex;


public class VCBF {
	
	/* 
	 * erase a node (and take it to vc)
	 * brute force recursively on the remainder of the graph
	 * do this for every node
	*/
	public boolean solve(Graph g, Integer k){
		//Case: k is less or equal to 0
		if(k <= 0){
			if(k == 0 && g.getEdges().isEmpty()){
				return true;
			}
			return false;
		}
		// Case: G has no more edges to be covered -> VC exists
		if(k>=0 && g.getEdges().isEmpty()){
			return true;
		}
		//Case: k is larger than |V| -> VC exists
		if(k >= g.getVertices().size()){
			return true;
		}
		ArrayList<Vertex> verts = copyList(g.getVertices());
		for(Vertex v: verts){
			ArrayList<Vertex> vadj = new ArrayList<Vertex>();
			for(Vertex w: v.adj()){
				vadj.add(w);
			}
			g.removeVertex(v);
			if(solve(g,k-1)){
				return true;
			}
			g.addVertex(v);
			for(Vertex w: vadj){
				g.addEdge(new Edge(w,v));
			}
		}
		return false;
	}
	
	/*
	 * take arbitrary node and branch between itself and its neighborhood
	 * recurse on the remainder of the graph
	 */
	public boolean solve2(Graph g, Integer k){
		if(k <= 0){
			if(k == 0 && g.getEdges().isEmpty()){
				return true;
			}
			return false;
		}
		if(k>=0 && g.getEdges().isEmpty()){
			return true;
		}
		if(k >= g.getVertices().size()){
			return true;
		}
		/*take arbitrary vertex and branch between itself and its neighborhood*/
		boolean erg = false;
		ArrayList<Vertex> verts = copyList(g.getVertices());
		for(Vertex u: verts){
			ArrayList<Vertex> xadj = new ArrayList<Vertex>();
			Vertex x = u;
			
			//storing G
			for(Vertex v: x.adj()){
				xadj.add(v);
			}
			
			//modify G
			g.removeVertex(x);
			
			//compute VC
			//System.out.println("1. branch: k = " + (k-1));
			erg = solve2(g,k-1);
			
			//restoring g
			g.addVertex(x);
			for(Vertex v: xadj){
				g.addEdge(new Edge(x,v));
			}
			
			//branch to N(x) if VC still not found
			if(!erg){
				
				//storing G
				HashMap<Vertex, ArrayList<Vertex>> xN = new HashMap<Vertex, ArrayList<Vertex>>();
				for(Vertex v: x.adj()){
					ArrayList<Vertex> nV = new ArrayList<Vertex>();
					for(Vertex w:  v.adj()){
						nV.add(w);
					}
					xN.put(v,nV);
				}
				
				//modify G
				for(Vertex v: x.adj()){
					g.removeVertex(v);
				}
				g.removeVertex(x);
				
				//compute VC
				erg = solve2(g,k-xN.keySet().size());
				
				//restoring g
				g.addVertex(x);
				for(Vertex v: xN.keySet()){
					g.addVertex(v);
					for(Vertex w: xN.get(v)){
						g.addEdge(new Edge(v,w));
					}
				}
			}
		}
		return erg;
	}
	
	private ArrayList<Vertex> copyList(ArrayList<Vertex> verts){
		ArrayList<Vertex> verts2 = new ArrayList<Vertex>();
		for(Vertex v: verts){
			verts2.add(v);
		}
		return verts2;
	}
	
	/*
	 * runtime comparison between the two brute force approaches
	 */
	public static void main(String[] args){
		//Input
		Integer numberOfGraphs = 10;
		Integer graphsize = 13;
		int k = 6;

		//setup
		Graph[] gs = new Graph[numberOfGraphs];
		VCBF vcbf = new VCBF();
		
		for(int i=0; i < gs.length;i++){
			Graph g = new RandomGraph(graphsize);
			gs[i] = g;
		}
		
		
		
		long starttime1 =  System.currentTimeMillis();
		VCBF vcbf1 = new VCBF();
		ArrayList<Boolean> ergs1 = new ArrayList<Boolean>();
		
		for(int i = 0; i < gs.length; i++){
			Graph g = gs[i];
			boolean erg1 = vcbf1.solve(g,k);
			ergs1.add(erg1);
		}
			
		int yes1 = 0;
		int no1 = 0;
		for(Boolean b: ergs1){
			if(b){
				yes1++;
			}else{
				no1++;
			}
		}
		long endtime1 = System.currentTimeMillis();
		
		System.out.println(yes1 + "|" + no1 + " in " + (endtime1 - starttime1) + " mS");
		
		
		long starttime =  System.currentTimeMillis();
		ArrayList<Boolean> ergs = new ArrayList<Boolean>();
		
		for(int i = 0; i < gs.length; i++){
			Graph g = gs[i];
			boolean erg = vcbf.solve(g,k);
			ergs.add(erg);
		}
			
		int yes = 0;
		int no = 0;
		for(Boolean b: ergs){
			if(b){
				yes++;
			}else{
				no++;
			}
		}
		long endtime = System.currentTimeMillis();
		
		System.out.println(yes + "|" + no + " in " + (endtime - starttime) + " mS");
		
	}
}
