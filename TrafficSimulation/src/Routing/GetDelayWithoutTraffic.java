package Routing;

import Topology.Topology;
import Utility.TimeInterval;

public class GetDelayWithoutTraffic implements IGetDelay {

	@Override
	public TimeInterval GetDelay(long startCity, long destinationCity,
			TimeInterval time) throws Exception {
		return Topology.getIntersection(startCity).
				GetNeighborById(destinationCity).getStandardDelay();
	}

	@Override
	public TimeInterval GetDelay(long startCity, long destinationCity)
			throws Exception
	{
		return Topology.getIntersection(startCity).
				GetNeighborById(destinationCity).getStandardDelay();
	}
}
