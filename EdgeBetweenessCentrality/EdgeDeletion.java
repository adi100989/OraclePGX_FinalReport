import oracle.pgx.api.CompiledProgram;
import oracle.pgx.api.Pgx;
import oracle.pgx.api.PgxGraph;
import oracle.pgx.api.VertexProperty;
import oracle.pgx.common.types.PropertyType;
import oracle.pgx.api.PgxSession;
import oracle.pgx.api.internal.AnalysisResult;
import java.util.*;


public class EdgeDeletion {

  public static void main(String[] args) throws Exception {
	System.out.println("\n entered Java Program");
    	PgxSession session = Pgx.createSession("my-session");
    	CompiledProgram EdgeDeletion = session.compileProgram("../bug_list/EdgeDeletion.gm");
  	System.out.println("\n After Compiling greenmarl Java Program");



   	PgxGraph graph = session.readGraphWithProperties("/var/services/homes/adisingh/code/facebook.json");
    	graph=graph.undirect();
    
    	System.out.println("\n graph loaded");
	
    	VertexProperty<Integer,Integer> ID = graph.getVertexProperty("nodeID");
	//EdgeProperty<Integer,Integer> deleted = graph.createEdgeProperty(PropertyType.INTEGER, "deleted");		
  
  	System.out.println("\n run start");

  	AnalysisResult <Integer> result = EdgeDeletion.run(graph,ID);
	

    	System.out.println("\n run ended");
    	System.out.println("Total number of nodes:"+ID.size());
	int finalCount = 0;
    	int totalEdges = 0;
	//HashSet<Integer> communities = new HashSet<Integer>();
    	//for(int i=0; i<ID.size();i++)
	{	
		
	//	if( deleted.get(i) == 1)
	//		finalCount += 1;
		//System.out.println("\n the ID is  "+i +"  count of the neighbours=  "+countNbr.get(i));

	}


    	
    	 System.out.println("Total Number of edges after deletion as a return value = " + result.getReturnValue() + " (took " + result.getExecutionTimeMs() + "ms)");
	 //System.out.println("Total Number of edges after deletion as found out using java = " + finalCount + " (took " + result.getExecutionTimeMs() + "ms)");
//	 System.out.println("Total Number of edges as a return value = " + result.getReturnValue() + " (took " + result.getExecutionTimeMs() + "ms)");
  }
}

