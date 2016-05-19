import oracle.pgx.api.CompiledProgram;
import oracle.pgx.api.Pgx;
import oracle.pgx.api.PgxGraph;
import oracle.pgx.api.VertexProperty;
import oracle.pgx.common.types.PropertyType;
import oracle.pgx.api.PgxSession;
import oracle.pgx.api.internal.AnalysisResult;
import java.util.*;
import oracle.pgx.api.filter.*;
//import oracle.pgx.filter.expressions.*;   //package does not exist ERROR! 

public class FilterTest {

  public static void main(String[] args) throws Exception {
	System.out.println("\n entered Java Program");
    	PgxSession session = Pgx.createSession("my-session");
   	PgxGraph graph = session.readGraphWithProperties("/var/services/homes/adisingh/code/facebook.json"); 
    	System.out.println("\n graph loaded");
	
    	VertexProperty<Integer,Integer> ID = graph.getVertexProperty("nodeID");
	//creating a subgraph
	System.out.println("\n subgraph creation start");
	PgxGraph subgraph = graph.filter(new VertexFilter("vertex.nodeID < 10"));
	System.out.println("\n subgraph creation ended");

  }
}

