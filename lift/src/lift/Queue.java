package lift;

import java.util.ArrayList;

public class Queue implements Print{
	private ArrayList<Request> list;
	FileWrite fw;
	
	public Queue(FileWrite fw){
		this.list = new ArrayList<Request>();
		this.fw = fw;
	}
	
	public synchronized void add(Request ask){
		if(this.list.isEmpty()){
			this.list.add(ask);
		}else{
			if(ask.getTime() >  this.list.get(this.list.size() - 1).getTime()){
				this.list.add(ask);
			}else if(ask.getTime() == this.list.get(this.list.size() - 1).getTime()){
				if(ask.getTo() == this.list.get(this.list.size() - 1).getTo()){
					if(!ask.getType().equals(this.list.get(this.list.size() - 1).getType())){
						this.list.add(ask);
					}else if(!ask.getWay().equals(this.list.get(this.list.size() - 1).getWay())){
						this.list.add(ask);
					}else{
						print(0, null, 0, ask);
					}
				}else{
					this.list.add(ask);
				}
			}else{
				print(0, null, 0, ask);
			}
		}
	}
	
	public synchronized Request getAsk(){
		Request ask = this.list.get(0);
		this.list.remove(0);
		return ask;
	} 
	
	public synchronized boolean isEmpty(){
		return this.list.isEmpty();
	}
	
	public synchronized int size(){
		return this.list.size();
	}
	
	public synchronized Request getdoAsk(int i){
		return this.list.get(i);
	} 
	
	public synchronized void add(int i, Request ask){
		this.list.add(i, ask);
	}
	
	public synchronized void remove(int i){
		this.list.remove(i);
	}
	
	public synchronized void doAdd(Request ask){
		this.list.add(ask);
	}

	public synchronized void print(int pos, String state, double etime, Request ask) {
		int i =(int) (ask.getTime());
		if(ask.getType().equals("ER")){
			this.fw.tofile(System.currentTimeMillis() + ": SAME" + "["+ ask.getType()+ ", #" + ask.getId() + ", " + ask.getTo()+ ", " + ask.getTime() + "]");
			System.out.println(System.currentTimeMillis() + ": SAME" + "["+ ask.getType()+ ", #" + ask.getId() + ", " + ask.getTo()+ ", " + ask.getTime() + "]" );
		}else{
			this.fw.tofile(System.currentTimeMillis() + ": SAME" + "["+ ask.getType() + ", " +ask.getTo() + ", " + ask.gettheWay() + ", " + ask.getTime() + "]");
			System.out.println(System.currentTimeMillis() + ": SAME" + "["+ ask.getType() + ", " +ask.getTo() + ", " + ask.gettheWay() + ", " + ask.getTime() + "]");
		}	
	}
}
