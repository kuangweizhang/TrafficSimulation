package Utility;


public class TimeInterval implements Cloneable{

	private int Hours;
	private int Minutes;
	private int Seconds;
	
	public TimeInterval(int hours, int minutes, int seconds)
	{
		if (hours < 0)
		{
			throw new UnsupportedOperationException("Hour cannot be less than 0");
		}
		if ((minutes < 0)||(minutes > 59))
		{
			throw new UnsupportedOperationException("Minutes out of bound");
		}
		if ((seconds < 0)||(seconds > 59))
		{
			throw new UnsupportedOperationException("Seconds out of bound");
		}
		this.Hours = hours;
		this.Minutes = minutes;
		this.Seconds = seconds;
	}
	
	public TimeInterval()
	{
		this.Hours = 0;
		this.Minutes = 0;
		this.Seconds = 0;
	}
	
	public TimeInterval addIntervalToThis(TimeInterval timeInterval)
	{
		this.Seconds += timeInterval.getSeconds();
		if (this.Seconds >= 60)
		{
			this.Seconds -= 60;
			this.Minutes++;
		}
		this.Minutes += timeInterval.getMinutes();
		if (this.Minutes >= 60)
		{
			this.Minutes -= 60;
			this.Hours++;
		}
		this.Hours += timeInterval.Hours;
		return this;
	}
	
	public TimeInterval addInterval(TimeInterval timeInterval)
	{
		TimeInterval retval = this.clone();
		retval.addIntervalToThis(timeInterval);
		return retval;
	}
	
	public double devidedBy(TimeInterval timeInterval)
	{
		return this.getTotalSeconds()/(double)timeInterval.getTotalSeconds();
	}
	
	public boolean earlierThan(TimeInterval timeInterval)
	{
		return this.getTotalSeconds() < timeInterval.getTotalSeconds();
	}
	
	public boolean isSmaller(TimeInterval timeInterval)
	{
		return earlierThan(timeInterval);
	}
	
	public int getTotalSeconds(){
		return this.Hours * 3600 + this.Minutes*60 + this.Seconds;
	}
	
	public static TimeInterval FromHour(double hours)
	{
		return TimeInterval.FromeSeconds((int)(hours*3600));
	}
	
	public static TimeInterval FromMinute(int minute)
	{
		return new TimeInterval(minute/60, minute%60, 0);
	}
	
	public static TimeInterval FromeSeconds(int sec)
	{
		return new TimeInterval(sec/3600, (sec / 60)%60 , sec % 60);
	}
	
	public static TimeInterval MaxTimeInterval = new TimeInterval(1000, 0, 0);
	
	public TimeInterval subtractInterval(TimeInterval timeInterval)
	{
		return FromeSeconds(this.getTotalSeconds() - timeInterval.getTotalSeconds());
	}

	public int getHours() {
		return Hours;
	}

	public void setHours(int hours) {
		Hours = hours;
	}

	public int getMinutes() {
		return Minutes;
	}

	public void setMinutes(int minutes) {
		if ((minutes < 0)||(minutes > 59))
		{
			throw new UnsupportedOperationException("Minutes out of bound");
		}
		Minutes = minutes;
	}

	public int getSeconds() {
		return Seconds;
	}

	public void setSeconds(int seconds) {
		if ((seconds < 0)||(seconds > 59))
		{
			throw new UnsupportedOperationException("Seconds out of bound");
		}
		Seconds = seconds;
	}
	
	public double distanceTraveled(double hourSpeed)
	{
		double hour = this.Hours + this.Minutes/60.0 + this.Seconds/3600.0;
		return hour * hourSpeed;
	}
	
	public TimeInterval devideBy(int k)
	{
		if(k == 0)
		{
			return TimeInterval.FromeSeconds(0);
		}
		else
		{
			return TimeInterval.FromeSeconds(this.getTotalSeconds()/k);
		}
	}
	
	public TimeInterval clone()
	{
		return new TimeInterval(this.getHours(), this.getMinutes(), this.getSeconds());
	}
	
	@Override
	public String toString()
	{
		return this.Hours + ":" + this.Minutes + ":" + this.Seconds;
	}
	
	public final static TimeInterval LARGE_VALUE = new TimeInterval(9999, 59, 59);
	
}
