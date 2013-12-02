package Routing;

import Utility.TimeInterval;

public interface IGetDelay
{

	/**
	 * Return the delay between two immediate neighbors. If two neighbors are
	 * not immediate adjacent, throw neighbor not exist exception.
	 * 
	 * @param startCity
	 * @param destinationCity
	 * @param time
	 *            - Time offset for future delay.
	 * @return
	 * @throws Exception
	 */
	public TimeInterval GetDelay(long startCity, long destinationCity,
			TimeInterval time) throws Exception;

	/**
	 * Return the delay between two immediate neighbors. If two neighbors are
	 * not immediate adjacent, throw neighbor not exist exception.
	 * @param startCity
	 * @param destinationCity
	 * @return
	 * @throws Exception
	 */
	public TimeInterval GetDelay(long startCity, long destinationCity)
			throws Exception;
}
