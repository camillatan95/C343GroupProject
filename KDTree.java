package group;

import java.util.Arrays;
import java.util.Scanner;

class Node {
	private double lat;
	private double lng;
	private int level;
	private Node left;
	private Node right;
	
	Node (double la, double lo) {
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
	Node getLeft() {
		return left;
	}
	Node getRight() {
		return right;
	}
	void setLeft(Node i) {
		left = i;
	}
	void setRight(Node i) {
		right = i;
	}
	void setData(double x, double y) {
		lat = x;
		lng = y;
	}
}

public class KDTree {
	public static Node insert(Node root, double x, double y) {   
		if (root == null) {
			Node newNode = new Node(x,y);
			newNode.setLevel(1);    //set root as level x (1)
			return newNode;
		} else {
			Node cur;
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
	
	public static Node find(Node root, double x, double y, int r) {   //rangeSearch e.g(60,40) r=5, 55<x<65, 35<y<45
		if (root == null) {
			return null;
		} else if (x<=root.getX()+r && x>root.getX()-r && y<=root.getY()+r && y>root.getY()-r){
			//now we check if the node is in the radius
			double dist = Math.sqrt(Math.pow(Math.abs(root.getX()-x),2) + Math.pow(Math.abs(root.getY()-y), 2));
			if(dist<=r) return root;
		} else {
			if(root.getLev()==1) {
				if (root.getX()< x-r) {
					return null;
				} else if (x+r < root.getX()){
					return find(root.getLeft(), x,y,r);
				}else if(x+r >= root.getX()) {
					if (x<=root.getX()+r && x>root.getX()-r && y<=root.getY()+r && y>root.getY()-r){
						//check if the node is in the radius
						double dist = Math.sqrt(Math.pow(Math.abs(root.getX()-x),2) + Math.pow(Math.abs(root.getY()-y), 2));
						if(dist<=r) return root;
					}
					find(root.getRight(), x,y,r);
					find(root.getLeft(), x,y,r);
				}
			}
			if(root.getLev()==2) {
				if(root.getY() < y-r) {   //current node y smaller than minimum range, break
					return null;
				}else if (y+r < root.getY()) {   //current node y larger than max range, check left
					find(root.getLeft(), x,y,r);
				} else if(y+r >= root.getY()) {   //current node y same as max range, check both left and right
					if (x<=root.getX()+r && x>root.getX()-r && y<=root.getY()+r && y>root.getY()-r){
						//check if the node is in the radius
						double dist = Math.sqrt(Math.pow(Math.abs(root.getX()-x),2) + Math.pow(Math.abs(root.getY()-y), 2));
						if(dist<=r) return root;
					}
					find(root.getRight(), x,y,r);
					find(root.getLeft(), x,y,r);
				}
			}
		}
		return null;	
	}
	
	public static Boolean efind(Node root, double x, double y) {   //exactSearch
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
	
    public static void inOrder(Node root){ 
        if (root == null) {
            return;
        }
        inOrder(root.getLeft());
        System.out.print(root.getX() + " " + root.getY());
        inOrder(root.getRight());
    }
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Node root = null;
		int size= 36650;      
		while(size>0) {             //不知道为什么如果用in.nextline()的话永远都不会break。。。
			String tmp = in.nextLine();
			String[] tmp1 = tmp.split(",");
			double la = Double.valueOf(tmp1[0]);
			double lo = Double.valueOf(tmp1[1]);
			root = insert(root, la, lo); //insert one by one
			size--;
		}
		in.close();
		Boolean res = efind(root, 46.6436,-118.5566);  //return true
		System.out.println(res);
		Node[] range = find(root, 46.6436,-118.5566,4);
		//System.out.println(root.getLeft().getX());
		//inOrder(root);
	}
}
