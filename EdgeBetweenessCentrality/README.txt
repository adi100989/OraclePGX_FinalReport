#############   Test for edge deletion  #############

1. 	 javac  -cp lib/embedded/*:lib/common/*:third-party/* -d classes examples/java/EdgeDeletion.java
	 
	 java -cp lib/embedded/*:lib/common/*:third-party/*:classes:conf EdgeDeletion  > ../bug_list/EdgeDeletion_output


2. this works. passing the total number of deleted edges which pass through the node whih has the max BC value as return value.
