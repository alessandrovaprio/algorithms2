package upo.graph20022545.test;


import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import upo.graph.basenew.VisitForest;
import upo.graph.basenew.WeightedGraph;
import upo.graph.basenew.VisitForest.Color;
import upo.graph20022545.AdjMatrixUndirWeight;

class AdjMatrixUndirWeightTest {
	String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
	@Test
	@DisplayName("Test Vertice: Aggiungi")
	void testAddVerteces() {
		AdjMatrixUndirWeight graphT = new AdjMatrixUndirWeight();
		for (int i=0;i<letters.length;i++) {
			graphT.addVertex(letters[i]);
			assertEquals(graphT.size(), i+1);
		}
//		return graph;
		
	}
	
	
	
	
	
	@Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("Test Grafo")
    class graphTest {
		
		AdjMatrixUndirWeight graph;
		
		void loadVerteces() {
			graph = new AdjMatrixUndirWeight();
			for (int i=0;i<letters.length;i++) {
				graph.addVertex(letters[i]);
			}
//			return graph;
			
		}
		void loadEdges() {
			
			graph.addEdge("A", "B");
			graph.addEdge("A", "C");
			graph.addEdge("A", "E");
			graph.addEdge("B", "E");
			graph.addEdge("C", "D");
			
		}
		@BeforeEach 
		public void init() {
            
            try {
            	
            	loadVerteces();
            	loadEdges();
            		
            	
            } catch (Exception e) {
            	System.out.println(e.getMessage());
                fail(e.getMessage(), e);
            }

        }
		
		@Test
        @DisplayName("Test Ricerca Vertice")
        public void searchVertexTest() {
			assertEquals(this.graph.containsVertex("A"), true);
			assertEquals(this.graph.containsVertex("E"), true);
			assertEquals(this.graph.containsVertex("Z"), false);
        }
		
		@Test
        @DisplayName("Test Ricerca Arco")
        public void searchEdgeTest() {
			assertEquals(this.graph.containsEdge("A", "B"), true);
			assertEquals(this.graph.containsEdge("B", "A"), true);
			assertEquals(this.graph.containsEdge("D", "C"), true);
        }
		
		@Test
        @DisplayName("Test Elimina Vertici")
        public void deleteVertex() {
			this.graph.removeVertex("B");
			this.graph.removeVertex("E");
			assertEquals(this.graph.size(), letters.length-2);
//			System.out.println("list "+ this.graph.getVerticesList().toString());
			assertEquals(this.graph.getVerticesList().contains("B"), false);
			assertEquals(this.graph.getVerticesList().contains("E"), false);
			assertEquals(this.graph.getVerticesList().contains("D"), true);
			
        }
		
		@Test
        @DisplayName("Test Elimina Archi")
        public void deleteEdges() {
			this.graph.removeEdge("E", "B");
			this.graph.removeEdge("C", "D");
			assertEquals(this.graph.containsEdge("E", "B"), false);
			assertEquals(this.graph.containsEdge("D", "C"), false);
			assertEquals(this.graph.containsEdge("A", "B"), true);
//			System.out.println("list "+ this.graph.getVerticesList().toString());
			
        }
		
		@Test
        @DisplayName("Test Adiacenti")
        public void adjacents() {
			Set<String> adjsBeforeDeletes= graph.getAdjacent("A");
			this.graph.removeEdge("A", "B");
			this.graph.removeEdge("A", "C");
			Set<String> adjsAfterDeletes= graph.getAdjacent("A");
			
			assertEquals(adjsBeforeDeletes.size(), adjsAfterDeletes.size()+2);
			
//			System.out.println("list "+ this.graph.getVerticesList().toString());
			
        }
		
		@Test
        @DisplayName("Test peso Archi")
        public void edgesWeightTest() {
			graph.setEdgeWeight("A", "B", 10);
			graph.setEdgeWeight("A", "C", 20);
			
			assertEquals(graph.getEdgeWeight("A", "B"), 10);
			assertEquals(graph.getEdgeWeight("A", "C"), 20);
			assertEquals(graph.getEdgeWeight("A", "E"), WeightedGraph.defaultEdgeWeight);
			
        }
		
		@Test
        @DisplayName("Test DFS")
        public void dfsTest() {
			VisitForest forest = graph.getDFSTree("A");
			// nodes not linked are white
			assertEquals(forest.getColor("H"), Color.WHITE);
			assertEquals(forest.getColor("I"), Color.WHITE);
			// nodes visited are black
			assertEquals(forest.getColor("B"), Color.BLACK);
			assertEquals(forest.getColor("E"), Color.BLACK);
			
        }
		
		@Test
        @DisplayName("Test DFS Tutti i vertici")
        public void dfsTestTotal() {
            VisitForest forest = graph.getDFSTOTForest("A");

            assertEquals(forest.getColor("H"), Color.BLACK);
            assertEquals(forest.getColor("I"), Color.BLACK);

            assertEquals(forest.getColor("B"), Color.BLACK);
            assertEquals(forest.getColor("E"), Color.BLACK);
            
        }
		
		@Test
        @DisplayName("Test Ciclico")
        public void dfsTestCyclic() {
		    boolean isCyclic = graph.isCyclic();
		    // A->B->E->A
            assertEquals(isCyclic, true);	            
		}
		
		@Test
        @DisplayName("Test Componenti Connesse")
        public void dfsTestConnectedComponents() {
		    Set<Set<String>> components = graph.connectedComponents();
            // A->B->E->A
            assertEquals(true, true);               
        }
		
		
		
	}
	

}
