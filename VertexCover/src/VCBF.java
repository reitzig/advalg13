import java.util.ArrayList;


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
