package Vehicle;

import java.util.LinkedList;
import java.util.Random;

import javax.swing.text.Position;

import Routing.RoutingStrategy;
import Simulator.Simulator;
import Topology.Topology;
import Utility.Configurations;
import Utility.RoutingDelayOption;
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
		//System.out.println(this);
		//System.out.println("Arrive:" + ArrivalTime + " Expecting:" + ExpectingArrivalTime);
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
		this.StartTime = currentTime.clone();
		this.MaxSpeed = maxSpeed;
		this.Topology = topology;
		this.RoutingStrategy = routingStrategy;
		this.Stage = VehicleStage.AtIntersection;
		this.Position = new VehiclePosition(this.StartCity, this.StartCity, 0, 0);
		this.ExpectingArrivalTime = getExpectingArrivalTime();
	}
	
	private TimeInterval getExpectingArrivalTime() throws Exception
	{
		Configurations configs = new Configurations();
		configs.setRoutingDelayOption(RoutingDelayOption.NoTraffic);
		configs.setRoutingAlgorithm("Dijkstra");
		configs.setRoutingOption("RunOnce");
		RoutingStrategy estimatedStrategy = new RoutingStrategy(configs);
		return estimatedStrategy
				.GetNextCity(this.Topology, this.DestinationCity,
						this.StartCity, this.MaxSpeed).getExpectingDelay()
				.addInterval(Simulator.WorldClock);
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
	
	public void goingForward(TimeInterval timeInterval) throws Exception
	{
		if (this.Stage == VehicleStage.OnRoad)
		{
			goingForwardAtRoad(timeInterval);
		}
		else {
			goingForwardAtIntersection(timeInterval);
		}
	}
	
	private void goingForwardAtIntersection(TimeInterval timeInterval) throws Exception
	{
		long fromCityId = this.getPosition()
				.getFromIntersection();
		long nextCity = this.getNextCity();
		
		if (Topology.CurrentDelayBetween(fromCityId, nextCity)
				.earlierThan(TimeInterval.MaxTimeInterval))
		{
			this.setStage(VehicleStage.OnRoad);
			this.UpdatePosition(new VehiclePosition(
					fromCityId, nextCity, 0, 
					Topology.DistanceBetween(fromCityId, nextCity)));
			this.Topology.AcquireUsingRoad(fromCityId, nextCity);
		}
	}
	
	private void goingForwardAtRoad(TimeInterval timeInterval) throws Exception
	{
		double newKM = this.Position.getKM()
				+ timeInterval.distanceTraveled(MaxSpeed);
		if (newKM >= Topology.DistanceBetween(
				this.Position.getFromIntersection(),
				this.Position.getToIntersection()))
		{
			this.Topology.ReleaseUsingRoad(
					this.Position.getFromIntersection(),
					this.Position.getToIntersection());
			this.setStage(VehicleStage.AtIntersection);
			this.Position.ArrivedIntersection();
			this.UpdatePosition(this.Position);
		} else
		{
			this.Position.setKM(newKM);
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
	
	public void UpdatePosition(VehiclePosition newPosition)
	{
		Position = newPosition;
		if (DestinationCity == Position.getFromIntersection())
		{
			ArrivalTime = Simulator.WorldClock.clone();
			Arrived = true;
		}
	}

	public long getId() {
		return Id;
	}
	
	public String toString()
	{
		StringBuilder log = new StringBuilder();
		log.append("ID:");
		log.append(this.Id);
		log.append(" Position:");
		log.append(this.Position);
		log.append(" StartCity:");
		log.append(this.StartCity);
		log.append(" DestinationCity:");
		log.append(this.DestinationCity);
		log.append(" MaxSpeed:");
		log.append(this.MaxSpeed);
		log.append(" StartTime:");
		log.append(this.StartTime);
		log.append(" ArrivalTime:");
		log.append(this.ArrivalTime);
		log.append(" Stage:");
		log.append(this.Stage.name());
		return log.toString();
	}
}
