package gameClient;

import java.awt.Color;
import java.awt.Font;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.google.gson.Gson;
import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.EdgeData;
import dataStructure.edge_data;
import dataStructure.node_data;
import gameUtils.Fruit;
import gameUtils.robot;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_graph;
import utils.Point3D;
import utils.StdDraw;

public class MyGameGUI implements Runnable {
	game_service game;
	DGraph g;
	int robots;
	GameServer GameServer;
	int level;
	int thread;
	Graph_Algo ga;
	public String type;

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
			System.out.println(i);
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


	public void drawTime() {
		long time = this.game.timeToEnd();
		StdDraw.setPenColor(Color.ORANGE);
		if(this.game.isRunning())
			StdDraw.textRight(StdDraw.xmax-0.0005, StdDraw.ymax-0.0005, "" + time/1000);
		else StdDraw.textRight(StdDraw.xmax-0.0005, StdDraw.ymax-0.0005, "game over");
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
		this.ga = new Graph_Algo(this.g);
		this.drawGraph();
		this.drawFruit();
		this.addManualRobots();
		this.game.startGame();
		StdDraw.enableDoubleBuffering();
		while (this.game.isRunning()) {
			startGameManual();
			runGame();
		}
		StdDraw.disableDoubleBuffering();
		StdDraw.clear();
		end();
	}


