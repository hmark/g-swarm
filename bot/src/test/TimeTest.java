package test;

public class TimeTest {
	
	private long _startTime;
	
	public TimeTest(){
		_startTime = System.currentTimeMillis();
	}
	
	public long getTime(){
		return System.currentTimeMillis() - _startTime;
	}
	
	public void reset(){
		_startTime = System.currentTimeMillis();
	}

}
