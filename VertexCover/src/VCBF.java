import java.util.ArrayList;
import java.util.HashMap;


public class VCBF {
	
	public boolean solve(Graph g, Integer k){
		if(k <= 0){
			if(k == 0 && g.edges.isEmpty()){
				return true;
			}
			//System.out.println("k decreased under or to 0");
			return false;
		}
		if(k>=0 && g.edges.isEmpty()){
			//System.out.println("g has no more edges");
			return true;
		}
		if(k >= g.vertices.size()){
			
			//System.out.println("k >= g");
			return true;
		}
		ArrayList<Vertex> verts = copyList(g.vertices);
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
	
	public boolean solve2(Graph g, Integer k){
		if(k <= 0){
			if(k == 0 && g.edges.isEmpty()){
				return true;
			}
			//System.out.println("k decreased under or to 0");
			return false;
		}
		if(k>=0 && g.edges.isEmpty()){
			//System.out.println("g has no more edges");
			return true;
		}
		if(k >= g.vertices.size()){
			
			//System.out.println("k >= g");
			return true;
		}
		/*take arbitrary vertex and branch between itself and its neighborhood*/
		boolean erg = false;
		ArrayList<Vertex> verts = copyList(g.vertices);
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
				//System.out.println("2. branch: k = " + (k-xN.size()));
				erg = solve2(g,k-xN.keySet().size());
				
				//restoring g
				g.addVertex(x);
				for(Vertex v: xN.keySet()){
					g.addVertex(v);
	//				g.addEdge(new Edge(x,v));
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
	
	public static void main(String[] args){
		long starttime =  System.currentTimeMillis();
		VCBF vcbf = new VCBF();
		ArrayList<Boolean> ergs = new ArrayList<Boolean>();
		
		for(int i = 0; i < 1; i++){
			Graph g = new RandomGraph(12);
			boolean erg = vcbf.solve(g,7);
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
