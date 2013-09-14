package Utility;

public class Configurations {

	private String routingAlgo;

	public String getRoutingAlgo() {
		return routingAlgo;
	}

	public void setRoutingAlgo(String routingAlgo) throws Exception {
		if (routingAlgo.isEmpty())
		{
			throw new Exception("Routing Algo is empty");
		}
		this.routingAlgo = routingAlgo;
	}
}
