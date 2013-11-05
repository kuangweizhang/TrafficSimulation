package Vehicle;

import Utility.TimeInterval;

public class Reservation {
	private TimeInterval time;
	private long fromIntersect;
	private long toIntersect;
	
	public Reservation(TimeInterval time, long from, long to)
	{
		this.time = time;
		this.fromIntersect = from;
		this.toIntersect = to;
	}

	public TimeInterval getTime() {
		return time;
	}

	public long getFromIntersect() {
		return fromIntersect;
	}

	public long getToIntersect() {
		return toIntersect;
	}
	
	
}
