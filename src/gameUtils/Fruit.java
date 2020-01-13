package gameUtils;

import com.google.gson.Gson;
import utils.Point3D;

public class Fruit {

	public Point3D location;
	public String image;


	public Fruit(String json) {
		json = json.substring(9, json.length()-1);
		FruitTemp temp = new FruitTemp(json);
		this.location = new Point3D(temp.pos);
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
		
		public FruitTemp(String json) {
			Gson gson = new Gson();
			FruitTemp temp = gson.fromJson(json,FruitTemp.class);
			this.pos = temp.pos;
			this.type = temp.type;
		}
	}



	public Point3D getLocation() {
		return location;
	}

	public String getImage() {
		return image;
	}
}
