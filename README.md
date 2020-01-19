# The-Maze-Of-Waze-part2

This project represents the creation of graph and other operation that can be done on it, such as add/remove vertex, check if it is a "connected graph", Finding the shortest path between two vertices and more.

Node- A class represents a vertex in the graph defined by an id and has set of operations applicable on a vertex.

Edge- A class represents an edge in the graph defined by Source and destination vertices and has set of operations applicable on a directional edge(src,dest) in a (directional) weighted graph.

Dgraph- A class represents a directional weighted graph. It has two data structure from type of hash map that represents the vertices and the edges. This class has all the operations to create and change a graph.

Graph_Algo- A class represents the "regular" Graph Theory algorithms that can be done on a variable graph.

Window- A class represents the graphical user interface. Writing this class We used These links: https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html, https://stackoverflow.com/questions/16995308/can-you-increase-line-thickness-when-using-java-graphics-for-an-applet-i-dont, https://stackoverflow.com/questions/8852560/how-to-make-popup-window-in-java, https://www.javatpoint.com/java-jcheckbox

DGraphTest & Graph_AlgoTest-Those are JUnit testers that test minor and difficult cases of the four classes methods.
