package Simulator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import Utility.Configurations;
import Utility.TimeInterval;
import Vehicle.Vehicle;
import Vehicle.VehiclePosition;
import Vehicle.VehicleStage;
import Routing.DijkstraRouting;
import Routing.GetDelayWithoutTraffic;
import Routing.RoutingStrategy;
import Topology.Topology;

public class Simulator
{

	private Topology Topology;
	private Configurations Configurations;
	public static TimeInterval WorldClock;
	public final static TimeInterval UnviersalInterval = new TimeInterval(0, 0,
			20);
	private static long VehicleIdCounter = 0;
	private HashMap<Long, Vehicle> Vehicles = new HashMap<Long, Vehicle>();
	private HashMap<Long, Vehicle> ArrivedVehicles = new HashMap<Long, Vehicle>();
	private final double MaxSpeed = 60;
	private Random RandomCity;
	private PrintWriter Logwritter;

	public Simulator(Configurations configs) throws IOException
	{
		this.Configurations = configs;
		this.Topology = new Topology(this.Configurations.getMapFile());
		Simulator.WorldClock = new TimeInterval();
		this.RandomCity = new Random(this.Configurations.getRandomSeed());
		if (this.Configurations.isLogging())
		{
			Logwritter = new PrintWriter(new BufferedWriter(new FileWriter("T"
					+ System.currentTimeMillis() + ".txt")));
		}
	}

	public void Run() throws Exception
	{
		Simulator.WorldClock.addIntervalToThis(UnviersalInterval);
		// LinkedList<Long> arrivedVehicles = new LinkedList<Long>();
		for (long vehicleId : Vehicles.keySet())
		{
			Vehicle currentVehicle = Vehicles.get(vehicleId);
			// System.out.println("World clock write:" + WorldClock);
			this.WrittingLogs("Current Vehicle:" + currentVehicle.toString());
			currentVehicle.goingForward(this.UnviersalInterval);
			this.WrittingLogs("Current Vehicle After Move:"
					+ currentVehicle.toString());
			if (currentVehicle.isArrived())
			{
				ArrivedVehicles.put(currentVehicle.getId(), currentVehicle);
			}
		}
		for (Long arrivedVehiclesId : ArrivedVehicles.keySet())
		{
			Vehicles.remove(arrivedVehiclesId);
		}
	}

	public boolean isFinished()
	{
		return Vehicles.keySet().size() == 0;
	}

	public void FinishRun()
	{
		this.Logwritter.close();
	}

	private void WrittingLogs(String log)
	{
		if (Logwritter != null)
		{
			Logwritter.println(Simulator.WorldClock + ":" + log);
			Logwritter.flush();
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
		stringBuilder.append("Total cars:"
				+ (this.Vehicles.size() + this.ArrivedVehicles.size()) + " ");
		stringBuilder.append("Driving Cars:" + (this.Vehicles.size()) + " ");
		stringBuilder.append("Arrived Cars:" + (this.ArrivedVehicles.size())
				+ " ");
		stringBuilder.append("Average Alpha" + getAverageAlpha());

		return stringBuilder.toString();
	}

	public TimeInterval getAverageAlpha()
	{
		TimeInterval counterInterval = new TimeInterval();
		int kCounter = 0;
		for (Vehicle arraiedVehicle : ArrivedVehicles.values())
		{
			counterInterval
					.addIntervalToThis(arraiedVehicle.getExpectingDifference());
			kCounter++;
		}
		return counterInterval.devideBy(kCounter);
	}

	public void AddVehicle() throws Exception
	{
		long startCity = GetRandomCity();
		long endCity = GetRandomCity();
		while (startCity == endCity || !Connective(startCity, endCity))
		{
			startCity = GetRandomCity();
			endCity = GetRandomCity();
		}

		Vehicle newVehicle = new Vehicle(VehicleIdCounter++, startCity,
				endCity, WorldClock, MaxSpeed, new RoutingStrategy(
						this.Configurations), this.Topology);
		Vehicles.put(newVehicle.getId(), newVehicle);
	}

	private boolean Connective(long city1, long city2) throws Exception
	{
		DijkstraRouting routing = new DijkstraRouting();
		return null != routing.getRoutingResult(this.Topology, city2, city1,
				60, new GetDelayWithoutTraffic());
	}

	private long GetRandomCity()
	{
		Object[] keys = Topology.getIntersections().keySet().toArray();
		return (Long) keys[this.RandomCity.nextInt(keys.length)];
	}
}
