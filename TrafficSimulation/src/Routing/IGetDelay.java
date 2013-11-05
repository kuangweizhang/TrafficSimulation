package Routing;

import Utility.TimeInterval;

public interface IGetDelay {

	public TimeInterval GetDelay(long startCity, long destinationCity, 
			TimeInterval time) throws Exception;
}
