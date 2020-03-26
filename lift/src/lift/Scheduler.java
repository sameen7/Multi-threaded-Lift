package lift;

public class Scheduler extends Thread{
	protected Queue queue;
	protected Queue doqueue;
	
	public Scheduler(Queue queue){
		this.queue = queue;
		//this.doqueue = new Queue();
	}
	
	public void command(Eleavtor lift){
		if(this.queue.isEmpty()){
			System.exit(0);
		}else{
			Request ask = this.queue.getAsk();
			if(!pass(ask)){
				lift.run(ask);
				this.doqueue.add(ask);
			}
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
}
