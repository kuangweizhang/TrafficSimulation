package Topology;

public class CoordinateCalculator {

	public static double CoordinateToDistance(double startlon, double startlat,
			double endlon, double endlat)
	{
		double d2r = Math.PI / 180;

		double dlong = (endlon - startlon) * d2r;
		double dlat = (endlat - startlat) * d2r;
		double a = Math.pow(Math.sin(dlat / 2.0), 2) + Math.cos(startlat * d2r)
				* Math.cos(endlat * d2r) * Math.pow(Math.sin(dlong / 2.0), 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = 6367 * c;

		return d;
	}
	
	public static double CalculateDistanceBetween(Intersection intersection1, 
			Intersection intersection2)
	{
		return CoordinateToDistance(
				intersection1.getLongitude(), intersection1.getLatitude(), 
				intersection2.getLongitude(), intersection2.getLatitude());
	}
	
	public static double CalculateDistanceBetween(Neighbor neighbor, Intersection intersection)
	{
		return CalculateDistanceBetween(neighbor.getNeighborValue(), intersection);
	}
	
	public static double CalculateDistanceBetween(Intersection intersection, Neighbor neighbor)
	{
		return CalculateDistanceBetween(intersection, neighbor.getNeighborValue());
	}
}
