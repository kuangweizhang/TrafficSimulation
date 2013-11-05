package Topology;

import java.util.LinkedList;
import Simulator.Simulator;
import Utility.TimeInterval;

public class Neighbor {
	private Intersection NeighborValue;
	private Double Distance;
	private double SpeedLimit;
	private Integer NumberOfLane;
	private int NumberOfCars;
	private TimeInterval StandardDelay = TimeInterval.FromHour(Distance/(double)SpeedLimit);
	/**
	 * 
	 * Every single element represent the number of reservations during next Universal time interval.
	 */
	LinkedList<Integer> NumberOfReservations;

	public Neighbor(Intersection me, Intersection myNeighbor, int numberOfLaneBetween, double speedLimit)
	{
		NeighborValue = myNeighbor;
		NumberOfLane = numberOfLaneBetween;
		Distance = CoordinateCalculator.CoordinateToDistance(me.getLongitude(), me.getLatitude(), 
				myNeighbor.getLongitude(), myNeighbor.getLatitude());
		SpeedLimit = speedLimit;
	}
	
	public TimeInterval getStandardDelay() {
		return StandardDelay;
	}



	/**
	 * 
	 * @return - Delay in current traffic condition.
	 */
	public TimeInterval UseRoad()
	{
		this.NumberOfCars++;
		return this.getCurrentDelay();
	}
	
	public void LeaveRoad()
	{
		this.NumberOfCars--;
	}
	
	public TimeInterval getCurrentDelay() {
		return TimeInterval.FromHour(Distance/(double)SpeedLimit).
				addInterval(extraInterval(NumberOfCars));
	}
	
	/**
	 * Estimated average length of the car and space is 0.008m.
	 */
	private final int MaxNumberOfCars = (int)(Distance / 0.008);
	
	/**
	 * Heuristic function for extra delay due to the number of
	 * cars on the road.
	 * @param numberOfCars
	 * @return
	 */
	public TimeInterval extraInterval(int numberOfCars)
	{
		if(numberOfCars > MaxNumberOfCars)
		{
			return TimeInterval.MaxTimeInterval;
		}
		else {
			return TimeInterval.FromMinute((int) (10*Math.log(numberOfCars)));
		}
	}
	
	public void AddReservation(TimeInterval time) throws Exception
	{
		if(time.earlierThan(Simulator.WorldClock))
		{
			throw new Exception("Reservation is earlier than current time");
		}
		int from = (int)time.subtractInterval(Simulator.WorldClock).
				devidedBy(Simulator.UnviersalInterval);
		int count = (int) StandardDelay.devidedBy(Simulator.UnviersalInterval);
		for(int i = 0; i < from + count; i++)
		{
			if(i < NumberOfReservations.size())
			{
				NumberOfReservations.set(i, NumberOfReservations.get(i));
			}
			else {
				NumberOfReservations.add(1);
			}
		}
	}
	
	public void RemoveReservation(TimeInterval time) throws Exception
	{
		if(time.earlierThan(Simulator.WorldClock))
		{
			throw new Exception("Reservation is earlier than current time");
		}
		int from = (int)time.subtractInterval(Simulator.WorldClock).
				devidedBy(Simulator.UnviersalInterval);
		int count = (int) StandardDelay.devidedBy(Simulator.UnviersalInterval);
		for(int i = 0; i < from + count; i++)
		{
			if(i < NumberOfReservations.size())
			{
				NumberOfReservations.set(i, NumberOfReservations.get(i) - 1);
			}
		}
	}
	
	public TimeInterval getFutureDelay(TimeInterval time) throws Exception
	{
		if(time.earlierThan(Simulator.WorldClock))
		{
			throw new Exception("Reservation is earlier than current time");
		}
		int from = (int)time.subtractInterval(Simulator.WorldClock).
				devidedBy(Simulator.UnviersalInterval);
		int count = (int) StandardDelay.devidedBy(Simulator.UnviersalInterval);
		int index = from + count /2;
		if (index >= NumberOfReservations.size()) {
			return StandardDelay;
		} else 
		{
			return TimeInterval
					.FromHour(Distance / (double) SpeedLimit)
					.addInterval(extraInterval(NumberOfReservations.get(index)));
		}
	}
	
	public Intersection getNeighborValue() {
		return NeighborValue;
	}

	public void setNeighborValue(Intersection neighborValue) {
		NeighborValue = neighborValue;
	}

	public Double getDistance() {
		return Distance;
	}

	public void setDistance(Double distance) {
		Distance = distance;
	}

	public Integer getNumberOfLane() {
		return NumberOfLane;
	}

	public void setNumberOfLane(Integer numberOfLane) {
		NumberOfLane = numberOfLane;
	}
}
