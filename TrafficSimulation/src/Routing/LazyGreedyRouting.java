package Routing;

import java.beans.DesignMode;
import java.util.HashSet;

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
		return getRoutingResult(topology, destinationCity, currentCity, new HashSet<Long>());
	}
	
	public RoutingResult getRoutingResult(Topology topology,
			long destinationCity, long currentCity, HashSet<Long> visited) throws Exception
	{
		Intersection destIntersection = topology.getIntersection(destinationCity);
		Intersection currentIntersection = topology.getIntersection(currentCity);
		long nextcity = Long.MIN_VALUE;
		double minDistance = Double.MAX_VALUE;
		for (Neighbor neighbor : currentIntersection.GetNeighbors()) {
			if (CoordinateCalculator.CalculateDistanceBetween(
					destIntersection, neighbor) < minDistance && 
					!visited.contains(neighbor.getNeighborValue().getId()))
			{
				minDistance = CoordinateCalculator.CalculateDistanceBetween(
						destIntersection, neighbor);
				nextcity = neighbor.getNeighborValue().getId();
			}
		}
		return new RoutingResult(nextcity, null, null);
	}

}
