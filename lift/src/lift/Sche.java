package lift;

public class Sche extends Scheduler implements Print {
	//private Queue queue;
	//private Queue doqueue;
	private Eleavtor[] lift;
	private Queue[] elequeue;
	private FileWrite fw;
	
	public Sche(Queue queue, Eleavtor[] lift, Queue[] elequeue, FileWrite fw) {
		super(queue);
		this.lift = lift;
		this.elequeue = elequeue;
		this.fw = fw;
	}
	
	public void run(){
		while(true){
			while(!this.queue.isEmpty()){
				//System.out.println("0");
				int psflag = 0;
				//synchronized (this.queue){
					Request ask = this.queue.getdoAsk(0);
					int[] flag = new int [3];
					if(ask.getType().equals("FR")){ 
						for(int i = 0; i < 3; i++){
							flag[i] = 0;
						}
						int min = -1;
						int j = 0;
						for(int i = 0; i < 3; i++){
							if(!this.lift[i].pass(ask)){
								if(this.lift[i].gettheState().equals("UP")){
									if(ask.getTo() > lift[i].getFloor() && ask.getTo() <= lift[i].gettoFloor() && ask.getWay().equals("+")){
										flag[i] = 1;
										min = this.lift[i].getMove();
									}
								}else if(this.lift[i].gettheState().equals("DOWN")){
									if(ask.getTo() < lift[i].getFloor() && ask.getTo() >= lift[i].gettoFloor() && ask.getWay().equals("-")){
										flag[i] = 1;
										min = this.lift[i].getMove();
									}
								}
							}else{
								print(i, "", 0.0, ask);
								if(!this.queue.isEmpty()){
									this.queue.remove(0);
									psflag = 1;
								}
							}
						}
						if(psflag == 0){
							if(min != -1){
								for(int i = 0; i < 3; i++){
									if(flag[i] == 1 && min > this.lift[i].getMove()){
										min = this.lift[i].getMove();
										j = i;
									}
								}
								elequeue[j].doAdd(ask);
								if(!this.queue.isEmpty()){
									this.queue.remove(0);
								}
							}else{
								for(int i = 0; i < 3; i++){
									flag[i] = 0;
								}
								min = -1;
								j = 0;
								for(int i = 0; i < 3; i++){
									if(this.lift[i].getIfend().equals("yes")){
										flag[i] = 1;
										min = this.lift[i].getMove();
									}
								}
								if(min != -1){
									for(int i = 0; i < 3; i++){
										if(flag[i] == 1 && min > this.lift[i].getMove()){
											min = this.lift[i].getMove();
											j = i;
										}
									}
									elequeue[j].doAdd(ask);
									if(!this.queue.isEmpty()){
										this.queue.remove(0);
									}
								}else{
									this.queue.doAdd(ask);
									this.queue.remove(0);
								}
							}
						}
					}else{
						if(!this.queue.isEmpty()){
							this.queue.remove(0);
						}
					}
				//}
			}
		}
		/*if(this.queue.isEmpty()){
			System.exit(0);
		}else{
			Request ask = this.queue.getAsk();
			if(!pass(ask)){
				this.time += 1;
				this.lift.print(ask.getTo(), this.state, this.time, ask);
				ask.seteTime(this.time);
				this.doqueue.doAdd(ask);
			}else{
				print(floor, state, time, ask);
			}
			while(!this.queue.isEmpty()){
				int flag = 0;
				ask = queue.getdoAsk(0);
				int tofloor;
				if(!pass(ask)){
					tofloor = ask.getTo();
					if(ask.getTime() > this.time){
						this.time = ask.getTime();
					}
					if(tofloor > this.floor){
						this.state = "UP";
						ask.setWay("+");
					}else if(tofloor < this.floor){
						this.state = "DOWN";
						ask.setWay("-");
					}else{
						this.state = "STILL";
					}
					if(this.state.equals("UP")){
						for(int i = this.floor; i <= tofloor; i++){
							for(int k = 0; k < this.queue.size(); k++){
								ask = this.queue.getdoAsk(k);
								if(!pass(ask)){
									if(ask.getTime() < this.time && !ask.getWay().equals("-") && ask.getTo() != this.floor){
										if(tofloor < ask.getTo() && ask.getType().equals("ER")){
											tofloor = ask.getTo();
										}
										if(i == ask.getTo()){
											flag = 1;
											lift.print(i, this.state, time, ask);
											ask.seteTime(this.time + 1);
											this.doqueue.doAdd(ask);
											this.queue.remove(k);
											k--;
										}
									}
								}else{
									print(floor, state, time, ask);
									this.queue.remove(k);
									k--;
								}
							}
							if(flag == 1){
								this.time += 1;
							}
							if(i != tofloor){
								this.time += 0.5;
							}
							flag = 0;
						}
						this.floor = tofloor;
					}else if(this.state.equals("DOWN")){
						for(int i = this.floor; i >= tofloor; i--){
							for(int k = 0; k < this.queue.size(); k++){
								ask = this.queue.getdoAsk(k);
								if(!pass(ask)){
									if(ask.getTime() < this.time && !ask.getWay().equals("+") && ask.getTo() != this.floor){
										if(tofloor > ask.getTo() && ask.getType().equals("ER")){
											tofloor = ask.getTo();
										}
										if(i == ask.getTo()){
											flag = 1;
											lift.print(i, this.state, this.time, ask);
											ask.seteTime(this.time + 1);
											this.doqueue.doAdd(ask);
											this.queue.remove(k);
											k--;
										}
									}
								}else{
									print(floor, state, time, ask);
									this.queue.remove(k);
									k--;
								}
							}
							if(flag == 1){
								this.time += 1;
							}
							if(i != tofloor){
								this.time += 0.5;
							}
							flag = 0;
						}
						this.floor = tofloor;
					}else{
						if(ask.getTime() > this.time){
							this.time = ask.getTime();
						}
						this.time += 1;
						lift.print(tofloor, this.state, this.time, ask);
						ask.seteTime(this.time);
						this.doqueue.doAdd(ask);
						this.queue.remove(0);
					}
				}else{
					print(floor, state, time, ask);
					this.queue.remove(0);
				}
			}
		}*/
		
	}

	public void print(int pos, String state, double etime, Request ask) {
		int i =(int) (ask.getTime());
		if(ask.getType().equals("ER")){
			this.fw.tofile(System.currentTimeMillis() + ": SAME" + "["+ ask.getType()+ ", #" + ask.getId() + ", " + ask.getTo()+ ", " + ask.getTime() + "]" );
			System.out.println(System.currentTimeMillis() + ": SAME" + "["+ ask.getType()+ ", #" + ask.getId() + ", " + ask.getTo()+ ", " + ask.getTime() + "]" );
		}else{
			this.fw.tofile(System.currentTimeMillis() + ": SAME" + "["+ ask.getType() + ", " +ask.getTo() + ", " + ask.gettheWay() + ", " + ask.getTime() + "]");
			System.out.println(System.currentTimeMillis() + ": SAME" + "["+ ask.getType() + ", " +ask.getTo() + ", " + ask.gettheWay() + ", " + ask.getTime() + "]");
		}	
	}
	
	public String toString(){
		return this.lift[0].gettheState() + ":" ;
	}
}
