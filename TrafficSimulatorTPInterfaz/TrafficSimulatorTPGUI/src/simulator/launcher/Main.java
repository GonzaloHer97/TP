package simulator.launcher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import simulator.control.Controller;
import simulator.factories.*;
import simulator.misc.SortedArrayList;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.LightSwitchingStrategy;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimulator;
import simulator.model.Vehicle;
import simulator.view.MainWindow;

public class Main {

	private final static Integer _timeLimitDefaultValue = 10;
	private static String _inFile = null;
	private static String _outFile = null;
	private static InputStream in = null;
	private static OutputStream out = null;
	private static int _time = -1;
	private static String mode = "gui";
	private static Boolean batchMode = false;
	private static Boolean guiMode = true;
	
	private static Factory<LightSwitchingStrategy> lssFactory = null;
	private static Factory<DequeuingStrategy> dqsFactory = null;
	private static Factory<Event> _eventsFactory = null;
	

	private static void parseArgs(String[] args) throws FileNotFoundException {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseModeOption(line);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseTicksOption(line);
			

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file.").build());
		cmdLineOptions.addOption(Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message.").build());
		cmdLineOptions.addOption(Option.builder("t").longOpt("ticks").hasArg().desc("Ticks to the simulator’s main loop (default value is 10).").build());
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Start the aplication in batch mode or GUI mode(default value is GUI mode)").build());

		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException, FileNotFoundException {
		_inFile = line.getOptionValue("i");
		if (_inFile == null && guiMode == false) {
			throw new ParseException("An events file is missing");
		}
		else if (_inFile == null && guiMode == true) {
			//no hace nada	
		}
		else
			in = new FileInputStream(_inFile);
			
	}

	private static void parseOutFileOption(CommandLine line) throws FileNotFoundException {
		if(guiMode != true) {
			_outFile = line.getOptionValue("o");
			if (_outFile != null) {
				out = new FileOutputStream(_outFile);
			}
			else
				out = System.out;
		}
	}
	
	private static void parseTicksOption(CommandLine line) throws ParseException {
		if (line.hasOption("t")) {
			_time = Integer.parseInt(line.getOptionValue("t"));
		}
	}
	
	private static void parseModeOption(CommandLine line) throws ParseException {
		if (line.hasOption("m")) {
			mode = line.getOptionValue("m", "gui");
			if (mode.equalsIgnoreCase("console")) {
				batchMode = true;
				guiMode = false;
			}
			else if (!mode.equalsIgnoreCase("gui") && !mode.equalsIgnoreCase("console"))
				throw new ParseException("wrong mode option");
		}
	}
	
	

	private static void initFactories() {
		//LightSwitchingStrategy
		List<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
		lsbs.add( new RoundRobinStrategyBuilder());
		lsbs.add( new MostCrowdedStrategyBuilder() );
		lssFactory = new BuilderBasedFactory<>(lsbs);
		//DequeuingStrategy
		List<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
		dqbs.add( new MoveFirstStrategyBuilder() );
		dqbs.add( new MoveAllStrategyBuilder() );
		dqsFactory = new BuilderBasedFactory<>(dqbs);
		//Events
		List<Builder<Event>> ebs = new ArrayList<>();
		ebs.add( new NewJunctionEventBuilder(lssFactory,dqsFactory) );
		ebs.add( new NewCityRoadEventBuilder() );
		ebs.add( new NewInterCityRoadEventBuilder() );
		ebs.add( new NewVehicleEventBuilder() );
		ebs.add( new SetWeatherEventBuilder() );
		ebs.add( new SetContClassEventBuilder() );
		_eventsFactory = new BuilderBasedFactory<>(ebs);

	}
	
	private static RoadMap initRoadMap(){
		List<Junction> junList = new ArrayList<Junction>();
		List<Road> roadList = new ArrayList<Road>();
		List<Vehicle> vehiList = new ArrayList<Vehicle>();
		Map<String,Junction> junMap = new HashMap<String,Junction>();
		Map<String,Road> roadMap =  new HashMap<String,Road>();
		Map<String,Vehicle> vehiMap = new HashMap<String,Vehicle>();
		RoadMap road = new RoadMap(junList,roadList, vehiList, junMap, roadMap, vehiMap);
		return road;
	}

	private static void startBatchMode() throws Exception {
		List<Event> listEvent = new SortedArrayList<Event>();
		RoadMap road = initRoadMap();
		TrafficSimulator tfs = new TrafficSimulator(road,listEvent, 0);
		Controller crtl = new Controller(tfs,_eventsFactory);
		
		crtl.loadEvents(in);
		if(_time != -1)
			crtl.run(_time, out);
		else
			crtl.run(_timeLimitDefaultValue, out);
	}
	
	private static void startGUIMode() throws Exception {
		List<Event> listEvent = new SortedArrayList<Event>();
		RoadMap road = initRoadMap();
		TrafficSimulator tfs = new TrafficSimulator(road,listEvent, 0);
		Controller crtl = new Controller(tfs,_eventsFactory);
		if(in != null) {
			crtl.loadEvents(in);
		}	
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainWindow(crtl);
			}
		});
		
	}

	private static void start(String[] args) throws Exception {
		initFactories();
		parseArgs(args);
		if(batchMode == true)
			startBatchMode();
		else
			startGUIMode();
	}

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// --help

	public static void main(String[] args) {
		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
