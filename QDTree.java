package project;

import java.util.LinkedList;
import java.util.Scanner;

class Node{
	// data, if the node is a leaf node
	double lat; 
	double lng; 
	NodeType nt = NodeType.EMPTY;
	double x; 
	double y; 
	double w; // Width of node
	double h; // Height of node
	// four child
	Node nw;
	Node ne;
	Node sw;
	Node se;	
	
	Node(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
	
	public String toString(){
		return "This square is located at ("+this.x+", "+this.y+"). "+
	"It has length: "+ this.w+", height: "+ this.h+".";
	}
}

public class QDTree{
	Node root;
	
	QDTree(double minX, double minY, double maxX, double maxY) {
        this.root = new Node(minX, minY, maxX - minX, maxY - minY);
    }
	
	boolean insert(Node parent, double x, double y) {
		switch (parent.nt) {
			case EMPTY:
				parent.nt = NodeType.LEAF;
				parent.lat = x;
				parent.lng = y;
				return true;
			case LEAF:
				if (parent.lat == x && parent.lng == y) {
					System.out.println("Point already exist!");
					return false;
				}else {
					this.split(parent);
					return this.insert(parent, x, y);
				}
			case POINTER:
				return this.insert(this.getSubSquare(parent, x, y), x, y);
		}
		return false;
	}
	
	private void split(Node node) {
		node.nt = NodeType.POINTER;
		double new_x = node.x;
		double new_y = node.y;
		double new_w = node.w/2;
		double new_h = node.h/2;
		node.nw = new Node(new_x, new_y, new_w, new_h);
		node.ne = new Node(new_x+new_w, new_y, new_w, new_h);
		node.sw = new Node(new_x, new_y+new_h, new_w, new_h);
		node.se = new Node(new_x+new_w, new_y+new_h, new_w, new_h);
		this.insert(node, new_x, new_y);
	}
	
	Node find(Node node, double x, double y) {
        Node result = null;
        switch (node.nt) {
            case EMPTY:
                break;

            case LEAF:
                result = (node.lat == x && node.lng == y ? node : null);
                break;

            case POINTER:
                result = this.find(this.getSubSquare(node, x, y), x, y);
                break;
        }
        return result;
    }
	
	private Node getSubSquare(Node parent, double x, double y) {
        double mx = parent.x + parent.w / 2;
        double my = parent.y + parent.h / 2;
        if (x < mx) {
            return y < my ? parent.nw : parent.sw;
        } else {
            return y < my ? parent.ne : parent.se;
        }
    }
	public LinkedList<Double> rangeSearch(double xMin, double yMin, double xMax, double yMax) {
		LinkedList<Double> arr = new LinkedList<>();
		Func fun = new Func() {
			public void call(QDTree tree, Node node) {
                if (node.lat >= xMin && node.lat < xMax && node.lng >= yMin && node.lng < yMax) {
                    arr.add(node.lat);
                    arr.add(node.lng);
                }
			}
		};
		this.navigate(this.root, fun, xMin, yMin, xMax, yMax);
		return arr;
	}
	private void navigate(Node node, Func fun, double xMin, double yMin, double xMax, double yMax) {
		switch(node.nt) {
			case LEAF:
				fun.call(this, node);
				break;
			case POINTER:
				if (this.intersects(xMin, yMax, xMax, yMin, node.ne))
	                this.navigate(node.ne, fun, xMin, yMin, xMax, yMax);
				if (this.intersects(xMin, yMax, xMax, yMin, node.se))
	                this.navigate(node.se, fun, xMin, yMin, xMax, yMax);
				if (this.intersects(xMin, yMax, xMax, yMin, node.sw))
	                this.navigate(node.sw, fun, xMin, yMin, xMax, yMax);
				if (this.intersects(xMin, yMax, xMax, yMin, node.nw))
	                this.navigate(node.nw, fun, xMin, yMin, xMax, yMax);
	            break;
			default:
				break;
		}
	}
	
	private boolean intersects(double xMin, double yMin, double xMax, double yMax, Node node) {
		return !(node.x > xMax || 
				(node.x+node.w) < xMin || 
				node.y > yMin || 
				(node.y+node.h) < yMax);
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		QDTree tree = new QDTree(19, -162, 65, -68);
		int size = 36650;
		while(size>0) {
			String tmp = in.nextLine();
			String[] tmp1 = tmp.split(",");
			double la = Double.valueOf(tmp1[0]);
			double lo = Double.valueOf(tmp1[1]);
			if (tree.insert(tree.root, la, lo) == false) {
				System.out.println("This point can't be inserted: (" +la+", "+lo+").");
			}
			size--;
		}
		in.close();
		// check find
		Node a = tree.find(tree.root, 48.0028, -122.0964);
		Node b = tree.find(tree.root, 52, -100);
		if (a != null) {
			System.out.println();
			System.out.println("The point (48.0028, -122.0964) has been found!");
			System.out.println(a.toString());
		}else {
			System.out.println();
			System.out.println("The point (48.0028, -122.0964) didn't existed.");
		}
		if (b != null) {
			System.out.println();
			System.out.println("The point (52, -100) has been found!");
			System.out.println(b.toString());
		}else {
			System.out.println();
			System.out.println("The point (52, -100) didn't existed.");
			//check range search
		LinkedList<Double> arr;
		arr = tree.rangeSearch(40, -100, 40.5, -99.5);
		int s = arr.size()/2;
		System.out.println();
		System.out.println("There are " + s + " citizes within range(40, -100, 40.5, -99.5):");
		double m,n;
		while(s>0) {
			m = arr.remove();
			n = arr.remove();
			System.out.println("("+m+", "+n+")");
			s--;
		}
		}
	}
}
