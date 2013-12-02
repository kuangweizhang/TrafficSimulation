package Routing;

import java.util.concurrent.ExecutionException;

import Topology.Topology;
import Utility.TimeInterval;

public class GetDelayWithReservation implements IGetDelay {

	@Override
	public TimeInterval GetDelay(long startCity, long destinationCity,
			TimeInterval time) throws Exception {
		return Topology.getIntersection(startCity).
				GetNeighborById(destinationCity).getFutureDelay(time);
	}

	@Override
	public TimeInterval GetDelay(long startCity, long destinationCity)
			throws Exception
	{
		throw new Exception("Need time offset");
	}
}
