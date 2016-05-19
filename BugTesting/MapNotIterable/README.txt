####### TO SEE IF MAPS CAN BE ITERATED AS THE OTHER COLLECTION CLASSES#####

	 javac  -cp lib/embedded/*:lib/common/*:third-party/* -d classes examples/java/MapNotIterable.java
	
	  java -cp lib/embedded/*:lib/common/*:third-party/*:classes:conf MapLabelKey > ../bug_list/MapLabelKey_output
 


#####################################################################################
I wanted to see if the map object can be iterated over using keys like the other collection types like nodesets etc.
But I get the error that map is not a collection type object. 
Thus wanted to know how do we iterate over a map object.

one workaround is that we can store the keys of the map object into a set, that can be accessed to iterate over the map.



####################################################################################
ERROR:


Caused by: java.lang.IllegalArgumentException: Compilation of null failed: gm_comp returned status code 1: 
GM_code_rivepllbjmhot5vqqsjl5b7uki.gm:19:12: error: sample is not a collection type object.
item is not defined.
