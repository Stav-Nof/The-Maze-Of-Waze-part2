package gameClient;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.google.gson.Gson;
import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.node_data;
import gameUtils.Fruit;
import gameUtils.robot;
import utils.Point3D;
import utils.StdDraw;

public class MyGameGUI implements Runnable, MouseListener {
	game_service game;
	DGraph g;
	int robots;
	GameServer GameServer;
	int level;


	public MyGameGUI() {
		this.level = -1;
		openWindow();

	}


	public synchronized int getLevel() {
		return this.level;
	}
	public synchronized void setLevel(int level) {
		this.level = level;
	}


	private void openWindow() {
		StdDraw.setCanvasSize(1000, 500);
	}


	private void drawGraph() {
		Collection<node_data> nodes = this.g.getV();
		double maxX = Double.NEGATIVE_INFINITY;
		double maxY = Double.NEGATIVE_INFINITY;
		double minX = Double.POSITIVE_INFINITY;
		double minY = Double.POSITIVE_INFINITY;
		for (node_data i : nodes) {
			Point3D temp = i.getLocation();
			double tempX = temp.x();
			double tempY = temp.y();
			if(tempX > maxX) maxX = tempX;
			if(tempX < minX) minX = tempX;
			if(tempY > maxY) maxY = tempY;
			if(tempY < minY) minY = tempY;
		}
		StdDraw.setXscale(minX-0.0005, maxX+0.0005);
		StdDraw.setYscale(minY-0.0005, maxY+0.0005);
		StdDraw.setPenColor(Color.BLACK);
		for (node_data i : nodes) {
			double x0 = i.getLocation().x();
			double y0 = i.getLocation().y();
			Collection<edge_data> edges = this.g.getE(i.getKey());
			for (edge_data j : edges) {
				double x1 = this.g.getNode(j.getDest()).getLocation().x();
				double y1 = this.g.getNode(j.getDest()).getLocation().y();
				StdDraw.line(x0, y0, x1, y1);
			}
		}
		StdDraw.setPenColor(Color.RED);
		for (node_data i : nodes) {
			StdDraw.setPenRadius();
			Point3D Location = i.getLocation();
			StdDraw.filledCircle(Location.x(), Location.y(), 0.0001);
			StdDraw.setFont(new Font("arial", Font.PLAIN, 20));
			StdDraw.text(Location.x() + 0.0002, Location.y() + 0.0002, "" + i.getKey());
		}
	}


	private void drawFruit() {
		List<String> Fruit = this.game.getFruits();
		for (String i : Fruit) {
			Fruit temp = new Fruit(i);
			StdDraw.picture(temp.getLocation().x(), temp.getLocation().y(), temp.getImage(), 0.0008, 0.0008);
		}
	}


	private void drawrobots() {
		List<String> robots = this.game.getRobots();
		for (String i : robots) {
			robot temp = new robot(i);
			StdDraw.picture(temp.getLocation().x(), temp.getLocation().y(), temp.getImage(), 0.0008, 0.0008);
		}
	}


	private void initGameServer(String s) {
		Gson gson = new Gson();
		MyGameGUI temp = gson.fromJson(s, MyGameGUI.class);
		this.GameServer = temp.GameServer;
	}


	private class GameServer {
		int robots;
	}

	public void manualGame() {
		int levelToPrint = levelSelect();
		this.GameServer = new GameServer();
		this.level = levelToPrint;
		this.game = Game_Server.getServer(levelToPrint - 1);
		this.initGameServer(game.toString());
		this.robots = this.GameServer.robots;
		game = Game_Server.getServer(this.level-1);
		this.g = new DGraph();
		this.g.init(this.game.getGraph());
		this.drawGraph();
		this.drawFruit();
		this.addManualRobots();
		this.startGameManual();
	}




	public boolean addManualRobots() {
		for (int i = 0; i < this.robots; i++) {
			final JFrame addManual = new JFrame();
			int robotI = 0;
			String robotS = JOptionPane.showInputDialog(addManual,"enter a vertex key to add robot to\nrobot id: " + i, null);
			if(robotS == null) {
				JOptionPane.showMessageDialog(null, "Action canceled");
				return false;
			}
			try {
				robotI = Integer.parseInt(robotS);
			}
			catch (NumberFormatException er) {
				JOptionPane.showMessageDialog(null, "you entered a char instead of a number!\ngame has bin cancel");
				return false;
			}
			if (this.g.getNode(robotI) == null) {
				JOptionPane.showMessageDialog(null, "the key you entered doesnt exist\ngame has bin cancel");
				return false;
			}
			this.game.addRobot(robotI);
			this.drawrobots();
		}
		return true;
	}


	private int  levelSelect() {
		String levelS = JOptionPane.showInputDialog("select a level Between 1 and 24\nAny other character for random", null);
		int level = -1;
		try {
			level = Integer.parseInt(levelS);
		}catch (Exception e1) {
			level = (int) (Math.random()*25);
		}
		if (level < 1 || level > 24) {
			level = (int) (Math.random()*25);
		}
		return level;
	}


	public void NodePressed() {
		//if(StdDraw.isMousePressed()) {
			double x = StdDraw.mouseX();
			double y = StdDraw.mouseY();
			double epsilon = 0.001;
			for (node_data i : this.g.getV()) {
				if(i.getLocation().x() + epsilon > x && i.getLocation().x() - epsilon < x) {
					if(i.getLocation().y() + epsilon > y && i.getLocation().y() - epsilon < y) {
						System.out.println("yarin");
					}

				}
			}
		}

	//}

	public void startGameManual() {
		this.game.startGame();
		Thread gameRun = new Thread(this);
		gameRun.start();
		while(this.game.isRunning()) {
			List<String> robots = this.game.getRobots();
			for (String i : robots) {
				robot temp = new robot(i);
				if (temp.getDest() == -1) {

					//this.game.chooseNextEdge(temp.getId(), Integer.parseInt(robotDes));
				}
			}


		}

	}


	@Override
	public void run() {
		StdDraw.enableDoubleBuffering();
		while (this.game.isRunning()) {
			this.game.move();
			StdDraw.clear();
			drawGraph();
			drawFruit();
			drawrobots();
			StdDraw.show();
			double x = StdDraw.mouseX();
			double y = StdDraw.mouseY();
			System.out.println(x + ", " + y);
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {


		if (this.game.isRunning()) {
			List<String> robots = this.game.getRobots();
			for (String i : robots) {
				robot temp = new robot(i);
				if (temp.getDest() == -1) {
					System.out.println(StdDraw.userX(e.getX()));
					System.out.println(StdDraw.userY(e.getY()));
					//this.game.chooseNextEdge(temp.getId(), Integer.parseInt(robotDes));
				}
			}
		}

	}


	@Override
	public void mousePressed(MouseEvent e) {

	}



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}