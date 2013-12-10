package Utility;

public class Configurations
{

	private RoutingAlgorithm RoutingAlgorithm;
	private RoutingOption RoutingOption;
	private RoutingDelayOption routingDelayOption;
	private String MapFile;
	private long RandomSeed;
	private boolean Logging;
	private boolean VehicleLogging;
	private double VehicleGenerateRate;

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

	public RoutingDelayOption getRoutingDelayOption()
	{
		return routingDelayOption;
	}

	public void setRoutingDelayOption(RoutingDelayOption routingDelayOption)
	{
		this.routingDelayOption = routingDelayOption;
	}

	public RoutingOption getRoutingOption()
	{
		return RoutingOption;
	}

	public String getMapFile()
	{
		return MapFile;
	}

	public void setMapFile(String mapFile)
	{
		MapFile = mapFile;
	}

	public RoutingAlgorithm getRoutingAlgo()
	{
		return RoutingAlgorithm;
	}

	public void setRoutingDelayOption(String delayOption) throws Exception
	{
		if (delayOption.isEmpty())
		{
			throw new Exception("Delay Option is empty");
		}
		if (delayOption.equals("NoTraffic"))
		{
			this.routingDelayOption = RoutingDelayOption.NoTraffic;
			return;
		}
		if (delayOption.equals("CurrentTraffic"))
		{
			this.routingDelayOption = RoutingDelayOption.CurrentTraffic;
			return;
		}
		if (delayOption.equals("Reservation"))
		{
			this.routingDelayOption = RoutingDelayOption.Reservation;
			return;
		}
		throw new Exception("Routing Algorithm not supported:" + delayOption);
	}

	public void setRoutingAlgorithm(String routingAlgo) throws Exception
	{
		if (routingAlgo.isEmpty())
		{
			throw new Exception("Routing Algo is empty");
		}
		if (routingAlgo.equals("Greedy"))
		{
			this.RoutingAlgorithm = Utility.RoutingAlgorithm.Greedy;
			return;
		}
		if (routingAlgo.equals("AStar"))
		{
			this.RoutingAlgorithm = Utility.RoutingAlgorithm.AStarSearch;
			return;
		}
		if (routingAlgo.equals("Dijkstra"))
		{
			this.RoutingAlgorithm = Utility.RoutingAlgorithm.Dijkstra;
			return;
		}
		throw new Exception("Routing Algorithm not supported:" + routingAlgo);
	}

	public void setRoutingOption(String routingOption) throws Exception
	{
		if (routingOption.isEmpty())
		{
			throw new Exception("Routing Algo is empty");
		}
		if (routingOption.equals("RunOnce"))
		{
			this.RoutingOption = Utility.RoutingOption.RunOnce;
			return;
		}
		if (routingOption.equals("Iterative"))
		{
			this.RoutingOption = Utility.RoutingOption.Iterative;
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