import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;


public class VCviaBFS {

	ArrayList<Vertex> verts;
	ArrayList<Vertex> res = new ArrayList<Vertex>();
	
	/* the ansatz for DFS does not work for BFS since the leafs in the tree can be connected.
	 * we alternatingly choose nodes in the BFS-tree to be part of VC.
	 * The root is part of VC.
	 * If a node should not part of VC but it has a neighbor which is also not part we make that node part of VC.
	 */
	public int solve(Graph g){
		res = new ArrayList<Vertex>();
		BFS(g);
		makeValidVC();
		return res.size();
	}
	
	//mark every other node starting with the root
	private void BFS(Graph g){
		verts = g.copyVertices();
		int currentdist = 0;
		Queue<Vertex> queue = new LinkedList<Vertex>();
		
		Vertex root = g.vertices.get(0);
		queue.add(root);
		root.visited = true;
		res.add(root);
		
		while(!queue.isEmpty()){
			Vertex v = queue.poll();
			if(currentdist == v.dist){
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
			if(currentdist < v.dist){
				currentdist = currentdist +1;
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
			if(queue.isEmpty() && !allVisited()){
				for(Vertex w: verts){
					if(!w.visited){
						queue.add(w);
						w.visited = true;
						res.add(w);
						currentdist = 0;
						break;
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
