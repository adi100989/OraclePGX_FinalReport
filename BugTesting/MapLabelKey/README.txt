// Code to test if this bug still exists in the newer version of pgx

	 javac  -cp lib/embedded/*:lib/common/*:third-party/* -d classes examples/java/MapLabelKey.java

	java -cp lib/embedded/*:lib/common/*:third-party/*:classes:conf MapLabelKey > ../bug_list/MapLabelKey_output


//The output shows that this bug in which node properties were used as keys to access maps being their keys is resolved.