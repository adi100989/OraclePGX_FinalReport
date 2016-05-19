import oracle.pgx.api.CompiledProgram;
import oracle.pgx.api.Pgx;
import oracle.pgx.api.PgxGraph;
import oracle.pgx.api.VertexProperty;
import oracle.pgx.common.types.PropertyType;
import oracle.pgx.api.PgxSession;
import oracle.pgx.api.internal.AnalysisResult;
import java.util.*;


public class MapNotIterable {

  public static void main(String[] args) throws Exception {
	System.out.println("\n entered Java Program");
    	PgxSession session = Pgx.createSession("my-session");
    	CompiledProgram MapNotIterable = session.compileProgram("../bug_list/MapNotIterable.gm");
  	System.out.println("\n After Compiling greenmarl Java Program");

   	PgxGraph graph = session.readGraphWithProperties("/var/services/homes/adisingh/code/facebook.json");
    
    	System.out.println("\n graph loaded");
	
    	VertexProperty<Integer,Integer> ID = graph.getVertexProperty("nodeID");

  	System.out.println("\n run start");

  	AnalysisResult <Integer> result = MapNotIterable.run(graph,ID);
	

    	System.out.println("\n run ended without errors");
    	
    	System.out.println("Sum of values in map = " + result.getReturnValue() + " (took " + result.getExecutionTimeMs() + "ms)");
  }
}

