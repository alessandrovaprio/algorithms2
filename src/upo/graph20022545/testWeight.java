package upo.graph20022545;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class testWeight {

	@Test
	public void testAddVertex() {
		AdjListDirWeight adjListDirWeight = new AdjListDirWeight();
		assertEquals(1,adjListDirWeight.addVertex());
		assertEquals(2,adjListDirWeight.addVertex());
		
	}
	
	@Test
	public void testContainsVertex() {
		AdjListDirWeight adjListDirWeight = new AdjListDirWeight();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();
		assertEquals(true,adjListDirWeight.containsVertex(0));
		assertEquals(true,adjListDirWeight.containsVertex(1));
		assertEquals(false,adjListDirWeight.containsVertex(2));
	}
	
	@Test
	public void testAddEdge() {
		AdjListDirWeight adjListDirWeight = new AdjListDirWeight();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();	
		adjListDirWeight.addEdge(0, 1);
		adjListDirWeight.addEdge(1, 2);
		
		//adjListDirWeight.viewGraph();
		
		assertEquals(true,adjListDirWeight.containsEdge(0, 1));
		assertEquals(true,adjListDirWeight.containsEdge(1, 2));
		assertEquals(false,adjListDirWeight.containsEdge(0, 2));		
		
	}
	
	@Test
	public void testRemoveEdge() {
		AdjListDirWeight adjListDirWeight = new AdjListDirWeight();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();	
		adjListDirWeight.addEdge(0, 1);
		adjListDirWeight.addEdge(1, 2);
		adjListDirWeight.addEdge(0, 2);
		//adjListDirWeight.viewGraph();
		adjListDirWeight.removeEdge(0, 1);
		//System.out.print("\n");
		//adjListDirWeight.viewGraph();
		assertEquals(true,adjListDirWeight.containsEdge(1, 2));
		assertEquals(true,adjListDirWeight.containsEdge(0, 2));		
		assertEquals(false,adjListDirWeight.containsEdge(0, 1));

	}
	
	@Test
	public void testRemoveVertex() {
		AdjListDirWeight adjListDirWeight = new AdjListDirWeight();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();	
		adjListDirWeight.addEdge(0, 1);
		adjListDirWeight.addEdge(1, 2);
		adjListDirWeight.addEdge(0, 2);
		//adjListDirWeight.viewGraph();
		adjListDirWeight.removeVertex(1);

		assertEquals(true,adjListDirWeight.containsVertex(0));
		assertEquals(true,adjListDirWeight.containsVertex(1));
		assertEquals(false,adjListDirWeight.containsVertex(2));
		assertEquals(true,adjListDirWeight.containsEdge(0, 1));

		try {
			adjListDirWeight.containsEdge(0, 2);
			fail();
		}catch(IllegalArgumentException e) {
			assertEquals(true,true);
		}

		//System.out.print("\n");
		//adjListDirWeight.viewGraph();
	}
	
	@Test
	public void testGetAdjacent() {
		AdjListDirWeight adjListDirWeight = new AdjListDirWeight();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();	
		adjListDirWeight.addEdge(0, 1);
		adjListDirWeight.addEdge(1, 2);
		adjListDirWeight.addEdge(0, 2);
		
		Set<Integer> hsTest=new HashSet<Integer>();
		hsTest.add(1);
		assertFalse(hsTest.equals(adjListDirWeight.getAdjacent(0)));
		hsTest.add(2);
		assertEquals(adjListDirWeight.getAdjacent(0),hsTest );
	}
	
	@Test
	public void testIsAdjacent() {
		AdjListDirWeight adjListDirWeight = new AdjListDirWeight();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();	
		adjListDirWeight.addEdge(0, 1);
		adjListDirWeight.addEdge(1, 2);
		
		assertEquals(true, adjListDirWeight.isAdjacent(0, 1));
		assertEquals(false, adjListDirWeight.isAdjacent(0, 2));		
	}
	
	@Test
	public void testIsCyclic() {
		AdjListDirWeight adjListDirWeight = new AdjListDirWeight();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();	
		adjListDirWeight.addEdge(0, 1);
		adjListDirWeight.addEdge(1, 2);
		adjListDirWeight.addEdge(0, 2);
		
		assertEquals(true, adjListDirWeight.isCyclic());
		
		adjListDirWeight.removeEdge(1, 2);
		//adjListDirWeight.viewGraph();
		assertEquals(false, adjListDirWeight.isCyclic());		
	}
	
	@Test
	public void testGetBFSTree() {
		AdjListDirWeight adjListDirWeight = new AdjListDirWeight();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();	
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();	
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();

		adjListDirWeight.addEdge(0, 1);
		adjListDirWeight.addEdge(1, 2);
		adjListDirWeight.addEdge(2, 3);
		adjListDirWeight.addEdge(3, 4);
		adjListDirWeight.addEdge(4, 5);
		adjListDirWeight.addEdge(5, 6);
		
		adjListDirWeight.addEdge(0, 7);
		adjListDirWeight.addEdge(1, 6);
		adjListDirWeight.addEdge(7, 2);
		adjListDirWeight.addEdge(7, 4);
		//adjListDirWeight.viewGraph();
		//adjListDirWeight.log = true;
		adjListDirWeight.getBFSTree(0);
		//System.out.print("\nvisita attesa\n");
		//System.out.print("0-1-7-2-6-4-3-5-\n");		
	}

	@Test
	public void testGetDSFTree() {
		AdjListDirWeight adjListDirWeight = new AdjListDirWeight();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();	
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();	
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();

		adjListDirWeight.addEdge(0, 1);
		adjListDirWeight.addEdge(1, 2);
		adjListDirWeight.addEdge(2, 3);
		adjListDirWeight.addEdge(3, 4);
		adjListDirWeight.addEdge(4, 5);
		adjListDirWeight.addEdge(5, 6);
		
		adjListDirWeight.addEdge(0, 7);
		adjListDirWeight.addEdge(1, 6);
		adjListDirWeight.addEdge(7, 2);
		adjListDirWeight.addEdge(7, 4);
		//adjListDirWeight.viewGraph();
		//adjListDirWeight.log = true;
		adjListDirWeight.getDFSTree(0);
		//System.out.print("\nvisita attesa\n");
		//System.out.print("0-1-2-3-4-5-6-7-\n");		
	}
	
	@Test
	public void testGetDFSTOTForest() {
		
		AdjListDirWeight adjListDirWeight = new AdjListDirWeight();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();	
		adjListDirWeight.addVertex();	
		adjListDirWeight.addVertex();	
		adjListDirWeight.addVertex();	
		adjListDirWeight.addVertex();	
		adjListDirWeight.addEdge(0, 1);
		adjListDirWeight.addEdge(1, 2);
		adjListDirWeight.addEdge(3, 4);
		adjListDirWeight.addEdge(5, 6);
		
		int[] myOrder = {2, 3};
		//adjListDirWeight.viewGraph();
		//adjListDirWeight.log = true;
		adjListDirWeight.getDFSTOTForest(myOrder);
		
		//System.out.print("\nvisita attesa\n");
		//System.out.print("Visito 2-1-0-\n");	
		//System.out.print("Visito 3-4-\n");	
		//System.out.print("Salto 0-1-2-3-4-\n");	
		//System.out.print("Visito 5-6\n");
		//System.out.print("Salto 6\n");			
	}
	
	@Test
	public void testConnectedComponents() {
		
		AdjListDirWeight adjListDirWeight = new AdjListDirWeight();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();	
		adjListDirWeight.addVertex();	
		adjListDirWeight.addVertex();	
		adjListDirWeight.addVertex();	
		adjListDirWeight.addVertex();	
		adjListDirWeight.addEdge(0, 1);
		adjListDirWeight.addEdge(1, 2);
		adjListDirWeight.addEdge(3, 4);
		adjListDirWeight.addEdge(5, 6);
		
		//adjListDirWeight.viewGraph();
		//adjListDirWeight.log = true;
		Set<Set<Integer>> connectedComponents = adjListDirWeight.connectedComponents();
		
		for(Set<Integer> component : connectedComponents){
			for(Integer index : component) {
				System.out.print(index + "-");		
			}
			//System.out.print("\n");		
		}
		
		//System.out.print("Atteso\n");
		//System.out.print("0-1-2\n");
		//System.out.print("3-4-\n");	
		//System.out.print("5-6-\n");	
	}
	
	@Test
	public void testSetGetEdgeWeight() {
		AdjListDirWeight adjListDirWeight = new AdjListDirWeight();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();
		adjListDirWeight.addVertex();	
		adjListDirWeight.addEdge(0, 1);
		adjListDirWeight.addEdge(1, 2);
		adjListDirWeight.addEdge(2, 0);

		adjListDirWeight.setEdgeWeight(0, 1, 5);
		adjListDirWeight.setEdgeWeight(1, 2, 7);
		adjListDirWeight.setEdgeWeight(2, 0, 9);				
		
		assertEquals(5,adjListDirWeight.getEdgeWeight(0, 1),0);
		assertEquals(7,adjListDirWeight.getEdgeWeight(1, 2),0);
		assertEquals(9,adjListDirWeight.getEdgeWeight(2, 0),0);
	}
	
	@Test
	public void equals() {
		
		AdjListDirWeight adjListDirWeight1 = new AdjListDirWeight();
		adjListDirWeight1.addVertex();
		adjListDirWeight1.addVertex();
		adjListDirWeight1.addVertex();
		adjListDirWeight1.addEdge(0, 1);
		adjListDirWeight1.addEdge(1, 2);
		adjListDirWeight1.addEdge(2, 0);		
		AdjListDirWeight adjListDirWeight2 = new AdjListDirWeight();
		adjListDirWeight2.addVertex();
		adjListDirWeight2.addVertex();
		adjListDirWeight2.addVertex();
		adjListDirWeight2.addEdge(2, 0);
		adjListDirWeight2.addEdge(1, 2);		
		adjListDirWeight2.addEdge(0, 1);
		
		assertEquals(true,adjListDirWeight1.equals(adjListDirWeight2) );
	}
}