package lift;

public class Request {
	private double time;
	private int getto;
	private double eTime;
	private String type;
	private String way;
	private String theway;
	private int id;
	
	public Request(double time, int getto, String type, String way, String theway, int id){
		this.getto = getto;
		this.time = time;
		this.way = way;
		this.type = type;
		this.theway = theway;
		this.id = id;
	}
	
	public double getTime(){
		return this.time;
	}
	
	public int getTo(){
		return this.getto;
	}
	
	public String getType(){
		return this.type;
	}
	
	public String getWay(){
		return this.way;
	}
	
	public String gettheWay(){
		return this.theway;
	}
	
	public void seteTime(double time){
		this.eTime = time;
	}
	
	public double geteTime(){
		return this.eTime;
	}
	
	public void setWay(String str){
		this.way = str;
	}
	
	public int getId(){
		return this.id;
	}
}
