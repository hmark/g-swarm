package external;

public class ThreadManager {
	
	private static Thread thread;

	static public void addThread(Thread t){
		thread = t;
	}
	
	static public void stopThreads(){
		thread.interrupt();
	}
}
