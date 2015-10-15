package VCAlgs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import DataWriter.CSVWriter;
import Graphs.Edge;
import Graphs.Graph;
import Graphs.RandomGraph;
import Graphs.Vertex;


public class VCDBS {
	
	private ArrayList<Vertex> vc = new ArrayList<Vertex>();
	
	//main method to decide whether VC of maximal size k exists
	public boolean solve(Graph g, Integer k){
		if(k <= 0){
			if(k == 0 && g.getEdges().isEmpty()){
				return true;
			}
			//System.out.println("k decreased under or to 0");
			return false;
		}
		if(k >= g.getVertices().size() || k>= g.getEdges().size()){
			//System.out.println("k >= g");
			return true;
		}
		if(c1(g,k)){
			//System.out.println("Case 1: node of deg 1");
			return p1(g,k);
		}
		else if(c2(g,k)){
			//System.out.println("Case 2: node of deg >= 5");
			return p2(g,k);
		}
		else if(c3(g,k)){
			//System.out.println("Case 3: regular");
			return p3(g,k);
		}
		else if(c4(g,k)){
			//System.out.println("Case 4: ...");
			return p4(g,k);
		}
		else if(c5(g,k)){
			//System.out.println("Case 5: ...");
			return p5(g,k);
		}
		else{
			System.out.println("Case 6: ...");
			g.print();
			System.exit(0);
			return p6(g,k);
		}
	}

	private boolean c1(Graph g, Integer k) {
		/* find node with degree one*/
		for(Vertex v: g.getVertices()){
			if(v.deg() == 1){
				return true;
			}
		}
		return false;
	}
	
	private boolean c2(Graph g, Integer k) {
		/*find vertex with deg() >= 5 */
		for(Vertex v: g.getVertices()){
			if(v.deg() >= 5){
				return true;
			}
		}
		return false;
	}

	private boolean c3(Graph g, Integer k) {
		/*look if g is regular*/
		boolean erg = true;
		int deg = -1;
		for(Vertex v: g.getVertices()){
			if(v.deg() != 0){
				deg = v.deg();
				break;
			}
		}
		for(Vertex v: g.getVertices()){
			if(v.deg() != deg && v.deg() != 0){
				erg = false;
			}
		}
		return erg;
	}

	private boolean c4(Graph g, Integer k) {
		/*If there is no node with deg = 1 or >5 but one with deg = 2 then do case distinction */
		for(Vertex v: g.getVertices()){
			if(v.deg() == 2){
				return true;
			}
		}
		return false;
	}
	
	private boolean c5(Graph g, Integer k) {
		/*if node with deg =3*/
		for(Vertex v: g.getVertices()){
			if(v.deg() == 3){
				return true;
			}
		}
		return false;
	}

	
	private boolean p1(Graph g, Integer k) {
		/* find node with deg()==1 and take its neighborhood */
		boolean erg = false;
		Vertex x = null;
		Vertex v = null;
		for(Vertex w: g.getVertices()){
			if(w.deg() == 1){
				x = w;
				v = w.adj().get(0);
			}
		}
		
		//set VC
		vc.add(v);
		
		//store G
		ArrayList<Vertex> vadj = new ArrayList<Vertex>();
		for(Vertex w: v.adj()){
			vadj.add(w);
		}
		
		//modify G
		g.removeVertex(x);
		g.removeVertex(v);
		
		//compute VC
		erg = solve(g,k-1);
		
		//restore G
		g.addVertex(x);
		g.addVertex(v);
		for(Vertex w: vadj){
			g.addEdge(new Edge(v, w));
		}
			
		//set VC
		if(!erg){
			vc.remove(v);
		}
		return erg;
	}

