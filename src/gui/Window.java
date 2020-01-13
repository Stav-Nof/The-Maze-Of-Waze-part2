package gui;

import java.awt.Color;
import java.util.Collection;
import java.util.List;

import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.node_data;
import gameUtils.Fruit;
import gameUtils.robot;
import utils.Point3D;
import utils.StdDraw;


public class Window {
	int robots;
	DGraph g;


	public Window() {
		game_service game = Game_Server.getServer((int)(Math.random()*24));
		String graph = game.getGraph();
		DGraph temp = new DGraph();
		temp.init(graph);
		this.g = temp;
		openWindow();
		game.addRobot(0);
		drawFruit(game.getFruits());
		drawrobots(game.getRobots());
		StdDraw.show();


	}

	private void openWindow() {
		StdDraw.setCanvasSize(1000,500);
		drawGraph();
		StdDraw.enableDoubleBuffering();
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


	private void drawFruit(List<String> Fruit) {
		for (String i : Fruit) {
			Fruit temp = new Fruit(i);
			StdDraw.picture(temp.getLocation().x(), temp.getLocation().y(), temp.getImage(), 0.0008, 0.0008);
		}
	}
	
	
	private void drawrobots(List<String> robots) {
		for (String i : robots) {
			robot temp = new robot(i);
			StdDraw.picture(temp.getLocation().x(), temp.getLocation().y(), temp.getImage(), 0.0008, 0.0008);
		}
	}
}
