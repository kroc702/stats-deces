module stats_deces {
	requires com.google.gson;

	// Open package for reflective access to Gson
	opens pop_statistics to com.google.gson;

}