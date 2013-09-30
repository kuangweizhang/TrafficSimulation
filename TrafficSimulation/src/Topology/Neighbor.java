package Topology;

public class Neighbor {
	private Intersection NeighborValue;
	private Double Distance;
	private Integer NumberOfLane;
	private Double CurrentDelay;

	public Neighbor(Intersection me, Intersection myNeighbor, int numberOfLaneBetween)
	{
		NeighborValue = myNeighbor;
		NumberOfLane = numberOfLaneBetween;
		Distance = CoordinateCalculator.CoordinateToDistance(me.getLongitude(), me.getLatitude(), 
				myNeighbor.getLongitude(), myNeighbor.getLatitude());
	}
	
	public Double getCurrentDelay() {
		return CurrentDelay;
	}

	public void setCurrentDelay(Double currentDelay) {
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
