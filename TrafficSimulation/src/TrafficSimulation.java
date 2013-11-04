import java.io.File;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import Simulator.Simulator;
import Utility.Configurations;


public class TrafficSimulation {

	private static String DefaultConfig = "Config.xml";
	
	public static void main(String[] args) throws Exception
	{
		String configFileNameString = ParseArgs(args);
		Configurations configs = ParseConfigFile(configFileNameString);
		Simulator simulator = new Simulator(configs);
		for(int i = 0; i <= 1000; i++)
		{
			simulator.AddVehicle();
		}
		for(int i = 0; i <= 1000; i++)
		{
			simulator.Run();
		}
	}
	
	private static Configurations ParseConfigFile(String FileName) throws Exception
	{
		if (FileName.isEmpty())
		{
			throw new Exception("Config File Name is empty");
		}
		
		Configurations retval = new Configurations();
		
		File xmlFile = new File(FileName);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
		        .newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = (Document) documentBuilder.parse(xmlFile);
		retval.setRoutingAlgorithm(((org.w3c.dom.Document) document).
				getElementsByTagName("RoutingAlgo").item(0).getTextContent());
		retval.setRoutingAlgorithm(((org.w3c.dom.Document) document).
				getElementsByTagName("MapFile").item(0).getTextContent());
		return retval;
		
	}
	
	private static String ParseArgs(String[] args)
	{
		if (args.length == 0)
		{
			System.out.println("Using default config file");
			return DefaultConfig;
		}
		else 
		{
			if(args.length != 1)
			{
				System.err.println("Usage: ConfigFileName");
				System.exit(1);
				return "";
			}
			else 
			{
				return args[0];
			}
		}
	}
}
