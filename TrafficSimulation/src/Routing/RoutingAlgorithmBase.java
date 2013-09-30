package Routing;

import java.sql.Time;
import java.util.LinkedList;

import Topology.Topology;

public abstract class RoutingAlgorithmBase {
	private long NextCity;
	private Time ExpectingDelay;
	private LinkedList<Long> Path;

	public abstract void getNextCity(Topology topology, 
			long destinationCity, long currentCity, double maxSpeed);
	
	public abstract double getCost(long fromCity, long toCity);
	
	public long getNextCity() {
		return NextCity;
	}
	public Time getExpectingDelay() {
		return ExpectingDelay;
	}
	public LinkedList<Long> getPath() {
		return Path;
	}
}
