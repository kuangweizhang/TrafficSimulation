package Utility;

public class Configurations {

	private RoutingAlgorithm RoutingAlgorithm;
	private RoutingOption RoutingOption;
	private boolean RoutingWithCurrentTraffic;
	private String MapFile;
	private long RandomSeed;
	
	public boolean isRoutingWithCurrentTraffic() {
		return RoutingWithCurrentTraffic;
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

	public void setRoutingAlgorithm(String routingAlgo) throws Exception {
		if (routingAlgo.isEmpty())
		{
			throw new Exception("Routing Algo is empty");
		}
		if (routingAlgo == "Greedy")
		{
			this.RoutingAlgorithm = RoutingAlgorithm.Greedy;
			return;
		}
		if (routingAlgo == "AStar")
		{
			this.RoutingAlgorithm = RoutingAlgorithm.AStarSearch;
			return;
		}
		if (routingAlgo == "Dijkstra")
		{
			this.RoutingAlgorithm = RoutingAlgorithm.Dijkstra;
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
			this.RoutingOption = RoutingOption.RunOnce;
			return;
		}
		if (routingOption == "Iterative")
		{
			this.RoutingOption = RoutingOption.Iterative;
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