	private boolean p2(Graph g, Integer k) {
		/*take node with degree 5 or larger and branch between itself and its neighborhood*/
		boolean erg = false;
		ArrayList<Vertex> xadj = new ArrayList<Vertex>();
		Vertex x = null;
		for(Vertex v: g.getVertices()){
			if(v.deg() >= 5){
				x = v;
			}
		}
		
		//branch to x and set VC
		vc.add(x);
		
		//storing G
		for(Vertex v: x.adj()){
			xadj.add(v);
		}
		
		//modify G
		g.removeVertex(x);
		
		//compute VC
		erg = solve(g,k-1);
		
		//restoring g
		g.addVertex(x);
		for(Vertex v: xadj){
			g.addEdge(new Edge(x,v));
		}
		
		//set VC
		if(!erg){
			vc.remove(x);
		}
		
		//branch to N(x) if VC still not found
		if(!erg){
			//Set VC
			for(Vertex v: x.adj()){
				vc.add(v);
			}
			
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
			erg = solve(g,k-xN.keySet().size());
			
			//restoring g
			g.addVertex(x);
			for(Vertex v: xN.keySet()){
				g.addVertex(v);
				for(Vertex w: xN.get(v)){
					g.addEdge(new Edge(v,w));
				}
			}
			
			//set VC
			if(!erg){
				for(Vertex v: xN.keySet()){
					vc.remove(v);
				}
			}
		}
		return erg;
		
	}

	private boolean p3(Graph g, Integer k) {
		/*take arbitrary vertex and branch between itself and its neighborhood*/
		boolean erg = false;
		ArrayList<Vertex> xadj = new ArrayList<Vertex>();
		Vertex x = null;
		for(Vertex v: g.getVertices()){
			if(v.deg() != 0){
				x = v;
			}
		}
		
		//branch to x and set VC
		vc.add(x);
		
		//storing G
		for(Vertex v: x.adj()){
			xadj.add(v);
		}
		
		//modify G
		g.removeVertex(x);
		
		//compute VC
		erg = solve(g,k-1);
		
		//restoring g
		g.addVertex(x);
		for(Vertex v: xadj){
			g.addEdge(new Edge(x,v));
		}
		
		//set VC
		if(!erg){
			vc.remove(x);
		}
		
		//branch to N(x) if VC still not found
		if(!erg){
			//set VC
			for(Vertex v: x.adj()){
				vc.add(v);
			}
			
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
			for(Vertex v: xN.keySet()){
				g.removeVertex(v);
			}
			g.removeVertex(x);
			
			//compute VC
			erg = solve(g,k-xN.keySet().size());
			
			//restoring g
			g.addVertex(x);
			for(Vertex v: xN.keySet()){
				g.addVertex(v);
				for(Vertex w: xN.get(v)){
					g.addEdge(new Edge(v,w));
				}
			}
			
			//set VC
			if(!erg){
				for(Vertex v: xN.keySet()){
					vc.remove(v);
				}
			}
		}
		return erg;
	}

	private boolean p4(Graph g, Integer k) {
		// Do case distinction for a not regular graph without nodes of degree 1 or >5 but with a degree 2 node
		Vertex x = null;
		for(Vertex v: g.getVertices()){
			if (v.deg() == 2){
				x = v;
			}
		}
		//1. Case  a and b connected
		if(x.adj().get(0).adj().contains(x.adj().get(1))){
			return p4c1(g,k,x);
		}
		//2. Case a and b have a common neighbor different from x
		if(x.adj().get(0).deg() == 2 && 2 == x.adj().get(1).deg()){
			for(Vertex w: x.adj().get(0).adj()){
				if(x.adj().get(1).adj().contains(w) && !w.equals(x)){
					return p4c2(g,k,x,w);
				}
			}
		}
		//3.Case
		return p4c3(g,k,x);
	}
	
	private boolean p4c1(Graph g, Integer k, Vertex x) {
		//Case 1: neighbors are connected
		ArrayList<Vertex> bvc = new ArrayList<Vertex>();
		bvc.add(x.adj().get(0));
		bvc.add(x.adj().get(1));
		
		//Set VC (add a and b)
		for(Vertex v: bvc){
			vc.add(v);
		}
		
		//store G
		HashMap<Vertex, ArrayList<Vertex>> N = new HashMap<Vertex, ArrayList<Vertex>>();
		for(Vertex v: bvc){
			ArrayList<Vertex> nV = new ArrayList<Vertex>();
			for(Vertex w:  v.adj()){
				nV.add(w);
			}
			N.put(v,nV);
		}
		
		//modify G
		//remove a and b
		for(Vertex v: bvc){
			g.removeVertex(v);
		}
		g.removeVertex(x);
		
		//compute VC
		boolean erg = solve(g,k-bvc.size());
		
		//restoring G
		g.addVertex(x);
		for(Vertex v: N.keySet()){
			g.addVertex(v);
			for(Vertex w: N.get(v)){
				g.addEdge(new Edge(v,w));
			}
		}
		
		//Setting VC back if erg = false
		if(!erg){
			for(Vertex v: bvc){
				vc.remove(v);
			}
		}
		return erg;
	}
	
