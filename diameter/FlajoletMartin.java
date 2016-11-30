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
import java.io.*;

public class FlajoletMartin {
 
  public  static int rho(long v) {
                int rho = 0;
                for (int i=0; i<64; i++) { // size of long=64 bits.
                        if ((v & 0x01) == 0) {
                                v = v >> 1;
                        rho++;
                        } else {
                                break;
                        }
                }
		System.out.println("rho ="+rho);
                return rho == 64 ? 0 : rho;
        }




  public static void main(String[] args) throws Exception {
	System.out.println("\n entered Java Program");
    	PgxSession session = Pgx.createSession("my-session");

//	PgxGraph graph = session.readGraphWithProperties("/var/services/homes/yoshen/work/projects/graph-pgx/datasets/soc-LiveJournal1.vid.adj.json");
	PgxGraph graph = session.readGraphWithProperties("../github/OraclePGX_FinalReport/Link_Prediction/facebook.json");
    
    	System.out.println("\n graph loaded");
	VertexProperty<Integer,Integer> ID = graph.getVertexProperty("nodeID");
        System.out.println("\n the size is = "+(int)ID.size());

	/*
		generate a set of hashfunctions using random numbers as seeds
	*/
	//generate 1 array of 64 random numbers , which will be used as seeds for hashing later
/*	int[] seed = new int[64];
	for (int i = 0 ; i < 64; i++)
		{
		 	seed[i] = (int )(Math.random() * 64 + 1);
		}
        //getting the initial value of the hash function for k =10       
	int k = 10;
	int[][] bitmaps = new int[k][];
	//to get a unique int value , convert nodeId to string and then hash it
	int i =0;
	int nodeid = 4001;
	String numberAsString = String.valueOf(nodeid);
	long v = (long) seed[i]+seed[i+1]*numberAsString.hashCode();
        int index = rho(v);
	System.out.println("The index is "+index);
        //if (!bitmaps[hashGroup][hashNumWithinGroup][index]) {
        //        bitmaps[hashGroup][hashNumWithinGroup][index] = true;
        //        }	
*/
	int size = (int)Math.ceil(Math.log(ID.size())/Math.log(2));
	System.out.println("size = "+size);
	int k = 2;		


        //VertexProperty<Integer,PgxVect<Integer>> bitString = graph.createVertexVectorProperty(PropertyType.INTEGER,(int)Math.pow(2,size)*k, "bitString");
	VertexProperty<Integer,Integer> radius = graph.createVertexProperty(PropertyType.INTEGER,"radius");
	CompiledProgram FlajoletMartin = session.compileProgram("../github/OraclePGX_FinalReport/diameter/FlajoletMartin.gm");

        System.out.println("\n After Compiling greenmarl Java Program");

     	System.out.println("\n run start");
//	double avg = 0.0;	
//	for(int i =0;i<1;i++)
//	{  	
//		long startTime = System.currentTimeMillis();
		// here k = 10
		AnalysisResult <Integer> result = FlajoletMartin.run(graph,ID,((int)Math.pow(2,size))*k ,size ,k ,radius);
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
//		System.out.println("\n the list node count for  "+i+" count "+radius.get(i));
//	}
    	System.out.println("diameter = " + result.getReturnValue() + " (took " + result.getExecutionTimeMs() + "ms)");
  }
}

