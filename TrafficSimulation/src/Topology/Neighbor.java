package Topology;

import Utility.TimeInterval;

public class Neighbor {
	private Intersection NeighborValue;
	private Double Distance;
	private Integer NumberOfLane;
	private TimeInterval CurrentDelay;

	public Neighbor(Intersection me, Intersection myNeighbor, int numberOfLaneBetween)
	{
		NeighborValue = myNeighbor;
		NumberOfLane = numberOfLaneBetween;
		Distance = CoordinateCalculator.CoordinateToDistance(me.getLongitude(), me.getLatitude(), 
				myNeighbor.getLongitude(), myNeighbor.getLatitude());
	}
	
	public TimeInterval getCurrentDelay() {
		return CurrentDelay;
	}

	public void setCurrentDelay(TimeInterval currentDelay) {
		CurrentDelay = currentDelay;
	}
	
	public Intersection getNeighborValue() {
		return NeighborValue;
	}

	public void setNeighborValue(Intersection neighborValue) {
		NeighborValue = neighborValue;
	}

	public Double getDistance() {
		return Distance;
	}

	public void setDistance(Double distance) {
		Distance = distance;
	}

	public Integer getNumberOfLane() {
		return NumberOfLane;
	}

	public void setNumberOfLane(Integer numberOfLane) {
		NumberOfLane = numberOfLane;
	}
}
