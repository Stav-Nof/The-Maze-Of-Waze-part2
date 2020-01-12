package gameClient;

import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import gui.Window;

public class MyGameGUI {
	public static void main(String[] args) {
		game_service game  = Game_Server.getServer((int)(Math.random()*24));
		String g = game.getGraph();
		System.out.println(g);
		DGraph gg = new DGraph();
		gg.init(g);
		Window test = new Window(gg);
	}

}