	private boolean p4c2(Graph g, Integer k, Vertex x, Vertex c) {
		//Case 2: neighbors have a common neighbor that is not x itself
		
		//Setting VC
		vc.add(x);
		vc.add(c);
		
		//Save graph
		ArrayList<Vertex> xadj = new ArrayList<Vertex>();
		ArrayList<Vertex> cadj = new ArrayList<Vertex>();
		for(Vertex v: c.adj()){
			cadj.add(v); 
		}
		for(Vertex v: x.adj()){
			xadj.add(v);
		}
		
		//modify G
		g.removeVertex(x);
		g.removeVertex(c);
		
		//compute possible VC
		boolean erg = solve(g,k-2);
		
		//restoring G
		g.addVertex(x);
		g.addVertex(c);
		for(Vertex v: xadj){
			g.addEdge(new Edge(x,v));
		}
		for(Vertex v: cadj){
			g.addEdge(new Edge(c,v));
		}
			
		//setting VC
		if(!erg){
			vc.remove(x);
			vc.remove(c);
		}
		return erg;
	}
	
	private boolean p4c3(Graph g, Integer k, Vertex x) {
		//Case 3: otherwise (here we have to branch)
		boolean erg = false;
		
		//branch to N(x) and setting VC
		for(Vertex v: x.adj()){
			vc.add(v);
		}
		
		//store G
		HashMap<Vertex, ArrayList<Vertex>> xN = new HashMap<Vertex, ArrayList<Vertex>>();
		for(Vertex v: x.adj()){
			ArrayList<Vertex> nV = new ArrayList<Vertex>();
			for(Vertex w:  v.adj()){
				nV.add(w);
			}
			xN.put(v,nV);
		}
		
		//modify G
		//remove x, a and b
		g.removeVertex(x);
		for(Vertex v: xN.keySet()){
			g.removeVertex(v);
		}
		
		//compute VC
		erg = solve(g,k-xN.keySet().size());
		
		//restoring G
		g.addVertex(x);
		for(Vertex v: xN.keySet()){
			g.addVertex(v);
			for(Vertex w: xN.get(v)){
				g.addEdge(new Edge(w,v));
			}
		}
		
		//Setting VC
		if(!erg){
			for(Vertex v: xN.keySet()){
				vc.remove(v);
			}
		}
			
		//branch to (N(a) union N(b)) if VC still not found
		if(!erg){
			Vertex a = x.adj().get(0);
			Vertex b = x.adj().get(1);
			
			//storing G
			HashMap<Vertex, ArrayList<Vertex>> aN = new HashMap<Vertex, ArrayList<Vertex>>();
			for(Vertex v: a.adj()){
				ArrayList<Vertex> nV = new ArrayList<Vertex>();
				for(Vertex w:  v.adj()){
					nV.add(w);
				}
				aN.put(v,nV);
			}
			HashMap<Vertex, ArrayList<Vertex>> bN = new HashMap<Vertex, ArrayList<Vertex>>();
			for(Vertex v: b.adj()){
				ArrayList<Vertex> nV = new ArrayList<Vertex>();
				for(Vertex w:  v.adj()){
					nV.add(w);
				}
				bN.put(v,nV);
			}
			
			//Setting VC
			ArrayList<Vertex> abN = new ArrayList<Vertex>();
			for(Vertex v: aN.keySet()){
				abN.add(v);
			}
			for(Vertex v:  bN.keySet()){
				if(!abN.contains(v)){
					abN.add(v);
				}
			}
			for(Vertex v: abN){
				vc.add(v);
			}
			
			//modify G
			g.removeVertex(a);
			g.removeVertex(b);
			for(Vertex v: abN){
				g.removeVertex(v);
			}
			
			//compute VC
			erg = solve(g,k-abN.size());
			
			//restoring g
			g.addVertex(a);
			g.addVertex(b);
			for(Vertex v: aN.keySet()){
				if(!g.getVertices().contains(v)){
					g.addVertex(v);
				}
				for(Vertex w: aN.get(v)){
					g.addEdge(new Edge(v,w));
				}
			}
			for(Vertex v: bN.keySet()){
				if(!g.getVertices().contains(v)){
					g.addVertex(v);
				}
				for(Vertex w: bN.get(v)){
					if(!aN.keySet().contains(v)){
						g.addEdge(new Edge(v,w));
					}
				}
			}
			//set VC
			if(!erg){
				for(Vertex v: abN){
					vc.remove(v);
				}
			}
		}
		return erg;
	}
	
