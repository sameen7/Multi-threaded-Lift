package lift;

public class Floor {
	
	public void Up(Queue queue, double time,  int pos, FileWrite fw){
		if(pos < 20 && pos > 0){
			Request ask = new Request(time, pos, "FR", "+", "UP", 0);
			queue.add(ask);
		}else{
			fw.tofile(System.currentTimeMillis() + ": INVALID" + "[" + "FR, " + pos + ", " + "UP, " + time + "]");
			System.out.println(System.currentTimeMillis() + ": INVALID" + "[" + "FR, " + pos + ", " + "UP, " + time + "]");
		}
	}
	
	public void Down(Queue queue, double time,  int pos,FileWrite fw){
		if(pos > 1 && pos < 21){
			Request ask = new Request(time, pos, "FR", "-", "DOWN", 0);
			queue.add(ask);
		}else{
			fw.tofile(System.currentTimeMillis() + ": INVALID" + "[" + "FR, " + pos + ", " + "DOWN, " + time + "]");
			System.out.println(System.currentTimeMillis() + ": INVALID" + "[" + "FR, " + pos + ", " + "DOWN, " + time + "]");
		}
	}

}
