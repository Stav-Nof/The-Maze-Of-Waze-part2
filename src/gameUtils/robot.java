package gameUtils;

import com.google.gson.Gson;
import utils.Point3D;

public class robot {



	public Point3D location;
	public String image;
	public int dest;
	public int id;
	public int src;

	public robot(String json) {
		json = json.substring(9, json.length()-1);
		RobotTemp temp = new RobotTemp(json);
		this.location = new Point3D(temp.pos);
		this.image = "Images/robot.png";
		this.dest = temp.dest;
		this.id = temp.id;
		this.src = temp.src;
	}
	
	private class RobotTemp {
		String pos;
		int id;
		int dest;
		int src;
		
		public RobotTemp(String json) {
			Gson gson = new Gson();
			RobotTemp temp = gson.fromJson(json,RobotTemp.class);
			this.pos = temp.pos;
			this.id = temp.id;
			this.dest = temp.dest;
			this.src = temp.src;
		}
	}



	public Point3D getLocation() {
		return location;
	}

	public String getImage() {
		return image;
	}
	public int getDest() {
		return dest;
	}

	public int getId() {
		return id;
	}
	public int getSrc() {
		return src;
	}
}
