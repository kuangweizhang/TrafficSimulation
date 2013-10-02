package Utility;

import Topology.Topology;

public class GetDelayWithTraffic implements IGetDelay {

	@Override
	public double GetDelay(Topology topology, long startCity,
			long destinationCity, double maxSpeed) throws Exception {
		return topology.getIntersections().get(startCity).
				GetNeighborById(destinationCity).getCurrentDelay();
	}

}
