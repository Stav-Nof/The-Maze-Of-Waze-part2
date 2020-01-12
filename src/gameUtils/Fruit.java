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

	public void init(String json) {	
		Gson gson = new Gson();
		Fruit temp = gson.fromJson(json, Fruit.class);
		this.value = temp.value;
		this.type = temp.type;
		this.pos = temp.pos;
		this.edge = temp.edge;
		this.points = temp.points;

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


}
