package Routing;

import Topology.Topology;
import Utility.TimeInterval;

public interface IGetDelay {

	public TimeInterval GetDelay(Topology topology, long startCity, 
			long destinationCity, double maxSpeed) throws Exception;
}
