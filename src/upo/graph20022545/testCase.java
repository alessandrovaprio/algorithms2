package upo.graph20022545;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import upo.graph.base.VisitForest;

public class testCase {

    @Test
    public void testAddVertex() {
        AdjListDir adjListDir = new AdjListDir();
        assertEquals(1,adjListDir.addVertex());
        assertEquals(2,adjListDir.addVertex());
        
    }
    
    @Test
    public void testContainsVertex() {
        AdjListDir adjListDir = new AdjListDir();
        adjListDir.addVertex();
        adjListDir.addVertex();
        assertEquals(true,adjListDir.containsVertex(0));
        assertEquals(true,adjListDir.containsVertex(1));
        assertEquals(false,adjListDir.containsVertex(2));
    }
    
    @Test
    public void testAddEdge() {
        AdjListDir adjListDir = new AdjListDir();
        adjListDir.addVertex();
        adjListDir.addVertex();
        adjListDir.addVertex();   
        adjListDir.addEdge(0, 1);
        adjListDir.addEdge(1, 2);
        
        assertEquals(true,adjListDir.containsEdge(0, 1));
        assertEquals(true,adjListDir.containsEdge(1, 2));
        assertEquals(false,adjListDir.containsEdge(0, 2));        
        
        //System.out.print("DIM:" + adjListDir.size() + "\n");
        //adjListDir.viewGraph();
    }
    
    @Test
    public void testRemoveEdge() {
        AdjListDir adjListDir = new AdjListDir();
        adjListDir.addVertex();
        adjListDir.addVertex();
        adjListDir.addVertex();   
        adjListDir.addEdge(0, 1);
        adjListDir.addEdge(1, 2);
        adjListDir.addEdge(0, 2);
        //adjListDir.viewGraph();
        adjListDir.removeEdge(0, 1);
        
        assertEquals(false,adjListDir.containsEdge(0, 1));
        assertEquals(true,adjListDir.containsEdge(1, 2));
        assertEquals(true,adjListDir.containsEdge(0, 2));     
        
        //System.out.print("DIM:" + adjListDir.size() + "\n");
        //adjListDir.viewGraph();
    }
    
    @Test
    public void testRemoveVertex() {
        AdjListDir adjListDir = new AdjListDir();
        adjListDir.addVertex();
        adjListDir.addVertex();
        adjListDir.addVertex();   
        adjListDir.addEdge(0, 1);
        adjListDir.addEdge(1, 2);
        adjListDir.addEdge(0, 2);
        //adjListDir.viewGraph();
        adjListDir.removeVertex(1);

        assertEquals(true,adjListDir.containsVertex(0));
        assertEquals(true,adjListDir.containsVertex(1));
        assertEquals(false,adjListDir.containsVertex(2));
        assertEquals(true,adjListDir.containsEdge(0, 1));

        try {
            adjListDir.containsEdge(0, 2);
            fail();
        }catch(IllegalArgumentException e) {
            assertEquals(true,true);
        }

        //System.out.print("\n");
        //adjListDir.viewGraph();
    }
    
    @Test
    public void testGetAdjacent() {
        AdjListDir adjListDir = new AdjListDir();
        adjListDir.addVertex();
        adjListDir.addVertex();
        adjListDir.addVertex();   
        adjListDir.addEdge(0, 1);
        adjListDir.addEdge(1, 2);
        adjListDir.addEdge(0, 2);
        
        Set<Integer> hsTest=new HashSet<Integer>();
        hsTest.add(1);
        assertFalse(hsTest.equals(adjListDir.getAdjacent(0)));
        hsTest.add(2);
        assertEquals(adjListDir.getAdjacent(0),hsTest );
    }
    
    @Test
    public void testIsAdjacent() {
        AdjListDir adjListDir = new AdjListDir();
        adjListDir.addVertex();
        adjListDir.addVertex();
        adjListDir.addVertex();   
        adjListDir.addEdge(0, 1);
        adjListDir.addEdge(1, 2);
        
        assertEquals(true, adjListDir.isAdjacent(0, 1));
        assertEquals(false, adjListDir.isAdjacent(0, 2));     
    }
    
