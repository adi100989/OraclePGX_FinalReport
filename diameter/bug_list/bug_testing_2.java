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


public class bug_testing_2 {

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
	CompiledProgram bug_testing_2 = session.compileProgram("../github/OraclePGX_FinalReport/diameter/bug_testing_2.gm");

        System.out.println("\n After Compiling greenmarl Java Program");

     	System.out.println("\n run start");
//	double avg = 0.0;	
//	for(int i =0;i<1;i++)
//	{  	
//		long startTime = System.currentTimeMillis();
		AnalysisResult <Integer> result = bug_testing_2.run(graph,ID,radius);
//		long endTime   = System.currentTimeMillis();
//		long totalTime = endTime - startTime;
//		avg = avg + totalTime;
//		System.out.println(totalTime);
//		AnalysisResult <Integer> result = commonNeighbors5.run(graph,ID,(int)ID.size(), link_node);
//	}
//	System.out.println("the average run time over 20 runs == "+avg/20);
    	System.out.println("\n run ended");
//	for(int i=0; i< ID.size();i++)
//	{
//		System.out.println("\n NODE : "+i);
	//	System.out.println("\n the list node count for  "+i+" count "+radius.get(i));
//	}
    	System.out.println("diameter = " + result.getReturnValue() + " (took " + result.getExecutionTimeMs() + "ms)");
  }
}

