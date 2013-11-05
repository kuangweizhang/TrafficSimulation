package Routing;

import Topology.Topology;
import Utility.TimeInterval;

public class GetDelayWithTraffic implements IGetDelay {

	@Override
	public TimeInterval GetDelay(Topology topology, long startCity,
			long destinationCity, double maxSpeed) throws Exception {
		throw new UnsupportedOperationException("Unimplemented");
		
//		return topology.getIntersections().get(startCity).
//				GetNeighborById(destinationCity).getCurrentDelay();
	}

}
