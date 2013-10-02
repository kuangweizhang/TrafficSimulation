package Utility;

import java.sql.Time;
import java.util.LinkedList;

public class RoutingResult {
	private long NextCity;
	private Time ExpectingDelay;
	private LinkedList<Long> Path;
	
	public RoutingResult(long nextcity, Time expectingDelay, LinkedList<Long> path)
	{
		NextCity = nextcity;
		ExpectingDelay = expectingDelay;
		Path = path;
	}
	
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
