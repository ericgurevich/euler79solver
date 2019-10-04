/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package euler79;

import java.util.*;
import java.io.*;
import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.graph.event.GraphEvent.Edge;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import javax.swing.JFrame;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Eric Gurevich
 */
public class Euler79 {

    public static void reader(Graph g) throws FileNotFoundException {       //reads file and turns it into a graph
        Scanner scan = new Scanner(new File("p079_keylog.txt"));
        
        while(scan.hasNextLine()) {
            String line = scan.nextLine();
            
            List<Integer> intsplit = new ArrayList<>();
            
            for (int i = 0; i < line.length(); i++) {       //Gets int array of each line
                intsplit.add(Integer.parseInt(String.valueOf(line.charAt(i))));
            }
            
            for (int i = 0; i < intsplit.size() - 1; i++) {     //make a directed edge from i to i+1
                if (!g.containsVertex(intsplit.get(i))) {
                    g.addVertex(intsplit.get(i));
                }
                if (!g.containsVertex(intsplit.get(i+1))) {
                    g.addVertex(intsplit.get(i+1));
                }
                if (!g.isNeighbor(intsplit.get(i), intsplit.get(i+1))) {
                    g.addEdge((intsplit.get(i) + " " + intsplit.get(i+1)), intsplit.get(i), intsplit.get(i+1));
                }
            }
            
        }
    }

    public static void visual(Graph g) {

        // The Layout<V, E> is parameterized by the vertex and edge types
        Layout<Integer, String> layout = new FRLayout(g);
        layout.setSize(new Dimension(800, 800)); // sets the initial size of the space
        // The BasicVisualizationServer<V,E> is parameterized by the edge types
        BasicVisualizationServer<Integer, String> vv
                = new BasicVisualizationServer<Integer, String>(layout);
        vv.setPreferredSize(new Dimension(850, 850)); //Sets the viewing area size

        //adds labels
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());

        JFrame frame = new JFrame("Simple Graph View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);

    }
    
    public static void search(Graph g) {            //search graph for path from first number to last in passcode
        int end = -1;                       //finds ending vertex = one with no successors
        Set<Integer> vertices = new HashSet(g.getVertices());
        for(int i: vertices) {
            if (g.getSuccessorCount(i) == 0) {
                end = i;
                break;
            }
        }
        //iteratively finds predecessor of each node from node with most predeccors to least e.g. 7 has no predecessors so its 1st number 
        List<Integer> l = new LinkedList<>();       
        int current = end;
        l.add(0, current);
        
        while(g.getPredecessorCount(current) != 0) {
            int precount = Integer.MIN_VALUE;
            Set<Integer> pre = new HashSet<>(g.getPredecessors(current));
            
            for (int i: pre) {
                if (g.getPredecessorCount(i) > precount) {
                    precount = g.getPredecessorCount(i);
                    current = i;
                    
                }
            }
            l.add(0, current);
        }
        System.out.println(l.toString());       //outputs shortest possible passcode
        
    }
       
    public static void main(String[] args) throws Exception {
        Graph<Integer, String> mygraph = new DirectedSparseGraph<>();
        
        reader(mygraph);
        
        visual(mygraph);
        
        search(mygraph);
    }

}
