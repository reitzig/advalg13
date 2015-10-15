package VCAlgs;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import Graphs.Graph;
import Graphs.RandomGraph;
import Graphs.Vertex;



public class VCviaDFS {
	
	Stack<Vertex> s = new Stack<Vertex>();
	ArrayList<Vertex> verts;

	public int solve(Graph g){
		verts = g.copyVertices();
		s.push(verts.get(0));
		ArrayList<Vertex> res = computeDFS(g);
		g.reset();
		return res.size();
	}
	
	//compute DFS Tree without leaves
	private ArrayList<Vertex> computeDFS(Graph g){
		ArrayList<Vertex> res = new ArrayList<Vertex>();
		while(!s.isEmpty()){
			Vertex cv = s.pop();
			if(!cv.visited){
				cv.visited = true;
				verts.remove(cv);
				if(!isLeaf(cv)){
					res.add(cv);
				}
				for(Vertex v: cv.adj()){
					s.push(v);
				}
			}
			if(s.isEmpty() && !verts.isEmpty()){
				s.push(verts.get(0));
			}
			
		}
		return res;
	}

	//checks if vertex is a leaf (i.e. all his neighbors have already been visited)
	private boolean isLeaf(Vertex cv) {
		boolean res = true;
		for(Vertex v: cv.adj()){
			if(!v.visited){
				res = false;
			}
		}
		return res;
	}

	
	public static void main(String[] args){
		Graph g = new RandomGraph(100);
		//VC via DFS
		VCviaDFS solver = new VCviaDFS();
		int r = solver.solve(g);
		System.out.println(r);
		
		g.reset();
		
		//VC via BFSs
		VCviaBFS solver2 = new VCviaBFS();
		int r2 = solver2.solve(g);
		System.out.println(r2);
		
		
		VCDBS vcdbs = new VCDBS();
//		for(int k = 0; k<100; k++){
//			if(vcdbs.solve(g, k)){
//				System.out.println(k);
//				break;
//			}
//		}
		System.out.println(vcdbs.solve(g, r2));
	}
}