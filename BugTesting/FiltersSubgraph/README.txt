TEST to check if the filter packages work for subgraph creation:
**************************************************************


1. a.	import oracle.pgx.api.filter.*;            // This works for subgraph creation

   b.	import oracle.pgx.filter.expressions.*;   //package does not exist ERROR! 

2. 	The documentation mentions the  import b. Example for creating and using an edge filter and Example for creating and using an vertex filter
    	in https://docs.oracle.com/cd/E56133_01/1.2.0/tutorials/subgraph.html. but this does not work. shows the above error.
	The import a. seems to work in creating the subgraph as shown in the code along with this.
		