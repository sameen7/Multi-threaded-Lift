package lift;

import java.math.BigDecimal;

public class Eleavtor extends Thread implements Print {
	private int pos;
	private String state;
	private double etime = 0;
	private String runState;
	private int runflo;
	private int id;
	private Queue elequeue;
	private Queue doqueue;
	private double time = 0;
	private int floor;
	private Timer timer;
	private String elestate = "STILL";
	private int tofloor;
	private String ifend = "yes";
	private Input input;
	private FileWrite fw;
	
	public Eleavtor(int id, Queue elequeue, FileWrite fw){
		this.pos = 1;
		this.state = "STILL";
		this.runflo = 0;
		this.id = id + 1;
		this.floor = 1;
		this.timer = new Timer();
		this.doqueue = new Queue(fw);
		this.elequeue = elequeue;
		this.input = new Input(elequeue, null, null,fw);
		this.fw = fw;
	}
	
	public void Go(Queue queue, double time,  int pos, int id){
		if(pos > 0 && pos < 21){
			Request ask = new Request(time, pos, "ER", "=", "=", id);
			queue.add(ask);
			this.elequeue.add(ask);
		}else{
			System.out.println(System.currentTimeMillis() + ": INVALID" + "[" + "ER, #" + id + "," +pos + ", "  + time + "]");
			fw.tofile(System.currentTimeMillis() + ": INVALID" + "[" + "ER, #" + id + "," +pos + ", "  + time + "]");
		}
	}
	
	public void run(){
		while(true) {
            while (!this.elequeue.isEmpty()) {
            	//System.out.println(input.getFirsttime());
                this.go();
            }
        }
	}
	
