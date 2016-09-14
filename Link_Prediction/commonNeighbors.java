import oracle.pgx.api.CompiledProgram;
import oracle.pgx.api.Pgx;
import oracle.pgx.api.PgxGraph;
import oracle.pgx.api.VertexProperty;
import oracle.pgx.common.types.PropertyType;
import oracle.pgx.api.PgxSession;
import oracle.pgx.api.internal.AnalysisResult;
import java.util.*;
//import oracle.pgx.filter.expressions.*;

public class commonNeighbors {

  public static void main(String[] args) throws Exception {
	System.out.println("\n entered Java Program");
    	PgxSession session = Pgx.createSession("my-session");
    	CompiledProgram commonNeighbors = session.compileProgram("../code/Link_Prediction/commonNeighbors.gm");
	//CompiledProgram label_propagation = session.compileProgram("/var/services/homes/yoshen/work/projects/graph-pgx/first-oracle-report/label_propagation.gm");   
  	System.out.println("\n After Compiling greenmarl Java Program");
	PgxGraph graph = session.readGraphWithProperties("/var/services/homes/yoshen/work/projects/graph-pgx/datasets/soc-LiveJournal1.vid.adj.json");


//	PgxGraph graph = session.readGraphWithProperties("/var/services/homes/adisingh/code/facebook.json");
	//	PgxGraph graph = session.readGraphWithProperties("/var/services/homes/adisingh/code/small_graph.json");
    //	graph=graph.undirect();
    
    	System.out.println("\n graph loaded");
   	// PgxGraph subgraph = graph.filter(new VertexFilter("nodeID < 100"));	
    	VertexProperty<Integer,Integer> ID = graph.getVertexProperty("nodeID");
    //	VertexProperty<Integer,Integer> label = graph.createVertexProperty(PropertyType.INTEGER, "label");
//	VertexProperty<Integer,Integer> countNbr = graph.createVertexProperty(PropertyType.INTEGER, "countNbr");		
  
  	//    PgxGraph subgraph = graph.filter(new VertexFilter("name.nodeID < 100"));
     	System.out.println("\n run start");

  	AnalysisResult <Integer> result = commonNeighbors.run(graph,1,2);
	

    	System.out.println("\n run ended");
    //	System.out.println("Total number of nodes:"+label.size());
    //	HashSet<Integer> communities = new HashSet<Integer>();
    //	int li=0;
//	for(int i=0; i<label.size();i++)
//	{
//		li=label.get(i);
//		System.out.println("\n the label for node"+i +"is "+label.get(i));
//		communities.add(li);
//	}
	//	System.out.println("\n the label is "+label.get(0));
	//	 System.out.println("\n the label is "+label.get(1));	
//    	System.out.println("Size of the community:"+communities.size());
    	System.out.println("Total common neighbors= " + result.getReturnValue() + " (took " + result.getExecutionTimeMs() + "ms)");
  }
}