	public void automaticGame() {
		int levelToPrint = levelSelect();
		this.GameServer = new GameServer();
		this.level = levelToPrint;
		this.game = Game_Server.getServer(levelToPrint - 1);
		this.initGameServer(game.toString());
		this.robots = this.GameServer.robots;
		game = Game_Server.getServer(this.level-1);
		this.g = new DGraph();
		this.g.init(this.game.getGraph());
		this.ga = new Graph_Algo(this.g);
		this.drawGraph();
		this.drawFruit();
		this.addAutomaticlRobots();
		this.drawrobots();
		this.game.startGame();
		StdDraw.enableDoubleBuffering();
		while (this.game.isRunning()) {
			startGameautomatic();
			runGame();
		}
		StdDraw.disableDoubleBuffering();
		StdDraw.clear();
		end();
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


	public void startGameManual() {
		List<String> robots = this.game.move();
		for (String i : robots) {
			robot temp = new robot(i);
			if (temp.getDest() == -1) {
				String robotDes = JOptionPane.showInputDialog(new JFrame(),"select a node to go to\nrobot id: " + temp.id + " corent node: " + temp.src, null);
				int des = -1;
				try {
					des = Integer.parseInt(robotDes);
				}catch (Exception e) {
				}
				this.game.chooseNextEdge(temp.getId(), des);
			}
		}
	}


	public void startGameautomatic() {
		List<String> robots = this.game.move();
		for (String i : robots) {
			robot temp = new robot(i);
			if (temp.getDest() == -1) {
				int t = nextNode(temp);
				this.game.chooseNextEdge(temp.getId(), nextNode(temp));
			}
		}
	}


	private int nextNode(robot robot) {
		int dest = -1;
		double epsilon = 0.0;
		List<String> fruitsS = this.game.getFruits();
		LinkedList<Fruit> fruits = new LinkedList<Fruit>();
		for (String string : fruitsS) {
			fruits.add(new Fruit(string));
		}
		Collection<node_data> Nodes = this.g.getV();
		double distance = Double.POSITIVE_INFINITY;
		edge_data fruitIn = null;
		for (Fruit f : fruits) {
			for (node_data i : Nodes) {
				for (node_data j : Nodes) {
					if (i.getKey() == j.getKey())continue;
					if ((i.getLocation().x() - epsilon < f.getLocation().x() && j.getLocation().x() + epsilon > f.getLocation().x()) ||
							(i.getLocation().x() + epsilon > f.getLocation().x() && j.getLocation().x() - epsilon < f.getLocation().x())) {
						if ((i.getLocation().y() - epsilon < f.getLocation().y() && j.getLocation().y() + epsilon > f.getLocation().y()) ||
								(i.getLocation().y() + epsilon > f.getLocation().y() && j.getLocation().y() - epsilon < f.getLocation().y())) {
							fruitIn = this.g.getEdge(i.getKey(), j.getKey());
							break;
						}
					}
				}
				if (fruitIn != null) break;
			}
			if (fruitIn != null) {
				if (f.type == -1) {
					double srcY = this.g.getNode(fruitIn.getSrc()).getLocation().y();
					double destY = this.g.getNode(fruitIn.getDest()).getLocation().y();
					if (srcY > destY) {
						double tempDistance = this.ga.shortestPathDist(robot.src, fruitIn.getSrc());
						if (tempDistance < distance) {
							distance = tempDistance;
							dest = fruitIn.getSrc();
							if (dest == robot.src) {
								dest = fruitIn.getDest();
							}
						}
					}
					else {
						double tempDistance = this.ga.shortestPathDist(robot.src, fruitIn.getDest());
						if (tempDistance < distance) {
							distance = tempDistance;
							dest = fruitIn.getDest();
							if (dest == robot.src) {
								dest = fruitIn.getSrc();
							}
						}
					}
				}
				else {
					double srcY = this.g.getNode(fruitIn.getSrc()).getLocation().y();
					double destY = this.g.getNode(fruitIn.getDest()).getLocation().y();
					if (srcY < destY) {
						double tempDistance = this.ga.shortestPathDist(robot.src, fruitIn.getSrc());
						if (tempDistance < distance) {
							distance = tempDistance;
							dest = fruitIn.getSrc();
							if (dest == robot.src) {
								dest = fruitIn.getDest();
							}
						}
					}
					else {
						double tempDistance = this.ga.shortestPathDist(robot.src, fruitIn.getDest());
						if (tempDistance < distance) {
							distance = tempDistance;
							dest = fruitIn.getDest();
							if (dest == robot.src) {
								dest = fruitIn.getSrc();
							}
						}
					}
				}
			}
		}
		if (dest == -1)return dest;
		List<node_data> path =  this.ga.shortestPath(robot.src, dest);
		return path.get(1).getKey();
	}



	public void runGame() {
		this.game.move();
		StdDraw.clear();
		drawGraph();
		drawFruit();
		drawrobots();
		drawTime();
		StdDraw.show();
	}


	public void end() {
		int points = 0;
		for (String i : this.game.getRobots()) {
			robot temp = new robot(i);
			points = points + temp.getPoints();
		}
		StdDraw.setPenColor(Color.BLUE);
		StdDraw.textRight(((StdDraw.xmax + StdDraw.xmin) / 2), ((StdDraw.ymax + StdDraw.ymin) / 2) + 0.001, "game end");
		StdDraw.textRight(((StdDraw.xmax + StdDraw.xmin) / 2), ((StdDraw.ymax + StdDraw.ymin) / 2), "your points: " + points);
	}


	public void addAutomaticlRobots() {		
		double epsilon = 0.0000000001;			//TODO
		List<String> fruitsString = this.game.getFruits();
		LinkedList<Fruit> fruits = new LinkedList<Fruit>();
		for (String string : fruitsString) {
			fruits.add(new Fruit(string));
		}
		int x = 0;
		for (; x < this.robots; x++) {
			Fruit max = null;
			for (Fruit fruit : fruits) {
				if (max == null)max = fruit;
				else if(max.value < fruit.value);
				max = fruit;
			}
			Collection<node_data> Nodes = this.g.getV();
			edge_data fruitIn = null;
			for (node_data i : Nodes) {
				for (node_data j : Nodes) {
					if (i.getKey() == j.getKey())continue;
					if ((i.getLocation().x() - epsilon < max.getLocation().x() && j.getLocation().x() + epsilon > max.getLocation().x()) ||
							(i.getLocation().x() + epsilon > max.getLocation().x() && j.getLocation().x() - epsilon < max.getLocation().x())) {
						if ((i.getLocation().y() - epsilon < max.getLocation().y() && j.getLocation().y() + epsilon > max.getLocation().y()) ||
								(i.getLocation().y() + epsilon > max.getLocation().y() && j.getLocation().y() - epsilon < max.getLocation().y())) {
							fruitIn = this.g.getEdge(i.getKey(), j.getKey());
							if (fruitIn != null)break;
						}
					}
				}
				if (fruitIn != null)break;
			}
			double srcY = this.g.getNode(fruitIn.getSrc()).getLocation().y();
			double destY = this.g.getNode(fruitIn.getDest()).getLocation().y();
			if (max.type == -1) {
				if (srcY > destY) this.game.addRobot(fruitIn.getSrc());
				if (srcY < destY) this.game.addRobot(fruitIn.getDest());
			}
			else {
				if (srcY < destY) this.game.addRobot(fruitIn.getSrc());
				if (srcY > destY) this.game.addRobot(fruitIn.getDest());
			}
			fruits.remove(max);
		}
	}


	@Override
	public void run() {
		if (this.type.equals("manual")) manualGame();
		if (this.type.equals("automatic")) automaticGame();

	}
}