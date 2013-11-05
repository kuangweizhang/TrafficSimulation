package Routing;

import Topology.Topology;
import Utility.TimeInterval;

public class GetDelayWithoutTraffic implements IGetDelay {

	@Override
	public TimeInterval GetDelay(Topology topology, long startCity,
			long destinationCity, double maxSpeed) throws Exception {
		throw new UnsupportedOperationException();
//		return topology.getIntersections().get(startCity).
//				GetNeighborById(destinationCity).getDistance();
	}
}
