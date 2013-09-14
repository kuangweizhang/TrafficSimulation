import java.io.File;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import Utility.Configurations;


public class TrafficSimulation {

	private static String DefaultConfig = "Config.xml";
	
	public static void main(String[] args) throws Exception
	{
		String configFileNameString = ParseArgs(args);
		Configurations configs = ParseConfigFile(configFileNameString);
		
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
		retval.setRoutingAlgo(((org.w3c.dom.Document) document).
				getElementsByTagName("RoutingAlgo").item(0).getTextContent());
		
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
