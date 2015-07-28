import java.util.ArrayList;
import java.util.LinkedList;

import DataWriter.CSVWriter;
import Graphs.Graph;
import Graphs.RandomGraph;
import VCAlgs.VCDBS;
import VCAlgs.VCDBSwithBounding;


public class Test {

	public static void main(String[] args) {
		Integer numberOfGraphs = 100;
		Integer graphSize = 100;
		
		Graph[] graphs = generateGraphs(numberOfGraphs, graphSize);
		
//		int k = 99;

		VCDBS vcdbs = new VCDBS();
		VCDBSwithBounding vcdbswb = new VCDBSwithBounding();
		LinkedList<String[]> data = new LinkedList<String[]>();
		
		for(int k = graphSize; k > 0; k--){
			//###############VCDBS#########################
			Long starttime1 = System.currentTimeMillis();
			ArrayList<Boolean> ergs1 = new ArrayList<Boolean>();
			
			for(int i = 0; i < graphs.length; i++){
				boolean erg1 = vcdbs.solve(graphs[i],k);
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
			Long endtime1 = System.currentTimeMillis();
			
			//#############VCDBS-with-Bounding################
			Long starttime2 = System.currentTimeMillis();
			ArrayList<Boolean> ergs2 = new ArrayList<Boolean>();
			
			for(int i = 0; i < graphs.length; i++){
				boolean erg2 = vcdbswb.solve(graphs[i],k);
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
			Long endtime2 = System.currentTimeMillis();
			
			String[] row = {numberOfGraphs.toString(), graphSize.toString(), RandomGraph.getEdgeProbability().toString(), ((Long)(endtime1 - starttime1)).toString(), ((Long)(endtime2 - starttime2)).toString(), yes1 + "|" + no1, k + ""};
			data.add(row);
		}
		
		CSVWriter.writeCsvFile("firstrun", data);

	}

	private static Graph[] generateGraphs(int numberOfGraphs, int graphSize) {
		Graph[] graphs = new Graph[graphSize];
		for(int i = 0; i < graphs.length; i++){
			Graph g = new RandomGraph(graphSize);
			graphs[i] = g;
		}
		return graphs;
	}

}
