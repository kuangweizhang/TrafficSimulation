package Utility;

import Topology.Topology;

public class GetDelayWithoutTraffic implements IGetDelay {

	@Override
	public double GetDelay(Topology topology, long startCity,
			long destinationCity, double maxSpeed) throws Exception {
		throw new UnsupportedOperationException();
//		return topology.getIntersections().get(startCity).
//				GetNeighborById(destinationCity).getDistance();
	}
}
