package Topology;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class Intersection {
	
	/**
	 * The latitude of this intersection.
	 */
	private double latitude;
	
	/**
	 * The longitude of this intersection. 
	 */
	private double longitude;
	
	/**
	 * The id of this intersection.
	 */
	private long id;
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public long getId() {
		return id;
	}

	/**
	 * Neighbors with current intersection.
	 * Key is the ID of the neighbor and the value is the actual neighbor.
	 */
	private HashMap<Long, Neighbor> Neighbors = new HashMap<Long, Neighbor>();
	
	public long GetNumberOfNeighbors()
	{
		return Neighbors.keySet().size();
	}
	
	public Set<Long> GetNeighborIds()
	{
		return Neighbors.keySet();
	}
	
	public Collection<Neighbor> GetNeighbors()
	{
		return Neighbors.values();
	}
	
	public Neighbor GetNeighborById(long id) throws Exception
	{
		if (Neighbors.containsKey(id))
		{
			return Neighbors.get(id);
		}
		else {
			throw new Exception("Neighbor does not exist");
		}
	}
	
	/**
	 * The constructor of Intersection.
	 * @param lat
	 * @param lon
	 * @param id
	 */
	public Intersection(double lat, double lon, long id)
	{
		this.latitude = lat;
		this.longitude = lon;
		this.id = id;
	}
	
	public void print() throws Exception
	{
		throw new Exception("NotImplement");
	}
	
	/**
	 * Add an neighbor to this intersection.
	 * @param neighbor
	 */
	public void AddNeighbor(Intersection neighbor, int numberOfLanes, double speedLimit)
	{
		this.Neighbors.put(neighbor.id, new Neighbor(this, neighbor, numberOfLanes, speedLimit));
	}
}
