package VCAlgs;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;

import Graphs.Graph;
import Graphs.Vertex;


public class VCviaBFS {

	ArrayList<Vertex> verts;
	ArrayList<Vertex> res = new ArrayList<Vertex>();
	
	/* the approach for DFS does not work for BFS since the leafs in the tree can be connected.
	 * we choose every other node in the BFS-tree to be part of VC.
	 * The root is part of VC.
	 * If a node should not be part of VC but it has a neighbor which is also not part we make that node part of VC.
	 */
	public int solve(Graph g){
		res = new ArrayList<Vertex>();
		//take every other node in the tree
		BFS(g);
		//complete VC
		makeValidVC();
		//reset visit markers
		g.reset();
		return res.size();
	}
	
	//mark every other node starting with the root
	private void BFS(Graph g){
		verts = g.copyVertices();
		int currentdist = 0;
		Queue<Vertex> queue = new LinkedList<Vertex>();
		
		Vertex root = g.getVertices().get(0);
		queue.add(root);
		root.visited = true;
		res.add(root);
		
		while(!(queue.isEmpty() && allVisited())){
			//global controle for disconnected graphs
			if(queue.isEmpty() && !allVisited()){
				for(Vertex w: verts){
					if(!w.visited){
						queue.add(w);
						w.visited = true;
						res.add(w);
						currentdist = 0;
					}
				}
				
			} else {
				Vertex v = queue.poll();
				//check if we get to another height level
				if(currentdist < v.dist){
					currentdist = currentdist +1;
				}
				for(Vertex w: v.adj()){
					if(!w.visited){
						w.visited = true;
						queue.add(w);	
						w.dist = currentdist + 1;
						if((w.dist % 2) == 0){
							res.add(w);
						}
					}
				}
			}
		}
	}
	
	private boolean allVisited() {
		boolean res = true;
		for(Vertex v: verts){
			if(!v.visited){
				res= false;
			}
		}
		return res;
	}
	
	//adds nodes to form a valid VC	
	private void makeValidVC(){
		for(Vertex v: res){
			verts.remove(v);
		}
		for(Vertex v: verts){
			for(Vertex w: v.adj()){
				if(!res.contains(w)){
					res.add(v);
					break;
				}
			}
		}
	}
	
}
