package Simulator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;

import Utility.Configurations;
import Utility.TimeInterval;
import Vehicle.Vehicle;
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
	public static final double MaxSpeed = 60;
	private Random RandomGenerator;
	private PrintWriter RunningLogWriter;
	private PrintWriter FinishedVehicleWriter;

	public Simulator(Configurations configs) throws IOException
	{
		this.Configurations = configs;
		this.Topology = new Topology(this.Configurations.getMapFile());
		Simulator.WorldClock = new TimeInterval();
		this.RandomGenerator = new Random(this.Configurations.getRandomSeed());
		long currentTime = System.currentTimeMillis();
		if (this.Configurations.isLogging())
		{
			RunningLogWriter = new PrintWriter(new BufferedWriter(
					new FileWriter("R" + currentTime + ".txt")));
		}
		if (this.Configurations.isVehicleLogging())
		{
			FinishedVehicleWriter = new PrintWriter(new BufferedWriter(
					new FileWriter("V" + currentTime + ".txt")));
		}
	}

	public void Run() throws Exception
	{
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
				WrittingVehicle(currentVehicle);
				ArrivedVehicles.put(currentVehicle.getId(), currentVehicle);
			}
		}
		for (Long arrivedVehiclesId : ArrivedVehicles.keySet())
		{
			Vehicles.remove(arrivedVehiclesId);
		}
		Simulator.WorldClock.addIntervalToThis(UnviersalInterval);
	}

	public boolean isFinished()
	{
		return Vehicles.keySet().size() == 0;
	}

	public void FinishRun()
	{
		this.RunningLogWriter.close();
	}

	private void WrittingLogs(String log)
	{
		if (RunningLogWriter != null)
		{
			RunningLogWriter.println(Simulator.WorldClock + ":" + log);
			RunningLogWriter.flush();
		}
	}

	private void WrittingVehicle(Vehicle vehicle)
	{
		if (FinishedVehicleWriter != null)
		{
			FinishedVehicleWriter.println(vehicle);
			FinishedVehicleWriter.flush();
		}
	}

	public Iterable<Vehicle> GetMetrics()
	{
		return ArrivedVehicles.values();
	}

	public String getTimeTickReport() throws Exception
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(WorldClock);
		for (int i = stringBuilder.length(); i <= 10; i++)
		{
			stringBuilder.append(" ");
		}
		stringBuilder.append("Total cars:"
				+ (this.Vehicles.size() + this.ArrivedVehicles.size()) + " ");
		for (int i = stringBuilder.length(); i <= 32; i++)
		{
			stringBuilder.append(" ");
		}
		stringBuilder.append("Driving Cars:" + (this.Vehicles.size()) + " ");
		for (int i = stringBuilder.length(); i <= 52; i++)
		{
			stringBuilder.append(" ");
		}
		stringBuilder.append("Arrived Cars:" + (this.ArrivedVehicles.size())
				+ " ");
		for (int i = stringBuilder.length(); i <= 75; i++)
		{
			stringBuilder.append(" ");
		}
		stringBuilder.append("Average Alpha Ratio   " + getAverageAlphaRatio());

		return stringBuilder.toString();
	}

	public double getAverageAlphaRatio() throws Exception
	{
		double totalAlpha = 0;
		int kCounter = 0;
		for (Vehicle arraiedVehicle : ArrivedVehicles.values())
		{
			totalAlpha += arraiedVehicle.getAlphaRatio();
			kCounter++;
//			 System.out.println("Individual Alpha:" +
//					 arraiedVehicle.getAlphaRatio());
		}
		// System.out.println(kCounter);
		// System.out.println(counterInterval);
		return totalAlpha / (double) kCounter;
	}

	/**
	 * Add vehicles to system according to the VehicleGenerateRate
	 */
	public void AddVehicleToSystem() throws Exception
	{
		int numberOfVehicleToGenerate = (int) (Topology.NumberOfIntersections() * Configurations
				.getVehicleGenerateRate());
		for (int i = 0; i < numberOfVehicleToGenerate; i++)
		{
			AddVehicle();
		}
	}

	/**
	 * Add one vehicle from and to random selected cities.
	 * 
	 * @throws Exception
	 */
	public void AddVehicle() throws Exception
	{
		long startCity = GetRandomCity();
		long endCity = GetRandomCity();
		while (startCity == endCity || !Connective(startCity, endCity))
		{
			startCity = GetRandomCity();
			endCity = GetRandomCity();
		}
		double totalPercent = 0;
		for (int i = 0; i < this.Configurations.NumberOfCategoriesOfVehicle; i++)
		{
			totalPercent += this.Configurations.getVehiclePercentages()[i];
		}
		Vehicle newVehicle = null;
		double randomeChoice = this.RandomGenerator.nextDouble();
		if (randomeChoice < this.Configurations.getVehiclePercentages()[0]
				/ totalPercent)
		{
			newVehicle = new Vehicle(VehicleIdCounter++, startCity, endCity,
					WorldClock, MaxSpeed, new RoutingStrategy(
							this.Configurations, 0), this.Topology);
		} else
		{
			if (randomeChoice > (1 - this.Configurations
					.getVehiclePercentages()[2] / totalPercent))
			{
				newVehicle = new Vehicle(VehicleIdCounter++, startCity,
						endCity, WorldClock, MaxSpeed, new RoutingStrategy(
								this.Configurations, 2), this.Topology);
			} else
			{
				newVehicle = new Vehicle(VehicleIdCounter++, startCity,
						endCity, WorldClock, MaxSpeed, new RoutingStrategy(
								this.Configurations, 1), this.Topology);
			}
		}
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
		return (Long) keys[this.RandomGenerator.nextInt(keys.length)];
	}
}
