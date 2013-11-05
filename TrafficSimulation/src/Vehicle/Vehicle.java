package Vehicle;

import java.util.HashMap;
import java.util.LinkedList;

import Routing.RoutingStrategy;
import Simulator.Simulator;
import Topology.Topology;
import Utility.Configurations;
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
	private Topology Topology;

	/**
	 * List of expecting arrival time, each represent the expecting
	 * arrival time at a node.
	 */
	//private LinkedList<TimeInterval> ExpectingArrivalTimes;
	
	private TimeInterval ExpectingArrivalTime;
	private boolean Arrived = false;
	private RoutingStrategy RoutingStrategy;
	private LinkedList<Reservation> reservations;
	
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
	 * @throws Exception 
	 */
	public Vehicle(Long id, long startCity, long destinationCity, TimeInterval currentTime, 
			double maxSpeed, RoutingStrategy routingStrategy, Topology topology) throws Exception
	{
		this.Id = id;
		this.StartCity = startCity;
		this.DestinationCity = destinationCity;
		this.StartTime = currentTime;
		this.MaxSpeed = maxSpeed;
		this.Topology = topology;
		this.RoutingStrategy = routingStrategy;
		
		this.ExpectingArrivalTime = getExpectingArrivalTime();
	}
	
	private TimeInterval getExpectingArrivalTime() throws Exception
	{
		Configurations configs= new Configurations();
		configs.enableTraffic(false);
		configs.setRoutingAlgorithm("Greedy");
		configs.setRoutingOption("RunOnce");
		RoutingStrategy estimatedStrategy = new RoutingStrategy(configs);
		return estimatedStrategy.GetNextCity(this.Topology, 
				this.DestinationCity, this.StartCity, this.MaxSpeed).getExpectingDelay();
	}
	
	public long getNextCity() throws Exception
	{
		return this.RoutingStrategy.GetNextCity(this.Topology, this.DestinationCity, 
				this.Position.getFromIntersection(), this.MaxSpeed).getNextCity();
	}
	
	public void UpdateReservations(RoutingResult routingResult) throws Exception
	{
		for (Reservation reservation : reservations) {
			Topology.RemoveReservation(reservation);
		}
		TimeInterval timeCounter = Simulator.WorldClock.clone();
		int stepCounter = 0;
		long from = this.Position.getFromIntersection();
		long to = routingResult.getPath().get(stepCounter);
		while(to != routingResult.getPath().peekLast())
		{
			Topology.AddReservation(from, to, timeCounter);
			timeCounter.addInterval(Topology.EstimateDelayBetween(from, to, timeCounter));
			stepCounter++;
			from = to;
			to = routingResult.getPath().get(stepCounter);
		}
	}
	
//	public RoutingStrategy getRoutingStrategy()
//	{
//		return this.RoutingStrategy;
//	}
	
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
