package dataStructure;

import java.io.Serializable;

public class EdgeData implements edge_data, Serializable {
	private double weight;
	private int src;
	private int dest;
	private String info;
	private int tag;
	
/*
 * Default constructor of Edge class.
 */
//	public Edge() {
//		this.src = null;
//		this.dest = null;
//		this.weight = 0;
//		this.info = "";
//		this.tag = 0;
//	}
	
/*
 * Constructor of an edge, receives a source node, a destination node and a weight.
 */	
	public EdgeData(node_data src, node_data dest,double weight) {
		this.src = src.getKey();
		this.dest = dest.getKey();
		this.weight = weight;
		
	}
	
/*
 * gets the id of the source node of this edge by its key
 */
	
	@Override
	public int getSrc() {
		return this.src;
	}

/*
 * Gets the id of the destination Node of this edge by its key.
 */	
	@Override
	public int getDest() {
		return this.dest;
	}

/*
 * 	Gets the weight of this edge.
 */	
	@Override
	public double getWeight() {
		return this.weight;
	}

/*
 * returns the info (string) associated to this edge.	
 */
	
	@Override
	public String getInfo() {
		return this.info;
	}

/*
 * sets the info of this edge.
 */	
	@Override
	public void setInfo(String s) {
		this.info = s;
	}

/*
 * gets the tag of this edge.
 */	
	@Override
	public int getTag() {
		return this.tag;
	}


/*
 * sets the tag of this edge.
 */	
	@Override
	public void setTag(int t) {
		this.tag = t;
	}
	

}