	private boolean p5(Graph g, Integer k) {
		// Do case distinction for a not regular graph without nodes of degree 1 or >5 but with a degree 3 node
		Vertex x = null;
		for(Vertex v: g.getVertices()){
			if (v.deg() == 3){
				x = v;
			}
		}
		//1. Case x part of triangle 
		for(Vertex v: x.adj()){
			for(Vertex w: v.adj()){
				if(!w.equals(x)){//not necessary, because it cannot occur in a triangle
					for(Vertex u: w.adj()){
						if(u.equals(x)){
							//Find c not part of triangle
							Vertex c = null; 
							for(Vertex y: x.adj()){
								if(!y.equals(v) && !y.equals(w)){
									c = y;
								}
							}
							return p5c1(g, k, x, c);
						}
					}
				}
			}
		}
		//2. Case x part of a 4 cycle
		for(Vertex v: x.adj()){
			for(Vertex w: v.adj()){
				if(!w.equals(x)){ //check if we dont go back and forth
					for(Vertex y: w.adj()){
						if(!y.equals(v)){ //check if we dont go back and forth
							for(Vertex u: y.adj()){
								if(u.equals(x)){
									return p5c2(g, k, x, w);
								}
							}
						}
					}
				}
			}
		}
		//3.Case
		return p5c3(g,k,x);
	}

	private boolean p5c1(Graph g, Integer k, Vertex x, Vertex c) {
		//Case 2: branch N(x) and N(c)
		boolean erg = false;
		
		//branch to N(x) and setting VC
		for(Vertex v: x.adj()){
			vc.add(v);
		}
		
		//store G
		HashMap<Vertex, ArrayList<Vertex>> xN = new HashMap<Vertex, ArrayList<Vertex>>();
		for(Vertex v: x.adj()){
			ArrayList<Vertex> nV = new ArrayList<Vertex>();
			for(Vertex w:  v.adj()){
				nV.add(w);
			}
			xN.put(v,nV);
		}
		
		//modify G
		//remove x and N(x)
		g.removeVertex(x);
		for(Vertex v: xN.keySet()){
			g.removeVertex(v);
		}
		
		//compute VC
		erg = solve(g,k-xN.keySet().size());
		
		//restoring G
		g.addVertex(x);
		for(Vertex v: xN.keySet()){
			g.addVertex(v);
			for(Vertex w: xN.get(v)){
				g.addEdge(new Edge(v,w));
			}
		}
		
		//Setting VC back if erg = false
		if(!erg){
			for(Vertex v: x.adj()){
				vc.remove(v);
			}
		}
			
		//branch to N(c) if VC still not found
		if(!erg){
			//setting VC
			for(Vertex v: c.adj()){
				vc.add(v);
			}
			
			//store G
			HashMap<Vertex, ArrayList<Vertex>> cN = new HashMap<Vertex, ArrayList<Vertex>>();
			for(Vertex v: c.adj()){
				ArrayList<Vertex> nV = new ArrayList<Vertex>();
				for(Vertex w:  v.adj()){
					nV.add(w);
				}
				cN.put(v,nV);
			}
			
			//modify G
			//remove c and N(c)
			g.removeVertex(c);
			for(Vertex v: cN.keySet()){
				g.removeVertex(v);
			}
			
			//compute VC
			erg = solve(g,k-cN.keySet().size());
			
			//restoring G
			g.addVertex(c);
			for(Vertex v: cN.keySet()){
				g.addVertex(v);
				for(Vertex w: cN.get(v)){
					g.addEdge(new Edge(v,w));
				}
			}
			
			//Setting VC back if erg = false
			if(!erg){
				for(Vertex v: c.adj()){
					vc.remove(v);
				}
			}
		}
		return erg;
	}

