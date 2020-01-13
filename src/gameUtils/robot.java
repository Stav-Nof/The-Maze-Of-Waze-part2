package gameUtils;

import com.google.gson.Gson;
import utils.Point3D;

public class robot {

	public Point3D location;
	public String image;


	public robot(String json) {
		json = json.substring(9, json.length()-1);
		RobotTemp temp = new RobotTemp(json);
		this.location = new Point3D(temp.pos);
		this.image = "Images/robot.png";
	}
	
	private class RobotTemp {
		String pos;
		int id;
		
		public RobotTemp(String json) {
			Gson gson = new Gson();
			RobotTemp temp = gson.fromJson(json,RobotTemp.class);
			this.pos = temp.pos;
			this.id = temp.id;
		}
	}



	public Point3D getLocation() {
		return location;
	}

	public String getImage() {
		return image;
	}
}
