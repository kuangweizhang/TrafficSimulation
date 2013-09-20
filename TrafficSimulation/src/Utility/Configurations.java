package Utility;

public class Configurations {

	private String RoutingAlgo;
	private String MapFile;
	
	
	public String getMapFile() {
		return MapFile;
	}

	public void setMapFile(String mapFile) {
		MapFile = mapFile;
	}

	public String getRoutingAlgo() {
		return RoutingAlgo;
	}

	public void setRoutingAlgo(String routingAlgo) throws Exception {
		if (routingAlgo.isEmpty())
		{
			throw new Exception("Routing Algo is empty");
		}
		this.RoutingAlgo = routingAlgo;
	}
}
