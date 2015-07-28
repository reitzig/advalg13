package Graphs;


public class TestGraph extends Graph{
	public TestGraph(){
		Vertex a = new Vertex();
		Vertex b = new Vertex();
		Vertex c = new Vertex();
		Vertex d = new Vertex();
		Vertex e = new Vertex();
		Vertex f = new Vertex();
		Vertex g = new Vertex();
		Vertex h = new Vertex();
		
		addVertex(a);
		addVertex(b);
		addVertex(c);
		addVertex(d);
		addVertex(e);
		addVertex(f);
		addVertex(g);
		addVertex(h);
		
		addEdge(new Edge(a,b));
		addEdge(new Edge(b,c));
		addEdge(new Edge(c,d));
		addEdge(new Edge(d,a));
		
		addEdge(new Edge(a,e));
		addEdge(new Edge(b,e));
		addEdge(new Edge(c,e));
		addEdge(new Edge(d,e));
		
		addEdge(new Edge(a,f));
		addEdge(new Edge(b,f));
		addEdge(new Edge(c,f));
		addEdge(new Edge(d,f));
		addEdge(new Edge(e,f));
		
		
		
	}
}
