//to run the program on my machine follow the steps shown below:


	javac  -cp lib/embedded/*:lib/common/*:third-party/* -d classes examples/java/DegreeTest.java

	java -cp lib/embedded/*:lib/common/*:third-party/*:classes:conf DegreeTest > ../bug_list/degree_output


QUESTIONS & OBSERVATIONS:

1. The graph which is run is a directed graph input using the Edge list (input.edge)
	The graph is not a simple graph i.e. There a multiple edges between two nodes.


2. For the undirected graph , the outDegree(),inDegree() and the degree() all return the same number because there is no concept of indegree and outdegree in undirected.
	For the directed graph on the other hand, there degree() should specify the sum of the inDegree() and outDegree().
	But degree() is same as outDegree(). IS this the expected behaviour or should degree be a sum of the indegree/outdegree. Or an Anomaly.? 
	