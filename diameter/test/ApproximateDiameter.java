/**
 * Copyright (C) 2013 - 2015 Oracle and/or its affiliates. All rights reserved.
 */


import oracle.pgx.api.CompiledProgram;
import oracle.pgx.api.Pgx;
import oracle.pgx.api.PgxGraph;
import oracle.pgx.api.PgxVertex;
import oracle.pgx.api.PgxSession;
import oracle.pgx.api.internal.AnalysisResult;
import oracle.pgx.api.VertexProperty;
import oracle.pgx.common.types.PropertyType;
import oracle.pgx.common.util.vector.Vect;
import oracle.pgx.api.PgxVect;
import java.lang.*;
import java.util.*;
import java.io.PrintWriter;
import java.io.FileOutputStream;

public class ApproximateDiameter {
  public static final int RAND_MAX = 32767;
  public static final int BITMASK_LENGTH = 64;
  public static Random rand = new Random();
  
  public static float my_rand(){
  	float res = (float) ( rand.nextInt(RAND_MAX) / (RAND_MAX + 1.0) );
  	return res;
  }
  
  public static int hash_value(){
  	int ret = 0;
  	while(my_rand() < 0.5){
  		ret ++;
  	} 
  	
  	return ret;
  }

  public static Integer[] create_bitmask(){
  	Integer[] bit_mask = new Integer[BITMASK_LENGTH];
  	for(int i = 0; i < BITMASK_LENGTH; i++) {
    		bit_mask[i] = Integer.valueOf(0); 
	}	
  	int hash = hash_value();
  	bit_mask[hash] = Integer.valueOf(1);
  	return bit_mask;
  	
  }
  
  
  public static void main(String[] args) throws Exception {
    PgxSession session = Pgx.createSession("my-session");
    CompiledProgram approximate_diameter = session.compileProgram("../github/OraclePGX_FinalReport/diameter/approximate_diameter.gm");
   // CompiledProgram approximate_diameter = session.compileProgram("examples/gm/node_property_vector.gm");
  // PgxGraph graph = session.readGraphWithProperties("examples/graphs/amazon_graphlab.adj.json");
  PgxGraph graph = session.readGraphWithProperties("../github/OraclePGX_FinalReport/Link_Prediction/facebook.json");
    VertexProperty<Integer,Integer> id = graph.getVertexProperty("id");
    //System.out.println("Number of nodes:"+(int)id.size());
    double n = (double)id.size();
    int maxIter = 256;
    int K =1;
    
    int log_n = (int) Math.log(n);
    VertexProperty<Integer,Integer> radius = graph.createVertexProperty(PropertyType.INTEGER, "radius");
    VertexProperty<Integer,PgxVect<Integer>> bit_string = graph.createVertexVectorProperty(PropertyType.INTEGER, BITMASK_LENGTH, "bit_string");
    //radius.getTopKValues(10)
   PrintWriter writer = new PrintWriter(new FileOutputStream("../github/OraclePGX_FinalReport/diameter/bitmasks.txt", false));
    VertexProperty<Integer,PgxVect<Integer>> bit_mask = graph.createVertexVectorProperty(PropertyType.INTEGER, BITMASK_LENGTH, "bit_mask");
    Iterable<Map.Entry<PgxVertex<Integer>,Integer>> id_iterator = id.getValues();
    	  // Display elements 
     int i = 0;
     for(Map.Entry me : id_iterator) {
     	 Integer[] bitmask = create_bitmask() ;
     	 
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
   
    
    AnalysisResult<Integer> result = approximate_diameter.run(graph, BITMASK_LENGTH,  maxIter, K, bit_mask, bit_string , radius  );
   //  AnalysisResult<Integer> result = approximate_diameter.run(graph, BITMASK_LENGTH, maxIter, K, bit_mask, bit_string, radius  );
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
  Iterable<Map.Entry<PgxVertex<Integer>,PgxVect<Integer>>> bm_iterator = bit_string.getValues();
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
  }

} 