	public void go(){
		if(this.elequeue.isEmpty()){
			//System.exit(0);
		}else{
			/*Request ask = this.elequeue.getAsk();
			if(!this.sche.pass(ask)){
				this.time += 1;
				this.lift.print(ask.getTo(), this.state, this.time, ask);
				ask.seteTime(this.time);
				this.doqueue.doAdd(ask);
			}else{
				print(floor, state, time, ask);
			}*/
			//while(!this.elequeue.isEmpty()){
				synchronized(this.elequeue){
					int flag = 0;
					Request ask = this.elequeue.getdoAsk(0);
					if(!pass(ask)){
						ifend = "no";
						this.tofloor = ask.getTo();
						/*if(ask.getTime() > this.time){
							this.time = ask.getTime();
						}*/
						if(this.tofloor > this.floor){
							this.elestate = "UP";
							ask.setWay("+");
						}else if(this.tofloor < this.floor){
							this.elestate = "DOWN";
							ask.setWay("-");
						}else{
							this.elestate = "STILL";
						}
						if(this.elestate.equals("UP")){
							for(int i = this.floor; i <= this.tofloor; i++){
								for(int k = 0; k < this.elequeue.size(); k++){
									ask = this.elequeue.getdoAsk(k);
									if(!pass(ask)){
										if(ask.getTime() < this.timer.getsystemTime() && !ask.getWay().equals("-") && ask.getTo() != this.floor){
											if(this.tofloor < ask.getTo() && ask.getType().equals("ER")){
												this.tofloor = ask.getTo();
											}
											if(i == ask.getTo()){
												flag = 1;
												print(i, this.elestate, this.timer.getsystemTime(), ask);
												ask.seteTime(this.timer.getsystemTime() + 6);
												this.doqueue.doAdd(ask);
												this.elequeue.remove(k);
												k--;
											}
										}
									}else{
										offprint(floor, elestate, this.timer.getsystemTime(), ask);
										this.elequeue.remove(k);
										k--;
									}
								}
								if(flag == 1){
									this.time += 6;
									try {
										sleep(6000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
								if(i != this.tofloor){
									this.time += 3;
									runflo++;
									try {
										sleep(3000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
								flag = 0;
							}
							this.floor = this.tofloor;
							ifend = "yes";
						}else if(this.elestate.equals("DOWN")){
							for(int i = this.floor; i >= this.tofloor; i--){
								for(int k = 0; k < this.elequeue.size(); k++){
									ask = this.elequeue.getdoAsk(k);
									if(!pass(ask)){
										if(ask.getTime() < this.timer.getsystemTime() && !ask.getWay().equals("+") && ask.getTo() != this.floor){
											if(this.tofloor > ask.getTo() && ask.getType().equals("ER")){
												this.tofloor = ask.getTo();
											}
											if(i == ask.getTo()){
												flag = 1;
												print(i, this.elestate, this.timer.getsystemTime(), ask);
												ask.seteTime(this.timer.getsystemTime() + 6);
												this.doqueue.doAdd(ask);
												this.elequeue.remove(k);
												k--;
											}
										}
									}else{
										offprint(floor, elestate, this.timer.getsystemTime(), ask);
										this.elequeue.remove(k);
										k--;
									}
								}
								if(flag == 1){
									this.time += 6;
									try {
										sleep(6000);
										ifend = "yes";
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
								if(i != this.tofloor){
									this.time += 3;
									runflo++;
									try {
										sleep(3000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
								flag = 0;
							}
							this.floor = this.tofloor;
							ifend = "yes";
						}else{
							if(ask.getTime() > this.time){
								this.time = ask.getTime();
							}
							this.time += 1;
							print(this.tofloor, this.elestate, this.timer.getsystemTime() + 6, ask);
							ask.seteTime(this.timer.getsystemTime() + 6);
							this.doqueue.doAdd(ask);
							this.elequeue.remove(0);
							try {
								sleep(6000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							ifend = "yes";
						}
					}else{
						offprint(floor, elestate, time, ask);
						this.elequeue.remove(0);
					}
				}
			}
		//}
	}
	
	
	public void run(Request ask){
		
	}
	
	public int getPos(){
		return this.pos;
	}
	
	public String gettheState(){
		return this.elestate;
	}
	
	public double getEtime(){
		return etime;
	}

	public void print(int pos, String state, double etime, Request ask) {
		if(ask.getType().equals("ER")){
			fw.tofile(System.currentTimeMillis() + ": ["+ ask.getType()+ ", #" + ask.getId() + ", " + ask.getTo() + ", " + (ask.getTime())+ "]" + " / " +"(#" + ask.getId() + "," +pos+","+state+","+ this.runflo + "," +(etime )+")");
			System.out.println(System.currentTimeMillis() + ": ["+ ask.getType()+ ", #" + ask.getId() + ", " + ask.getTo() + ", " + (ask.getTime())+ "]" + " / " +"(#" + ask.getId() + "," +pos+","+state+","+ this.runflo + "," +(etime)+")");
		}else{
			fw.tofile(System.currentTimeMillis() + ": ["+ ask.getType() + ", " +ask.getTo() + ", " + ask.gettheWay() + ", " +(ask.getTime())+ "]" + " / " +"(#"+this.id+","+ pos + ","+state+","+ this.runflo + "," +(etime)+")");
			System.out.println(System.currentTimeMillis() + ": ["+ ask.getType() + ", " +ask.getTo() + ", " + ask.gettheWay() + ", " +(ask.getTime())+ "]" + " / " +"(#"+this.id+","+ pos + ","+state+","+ this.runflo + "," +(etime)+")");
		}	
	}
	public void getRunstate(Sche sche){
		this.runState = sche.toString();
	}
	
	public void offprint(int pos, String state, double etime, Request ask) {
		int i =(int) (ask.getTime());
		if(ask.getType().equals("ER")){
			fw.tofile(System.currentTimeMillis() + ": SAME" + "["+ ask.getType()+ ", #" + ask.getId() + ", " + ask.getTo()+ ", " + ask.getTime() + "]" );
			System.out.println(System.currentTimeMillis() + ": SAME" + "["+ ask.getType()+ ", #" + ask.getId() + ", " + ask.getTo()+ ", " + ask.getTime() + "]" );
		}else{
			fw.tofile(System.currentTimeMillis() + ": SAME" + "["+ ask.getType() + ", " +ask.getTo() + ", " + ask.gettheWay() + ", " + ask.getTime() + "]");
			System.out.println(System.currentTimeMillis() + ": SAME" + "["+ ask.getType() + ", " +ask.getTo() + ", " + ask.gettheWay() + ", " + ask.getTime() + "]");
		}	
	}
	
	public boolean pass(Request ask){
		int i;
		for(i = doqueue.size() - 1; i >= 0 ; i--){
			Request doask = this.doqueue.getdoAsk(i);
			if(doask.getType().equals(ask.getType()) && doask.getTo() == ask.getTo()){
				if((ask.getType().equals("FR") && doask.gettheWay().equals(ask.gettheWay())) || ask.getType().equals("ER")){
					if(ask.getTime() <= doask.geteTime()){
						return true;
					}else{
						return false;
					}
				}
			}
		}
		return false;
	}
	
	public int getFloor(){
		return this.floor;
	}
	
	public int gettoFloor(){
		return this.tofloor;
	}
	
	public int getMove(){
		return this.runflo;
	}
	
	public String getIfend(){
		return this.ifend;
	}
	
	public Queue getQueue(){
		return this.elequeue;
	}
}
