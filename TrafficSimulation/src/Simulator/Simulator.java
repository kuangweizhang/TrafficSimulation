package Simulator;
import java.util.HashMap;
import java.util.Random;

import Utility.Configurations;
import Utility.RoutingResult;
import Utility.TimeInterval;
import Vehicle.Vehicle;
import Vehicle.VehiclePosition;
import Vehicle.VehicleStage;
import Routing.GetDelayWithTraffic;
import Routing.GreedyRouting;
import Routing.RoutingAlgorithmBase;
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
				long fromCityId = currentVehicle.getPosition()
						.getFromIntersection();
				long toCityId = currentVehicle.getPosition()
						.getFromIntersection();
				double km = currentVehicle.getPosition().getKM()
						+ UnviersalInterval.distanceTraveled(MaxSpeed);
				if (km >= Topology.DistanceBetween(fromCityId, toCityId)) {
					RoutingResult routingResult = currentVehicle
							.getRoutingAlgorithm().getRoutingResult(
									this.Topology,
									currentVehicle.getDestinationCity(),
									toCityId, MaxSpeed,
									new GetDelayWithTraffic());
					currentVehicle.UpdatePosition(new VehiclePosition(toCityId,
							routingResult.getNextCity(), 0),
							Simulator.WorldClock);
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
				RoutingResult routingResult = currentVehicle
						.getRoutingAlgorithm().getRoutingResult(
								this.Topology,
								currentVehicle.getDestinationCity(),
								fromCityId, MaxSpeed,
								new GetDelayWithTraffic());
				if(Topology.CurrentDelayBetween(fromCityId, routingResult.getNextCity()).
						earlierThan(TimeInterval.MaxTimeInterval))
				{
					currentVehicle.setStage(VehicleStage.OnRoad);
					currentVehicle.UpdatePosition(new VehiclePosition(fromCityId, 
							routingResult.getNextCity(), 0), WorldClock);
				}
			}
		}
	}
	
	public Iterable<Vehicle> GetMetrics()
	{
		return ArrivedVehicles.values();
	}
	
	public void AddVehicle() throws Exception {
		long startCity = GetRandomCity();
		long endCity = GetRandomCity();
		while (!Connective(startCity, endCity))
		{
			startCity = GetRandomCity();
			endCity = GetRandomCity();
		}
		RoutingAlgorithmBase routingAlgorithm = null;
		switch (Configurations.getRoutingAlgo()) {
		case Greedy:
			routingAlgorithm = new GreedyRouting();
			break;
		default:
			throw new Exception("Other algorithm not suppoted");
		}
		Vehicle newVehicle = new Vehicle(VehicleIdCounter++, startCity, endCity, 
				WorldClock, MaxSpeed, routingAlgorithm);
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
