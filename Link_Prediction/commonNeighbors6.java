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
//import oracle.pgx.filter.expressions.*;

//public enum CollectionType extends java.lang.Enum<CollectionType> implements Type;
public class commonNeighbors6 {

  public static void main(String[] args) throws Exception {
	System.out.println("\n entered Java Program");
    	PgxSession session = Pgx.createSession("my-session");
    	CompiledProgram commonNeighbors6 = session.compileProgram("../github/OraclePGX_FinalReport/Link_Prediction/commonNeighbors6.gm");
	//CompiledProgram label_propagation = session.compileProgram("/var/services/homes/yoshen/work/projects/graph-pgx/first-oracle-report/label_propagation.gm");   
  	System.out.println("\n After Compiling greenmarl Java Program");
//	PgxGraph graph = session.readGraphWithProperties("/var/services/homes/yoshen/work/projects/graph-pgx/datasets/soc-LiveJournal1.vid.adj.json");
	PgxGraph graph = session.readGraphWithProperties("../github/OraclePGX_FinalReport/Link_Prediction/facebook.json");

//	PgxGraph graph = session.readGraphWithProperties("/var/services/homes/adisingh/code/facebook.json");
	//	PgxGraph graph = session.readGraphWithProperties("/var/services/homes/adisingh/code/small_graph.json");
    //	graph=graph.undirect();
    
    	System.out.println("\n graph loaded");
   	// PgxGraph subgraph = graph.filter(new VertexFilter("nodeID < 100"));	
    	VertexProperty<Integer,Integer> ID = graph.getVertexProperty("nodeID");
	VertexProperty<Integer,String> link_node = graph.createVertexProperty(PropertyType.STRING, "link_node");
    	//collection<nodeSeq> link_node = graph.createVertexProperty(PropertyType.INTEGER, "link_node_ID");
	
        //VertexProperty<Integer,PgxVect<Integer>> link_node = graph.createVertexProperty(PropertyType.SPARSE, "link_node");

	//Scalar<PgxVect<Integer>>  link_node  = graph.createVectorScalar(PropertyType.INTEGER,40000,"link_node");		
  	//PgxVect.PgxVect(Integer[] link_node, PropertyType.INTEGER); 
  	//    PgxGraph subgraph = graph.filter(new VertexFilter("name.nodeID < 100"));
     	System.out.println("\n run start");

  	AnalysisResult <Integer> result = commonNeighbors6.run(graph,ID, link_node);
	

    	System.out.println("\n run ended");
    //	System.out.println("Total number of nodes:"+label.size());
    //	HashSet<Integer> communities = new HashSet<Integer>();
    	int li=0;
	//for(int i=0; i< link_node.size();i++)
	{
	//	li=label.get(i);
		//System.out.println("\n the max prob node for "+i +"is "+link_node.get(i)+"  with prob = "+cmn_nbr.get(i));
//		communities.add(li);
	}
	//	System.out.println("\n the label is "+label.get(0));
	//	 System.out.println("\n the label is "+label.get(1));	
//    	System.out.println("Size of the community:"+communities.size());
    	System.out.println("Total common neighbors= " + result.getReturnValue() + " (took " + result.getExecutionTimeMs() + "ms)");
  }
}

