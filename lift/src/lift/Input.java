package lift;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input extends Thread {
	private Queue queue;
	private Eleavtor[] lift;
	private Timer timer;
	private Queue[] elequeue;
	private double firsttime;
	private FileWrite fw;
	
	public Input(Queue queue, Eleavtor[] lift, Queue[] elequeue, FileWrite fw){
		this.timer = new Timer();
		this.queue = queue;
		this.lift = lift;
		this.elequeue = elequeue;
		this.fw = fw;
	}
	
	public void fAsk(int pos, String way, double time){
		if(pos > 0 && pos < 21 && time >= 0){
			Floor floor = new Floor();
			if(way.equals("+")){
				floor.Up(this.queue, time, pos, fw);
			}else{
				floor.Down(this.queue, time, pos, fw);
			}
		}else{
			if(way.equals("+")){
				this.fw.tofile(System.currentTimeMillis() + ": INVALID" + "[" + "FR, " + pos + ", " + "UP, " + time + "]");
				System.out.println(System.currentTimeMillis() + ": INVALID" + "[" + "FR, " + pos + ", " + "UP, " + time + "]");
			}else{
				this.fw.tofile(System.currentTimeMillis() + ": INVALID" + "[" + "FR, " + pos + ", " + "DOWN, " + time + "]");
				System.out.println(System.currentTimeMillis() + ": INVALID" + "[" + "FR, " + pos + ", " + "DOWN, " + time + "]");
			}
		}
	}
	
	public void eAsk(int pos, double time, int id){
		if(pos > 0 && pos < 21 && time >= 0 && id > 0 && id < 4){
			this.lift[id - 1].Go(this.queue, time, pos, id);
		}else{
			this.fw.tofile(System.currentTimeMillis() + ": INVALID" + "[" + "ER, #" + id + "," + pos + ", "  + time + "]");
			System.out.println(System.currentTimeMillis() + ": INVALID" + "[" + "ER, #" + id + "," + pos + ", "  + time + "]");
		}
	}
	
	public double getFirsttime(){
		return this.firsttime;
	}
	
	public void run(){
		String input = new String();
		double ct = 0;
		int flag = 0;
		Scanner in = new Scanner(System.in);
		try{
			input = in.nextLine().replace(" ", "");
			//this.timer.start();
			ct = this.timer.getsystemTime();
		}catch(Exception e){
			//System.exit(0);
		};
		while(!input.equals("run")){
			String[] str = input.split(";");
			int num = str.length;
			if(str.length > 10){
				num = 10;
			}
			double time = ct;
			for(int i = 0; i < num; i++){
				Pattern pat1 = Pattern.compile("\\(FR,(\\+?\\d+),UP\\)");
				Pattern pat2 = Pattern.compile("\\(FR,(\\+?\\d+),DOWN\\)");
				Pattern pat3 = Pattern.compile("\\(ER,\\#(\\+?\\d+),(\\+?\\d+)\\)");
				Matcher m1 = pat1.matcher(str[i]);
				Matcher m2 = pat2.matcher(str[i]);
				Matcher m3 = pat3.matcher(str[i]);
				if(m1.matches()){
					try{
						int pos = Integer.parseInt(m1.group(1));
						fAsk(pos, "+", time);
					}catch(Exception e){
						this.fw.tofile("INVALID" + input.replace("(", "[").replace(")", "]"));
						System.out.println("INVALID" + input.replace("(", "[").replace(")", "]"));
					};
				}else if(m2.matches()){
					try{
						int pos = Integer.parseInt(m2.group(1));
						fAsk(pos, "-", time);
					}catch(Exception e){
						this.fw.tofile("INVALID" + input.replace("(", "[").replace(")", "]"));
						System.out.println("INVALID" + input.replace("(", "[").replace(")", "]"));
					};
				}else if(m3.matches()){
					try{
						int id = Integer.parseInt(m3.group(1));
						int pos = Integer.parseInt(m3.group(2));
						eAsk(pos, time, id);
					}catch(Exception e){
						this.fw.tofile("INVALID" + input.replace("(", "[").replace(")", "]"));
						System.out.println("INVALID" + input.replace("(", "[").replace(")", "]"));
					};
				}else{
					this.fw.tofile(System.currentTimeMillis() + ": INVALID" + "[" + input + "]");
					System.out.println(System.currentTimeMillis() + ": INVALID" + "[" + input + "]");
				}
				try{
					input = in.nextLine().replace(" ", "");
					ct = this.timer.getsystemTime();
				}catch(Exception e){
					//System.exit(0);
				};
			}
		}
	}
}
