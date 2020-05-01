package cnvqc.annotator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class CNVSampleAnnotatorOptions {

	private Options options;
	
	private File rawCNVs;
	private File output;
	private File sampleFile;
	private File qcsumFile;
	private File tmpDirectory;
	private int sectionOfFile;
	private File xhmmFile;
	private File canoesFile;
	private File clammsFile;
	private File axiomFile;
	private File wesFile;
	
	public CNVSampleAnnotatorOptions (String args[]) {
		
		options = setOptions();
		loadOptions(args);
		
	}
		
	public File getRawCNVs() {
		return rawCNVs;
	}
	public File getOutput() {
		return output;
	}
	public File getSampleFile() {
		return sampleFile;
	}
	public File getQCsumFile() {
		return qcsumFile;
	}
	public File getTmpDirectory() {
		return tmpDirectory;
	}
	public int getSectionOfFile() {
		return sectionOfFile;
	}
	public File getXHMMFile() {
		return xhmmFile;
	}
	public File getCLAMMSFile() {
		return canoesFile;
	}
	public File getCANOESFile() {
		return clammsFile;
	}
	public File getAxiomFile() {
		return axiomFile;
	}
	public File getWESFile() {
		return wesFile;
	}
	
	private Options setOptions() {
		
		List<Option> optionsList = new ArrayList<Option>();
		Options options = new Options();
		
		optionsList.add(new Option("p", true, "Path to CNVs generated by PennCNV."));
		optionsList.add(new Option("o", true, "Path to output file."));
		optionsList.add(new Option("h", true, "Fasta reference file."));
		optionsList.add(new Option("axiom", true, "Path to the axiom probe list bgziped/tabixed bed file."));
		optionsList.add(new Option("wes", true, "Path to the WES probe list bgziped/tabixed bed file."));
		optionsList.add(new Option("samples", true, "Samples list file."));
		optionsList.add(new Option("qcsum", true, "concatenated qcsum file from PennCNV."));
		
		for (Option opt : optionsList) {
			opt.setRequired(true);
			options.addOption(opt);
		}
		
		options.addOption(new Option("s", true, "If included, section of CNV file to annotate in 500 line chunks. If not included, annotate entire file."));
		options.addOption(new Option("clamms", true, "Path to CLAMMS CNV calls."));
		options.addOption(new Option("xhmm", true, "Path to XHMM CNV calls."));
		options.addOption(new Option("canoes", true, "Path to CANOES CNV calls."));
		options.addOption(new Option("help", false, "Print help message."));
		options.addOption(new Option("tmp", true, "Path to tmp directory."));
		
		return options;
		
	}
	
	private void loadOptions(String args[]) {
		
		CommandLineParser parser = new BasicParser();
		CommandLine cmd = null;
				
		try {
			 cmd = parser.parse(options, args);
		} catch (org.apache.commons.cli.ParseException e) {
			System.out.println();
			ThrowHelp(e.getMessage());
		}
			
		if (cmd.hasOption("help")) {
			ThrowHelp("");
		}
		
		rawCNVs = new File(cmd.getOptionValue("p"));
		output = new File(cmd.getOptionValue("o"));
		sampleFile = new File(cmd.getOptionValue("samples"));
		qcsumFile = new File(cmd.getOptionValue("qcsum"));
				
		if (cmd.hasOption("s")) {
			sectionOfFile = Integer.parseInt(cmd.getOptionValue("s"));
		} else {
			sectionOfFile = -1;
		}
		if (cmd.hasOption("clamms")) {
			clammsFile = new File(cmd.getOptionValue("clamms"));
		} else {
			clammsFile = null;
		}
		if (cmd.hasOption("xhmm")) {
			xhmmFile = new File(cmd.getOptionValue("xhmm"));
		} else {
			xhmmFile = null;
		}
		if (cmd.hasOption("canoes")) {
			canoesFile = new File(cmd.getOptionValue("canoes"));
		} else {
			canoesFile = null;
		}
		if (cmd.hasOption("axiom")) {
			axiomFile = new File(cmd.getOptionValue("axiom"));
		} else {
			axiomFile = null;
		}
		if (cmd.hasOption("wes")) {
			wesFile = new File(cmd.getOptionValue("wes"));
		} else {
			wesFile = null;
		}
		if (cmd.hasOption("tmp")) {
			tmpDirectory = new File(cmd.getOptionValue("tmp"));
		} else {
			tmpDirectory = new File(System.getProperty("java.io.tmpdir"));
			System.out.println(tmpDirectory.getAbsolutePath());
		}
		
	}
	
	private void ThrowHelp(String top) {
		String header = "Axiom Array CNV Annotator\n\n\n";
		String footer = "\n\n(c) Eugene Gardner 2018";
		String usage = "java -jar CNVAnnotator.jar Annotate <options>";
		System.out.println();
		System.out.println(top);
		HelpFormatter formatter = new HelpFormatter();
		System.out.println();
		formatter.printHelp(9999, usage, header, options, footer);
		System.exit(1);
	}
	
}