import oracle.pgx.api.CompiledProgram;
import oracle.pgx.api.Pgx;
import oracle.pgx.api.PgxGraph;
import oracle.pgx.api.VertexProperty;
import oracle.pgx.common.types.PropertyType;
import oracle.pgx.api.PgxSession;
import oracle.pgx.api.internal.AnalysisResult;
import java.util.*;


public class ProcInProc {

  public static void main(String[] args) throws Exception {
	System.out.println("\n entered Java Program");
    	PgxSession session = Pgx.createSession("my-session");
    	CompiledProgram ProcInProc = session.compileProgram("../bug_list/ProcInProc.gm");
  	System.out.println("\n After Compiling greenmarl Java Program");


    	PgxGraph graph = session.readGraphWithProperties("/var/services/homes/adisingh/code/code_community/input.json");
	graph = graph.undirect();
    
    	System.out.println("\n graph loaded");
	
    	VertexProperty<Integer,Integer> ID = graph.getVertexProperty("nodeID");
	VertexProperty<Integer,Integer> degree = graph.createVertexProperty(PropertyType.INTEGER, "degree");

  	System.out.println("\n run start");
  	AnalysisResult <Integer> result = ProcInProc.run(graph,ID,degree);
	

    	System.out.println("\n run ended");
    	System.out.println("Total number of nodes:"+ID.size());    	
    	System.out.println("Total # nodes= " + result.getReturnValue() + " (took " + result.getExecutionTimeMs() + "ms)");
  }
}

