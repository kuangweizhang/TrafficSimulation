package Routing;

import Topology.Topology;
import Utility.IGetDelay;
import Utility.RoutingResult;

public abstract class RoutingAlgorithmBase {

	public abstract RoutingResult getRoutingResult(Topology topology, 
			long destinationCity, long currentCity, double maxSpeed, IGetDelay delayFunction) throws Exception;

}
