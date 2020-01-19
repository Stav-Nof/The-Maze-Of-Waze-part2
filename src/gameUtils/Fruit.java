package gameUtils;

import com.google.gson.Gson;
import utils.Point3D;

public class Fruit {

	public Point3D location;
	public String image;
	public double value;
	public int type;


	public Fruit(String json) {
		json = json.substring(9, json.length()-1);
		FruitTemp temp = new FruitTemp(json);
		this.location = new Point3D(temp.pos);
		this.value = temp.value;
		this.type = temp.type;
		if (temp.type == -1) {
			this.image = "Images/down.png";
		}
		else {
			this.image = "Images/up.png";
		}
	}
	
	private class FruitTemp {
		String pos;
		int type;
		double value;
		
		public FruitTemp(String json) {
			Gson gson = new Gson();
			FruitTemp temp = gson.fromJson(json,FruitTemp.class);
			this.pos = temp.pos;
			this.type = temp.type;
			this.value = temp.value;
		}
	}



	public Point3D getLocation() {
		return location;
	}

	public String getImage() {
		return image;
	}
}
