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
	private VehicleStage Stage;


	/**
	 * List of expecting arrival time, each represent the expecting
	 * arrival time at a node.
	 */
	//private LinkedList<TimeInterval> ExpectingArrivalTimes;
	
	private TimeInterval ExpectingArrivalTime;
	private boolean Arrived = false;
	private RoutingAlgorithmBase RoutingAlgorithm;
	private RoutingResult RoutingResult;
	
	public VehicleStage getStage() {
		return Stage;
	}

	public void setStage(VehicleStage stage) {
		Stage = stage;
	}
	
	public TimeInterval getExpectingDifference()
	{
		return ArrivalTime.subtractInterval(ExpectingArrivalTime);
	}
	
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
	
//	public void UpdatePosition(VehiclePosition newPosition, 
//			TimeInterval currentTime, TimeInterval expectingTime)
//	{
//		if (ExpectingArrivalTimes == null)
//		{
//			ExpectingArrivalTimes = new LinkedList<TimeInterval>();
//		}
//		ExpectingArrivalTimes.add(expectingTime);
//		UpdatePosition(newPosition, currentTime);
//	}
	
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