    @Test
    public void testIsCyclic() {
        AdjListDir adjListDir = new AdjListDir();
        adjListDir.addVertex();
        adjListDir.addVertex();
        adjListDir.addVertex();   
        adjListDir.addEdge(0, 1);
        adjListDir.addEdge(1, 2);
        adjListDir.addEdge(0, 2);
        
        assertEquals(true, adjListDir.isCyclic());
        
        adjListDir.removeEdge(1, 2);
        //adjListDir.viewGraph();
        assertEquals(false, adjListDir.isCyclic());       
    }
    
    @Test
    public void testGetBFSTree() {
        AdjListDir adjListDir = new AdjListDir();
        adjListDir.addVertex();
        adjListDir.addVertex();
        adjListDir.addVertex();   
        adjListDir.addVertex();
        adjListDir.addVertex();
        adjListDir.addVertex();   
        adjListDir.addVertex();
        adjListDir.addVertex();

        adjListDir.addEdge(0, 1);
        adjListDir.addEdge(1, 2);
        adjListDir.addEdge(2, 3);
        adjListDir.addEdge(3, 4);
        adjListDir.addEdge(4, 5);
        adjListDir.addEdge(5, 6);
        
        adjListDir.addEdge(0, 7);
        adjListDir.addEdge(1, 6);
        adjListDir.addEdge(7, 2);
        adjListDir.addEdge(7, 4);
        //adjListDir.viewGraph();
        //djListDir.log = true;
        adjListDir.getBFSTree(0);
        //System.out.print("\nvisita attesa\n");
        //System.out.print("0-1-7-2-6-4-3-5-\n");       
    }
    
    @Test
    public void testGetDSFTree() {
        AdjListDir adjListDir = new AdjListDir();
        adjListDir.addVertex();
        adjListDir.addVertex();
        adjListDir.addVertex();   
        adjListDir.addVertex();
        adjListDir.addVertex();
        adjListDir.addVertex();   
        adjListDir.addVertex();
        adjListDir.addVertex();

        adjListDir.addEdge(0, 1);
        adjListDir.addEdge(1, 2);
        adjListDir.addEdge(2, 3);
        adjListDir.addEdge(3, 4);
        adjListDir.addEdge(4, 5);
        adjListDir.addEdge(5, 6);
        
        adjListDir.addEdge(0, 7);
        adjListDir.addEdge(1, 6);
        adjListDir.addEdge(7, 2);
        adjListDir.addEdge(7, 4);
        adjListDir.getDFSTree(0);
        adjListDir.viewGraph();
        //adjListDir.log = true;
        System.out.print("\nvisitato\n");
        System.out.print("0-1-2-3-4-5-6-7-\n"); 
        
    }
    
    @Test
    public void testGetDFSTOTForest() {
        
        AdjListDir adjListDir = new AdjListDir();
        adjListDir.addVertex();
        adjListDir.addVertex();
        adjListDir.addVertex();   
        adjListDir.addVertex();   
        adjListDir.addVertex();   
        adjListDir.addVertex();   
        adjListDir.addVertex();   
        adjListDir.addEdge(0, 1);
        adjListDir.addEdge(1, 2);
        adjListDir.addEdge(3, 4);
        adjListDir.addEdge(5, 6);
        
        int[] myOrder = {2, 3};
        //adjListDir.viewGraph();
        //adjListDir.log = true;
        adjListDir.getDFSTOTForest(myOrder);
        /*
        System.out.print("\nvisita attesa\n");
        System.out.print("Visito 2-1-0-\n");    
        System.out.print("Visito 3-4-\n");  
        System.out.print("Salto 0-1-2-3-4-\n"); 
        System.out.print("Visito 5-6\n");
        System.out.print("Salto 6\n");          
        */
    }
    
