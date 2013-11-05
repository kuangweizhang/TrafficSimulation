package Routing;

import Topology.Topology;
import Utility.TimeInterval;

public class GetDelayWithTraffic implements IGetDelay {

	@Override
	public TimeInterval GetDelay(long startCity, long destinationCity,
			TimeInterval time) throws Exception {
		return Topology.getIntersection(startCity).
				GetNeighborById(destinationCity).getCurrentDelay();
	}
}
