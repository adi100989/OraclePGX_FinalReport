import oracle.pgx.api.CompiledProgram;
import oracle.pgx.api.*;
import oracle.pgx.api.PgxGraph;
import oracle.pgx.api.VertexProperty;
import oracle.pgx.common.types.PropertyType;
import oracle.pgx.api.PgxSession;
import oracle.pgx.api.internal.AnalysisResult;
import java.util.*;
import oracle.pgx.common.types.*;
import oracle.pgx.common.util.vector.Vect;
import oracle.pgx.api.PgxVect;
import oracle.pgx.api.Scalar;


public class bitwiseOr {

  public static void main(String[] args) throws Exception {
	System.out.println("\n entered Java Program");
    	PgxSession session = Pgx.createSession("my-session");

//	PgxGraph graph = session.readGraphWithProperties("/var/services/homes/yoshen/work/projects/graph-pgx/datasets/soc-LiveJournal1.vid.adj.json");
	PgxGraph graph = session.readGraphWithProperties("../github/OraclePGX_FinalReport/Link_Prediction/facebook.json");
    
    	System.out.println("\n graph loaded");
    	VertexProperty<Integer,Integer> ID = graph.getVertexProperty("nodeID");
	System.out.println("\n the size is = "+(int)ID.size());	
        //VertexProperty<Integer,PgxVect<Integer>> link_node = graph.createVertexVectorProperty(PropertyType.INTEGER,(int)ID.size(), "link_node");
	VertexProperty<Integer,Integer> radius = graph.createVertexProperty(PropertyType.INTEGER,"radius");
	CompiledProgram bitwiseOr = session.compileProgram("../github/OraclePGX_FinalReport/bug_list/bitwiseOr.gm");

        System.out.println("\n After Compiling greenmarl Java Program");

     	System.out.println("\n run start");
	AnalysisResult <Integer> result = bitwiseOr.run(graph,ID,radius);
    	System.out.println("\n run ended");
    	System.out.println("result = " + result.getReturnValue() + " (took " + result.getExecutionTimeMs() + "ms)");
  }
}

