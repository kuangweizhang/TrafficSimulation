import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import Simulator.Simulator;
import Utility.Configurations;
import Utility.TimeInterval;

public class TrafficSimulation
{

	private static String DefaultConfig = "Config.xml";
	/**
	 * Print out report per how many time ticks.
	 */
	private static int ReportFrequency = 1;

	private static TimeInterval RunningTime = new TimeInterval(100, 0, 0);

	public static void main(String[] args) throws Exception
	{
		String configFileNameString = ParseArgs(args);
		Configurations configs = ParseConfigFile(configFileNameString);
		Simulator simulator = new Simulator(configs);

		int tickCount = 0;
		while (simulator.WorldClock.earlierThan(RunningTime))
		{
			simulator.AddVehicleToSystem();
			simulator.Run();
			if (tickCount == ReportFrequency)
			{
				tickCount = 0;
				System.out.println(simulator.getTimeTickReport());
			}
			tickCount++;
		}
		
		simulator.FinishRun();
	}

	private static Configurations ParseConfigFile(String FileName)
			throws Exception
	{
		if (FileName.isEmpty())
		{
			throw new Exception("Config File Name is empty");
		}

		Configurations retval = new Configurations();

		File xmlFile = new File(FileName);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();
		org.w3c.dom.Document document = documentBuilder.parse(xmlFile);
		retval.setRoutingAlgorithm((document)
				.getElementsByTagName("RoutingAlgo").item(0).getTextContent());
		retval.setRoutingOption((document)
				.getElementsByTagName("RoutingOption").item(0).getTextContent());
		retval.setRoutingDelayOption((document)
				.getElementsByTagName("DelayOption").item(0).getTextContent());
		retval.setMapFile((document).getElementsByTagName("MapFile").item(0)
				.getTextContent());
		retval.setLogging((document).getElementsByTagName("DetailLog").item(0)
				.getTextContent());
		retval.setLogging((document).getElementsByTagName("VehicleLog").item(0)
				.getTextContent());
		retval.setVehicleGenerateRate(Double.parseDouble(((document).getElementsByTagName("VehicleGenerateRate").item(0)
				.getTextContent())));
		String seedValue = ((document).getElementsByTagName("RandomSeed").item(0)
				.getTextContent());
		if(seedValue.equals("-"))
		{
			retval.setRandomSeed(System.currentTimeMillis());
		}
		else
		{
			retval.setRandomSeed(Integer.parseInt(seedValue));
		}
		return retval;
	}

	private static String ParseArgs(String[] args)
	{
		if (args.length == 0)
		{
			System.out.println("Using default config file");
			return DefaultConfig;
		} else
		{
			if (args.length != 1)
			{
				System.err.println("Usage: ConfigFileName");
				System.exit(1);
				return "";
			} else
			{
				return args[0];
			}
		}
	}
}
