To run it on my system :

	 javac  -cp lib/embedded/*:lib/common/*:third-party/* -d classes examples/java/UndirectCompilationExample.java


 //to run as undirected graph	
	 java -cp lib/embedded/*:lib/common/*:third-party/*:classes:conf UndirectCompilationExample > undirected
 

 //to run as directed graph uncomment the line graph = graph.undirect() in the java code
	java -cp lib/embedded/*:lib/common/*:third-party/*:classes:conf UndirectCompilationExample > directed
 

// get the diff of files to see the difference
	diff ../bug_list/directed ../bug_list/undirected

