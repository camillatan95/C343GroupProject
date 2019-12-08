package group;

import java.util.Scanner;

class Node1 {
	private double lat;
	private double lng;
	private Node1 c1;
	private Node1 c2;
	private Node1 c3;
	private Node1 c4;
	//four children
	Node1 (double la, double lo) {
		this.lat = la;
		this.lng = lo;
		c1 = null;
		c2 = null;
		c3 = null;
		c4 = null;
	}
	double getX(){
		return lat;
	}
	double getY(){
		return lng;
	}
	Node1 get1() {
		return c1;
	}
	Node1 get2() {
		return c2;
	}
	Node1 get3() {
		return c3;
	}
	Node1 get4() {
		return c4;
	}
	void set1(Node1 i) {
		c1 = i;
	}
	void set2(Node1 i) {
		c2 = i;
	}
	void set3(Node1 i) {
		c3 = i;
	}
	void set4(Node1 i) {
		c4 = i;
	}
	void setData(double x, double y) {
		lat = x;
		lng = y;
	}
}

public class PRTree {
	public static Node1 insert(Node1 root, double x, double y) {
		if(root == null) {
			return new Node1(x,y);
		}else {
			Node1 cur;
			if(x<root.getX() && y>root.getY()) {
				cur = insert(root.get1(), x, y);
				root.set1(cur);  //No.1 child
			}else if(x>root.getX() && y>root.getY()) {
				cur = insert(root.get2(), x, y);
				root.set2(cur);  //No.2 child
			}else if(x<root.getX() && y<root.getY()) {
				cur = insert(root.get3(), x, y);
				root.set3(cur);  //No.3 child
			}else if(x>root.getX() && y<root.getY()) {
				cur = insert(root.get4(), x, y);
				root.set4(cur);  //No.4 child
			}
			return root;
		}
		
	}
	
	public static Boolean find(Node1 root, double x, double y) {  //exact find
		if(root == null) {
			return false;
		}else {
			if(x<root.getX() && y>root.getY()) {
				return find(root.get1(), x, y);
			}else if(x>root.getX() && y>root.getY()) {
				return find(root.get2(), x, y);
			}else if(x<root.getX() && y<root.getY()) {
				return find(root.get3(), x, y);
			}else if(x>root.getX() && y<root.getY()) {
				return find(root.get4(), x, y);
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Node1 root = null;
		int size = 36650;
		while(size>0) {
			String tmp = in.nextLine();
			String[] tmp1 = tmp.split(",");
			//System.out.print(Arrays.toString(tmp1));
			double la = Double.valueOf(tmp1[0]);
			double lo = Double.valueOf(tmp1[1]);
			root = insert(root, la, lo); //insert one by one
			size--;
		}
		in.close();
		find(root,47.1487,-122.5512);
	}

}
