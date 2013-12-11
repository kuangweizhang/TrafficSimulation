package Utility;

public class Configurations
{
	public static final int NumberOfCategoriesOfVehicle = 3;
	private RoutingAlgorithm[] RoutingAlgorithms = new RoutingAlgorithm[NumberOfCategoriesOfVehicle];
	private RoutingOption[] RoutingOptions = new RoutingOption[NumberOfCategoriesOfVehicle];
	private RoutingDelayOption[] RoutingDelayOptions = new RoutingDelayOption[NumberOfCategoriesOfVehicle];
	private int[] VehiclePercentages = new int[3];
	private String MapFile;
	private long RandomSeed;
	private boolean Logging;
	private boolean VehicleLogging;
	private double VehicleGenerateRate;


	public int[] getVehiclePercentages()
	{
		return VehiclePercentages;
	}

	public void setVehiclePercentages(String value, int sequence) throws Exception
	{
		if(sequence < 0 || sequence >= NumberOfCategoriesOfVehicle)
		{
			throw new Exception("Sequence request is invalid:" + sequence);
		}
		if(sequence < 0 || sequence >= NumberOfCategoriesOfVehicle)
		{
			throw new Exception("Sequence request is invalid:" + sequence);
		}
		int percent = Integer.parseInt(value);
		if(percent < 0 || percent > 100)
		{
			throw new Exception("Percentage out of bound:" + value);
		}
		this.VehiclePercentages[sequence] = percent;
	}
	
	public boolean isVehicleLogging()
	{
		return VehicleLogging;
	}

	public void setVehicleLogging(String loggingOption) throws Exception
	{
		if (loggingOption.isEmpty())
		{
			throw new Exception("Logging Option is empty");
		}
		if (loggingOption.equals("True"))
		{
			this.VehicleLogging = true;
			return;
		}
		if (loggingOption.equals("False"))
		{
			this.VehicleLogging = false;
			return;
		}
		throw new Exception("Logging Option not supported:" + loggingOption);
	}

	public double getVehicleGenerateRate()
	{
		return VehicleGenerateRate;
	}

	public void setVehicleGenerateRate(double rate)
	{
		VehicleGenerateRate = rate;
	}

	public boolean isLogging()
	{
		return Logging;
	}

	public RoutingDelayOption[] getRoutingDelayOption()
	{
		return RoutingDelayOptions;
	}

	public void setRoutingDelayOption(RoutingDelayOption routingDelayOption,
			int sequence) throws Exception
	{
		if(sequence < 0 || sequence >= NumberOfCategoriesOfVehicle)
		{
			throw new Exception("Sequence request is invalid:" + sequence);
		}
		this.RoutingDelayOptions[sequence] = routingDelayOption;
	}

	public RoutingOption[] getRoutingOption()
	{
		return RoutingOptions;
	}

	public String getMapFile()
	{
		return MapFile;
	}

	public void setMapFile(String mapFile)
	{
		MapFile = mapFile;
	}

	public RoutingAlgorithm[] getRoutingAlgo()
	{
		return RoutingAlgorithms;
	}

	public void setRoutingDelayOption(String delayOption, int sequence)
			throws Exception
	{
		if(sequence < 0 || sequence >= NumberOfCategoriesOfVehicle)
		{
			throw new Exception("Sequence request is invalid:" + sequence);
		}
		if (delayOption.isEmpty())
		{
			throw new Exception("Delay Option is empty");
		}
		if (delayOption.equals("NoTraffic"))
		{
			this.RoutingDelayOptions[sequence] = RoutingDelayOption.NoTraffic;
			return;
		}
		if (delayOption.equals("CurrentTraffic"))
		{
			this.RoutingDelayOptions[sequence] = RoutingDelayOption.CurrentTraffic;
			return;
		}
		if (delayOption.equals("Reservation"))
		{
			this.RoutingDelayOptions[sequence] = RoutingDelayOption.Reservation;
			return;
		}
		throw new Exception("Routing Algorithm not supported:" + delayOption);
	}

	public void setRoutingAlgorithm(String routingAlgo, int sequence)
			throws Exception
	{
		if(sequence < 0 || sequence >= NumberOfCategoriesOfVehicle)
		{
			throw new Exception("Sequence request is invalid:" + sequence);
		}
		if (routingAlgo.isEmpty())
		{
			throw new Exception("Routing Algo is empty");
		}
		if (routingAlgo.equals("Greedy"))
		{
			this.RoutingAlgorithms[sequence] = Utility.RoutingAlgorithm.Greedy;
			return;
		}
		if (routingAlgo.equals("AStar"))
		{
			this.RoutingAlgorithms[sequence] = Utility.RoutingAlgorithm.AStarSearch;
			return;
		}
		if (routingAlgo.equals("Dijkstra"))
		{
			this.RoutingAlgorithms[sequence] = Utility.RoutingAlgorithm.Dijkstra;
			return;
		}
		throw new Exception("Routing Algorithm not supported:" + routingAlgo);
	}

	public void setRoutingOption(String routingOption, int sequence)
			throws Exception
	{
		if(sequence < 0 || sequence >= NumberOfCategoriesOfVehicle)
		{
			throw new Exception("Sequence request is invalid:" + sequence);
		}
		if (routingOption.isEmpty())
		{
			throw new Exception("Routing Algo is empty");
		}
		if (routingOption.equals("RunOnce"))
		{
			this.RoutingOptions[sequence] = Utility.RoutingOption.RunOnce;
			return;
		}
		if (routingOption.equals("Iterative"))
		{
			this.RoutingOptions[sequence] = Utility.RoutingOption.Iterative;
			return;
		}
		throw new Exception("Routing Option not supported:" + routingOption);
	}

	public void setLogging(String loggingOption) throws Exception
	{
		if (loggingOption.isEmpty())
		{
			throw new Exception("Logging Option is empty");
		}
		if (loggingOption.equals("True"))
		{
			this.Logging = true;
			return;
		}
		if (loggingOption.equals("False"))
		{
			this.Logging = false;
			return;
		}
		throw new Exception("Logging Option not supported:" + loggingOption);
	}

	public long getRandomSeed()
	{
		return RandomSeed;
	}

	public void setRandomSeed(long randomSeed)
	{
		RandomSeed = randomSeed;
	}
}