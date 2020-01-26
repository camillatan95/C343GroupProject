package projects;

import java.util.LinkedList;
import java.util.Scanner;

class NodeK {
	private double lat;
	private double lng;
	private int level;
	private NodeK left;
	private NodeK right;
	
	NodeK (double la, double lo) {
		this.lat = la;
		this.lng = lo;
		left = null;
		right = null;
	}
	double getX(){
		return lat;
	}
	double getY(){
		return lng;
	}
	void setLevel(int a) {
		level = a; //set level of x or y, x = 1, y = 2
	}
	int getLev(){
		return level;
	} 
	NodeK getLeft() {
		return left;
	}
	NodeK getRight() {
		return right;
	}
	void setLeft(NodeK i) {
		left = i;
	}
	void setRight(NodeK i) {
		right = i;
	}
	void setData(double x, double y) {
		lat = x;
		lng = y;
	}
	public String toString() {
		return "("+this.lat+", "+this.lng+")";
	}
}

public class KDTree {
	public static NodeK insert(NodeK root, double x, double y) {   
		if (root == null) {
			NodeK newNode = new NodeK(x,y);
			newNode.setLevel(1);    //set root as level x (1)
			return newNode;
		} else {
			NodeK cur;
			if(root.getLev()==1) {
				if (x < root.getX()) {
					cur = insert(root.getLeft(), x,y);
					cur.setLevel(2); //since parent level is 1 (which is x), we set this child level as 2 (which is y)
					root.setLeft(cur);
				} else if (x >= root.getX()){
					cur = insert(root.getRight(), x,y);
					cur.setLevel(2);
					root.setRight(cur);
				}
			}
			else if(root.getLev()==2) {
				if (y < root.getY()) {
					cur = insert(root.getLeft(), x,y);
					cur.setLevel(1);
					root.setLeft(cur);
				} else if(y >= root.getY()) {
					cur = insert(root.getRight(), x,y);
					cur.setLevel(1);
					root.setRight(cur);
				}
			}
			return root;
		}
	}
	/*
	public static void insert2(Node root, double x, double y) {
		// Assume root is not null.
		if (root.getLev() == 1) { // x-tree
			if (x < root.getX()) {
				if (root.getLeft() != null) {
					insert2(root.getLeft(), x, y);
					root.getLeft().setLevel(2);
				} else {
					Node newNode = new Node(x, y);
					newNode.setLevel(2);
					root.setLeft(newNode);
				}
			} else {
				if (root.getRight != null) 
			}
		}
	}
	*/
	static LinkedList<NodeK> arr = new LinkedList<>();
	public static void find(NodeK root, double x, double y, int r) { 
		//rangeSearch e.g(60,40) r=5, 55<x<65, 35<y<45
		if (root == null) {
			return;
		} 
		if (root.getLev() == 1) {
			if (root.getX()<(x-r)) {
				find(root.getRight(), x, y, r);
				return;
			}else if(root.getX()>=(x+r)){
				find(root.getLeft(), x, y, r);
				return;
			}else {
				double dist = Math.sqrt(Math.pow(Math.abs(root.getX()-x),2) 
						+ Math.pow(Math.abs(root.getY()-y), 2));
				if(dist<=r) {
					arr.add(root);
				}
				find(root.getRight(), x,y,r);
				find(root.getLeft(), x,y,r);
				return;
			}
		}
		if (root.getLev() == 2) {
			if (root.getY()<(y-r)) {
				find(root.getRight(), x, y, r);
				return;
			}else if(root.getY()>=(y+r)){
				find(root.getLeft(), x, y, r);
				return;
			}else {
				double dist = Math.sqrt(Math.pow(Math.abs(root.getX()-x),2) 
						+ Math.pow(Math.abs(root.getY()-y), 2));
				if(dist<=r) {
					arr.add(root);
				}
				find(root.getRight(), x,y,r);
				find(root.getLeft(), x,y,r);
				return;
			}
		}
	}
	
	public static Boolean efind(NodeK root, double x, double y) {   //exactSearch
		if (root == null) {
			return false;
		} else {
			if(root.getLev()==1) {
				if (x < root.getX()) {
					return efind(root.getLeft(), x,y);
				}else if(x == root.getX() && y == root.getY()) {
					return true;
				}else if (x >= root.getX()){
					return efind(root.getRight(), x,y);
				} 
			}
			if(root.getLev()==2) {
				if (y < root.getY()) {
					return efind(root.getLeft(), x,y);
				} else if(x == root.getX()&& y==root.getY() ) {
					return true;
				}else if(y >= root.getY()) {
					return efind(root.getRight(), x,y);
				}
			}
		}
		return false;
	}
	
    public static void inOrder(NodeK root){ 
        if (root == null) {
            return;
        }
        inOrder(root.getLeft());
        System.out.print(root.getX() + " " + root.getY());
        inOrder(root.getRight());
    }
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		NodeK root = null;
		int size= 36650;      
		while(size>0) {             //in.nextline() will never break
			String tmp = in.nextLine();
			String[] tmp1 = tmp.split(",");
			double la = Double.valueOf(tmp1[0]);
			double lo = Double.valueOf(tmp1[1]);
			root = insert(root, la, lo); //insert one by one
			size--;
		}
		in.close();
		Boolean ex1 = efind(root, 46.6436, -118.5566); //return true
		System.out.println("City (46.6436, -118.5566) has been found:");
		System.out.println(ex1);
		Boolean ex2 = efind(root, 46, -118);//return false
		System.out.println("City (46.6436, -118.5566) has been found:");
		System.out.println(ex2);
		find(root, 46.6436,-118.5566,1);
		System.out.println();
		System.out.println("There are "+arr.size()+" citizes in the circle:");
		for(NodeK node: arr) {
			System.out.println(node);
		}
	}
}
