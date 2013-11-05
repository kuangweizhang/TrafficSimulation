package Simulator;
import java.util.HashMap;
import java.util.Random;

import Utility.Configurations;
import Utility.TimeInterval;
import Vehicle.Vehicle;
import Vehicle.VehiclePosition;
import Vehicle.VehicleStage;
import Routing.RoutingStrategy;
import Topology.Topology;

public class Simulator {
	
	private Topology Topology;
	private Configurations Configurations;
	public static TimeInterval WorldClock;
	public final static TimeInterval UnviersalInterval = new TimeInterval(0, 5, 0);
	private static long VehicleIdCounter = 0;
	private HashMap<Long, Vehicle> Vehicles = new HashMap<Long, Vehicle>();
	private HashMap<Long, Vehicle> ArrivedVehicles = new HashMap<Long, Vehicle>();
	private final double MaxSpeed = 60;
	private Random RandomCity;
	
	public Simulator(Configurations configs)
	{
		this.Configurations = configs;
		this.Topology = new Topology(this.Configurations.getMapFile());
		Simulator.WorldClock = new TimeInterval();
		this.RandomCity = new Random(this.Configurations.getRandomSeed());
	}
	
	public void Run() throws Exception
	{
		Simulator.WorldClock.addInterval(UnviersalInterval);
		for(long vehicleId : Vehicles.keySet())
		{
			Vehicle currentVehicle = Vehicles.get(vehicleId);
			if (currentVehicle.getStage() == VehicleStage.OnRoad) {
				long fromCityId = currentVehicle.getPosition().getFromIntersection();
				long toCityId = currentVehicle.getPosition().getFromIntersection();
				double km = currentVehicle.getPosition().getKM()
						+ UnviersalInterval.distanceTraveled(MaxSpeed);
				if (km >= Topology.DistanceBetween(fromCityId, toCityId)) {
					currentVehicle.setStage(VehicleStage.AtIntersection);
					this.Topology.ReleaseUsingRoad(fromCityId, toCityId);
					if (currentVehicle.isArrived()) {
						this.ArrivedVehicles.put(currentVehicle.getId(),
								currentVehicle);
						this.Vehicles.remove(currentVehicle.getId());
					}
				} else {
					currentVehicle.UpdatePosition(new VehiclePosition(
							fromCityId, toCityId, km), Simulator.WorldClock);
				}
			}
			else
			{
				long fromCityId = currentVehicle.getPosition()
						.getFromIntersection();
				long nextCity = currentVehicle.getNextCity();
				if(Topology.CurrentDelayBetween(fromCityId, nextCity).
						earlierThan(TimeInterval.MaxTimeInterval))
				{
					currentVehicle.setStage(VehicleStage.OnRoad);
					currentVehicle.UpdatePosition(new VehiclePosition(fromCityId, 
							nextCity, 0), WorldClock);
					this.Topology.AcquireUsingRoad(fromCityId, nextCity);
				}
			}
		}
	}
	
	public Iterable<Vehicle> GetMetrics()
	{
		return ArrivedVehicles.values();
	}
	
	public String getTimeTickReport()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(WorldClock);
		stringBuilder.append(" ");
		stringBuilder.append("Total cars:" + (this.Vehicles.size() + this.ArrivedVehicles.size()) + " ");
		stringBuilder.append("Driving Cars:" + (this.Vehicles.size()) + " ");
		stringBuilder.append("Arrived Cars:" + (this.ArrivedVehicles.size()) + " ");
		stringBuilder.append("Average Alpha" + getAverageAlpha());
		
		return stringBuilder.toString();
	}
	
	public TimeInterval getAverageAlpha()
	{
		TimeInterval counterInterval = new TimeInterval();
		int kCounter = 0;
		for (Vehicle arraiedVehicle : ArrivedVehicles.values()) {
			counterInterval.addInterval(arraiedVehicle.getExpectingDifference());
			kCounter++;
		}
		return counterInterval.devideBy(kCounter);
	}
	
	public void AddVehicle() throws Exception {
		long startCity = GetRandomCity();
		long endCity = GetRandomCity();
		while (!Connective(startCity, endCity))
		{
			startCity = GetRandomCity();
			endCity = GetRandomCity();
		}
		
		Vehicle newVehicle = new Vehicle(VehicleIdCounter++, startCity, endCity, 
				WorldClock, MaxSpeed, new RoutingStrategy(this.Configurations), this.Topology);
		Vehicles.put(newVehicle.getId(), newVehicle);
	}
	
	private boolean Connective(long city1, long city2)
	{
		return true;
	}
	
	private long GetRandomCity()
	{
		Object[] keys = Topology.getIntersections().keySet().toArray();
		return (Long) keys[this.RandomCity.nextInt(keys.length)]; 
	}
}
