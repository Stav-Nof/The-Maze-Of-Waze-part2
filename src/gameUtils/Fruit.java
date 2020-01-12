package gameUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import Server.Game_Server;
import Server.game_service;
import utils.Point3D;

public class Fruit {

	private double value;
	private int type;
	private String pos;
	private String image;
	private int edge;
	private int points;


	public Fruit (double Value, int Type, String Pos) {

		this.value= Value;
		this.type= Type;
		this.setPos(Pos);
	}

	public static Fruit init(String str) 
	{	
		Gson gson = new Gson();
		try
		{
			Fruit f=gson.fromJson(str, Fruit.class);
			return f;
		} 
		catch ( JsonSyntaxException  ex)
		{
			throw new RuntimeException("RuntimeException");
		}
	}

	public int getEdge() {
		return edge;
	}

	public void setEdge(int edge) {
		this.edge = edge;
	}

	public double getValue() {
		return value;
	}

	public int getType() {
		return type;
	}


	public void setPos(String pos) {
	this.pos = pos;
	}

	
	public Point3D getPos() {
		Point3D p =new Point3D(pos);
		return p;
	}



	public static void main(String[] args)	{

		game_service game  = Game_Server.getServer((int)(Math.random()*24));

		Fruit f= new Fruit(5.0,-1,"35.20273974670703,32.10439601193746,0.0");
		
		System.out.println(f.getValue());
		System.out.println(f.getType());
		System.out.println(f.getPos());
		
		System.out.println(game);

	}

	//{"value":5.0,"type":-1,"pos":"35.197656770719604,32.10191878639921,0.0"}
	// סטרינג של פוס להפוף ל3D
	//game_service game  = Game_Server.getServer((int)(Math.random()*24));

}
