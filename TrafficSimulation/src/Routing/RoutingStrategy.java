package Routing;

import Topology.Topology;
import Utility.*;

public class RoutingStrategy {

	private IGetDelay DelayFunction;
	private RoutingOption RoutingOption;
	private RoutingAlgorithmBase RoutingAlgorithm;
	private RoutingResult CachedRoutingResult;
	
	public RoutingStrategy(Configurations configurations)
	{
		switch (configurations.getRoutingAlgo()) {
		case Greedy:
			RoutingAlgorithm = new GreedyRouting();
			break;
		case AStarSearch:
			RoutingAlgorithm = new AStarRouting();
			break;
		case Dijkstra:
			RoutingAlgorithm = new DijkstraRouting();
			break;
		default:
			throw new UnsupportedOperationException();
		}
		
		if (configurations.isRoutingWithCurrentTraffic())
		{
			DelayFunction = new GetDelayWithTraffic();
		}
		else {
			DelayFunction = new GetDelayWithoutTraffic();
		}
		RoutingOption = configurations.getRoutingOption();
	}
	
	public RoutingResult GetNextCity(Topology topology, long destinationCity, long currentCity, double maxSpeed) throws Exception
	{
		switch (RoutingOption) {
		case Iterative:
			return GetNextCityIterative(topology, destinationCity, currentCity, maxSpeed);
		case RunOnce:
			return GetNextCityRunOnce(topology, destinationCity, currentCity, maxSpeed);
		default:
			throw new UnsupportedOperationException();
		}
	}
	
	private RoutingResult GetNextCityIterative(Topology topology, 
			long destinationCity, long currentCity, double maxSpeed) throws Exception
	{
		return RoutingAlgorithm.getRoutingResult(topology, destinationCity, currentCity, maxSpeed, DelayFunction);
	}
	
	private RoutingResult GetNextCityRunOnce(Topology topology, 
			long destinationCity, long currentCity, double maxSpeed) throws Exception
	{
		if (CachedRoutingResult == null)
		{
			CachedRoutingResult = 
					RoutingAlgorithm.getRoutingResult(topology, destinationCity, currentCity, maxSpeed, DelayFunction);
			return CachedRoutingResult;
		}
		else {
			return ForwardOneStep(CachedRoutingResult);
		}
	}
	
	/**
	 * Todo: estimate delay.
	 * @param routingResult
	 * @return
	 * @throws Exception 
	 */
	private RoutingResult ForwardOneStep(RoutingResult routingResult) throws Exception
	{
		routingResult.getPath().pollFirst();
		return new RoutingResult(routingResult.getPath().getFirst(), new TimeInterval(),routingResult.getPath());
	}
}
