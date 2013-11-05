package Utility;

public class Configurations {

	private RoutingAlgorithm RoutingAlgorithm;
	private RoutingOption RoutingOption;
	private RoutingDelayOption routingDelayOption;
	private String MapFile;
	private long RandomSeed;
	
	
	public RoutingDelayOption getRoutingDelayOption() {
		return routingDelayOption;
	}

	public void setRoutingDelayOption(RoutingDelayOption routingDelayOption) {
		this.routingDelayOption = routingDelayOption;
	}

	public RoutingOption getRoutingOption() {
		return RoutingOption;
	}
	
	public String getMapFile() {
		return MapFile;
	}

	public void setMapFile(String mapFile) {
		MapFile = mapFile;
	}

	public RoutingAlgorithm getRoutingAlgo() {
		return RoutingAlgorithm;
	}
	
	public void setRoutingDelayOption(String delayOption) throws Exception
	{
		if (delayOption.isEmpty())
		{
			throw new Exception("Delay Option is empty");
		}
		if (delayOption == "NoTraffic")
		{
			this.routingDelayOption = RoutingDelayOption.NoTraffic;
			return;
		}
		if (delayOption == "CurrentTraffic")
		{
			this.routingDelayOption = RoutingDelayOption.CurrentTraffic;
			return;
		}
		if (delayOption == "Reservation")
		{
			this.routingDelayOption = RoutingDelayOption.Reservation;
			return;
		}
		throw new Exception("Routing Algorithm not supported:" + delayOption);
	}

	public void setRoutingAlgorithm(String routingAlgo) throws Exception {
		if (routingAlgo.isEmpty())
		{
			throw new Exception("Routing Algo is empty");
		}
		if (routingAlgo == "Greedy")
		{
			this.RoutingAlgorithm = Utility.RoutingAlgorithm.Greedy;
			return;
		}
		if (routingAlgo == "AStar")
		{
			this.RoutingAlgorithm = Utility.RoutingAlgorithm.AStarSearch;
			return;
		}
		if (routingAlgo == "Dijkstra")
		{
			this.RoutingAlgorithm = Utility.RoutingAlgorithm.Dijkstra;
			return;
		}
		throw new Exception("Routing Algorithm not supported:" + routingAlgo);
	}
	
	public void setRoutingOption(String routingOption) throws Exception {
		if (routingOption.isEmpty())
		{
			throw new Exception("Routing Algo is empty");
		}
		if (routingOption == "RunOnce")
		{
			this.RoutingOption = Utility.RoutingOption.RunOnce;
			return;
		}
		if (routingOption == "Iterative")
		{
			this.RoutingOption = Utility.RoutingOption.Iterative;
			return;
		}
		throw new Exception("Routing Option not supported:" + routingOption);
	}

	public long getRandomSeed() {
		return RandomSeed;
	}

	public void setRandomSeed(long randomSeed) {
		RandomSeed = randomSeed;
	}
	
}
