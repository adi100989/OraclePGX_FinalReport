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
	private static final double PHI = 0.77351D;
        private int m_numHashGroups;
        private int m_numHashFunctionsInHashGroup;
        private HashFunction[][] m_hashes;

        private int m_bitmapSize;
        private boolean[][][] m_bitmaps; // m_bitmaps[i] is the bitmap for the i-th hash.

        private long m_numWords;

        public FlajoletMartin(int bitmapSize, int numHashGroups, int numHashFunctionsInEachGroup) {
                m_numHashGroups = numHashGroups;
                m_numHashFunctionsInHashGroup = numHashFunctionsInEachGroup;
                m_bitmapSize = bitmapSize;

                m_bitmaps = new boolean[numHashGroups][numHashFunctionsInEachGroup][bitmapSize];
                m_hashes = new HashFunction[numHashGroups][numHashFunctionsInEachGroup];

                generateHashFunctions();
        }

        private void generateHashFunctions() {
                Map<Integer, Collection<Integer>> m_and_n_map = new HashMap<Integer, Collection<Integer>>();
                for (int i=0; i<m_numHashGroups; i++) {
                        for (int j=0; j<m_numHashFunctionsInHashGroup; j++) {
                                m_hashes[i][j] = generateUniqueHashFunction(m_and_n_map);
                        }
                }
        }

        private HashFunction generateUniqueHashFunction(Map<Integer, Collection<Integer>> m_and_n_map) {
                // Get odd numbers for both m and n.
                int m = 0;
                do {
                        m = (int) (Integer.MAX_VALUE * Math.random());
                } while (m % 2 == 0);

                // Get pairs that we haven't seen before.
                int n = 0;
                do {
                        n = (int) (Integer.MAX_VALUE * Math.random());
                } while ((n % 2 == 0) || contains(m_and_n_map, m, n));

                // Make a note of the (m, n) pair, so we don't use it again.
                Collection<Integer> valueCollection = m_and_n_map.get(m);
                if (valueCollection == null) {
                        valueCollection = new HashSet<Integer>();
                        m_and_n_map.put(m, valueCollection);
                }
                valueCollection.add(n);

                // Generate hash function with the (m, n) pair.
                // System.out.println("Generating hashFunction with (m=" + m + ", n=" + n + ")");
                return new HashFunction(m, n, m_bitmapSize);
        }

        private static boolean contains(Map<Integer, Collection<Integer>> map, int m, int n) {
                Collection<Integer> valueList = map.get(m);
                return (valueList != null) && (valueList.contains(n));
        }

        public long countUniqueWords(Reader reader) throws IOException {
                LowerCaseTokenizer tokenizer = new LowerCaseTokenizer(reader);
                TermAttribute termAttr = (TermAttribute) tokenizer.getAttribute(TermAttribute.class);
                while(tokenizer.incrementToken()) {
                        String word = termAttr.term();
                        m_numWords++;
                        // Implement Flajolet-Martin algorithm here.
                        for (int i=0; i<m_numHashGroups; i++) {
                                for (int j=0; j<m_numHashFunctionsInHashGroup; j++) {
                                        flajoletMartin(i, j, word);
                                }
                        }
                }
                // Exhausted word list.

                List<Double> averageR = new ArrayList<Double>();
                for (int i=0; i<m_numHashGroups; i++) {
                        int sumR = 0;
                        for (int j=0; j<m_numHashFunctionsInHashGroup; j++) {
                                sumR += (getFirstZeroBit(m_bitmaps[i][j]));
                        }
                        averageR.add(sumR * 1.0 / m_numHashFunctionsInHashGroup);
                }

                // Find the median R and estimate unique words.
                Collections.sort(averageR);
                double r = 0;
                int averageRMid = averageR.size() / 2;
                if (averageR.size() % 2 == 0) {
                        r = (averageR.get(averageRMid) + averageR.get(averageRMid+1))/2;
                } else {
                        r = averageR.get(averageRMid + 1);
                }

                return (long) (Math.pow(2, r) / PHI);
        }
        private void flajoletMartin(int hashGroup, int hashNumWithinGroup, String word) {
                HashFunction f = m_hashes[hashGroup][hashNumWithinGroup];
                long v = f.hash(word);
                int index = rho(v);
                //System.out.println("hash(" + word + ")=" + v + ", rho=" + index);
                if (!m_bitmaps[hashGroup][hashNumWithinGroup][index]) {
                        //System.out.println("hash(" + word + ")=" + v + ", rho=" + index);
                        //System.out.println("Setting m_bitmaps[" + hashGroup + "][" + hashNumWithinGroup + "][" + index + "]");
                        m_bitmaps[hashGroup][hashNumWithinGroup][index] = true;
                }
        }

        private int rho(long v) {
                int rho = 0;
                for (int i=0; i<m_bitmapSize; i++) { // size of long=64 bits.
                        if ((v & 0x01) == 0) {
                                v = v >> 1;
                        rho++;
                        } else {
                                break;
                        }
                }
                return rho == m_bitmapSize ? 0 : rho;
        }

        private static int getFirstZeroBit(boolean[] b) {
                for (int i=0; i<b.length; i++) {
                        if (b[i] == false) {
                                return i;
                        }
                }
                return b.length;
        }

        private static class HashFunction {
                private int m_m;
                private int m_n;
                private int m_bitmapSize;
                private long m_pow2BitmapSize;

                public HashFunction(int m, int n, int bitmapSize) {
                        if (bitmapSize > 64) {
                                throw new IllegalArgumentException("bitmap size should be at max. 64");
                        }
                        this.m_m = m;
                        this.m_n = n;
                        m_bitmapSize = bitmapSize;

                        m_pow2BitmapSize = 1 << m_bitmapSize;
                }

                public long hash(String s) {
                        int hashCode = s.hashCode();
                        return m_m + m_n * hashCode;
                }
        }







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
	CompiledProgram approxDiameter = session.compileProgram("../github/OraclePGX_FinalReport/diameter/approxDiameter.gm");

        System.out.println("\n After Compiling greenmarl Java Program");

     	System.out.println("\n run start");
//	double avg = 0.0;	
//	for(int i =0;i<1;i++)
//	{  	
//		long startTime = System.currentTimeMillis();
		AnalysisResult <Integer> result = approxDiameter.run(graph,ID,(int)ID.size()+1,radius);
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

