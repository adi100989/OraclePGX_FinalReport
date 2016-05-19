import oracle.pgx.api.CompiledProgram;
import oracle.pgx.api.Pgx;
import oracle.pgx.api.PgxGraph;
import oracle.pgx.api.VertexProperty;
import oracle.pgx.common.types.PropertyType;
import oracle.pgx.api.PgxSession;
import oracle.pgx.api.internal.AnalysisResult;
import java.util.*;
//import oracle.pgx.filter.expressions.*;

public class UndirectCompilationExample {

  public static void main(String[] args) throws Exception {
	System.out.println("\n entered Java Program");
    	PgxSession session = Pgx.createSession("my-session");
    	CompiledProgram undirect = session.compileProgram("../bug_list/undirect_directed_test.gm");
  	System.out.println("\n After Compiling greenmarl Java Program");
	//PgxGraph graph = session.readGraphWithProperties("/var/services/homes/yoshen/work/projects/graph-pgx/datasets/soc-LiveJournal1.vid.adj.json");


   	PgxGraph graph = session.readGraphWithProperties("/var/services/homes/adisingh/code/facebook.json");
    	graph=graph.undirect();
    
    	System.out.println("\n graph loaded");
	
    	VertexProperty<Integer,Integer> ID = graph.getVertexProperty("nodeID");
	VertexProperty<Integer,Integer> countNbr = graph.createVertexProperty(PropertyType.INTEGER, "countNbr");		
  
  	System.out.println("\n run start");

  	AnalysisResult <Integer> result = undirect.run(graph,ID,countNbr);
	

    	System.out.println("\n run ended");
    	System.out.println("Total number of nodes:"+ID.size());
    	HashSet<Integer> communities = new HashSet<Integer>();
    	for(int i=0; i<ID.size();i++)
	{
		//int li=label.get(i);
		System.out.println("\n the ID is  "+i +"  count of the neighbours=  "+countNbr.get(i));
		//communities.add(li);
	}
	//	System.out.println("\n the label is "+label.get(0));
	//	 System.out.println("\n the label is "+label.get(1));	
    	
    	System.out.println("Total loops= " + result.getReturnValue() + " (took " + result.getExecutionTimeMs() + "ms)");
  }
}

