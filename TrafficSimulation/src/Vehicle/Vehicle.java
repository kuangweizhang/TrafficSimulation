package Vehicle;

import java.util.LinkedList;

import Routing.RoutingAlgorithmBase;
import Topology.Topology;
import Utility.RoutingResult;
import Utility.TimeInterval;

public class Vehicle {
	
	private long Id;
	private VehiclePosition Position;
	private long StartCity;
	private long DestinationCity;
	private double MaxSpeed;
	private TimeInterval StartTime;
	private TimeInterval ArrivalTime;
	private LinkedList<TimeInterval> ExpectingTimes;
	private LinkedList<TimeInterval> CurrentTimes;
	private boolean Arrived = false;
	private RoutingAlgorithmBase RoutingAlgorithm;
	private RoutingResult RoutingResult;
	
	public VehiclePosition getPosition() {
		return Position;
	}

	public long getStartCity() {
		return StartCity;
	}

	public long getDestinationCity() {
		return DestinationCity;
	}

	public double getMaxSpeed() {
		return MaxSpeed;
	}

	public TimeInterval getStartTime() {
		return StartTime;
	}

	public TimeInterval getArrivalTime() {
		return ArrivalTime;
	}

	public boolean isArrived() {
		return Arrived;
	}
	
	/**
	 * Generate exact vehicle.
	 * @param startCity
	 * @param destinationCity
	 * @param currentTime
	 * @param maxSpeed
	 */
	public Vehicle(Long id, long startCity, long destinationCity, TimeInterval currentTime, 
			double maxSpeed, RoutingAlgorithmBase routingAlgorithm)
	{
		this.Id = id;
		StartCity = startCity;
		DestinationCity = destinationCity;
		StartTime = currentTime;
		MaxSpeed = maxSpeed;
		this.RoutingAlgorithm = routingAlgorithm;
	}
	
	public RoutingAlgorithmBase getRoutingAlgorithm()
	{
		return this.RoutingAlgorithm;
	}
	
	public void UpdatePosition(VehiclePosition newPosition, 
			TimeInterval currentTime, TimeInterval expectingTime)
	{
		if (ExpectingTimes == null)
		{
			ExpectingTimes = new LinkedList<TimeInterval>();
		}
		ExpectingTimes.add(expectingTime);
		if (currentTime == null)
		{
			CurrentTimes = new LinkedList<TimeInterval>();
		}
		CurrentTimes.add(currentTime);
		UpdatePosition(newPosition, currentTime);
	}
	
	public void UpdatePosition(VehiclePosition newPosition, TimeInterval currentTime)
	{
		Position = newPosition;
		if (DestinationCity == Position.getFromIntersection())
		{
			ArrivalTime = currentTime;
			Arrived = true;
		}
	}

	public long getId() {
		return Id;
	}
	
}
