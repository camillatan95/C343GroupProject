Group project Problem 1 by Emma Cai and Camilla Tan.

This project is an implementation of kd-tree and point quadtree.
The dataset we used is a subset of American cities which only include latitude and longitude.

1. Q: Which one is easier to implement?
   A: kd-tree is easier to implement. For kd-tree, we only need to check the level to proceed, while for quadtree,
      we need to check if the node is empty, a leaf node or a pointer, and based on the options, we will store node, split 
      into more nodes, or re-insert previous nodes. Thus, quadtree has more data type involving, it's more complex to implement. 
   
2. Q: Which appears to be more space efficient?
   A: kd-tree is more space efficient. Although they are both theta(n).
   	  In kd-tree, each nodes has the data and two pointers. 
      In quadtree, less than half of the nodes has the data and each nodes has 
      4 pointers and more info like width, length.
 
3. Q: Which appears to be more time efficient.
   A:                kd-tree         quadtree
   		insert       O(log(n))       O(log(n))
   		search       O(log(n))       O(log(n))
      However, quadtree is actually more time efficient since instead of 2 children 
      it has 4. The time complexity is more precise to log4(n). 
      
4. Q: How to use the program?
   A: For both kd-tree and quadtree, we insert all cities one by one,
   	  then we do an exact find, finally we did a range search.
   	  The range search in kd-tree is within a circle, while in quadtree is a rectangle.
   	   
  	  Result from KDTree.java:
				City (46.6436, -118.5566) has been found:
				true
				City (46.6436, -118.5566) has been found:
				false
				
				There are 10 citizes in the circle:
				(46.6436, -118.5566)
				(46.7539, -118.3104)
				(46.8221, -118.8064)
				(46.9706, -118.614)
				(46.7736, -118.8276)
				(46.6627, -118.8403)
				(46.5184, -118.1251)
				(46.5736, -119.0013)
				(46.2989, -118.3126)
				(46.4587, -119.0172)
				
	Result from QDTree.java:
				// There are points too near with each other.
				Point already exist!
				This point can't be inserted: (37.6654, -78.6946).
				Point already exist!
				This point can't be inserted: (28.689, -100.4676).
				Point already exist!
				This point can't be inserted: (27.2992, -97.7996).
				Point already exist!
				This point can't be inserted: (18.1775, -66.1582).
				Point already exist!
				This point can't be inserted: (18.4411, -66.3993).
				Point already exist!
				This point can't be inserted: (40.036, -105.3324).
				Point already exist!
				This point can't be inserted: (35.5857, -95.0901).
				Point already exist!
				This point can't be inserted: (36.4553, -90.1428).
				
				The point (48.0028, -122.0964) has been found!
				This square is located at (47.974609375, -122.16015625). 
				It has length: 0.044921875, height: 0.091796875.
				
				The point (52, -100) didn't existed.
				
				There are 9 citizes within range(40, -100, 40.5, -99.5):
				(40.3049, -99.898)
				(40.203125, -99.9453125)
				(40.3828125, -99.578125)
				(40.2645, -99.5462)
				(40.203125, -99.578125)
				(40.0234375, -99.578125)
				(40.1313, -99.9711)
				(40.1313, -99.5945)
				(40.0234375, -99.9453125)
