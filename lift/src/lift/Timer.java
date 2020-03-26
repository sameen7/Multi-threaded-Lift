package lift;

public class Timer {
	private static double starttime;
	private static double inittime;
	
	public static void start(){
		starttime = System.currentTimeMillis();
	}
	
	public static double getTime(){
		return System.currentTimeMillis();
	}
	
	public static double getstartTime(){
		return starttime;
	}
	
	public static double getsystemTime(){
		double time = (int) (System.currentTimeMillis() - starttime)/1000;
		double i = (System.currentTimeMillis() - starttime)%1000;
		if(i >= 0.5){
			time += 1;
		}
		return time;
	}
}
