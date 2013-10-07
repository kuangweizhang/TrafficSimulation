package Utility;

import java.sql.Time;
import java.util.LinkedList;

public class RoutingResult {
	private long NextCity;
	private TimeInterval ExpectingDelay;
	private LinkedList<Long> Path;
	
	/**
	 * 
	 * @param nextcity
	 * @param expectingDelay Null means delay not calculated.
	 * @param path - Null means path not calculated.
	 */
	public RoutingResult(long nextcity, TimeInterval expectingDelay, LinkedList<Long> path)
	{
		NextCity = nextcity;
		ExpectingDelay = expectingDelay;
		Path = path;
	}
	
	public long getNextCity() {
		return NextCity;
	}

	public TimeInterval getExpectingDelay() throws Exception {
		if (ExpectingDelay != null)
		{
			return ExpectingDelay;
		}
		else {
			throw new Exception("Expecting delay not exist");
		}
	}

	public LinkedList<Long> getPath() throws Exception {
		if (Path != null)
		{
			return Path;
		}
		else {
			throw new Exception("Path does not exist");
		}
	}
}
