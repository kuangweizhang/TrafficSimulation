package Routing;

import java.sql.Time;
import java.util.HashSet;
import java.util.LinkedList;

import Simulator.Simulator;
import Topology.Intersection;
import Topology.Topology;
import Utility.RoutingResult;
import Utility.TimeInterval;

/**
 * Use direct distance as heuristic.
 * @author Kevin
 *
 */
public class GreedyRouting extends RoutingAlgorithmBase{

	@Override
	public RoutingResult getRoutingResult(Topology topology,
			long destinationCity, long currentCity, double maxSpeed,
			IGetDelay delayFunction) throws Exception {
		LinkedList<Long> path = new LinkedList<Long>();
		TimeInterval expectingDelay = new TimeInterval();
		LazyGreedyRouting lazyGreedyRouting = new LazyGreedyRouting();
		HashSet<Long> visited = new HashSet<Long>();
		visited.add(currentCity);
		while(currentCity != destinationCity)
		{
			long nextCity = lazyGreedyRouting.getRoutingResult(
					topology, destinationCity, currentCity, visited).getNextCity();
			path.addLast(nextCity);
			visited.add(nextCity);
			// notes here...
			expectingDelay.addInterval(delayFunction.GetDelay(currentCity, nextCity, 
					expectingDelay.addInterval(Simulator.WorldClock)));
			currentCity = nextCity;
		}
		
		return new RoutingResult(path.getFirst(), expectingDelay, path);
	}

}
