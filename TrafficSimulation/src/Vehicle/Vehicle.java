package Vehicle;

import java.sql.Time;
import java.util.Calendar;
import java.util.LinkedList;

public class Vehicle {
	
	private VehiclePosition Position;
	private long StartCity;
	private long DestinationCity;
	private double MaxSpeed;
	private Calendar StartTime;
	private Calendar ArrivalTime;
	private LinkedList<Time> ExpectingTimes;
	private LinkedList<Calendar> CurrentTimes;
	private boolean Arrived = false;
	
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

	public Calendar getStartTime() {
		return StartTime;
	}

	public Calendar getArrivalTime() {
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
	public Vehicle(long startCity, long destinationCity, Calendar currentTime, double maxSpeed)
	{
		StartCity = startCity;
		DestinationCity = destinationCity;
		StartTime = currentTime;
		MaxSpeed = maxSpeed;
	}
	
	public void UpdatePosition(VehiclePosition newPosition, 
			Calendar currentTime, Time expectingTime)
	{
		if (ExpectingTimes == null)
		{
			ExpectingTimes = new LinkedList<Time>();
		}
		ExpectingTimes.add(expectingTime);
		if (currentTime == null)
		{
			CurrentTimes = new LinkedList<Calendar>();
		}
		CurrentTimes.add(currentTime);
		UpdatePosition(newPosition, currentTime);
	}
	
	public void UpdatePosition(VehiclePosition newPosition, Calendar currentTime)
	{
		Position = newPosition;
		if (DestinationCity == Position.getFromIntersection())
		{
			ArrivalTime = currentTime;
			Arrived = true;
		}
	}
}
