import oracle.pgx.api.CompiledProgram;
import oracle.pgx.api.Pgx;
import oracle.pgx.api.PgxGraph;
import oracle.pgx.api.VertexProperty;
import oracle.pgx.common.types.PropertyType;
import oracle.pgx.api.PgxSession;
import oracle.pgx.api.internal.AnalysisResult;
import java.util.*;


public class DegreeTest {

  public static void main(String[] args) throws Exception {
	System.out.println("\n entered Java Program");
    	PgxSession session = Pgx.createSession("my-session");
    	CompiledProgram DegreeTest = session.compileProgram("../bug_list/degreeTest.gm");
  	System.out.println("\n After Compiling greenmarl Java Program");

   //	PgxGraph graph = session.readGraphWithProperties("/var/services/homes/adisingh/code/facebook.json");
    	PgxGraph graph = session.readGraphWithProperties("/var/services/homes/adisingh/code/code_community/input.json");
	//graph=graph.undirect();
    
    	System.out.println("\n graph loaded");
	
    	VertexProperty<Integer,Integer> ID = graph.getVertexProperty("nodeID");
	//VertexProperty<Integer,Integer> countNbr = graph.createVertexProperty(PropertyType.INTEGER, "countNbr");		
  	VertexProperty<Integer,Integer> indegree = graph.createVertexProperty(PropertyType.INTEGER, "indegree");
	VertexProperty<Integer,Integer> outdegree = graph.createVertexProperty(PropertyType.INTEGER, "outdegree");
	VertexProperty<Integer,Integer> degree = graph.createVertexProperty(PropertyType.INTEGER, "degree");
  	System.out.println("\n run start");

  	AnalysisResult <Integer> result = DegreeTest.run(graph,ID,indegree,outdegree, degree);
	

    	System.out.println("\n run ended");
    	System.out.println("Total number of nodes:"+ID.size());
    	HashSet<Integer> communities = new HashSet<Integer>();
    	for(int i=0; i<ID.size();i++)
	{
		System.out.println("\n the ID is  "+i +"  in-degree of nodes=  "+indegree.get(i));
		System.out.println("\n the ID is  "+i +"  out-degree of nodes=  "+outdegree.get(i));
		System.out.println("\n the ID is  "+i +"  degree of nodes=  "+degree.get(i));
	}

    	
    	System.out.println("Total # nodes= " + result.getReturnValue() + " (took " + result.getExecutionTimeMs() + "ms)");
  }
}