	private boolean p5c2(Graph g, Integer k, Vertex x, Vertex d) {
		//Case 2: branch N(x) and {x,d}
		boolean erg = false;
		
		//branch to N(x) and setting VC
		for(Vertex v: x.adj()){
			vc.add(v);
		}
		
		//store G
		HashMap<Vertex, ArrayList<Vertex>> xN = new HashMap<Vertex, ArrayList<Vertex>>();
		for(Vertex v: x.adj()){
			ArrayList<Vertex> nV = new ArrayList<Vertex>();
			for(Vertex w:  v.adj()){
				nV.add(w);
			}
			xN.put(v,nV);
		}
		
		//modify G
		//remove x and N(x)
		g.removeVertex(x);
		for(Vertex v: xN.keySet()){
			g.removeVertex(v);
		}
		
		//compute VC
		erg = solve(g,k-xN.keySet().size());
		
		//restoring G
		g.addVertex(x);
		for(Vertex v: xN.keySet()){
			g.addVertex(v);
//			g.addEdge(new Edge(x,v));
			for(Vertex w: xN.get(v)){
				g.addEdge(new Edge(v,w));
			}
		}
		
		//Setting VC back if erg = false
		if(!erg){
			for(Vertex v: x.adj()){
				vc.remove(v);
			}
		}
			
		//branch to {x,d} if VC still not found
		if(!erg){
			//setting VC
			ArrayList<Vertex> bvc = new ArrayList<Vertex>();
			bvc.add(x);
			bvc.add(d);
			
			for(Vertex v: bvc){
				vc.add(v);
			}
			
			//store G
			HashMap<Vertex, ArrayList<Vertex>> N = new HashMap<Vertex, ArrayList<Vertex>>();
			for(Vertex v: bvc){
				ArrayList<Vertex> nV = new ArrayList<Vertex>();
				for(Vertex w:  v.adj()){
					nV.add(w);
				}
				N.put(v,nV);
			}
			
			//modify G
			//remove x and d
			for(Vertex v: bvc){
				g.removeVertex(v);
			}
			
			//compute VC
			erg = solve(g,k-bvc.size());
			
			//restoring G
			for(Vertex y: N.keySet()){
				g.addVertex(y);
				for(Vertex w: N.get(y)){
					//TODO this edge is added twice because of x and d having 2 same neighbors
					g.addEdge(new Edge(y,w));
				}
			}
			
			//Setting VC back if erg = false
			if(!erg){
				for(Vertex v: bvc){
					vc.remove(v);
				}
			}
		}
		return erg;
	}

