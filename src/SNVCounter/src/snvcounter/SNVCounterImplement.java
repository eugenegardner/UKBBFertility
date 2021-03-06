package snvcounter;

import snvcounter.annotator.SNVCounter;
import snvcounter.utilities.SNVCounterOptions;

public class SNVCounterImplement {

	// Just an entry-point into the JAR
	public static void main(String[] args) throws Exception {

		SNVCounterOptions options = new SNVCounterOptions(args);	
		SNVCounter counter = new SNVCounter(options.getOptions());
		
		counter.doWork();
		
	}

}
