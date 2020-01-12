package gameUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;

import utils.Point3D;

public class robot {
	
	
	int id,src,dest;
	double value,speed;
	String image;
	Point3D pos;
	
	public robot(String s)  {
		
		Gson gson = new Gson();
		robot r = gson.fromJson(s,robot.class);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSrc() {
		return src;
	}

	public void setSrc(int src) {
		this.src = src;
	}

	public int getDest() {
		return dest;
	}

	public void setDest(int dest) {
		this.dest = dest;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Point3D getPos() {
		return pos;
	}

	public void setPos(Point3D pos) {
		this.pos = pos;
	}
	
	
	
	
	

}
