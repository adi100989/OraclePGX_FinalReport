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
import java.lang.*;

public class FlajoletMartin {
  public static final int RAND_MAX = 32767;
  public static final int BITMASK_LENGTH = 64;
  public static Random rand = new Random();
  
  public static float myrand(){
  	return  (float) ( rand.nextInt(RAND_MAX) / (RAND_MAX + 1.0) );
 
  }
  
  public static int hash_value(){
  	int ret = 0;
  	while(myrand() < 0.5){
  		ret ++;
  	} 
  	
  	return ret;
  }

//  int k = 10;  //duplication of bitmask
//  int max_iteration = 256;	// max number of iteration

  public static Integer[] create_hashed_bitmask(){
  	Integer[] bit_mask = new Integer[BITMASK_LENGTH];
  	for(int i = 0; i < BITMASK_LENGTH; i++) {
    		bit_mask[i] = Integer.valueOf(0); 
	}	
  	int hash = hash_value();
  	bit_mask[hash] = Integer.valueOf(1);
  	return bit_mask;
  	
  } 
  public static int rho(long v) {
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
	int k = 1;  //duplication of bitmask
	int max_iteration = 256;      // max number of iteration

	System.out.println("\n entered Java Program");
    	PgxSession session = Pgx.createSession("my-session");

	//PgxGraph graph = session.readGraphWithProperties("/var/services/homes/yoshen/work/projects/graph-pgx/datasets/soc-LiveJournal1.vid.adj.json");
	PgxGraph graph = session.readGraphWithProperties("../github/OraclePGX_FinalReport/Link_Prediction/facebook.json");
	
        CompiledProgram FlajoletMartin = session.compileProgram("../github/OraclePGX_FinalReport/diameter/FlajoletMartin.gm");

    
    	System.out.println("\n graph loaded");
	VertexProperty<Integer,Integer> ID = graph.getVertexProperty("nodeID");
        System.out.println("\n Number of nodes in the graph  = "+(int)ID.size());

	/*
		generate a set of hashfunctions using random numbers as seeds
	*/
	//generate 1 array of 64 random numbers , which will be used as seeds for hashing later
	/*	
	int[] seed = new int[64];
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
	System.out.println("\n size to be used = "+size);
	PrintWriter writer = new PrintWriter(new FileOutputStream("../github/OraclePGX_FinalReport/diameter/bitmasks.txt", false));
   	VertexProperty<Integer,PgxVect<Integer>> bit_mask = graph.createVertexVectorProperty(PropertyType.INTEGER, BITMASK_LENGTH, "bit_mask");
   	

        //VertexProperty<Integer,PgxVect<Integer>> bitString = graph.createVertexVectorProperty(PropertyType.INTEGER,(int)Math.pow(2,size)*k, "bitString");
	VertexProperty<Integer,Integer> radius = graph.createVertexProperty(PropertyType.INTEGER,"radius");
	VertexProperty<Integer,PgxVect<Integer>> bitString = graph.createVertexVectorProperty(PropertyType.INTEGER, BITMASK_LENGTH, "bitString");
   
	Iterable<Map.Entry<PgxVertex<Integer>,Integer>> id_iterator = ID.getValues();
    	  // Display elements 
     	int i = 0;
     	for(Map.Entry me : id_iterator) {
     		Integer[] bitmask = create_hashed_bitmask() ;
     	 
         	PgxVect<Integer> bm = new PgxVect(bitmask, PropertyType.INTEGER ) ;
         	System.out.println(i++);
         	//System.out.println(bm);
         	bit_mask.set((PgxVertex<Integer>)me.getKey(), bm);
         
         	writer.print((PgxVertex<Integer>)me.getKey() + " : " );
         	writer.println(bit_mask.get((PgxVertex<Integer>)me.getKey()));
   	 	//writer.println(me.getValue());
    		}
    	System.out.println("Done");    
   	writer.close();


	//CompiledProgram FlajoletMartin = session.compileProgram("../github/OraclePGX_FinalReport/diameter/FlajoletMartin.gm");

        System.out.println("\n After Compiling greenmarl Java Program");

     	System.out.println("\n run start");
	 AnalysisResult <Integer> result = FlajoletMartin.run(graph,ID,BITMASK_LENGTH ,k ,max_iteration,bit_mask, bitString, radius);

	writer = new PrintWriter(new FileOutputStream("../github/OraclePGX_FinalReport/diameter/radius_results.txt", false));
    
    Iterable<Map.Entry<PgxVertex<Integer>,Integer>> radius_iterator = radius.getValues();
    	  // Display elements 
     for(Map.Entry me : radius_iterator) {
         //Map.Entry me = (Map.Entry)radius_iterator.next();
         writer.print(me.getKey() + ": ");
   	 writer.println(me.getValue());
    
         //System.out.print(me.getKey() + ": ");
         //System.out.println(me.getValue());
      }
    writer.close();
    
     writer = new PrintWriter(new FileOutputStream("../github/OraclePGX_FinalReport/diameter/bitmask_results.txt", false));
    
   //Iterable<Map.Entry<PgxVertex<Integer>,PgxVect<Integer>>> bm_iterator = bit_mask.getValues();
  Iterable<Map.Entry<PgxVertex<Integer>,PgxVect<Integer>>> bm_iterator = bitString.getValues();
    	  // Display elements 
     for(Map.Entry me : bm_iterator) {
         //Map.Entry me = (Map.Entry)radius_iterator.next();
         writer.print(me.getKey() + ": ");
   	 writer.println(me.getValue());
    
         //System.out.print(me.getKey() + ": ");
         //System.out.println(me.getValue());
      }
    writer.close();
    System.out.println("Result = " + result.getReturnValue() + " (took " + result.getExecutionTimeMs() + "ms)");

	//double avg = 0.0;	
	//for(int i =0;i<1;i++)
//	{  	
//		long startTime = System.currentTimeMillis();
		// here k = 10
//		AnalysisResult <Integer> result = FlajoletMartin.run(graph,ID,BITMASK_LENGTH ,k ,max_iteration,bit_mask, bitString, radius);
//		long endTime   = System.currentTimeMillis();
//		long totalTime = endTime - startTime;
//		avg = avg + totalTime;
//		System.out.println(totalTime);
//		AnalysisResult <Integer> result = commonNeighbors5.run(graph,ID,(int)ID.size(), link_node);
//	}
//	System.out.println("the average run time over 20 runs == "+avg/20);
/*    	System.out.println("\n run ended");
	for(i=0; i< ID.size();i++)
	{
		System.out.print("\n NODE : "+i);
		System.out.print(" Radius  "+radius.get(i));
		System.out.println();
	}
    	System.out.println("diameter = " + result.getReturnValue() + " (took " + result.getExecutionTimeMs() + "ms)");
*/  }
}

