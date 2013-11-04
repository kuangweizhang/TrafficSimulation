package Simulator;
import java.lang.invoke.ConstantCallSite;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import Utility.Configurations;
import Utility.GetDelayWithTraffic;
import Utility.TimeInterval;
import Vehicle.Vehicle;
import Topology.Topology;

public class Simulator {
	
	private Topology Topology;
	private Configurations Configurations;
	private TimeInterval SimulatorClock;
	private final TimeInterval ForwardInterval = new TimeInterval(0, 5, 0);
	private static long VehicleIdCounter = 0;
	private HashMap<Long, Vehicle> Vehicles = new HashMap<Long, Vehicle>();
	private final double MaxSpeed = 60;
	private Random RandomCity;
	
	public Simulator(Configurations configs)
	{
		this.Configurations = configs;
		this.Topology = new Topology(this.Configurations.getMapFile());
		this.SimulatorClock = new TimeInterval();
		this.RandomCity = new Random(this.Configurations.getRandomSeed());
	}
	
	public void Run() throws Exception
	{
		this.SimulatorClock.addInterval(ForwardInterval);
		for(long vehicleId : Vehicles.keySet())
		{
			Vehicle currentVehicle = Vehicles.get(vehicleId);
			long fromCityId = currentVehicle.getPosition().getFromIntersection();
			long toCityId = currentVehicle.getPosition().getFromIntersection();
			double km = currentVehicle.getPosition().getKM() + ForwardInterval.distanceTraveled(MaxSpeed);
			if (km >= Topology.DistanceBetween(fromCityId, toCityId))
			{
				currentVehicle.getPosition().setFromIntersection(toCityId);
				currentVehicle.getPosition().setKM(0);
				currentVehicle.getRoutingAlgorithm().
				getRoutingResult(this.Topology, currentVehicle.getDestinationCity(), 
						toCityId, MaxSpeed, new GetDelayWithTraffic());
			}
			
		}
	}
	
	private void AddVehicle() {
		long startCity = GetRandomCity();
		long endCity = GetRandomCity();
		while (!Connective(startCity, endCity))
		{
			startCity = GetRandomCity();
			endCity = GetRandomCity();
		}
		Vehicle newVehicle = new Vehicle(VehicleIdCounter++, startCity, endCity, SimulatorClock, MaxSpeed);
		Vehicles.put(newVehicle.getId(), newVehicle);
	}
	
	private boolean Connective(long city1, long city2)
	{
		return true;
	}
	
	private long GetRandomCity()
	{
		Object[] keys = this.Topology.getIntersections().keySet().toArray();
		return (Long) keys[this.RandomCity.nextInt(keys.length)]; 
	}
}