	private boolean p5c3(Graph g, Integer k, Vertex x) {
		//Case 3: branch N(x) and N(a) and ({a} u  N(b) u N(c))
		boolean erg = false;
		
		Vertex a = x.adj().get(0);
		Vertex b = x.adj().get(1);
		Vertex c = x.adj().get(2);
		
		
		//branch to N(x) and setting VC
		for(Vertex v: x.adj()){
			vc.add(v);
		}
		
		//store G
		HashMap<Vertex, ArrayList<Vertex>> xN = new HashMap<Vertex, ArrayList<Vertex>>();
		for(Vertex v: x.adj()){
			ArrayList<Vertex> nV = new ArrayList<Vertex>();
			for(Vertex w:  v.adj()){
				nV.add(w);
			}
			xN.put(v,nV);
		}
		
		//modify G
		//remove x and N(x)
		g.removeVertex(x);
		for(Vertex v: xN.keySet()){
			g.removeVertex(v);
		}
		
		//compute VC
		erg = solve(g,k-xN.keySet().size());
		
		//restoring G
		g.addVertex(x);
		for(Vertex v: xN.keySet()){
			g.addVertex(v);
			for(Vertex w: xN.get(v)){
				//edge added several times
				g.addEdge(new Edge(v,w));
			}
		}
		
		//Setting VC back if erg = false
		if(!erg){
			for(Vertex v: x.adj()){
				vc.remove(v);
			}
		}
			
		//branch to N(a) if VC still not found
		if(!erg){
			//setting VC
			for(Vertex v: a.adj()){
				vc.add(v);
			}
			
			//store G
			HashMap<Vertex, ArrayList<Vertex>> aN = new HashMap<Vertex, ArrayList<Vertex>>();
			for(Vertex v: a.adj()){
				ArrayList<Vertex> nV = new ArrayList<Vertex>();
				for(Vertex w:  v.adj()){
					nV.add(w);
				}
				aN.put(v,nV);
			}
			
			//modify G
			//remove a and N(a)
			g.removeVertex(a);
			for(Vertex v: aN.keySet()){
				g.removeVertex(v);
			}
			
			//compute VC
			erg = solve(g,k-aN.keySet().size());
			
			//restoring G
			g.addVertex(a);
			for(Vertex v: aN.keySet()){
				g.addVertex(v);
				for(Vertex w: aN.get(v)){
					g.addEdge(new Edge(v,w));
				}
			}
			
			//Setting VC back if erg = false
			if(!erg){
				for(Vertex v: aN.keySet()){
					vc.remove(v);
				}
			}
		}
		//Still no VC branch ({a} u  N(b) u N(c))
		if(!erg){
			//storing G
			ArrayList<Vertex> aadj = a.adj();
			HashMap<Vertex, ArrayList<Vertex>> cN = new HashMap<Vertex, ArrayList<Vertex>>();
			for(Vertex v: c.adj()){
				ArrayList<Vertex> nV = new ArrayList<Vertex>();
				for(Vertex w:  v.adj()){
					nV.add(w);
				}
				cN.put(v,nV);
			}
			HashMap<Vertex, ArrayList<Vertex>> bN = new HashMap<Vertex, ArrayList<Vertex>>();
			for(Vertex v: b.adj()){
				ArrayList<Vertex> nV = new ArrayList<Vertex>();
				for(Vertex w:  v.adj()){
					nV.add(w);
				}
				bN.put(v,nV);
			}
			
			//Setting VC
			ArrayList<Vertex> cbN = new ArrayList<Vertex>();
			for(Vertex v: cN.keySet()){
				cbN.add(v);
			}
			for(Vertex v:  bN.keySet()){
				if(!cbN.contains(v)){
					cbN.add(v);
				}
			}
			
			vc.add(a);
			for(Vertex v: cbN){
				vc.add(v);
			}
			
			//modify G
			g.removeVertex(a);
			g.removeVertex(b);
			g.removeVertex(c);
			for(Vertex v: cbN){
				g.removeVertex(v);
			}
			
			//compute VC
			erg = solve(g,k-cbN.size()-1);
			
			//restoring g
			g.addVertex(a);
			g.addVertex(b);
			g.addVertex(c);
			for(Vertex v: cN.keySet()){
				if(!g.getVertices().contains(v)){
					g.addVertex(v);
				}
				for(Vertex w: cN.get(v)){
					g.addEdge(new Edge(v,w));
				}
			}
			for(Vertex v: bN.keySet()){
				if(!g.getVertices().contains(v)){
					g.addVertex(v);
				}
				for(Vertex w: bN.get(v)){
					if(!cN.keySet().contains(v)){
						g.addEdge(new Edge(v,w));
					}
				}
			}
			for(Vertex v: aadj){
				if(!cN.keySet().contains(a) && !bN.keySet().contains(a)){
					g.addEdge(new Edge(v, a));
				}
			}
			//Setting VC
			if(!erg){
				for(Vertex v: cbN){
					vc.remove(v);
				}
				vc.remove(a);
			}
		}
		return erg;
	}
	
	private boolean p6(Graph g, Integer k) { //
		/*take arbitrary vertex and branch between itself and its neighborhood*/
		boolean erg = false;
		ArrayList<Vertex> xadj = new ArrayList<Vertex>();
		Vertex x = g.getVertices().get(0);
		
		//branch to x and set VC
		vc.add(x);
		
		//storing G
		for(Vertex v: x.adj()){
			xadj.add(v);
		}
		
		//modify G
		g.removeVertex(x);
		
		//compute VC
		//System.out.println("1. branch: k = " + (k-1));
		erg = solve(g,k-1);
		
		//restoring g
		g.addVertex(x);
		for(Vertex v: xadj){
			g.addEdge(new Edge(x,v));
		}
		
		//set VC
		if(!erg){
			vc.remove(x);
		}
		
		//branch to N(x) if VC still not found
		if(!erg){
			for(Vertex v: x.adj()){
				vc.add(v);
			}
			
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
			erg = solve(g,k-xN.keySet().size());
			
			//restoring g
			g.addVertex(x);
			for(Vertex v: xN.keySet()){
				g.addVertex(v);
//				g.addEdge(new Edge(x,v));
				for(Vertex w: xN.get(v)){
					g.addEdge(new Edge(v,w));
				}
			}
			
			//set VC
			if(!erg){
				for(Vertex v: xN.keySet()){
					vc.remove(v);
				}
			}
		}
		return erg;
	}
	
