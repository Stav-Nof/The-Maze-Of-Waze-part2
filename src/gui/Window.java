package gui;

import java.awt.Color;
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


public class Window implements Runnable {
	game_service game;
	DGraph g;
	int robots;
	GameServer GameServer;





	public Window(String action) {
		openWindow();
		this.game = Game_Server.getServer((int)(Math.random()*24));
		this.initGameServer(this.game.toString());
		String graph = this.game.getGraph();
		DGraph temp = new DGraph();
		temp.init(graph);
		this.g = temp;
		drawGraph();
		drawFruit();
		this.robots = this.GameServer.robots;		
		System.out.println(this.game.toString());
	}


	private void initGameServer(String s) {
		Gson gson = new Gson();
		Window temp = gson.fromJson(s, Window.class);
		this.GameServer = temp.GameServer;
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
		StdDraw.setPenColor(Color.BLUE);
		for (node_data i : nodes) {
			Point3D Location = i.getLocation();
			StdDraw.filledCircle(Location.x(), Location.y(), 0.0001);
			StdDraw.text(Location.x() + 0.0001, Location.y() + 0.0001, "" + i.getKey());
		}
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

	public void startGameManual() {
		this.game.startGame();
		StdDraw.enableDoubleBuffering();
		while(this.game.isRunning()) {
			List<String> robots = this.game.getRobots();
			for (String i : robots) {
				System.out.println(i);
				robot temp = new robot(i);
				if (temp.getDest() == -1) {
					final JFrame startGameManual = new JFrame();
					String robotDes = JOptionPane.showInputDialog(startGameManual,"enter a vertex key to send robot to\nrobot id: " + temp.getId() + ", robot current vertex: " + temp.getSrc(), null);
					this.game.chooseNextEdge(temp.getId(), Integer.parseInt(robotDes));
				}
			}
			this.game.move();
			StdDraw.clear();
			drawGraph();
			drawFruit();
			drawrobots();
			StdDraw.show();

		}

	}


	@Override
	public void run() {
		while(true) {
			StdDraw.enableDoubleBuffering();
			StdDraw.clear();
			drawGraph();
			drawFruit();
			drawrobots();
			StdDraw.show();
		}
	}
}
