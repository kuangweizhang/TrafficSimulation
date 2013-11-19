package Vehicle;
/**
 * Describe the position of the vehicle on the map.
 * @author Kevin
 *
 */
public class VehiclePosition {
	
	private long FromIntersection;
	private long ToIntersection;
	private double KM;
	
	public boolean InCity()
	{
		return FromIntersection == ToIntersection;
	}
	
	public long getFromIntersection() {
		return FromIntersection;
	}

	public void setFromIntersection(long fromIntersection) {
		FromIntersection = fromIntersection;
	}

	public long getToIntersection() {
		return ToIntersection;
	}

	public void setToIntersection(long toIntersection) {
		ToIntersection = toIntersection;
	}

	public double getKM() {
		return KM;
	}

	public void setKM(double kM) {
		KM = kM;
	}

	public VehiclePosition(long from, long to, double km) {
		this.FromIntersection = from;
		this.ToIntersection = to;
		this.KM = km;
	}
	
	public void ArrivedIntersection()
	{
		this.FromIntersection = this.ToIntersection;
		this.KM = 0;
	}
	
	public String toString()
	{
		StringBuilder position = new StringBuilder();
		position.append("From:");
		position.append(this.FromIntersection);
		position.append(" To:");
		position.append(this.ToIntersection);
		position.append(" Distance:");
		position.append(this.KM);
		return position.toString();
	}
}