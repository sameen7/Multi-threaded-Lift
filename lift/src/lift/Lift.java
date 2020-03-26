package lift;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lift {
	public static void  main(String[] args){
		Timer timer = new Timer();
		timer.start();
		FileWrite fw = new FileWrite();
		Queue queue = new Queue(fw);
		Queue[] elequeue = new Queue[3];
		Eleavtor[] lift = new Eleavtor[3];
		for(int i = 0; i < 3; i++){
			elequeue[i] = new Queue(fw);
			lift[i] = new Eleavtor(i, elequeue[i], fw);
		}
		Scheduler sch = new Scheduler(queue);
		Scheduler sche = new Sche(queue, lift, elequeue, fw);
		Input input = new Input(queue, lift, elequeue,fw);
		input.start();
		sche.start();
		for(int i = 0; i < 3; i++){
			lift[i].start();
		}
	}
}