	/* 
	 * unnecessary methode while we cannot guess the right parameter k for the components
	 * to do recursive calls on the components
	 */
	private ArrayList<Graph> getConnectedComponents(Graph g){
		ArrayList<Graph> res = new ArrayList<Graph>();
		ArrayList<Vertex> notVisited = g.copyVertices();
		while(!notVisited.isEmpty()){
			ArrayList<Edge> edges = new ArrayList<Edge>();
			ArrayList<Vertex> vertices = new ArrayList<Vertex>();
			ArrayList<Vertex> visited = new ArrayList<Vertex>();
			Vertex x = notVisited.get(0);//TODO BFS
			vertices.add(x);
			visited.add(x);
			Iterator<Vertex> iter = vertices.iterator();
			while(iter.hasNext()){
				Vertex y = iter.next();
				for(Edge e: y.inc){
					if(e.v.equals(y) && !visited.contains(e.w)){
						edges.add(e);
					}
					if(e.w.equals(y) && !visited.contains(e.v)){
						edges.add(e);
					}
				}
				
				for(Vertex v: y.adj()){
					if(!visited.contains(v)){
						vertices.add(v);
						visited.add(v);
					}
				}
			}
			
			Graph newg = new Graph(vertices, edges);
			res.add(newg);
		}
		return res;
	}

	
	public static void main(String[] args){
		Integer numberOfGraphs = 1;
		Integer graphsize = 300;
		int k = 245;

		//###############VCDBS#########################
		VCDBS vcdbs = new VCDBS();
		Graph[] gs = new Graph[numberOfGraphs];
		
		
		Long starttime = System.currentTimeMillis();
		ArrayList<Boolean> ergs1 = new ArrayList<Boolean>();
		
		for(int i = 0; i < gs.length; i++){
			Graph g = new RandomGraph(graphsize);
			gs[i] = g;
//			Graph g = gs[i];
//			print(g);
			boolean erg1 = vcdbs.solve(g,k);
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
		
		Long endtime = System.currentTimeMillis();
		
		System.out.println(yes1 + "|" + no1 + " in " + (endtime - starttime) + " mS");
		/*
		//#####################VCBF#######################
		long starttime2 = System.currentTimeMillis();
		
		VCBF vcbf = new VCBF();
		ArrayList<Boolean> ergs2 = new ArrayList<Boolean>();
		for(int i = 0; i < gs.length; i++){
//			Graph g = new RandomGraph(12);
//			gs[i] = g;
			Graph g = gs[i];
			boolean erg2 = vcbf.solve(g,k);
			ergs2.add(erg2);
		}
		
		int yes2 = 0;
		int no2 = 0;
		for(Boolean b: ergs2){
			if(b){
				yes2++;
			}else{
				no2++;
			}
		}
		long endtime2 = System.currentTimeMillis();
		System.out.println(yes2 + "|" + no2 + " in " + (endtime2 - starttime2) + " mS");
		
		
		//#####################VCBF2#######################
		long starttime3 = System.currentTimeMillis();
		
		VCBF vcbf2 = new VCBF();
		ArrayList<Boolean> ergs3 = new ArrayList<Boolean>();
		for(int i = 0; i < gs.length; i++){
//					Graph g = new RandomGraph(12);
//					gs[i] = g;
			Graph g = gs[i];
			boolean erg3 = vcbf2.solve2(g,k);
			ergs3.add(erg3);
		}
		
		int yes3 = 0;
		int no3 = 0;
		for(Boolean b: ergs3){
			if(b){
				yes3++;
			}else{
				no3++;
			}
		}
		long endtime3 = System.currentTimeMillis();
		System.out.println(yes3 + "|" + no3 + " in " + (endtime3 - starttime3) + " mS");
		*/
		
	}
	
	/*
	 * prints the list of nodes and the adjacency list
	 * Attention: doing this before algorithm and afterwards
	 * does not necessarily output the same graph
	 * as they will only be isomorph
	 */

}