    @Test
    public void testConnectedComponents() {
        
        AdjListDir adjListDir = new AdjListDir();
        adjListDir.addVertex();
        adjListDir.addVertex();
        adjListDir.addVertex();   
        adjListDir.addVertex();   
        adjListDir.addVertex();   
        adjListDir.addVertex();   
        adjListDir.addVertex();   
        adjListDir.addEdge(0, 1);
        adjListDir.addEdge(1, 2);
        adjListDir.addEdge(3, 4);
        adjListDir.addEdge(5, 6);
        
        //adjListDir.viewGraph();
        //adjListDir.log = true;
        Set<Set<Integer>> connectedComponents = adjListDir.connectedComponents();
        
        for(Set<Integer> component : connectedComponents){
            for(Integer index : component) {
                System.out.print(index + "-");      
            }
            System.out.print("\n");     
        }
        /*
        System.out.print("Atteso\n");
        System.out.print("0-1-2\n");
        System.out.print("3-4-\n"); 
        System.out.print("5-6-\n"); */
    }
    
    @Test
    public void equals() {
        
        AdjListDir adjListDir1 = new AdjListDir();
        adjListDir1.addVertex();
        adjListDir1.addVertex();
        adjListDir1.addVertex();
        adjListDir1.addEdge(0, 1);
        adjListDir1.addEdge(1, 2);
        adjListDir1.addEdge(2, 0);        
        AdjListDir adjListDir2 = new AdjListDir();
        adjListDir2.addVertex();
        adjListDir2.addVertex();
        adjListDir2.addVertex();
        adjListDir2.addEdge(2, 0);
        adjListDir2.addEdge(1, 2);        
        adjListDir2.addEdge(0, 1);
        
        assertEquals(true,adjListDir1.equals(adjListDir2) );
    }

    @Test
    public void testMaxDisjointIntervals() {

        Integer[] starting = { 0, 2, 6, 11, 4, 16 };
        Integer[] ending = { 5, 13, 10, 15, 6, 17 };
        Integer[] expected = { 0, 2, 3, 5 };
        Integer[] res = Greedy.getMaxDisjointIntervals(starting, ending);
        System.out.println(res[0] + "" + res[1]);
        assertEquals(res.length, expected.length);
        for (int i = 0; i < res.length; i++) {
            assertEquals(res[i], expected[i]);
        }
    }

    @Test
    public void testPriorityQueue() {
        PriorityQueueArr q = new PriorityQueueArr(5);
        q.enqueue(0, 9);
        q.enqueue(1, 1);
        q.enqueue(2, 6);
        q.enqueue(3, -1);
        q.enqueue(4, 2);

        // q.ViewQueue();

        assertEquals(1, q.dequeue());
        assertEquals(4, q.dequeue());
        assertEquals(2, q.dequeue());
        assertEquals(0, q.dequeue());
        assertEquals(-1, q.dequeue());
    }

    @Test
    public void testBellman() {
        AdjListDirWeight adjListUndirWeight = new AdjListDirWeight();
        adjListUndirWeight.addVertex();
        adjListUndirWeight.addVertex();
        adjListUndirWeight.addVertex();
        adjListUndirWeight.addVertex();
        adjListUndirWeight.addVertex();
        adjListUndirWeight.addEdge(0, 1);
        adjListUndirWeight.addEdge(1, 2);
        adjListUndirWeight.addEdge(2, 3);
        adjListUndirWeight.addEdge(3, 4);
        adjListUndirWeight.addEdge(4, 0);
        adjListUndirWeight.addEdge(0, 3);
        adjListUndirWeight.addEdge(4, 2);

        adjListUndirWeight.setEdgeWeight(0, 1, 2);
        adjListUndirWeight.setEdgeWeight(1, 2, 3);
        adjListUndirWeight.setEdgeWeight(2, 3, 2);
        adjListUndirWeight.setEdgeWeight(3, 4, 2);
        adjListUndirWeight.setEdgeWeight(4, 0, 4);
        adjListUndirWeight.setEdgeWeight(0, 3, 9);
        adjListUndirWeight.setEdgeWeight(4, 2, 9);

        AdjListDirWeight res = (AdjListDirWeight) adjListUndirWeight.getBellmanFordShortestPaths(0);
        adjListUndirWeight.viewGraph();
    }
}