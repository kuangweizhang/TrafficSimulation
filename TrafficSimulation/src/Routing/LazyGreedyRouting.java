package Routing;

import Topology.CoordinateCalculator;
import Topology.Intersection;
import Topology.Neighbor;
import Topology.Topology;
import Utility.RoutingResult;

public class LazyGreedyRouting extends RoutingAlgorithmBase{

	@Override
	public RoutingResult getRoutingResult(Topology topology,
			long destinationCity, long currentCity, double maxSpeed,
			IGetDelay delayFunction) throws Exception {
		return getRoutingResult(topology, destinationCity, currentCity);
	}
	
	public RoutingResult getRoutingResult(Topology topology,
			long destinationCity, long currentCity) throws Exception
	{
		Intersection currentIntersection = topology.getIntersection(currentCity);
		long nextcity = Long.MIN_VALUE;
		double minDistance = Double.MAX_VALUE;
		for (Neighbor neighbor : currentIntersection.GetNeighbors()) {
			if (CoordinateCalculator.CalculateDistanceBetween(
					currentIntersection, neighbor) < minDistance)
			{
				minDistance = CoordinateCalculator.CalculateDistanceBetween(
						currentIntersection, neighbor);
				nextcity = neighbor.getNeighborValue().getId();
			}
		}
		return new RoutingResult(nextcity, null, null);
	}

}
