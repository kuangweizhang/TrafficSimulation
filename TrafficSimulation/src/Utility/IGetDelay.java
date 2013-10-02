package Utility;

import Topology.Topology;

public interface IGetDelay {

	public double GetDelay(Topology topology, long startCity, 
			long destinationCity, double maxSpeed) throws Exception;
}
