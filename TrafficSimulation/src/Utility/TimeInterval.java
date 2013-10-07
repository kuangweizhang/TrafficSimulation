package Utility;

public class TimeInterval {

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
	
	public TimeInterval addInterval(TimeInterval timeInterval)
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
	
	
}
