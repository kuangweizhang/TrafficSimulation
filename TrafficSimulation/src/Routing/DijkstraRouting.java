package Routing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import Simulator.Simulator;
import Topology.Topology;
import Utility.RoutingResult;
import Utility.TimeInterval;

public class DijkstraRouting extends RoutingAlgorithmBase
{

	static long StartCity = 0;
	static long DestinationCity = 0;

	@Override
	public RoutingResult getRoutingResult(Topology topology,
			long destinationCity, long currentCity, double maxSpeed,
			IGetDelay delayFunction) throws Exception
	{
		distance = new HashMap<Long, TimeInterval>();
		PreviousCity = new HashMap<Long, Long>();
		StartCity = currentCity;
		DestinationCity = destinationCity;

		HashSet<Long> visited = new HashSet<Long>();
		SetDelayTo(currentCity, new TimeInterval());
		long cityPointer = currentCity;

		while (cityPointer != destinationCity)
		{
			visited.add(cityPointer);
			for (long neigbor : topology.getIntersection(cityPointer)
					.GetNeighborIds())
			{
				if (GetDelayTo(cityPointer).addInterval(
						delayFunction.GetDelay(
								cityPointer, 
								neigbor,
								GetDelayTo(cityPointer).addInterval(Simulator.WorldClock)
								))
						.isSmaller(GetDelayTo(neigbor)))
				{
					SetDelayTo(
							neigbor,
							GetDelayTo(cityPointer).addInterval(
									delayFunction
											.GetDelay(
													cityPointer, 
													neigbor,
													GetDelayTo(cityPointer).addInterval(Simulator.WorldClock)
													)));
					PreviousCity.put(neigbor, cityPointer);
				}
			}

			TimeInterval mInterval = TimeInterval.LARGE_VALUE;
			long minId = -1;
			for (long neighbor : distance.keySet())
			{
				if (!visited.contains(neighbor))
				{
					if (GetDelayTo(neighbor).isSmaller(mInterval))
					{
						mInterval = GetDelayTo(neighbor);
						minId = neighbor;
					}
				}
			}
			if(minId == -1)
			{
				return null;
			}
			cityPointer = minId;
		}

		LinkedList<Long> path = findPath();
		RoutingResult retval = new RoutingResult(path.get(0),
				GetDelayTo(destinationCity), path);
		return retval;
	}

	private LinkedList<Long> findPath()
	{
		long cityPointer = DestinationCity;
		LinkedList<Long> retval = new LinkedList<Long>();
		while (cityPointer != StartCity)
		{
			retval.addFirst(cityPointer);
			cityPointer = PreviousCity.get(cityPointer);
		}
		return retval;
	}

	/**
	 * Delay between start city and key.
	 */
	private HashMap<Long, TimeInterval> distance = null;

	private HashMap<Long, Long> PreviousCity = null;

	private TimeInterval GetDelayTo(long city1)
	{
		if (distance.containsKey(city1))
		{
			return distance.get(city1);
		} else
		{
			return TimeInterval.LARGE_VALUE;
		}
	}

	private void SetDelayTo(long city1, TimeInterval value)
	{
		distance.put(city1, value);
	}
}
