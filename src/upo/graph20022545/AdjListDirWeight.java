package upo.graph20022545;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import upo.graph.base.*;
import upo.graph.base.VisitForest.Color;
import upo.graph.base.VisitForest.VisitType;

/**
 * Implementazione mediante <strong>liste di adiacenza</strong> di un grafo <strong>non orientato pesato</strong>.
 * 
 * @author Alessandro Vaprio 20022545
 *
 */

public class AdjListDirWeight implements WeightedGraph{

	private List<LinkedList<Edge>> vertexesList;	
	public boolean log; //Used only for view debug
	
	public class Edge {
	    public Edge(int v,double w) {
	        vertex = v;
	        weight=w;
	    }
	    public int vertex;
	    public double weight;
	}
	
	
	public AdjListDirWeight() {		
		vertexesList=new LinkedList<LinkedList<Edge>>();	
		log = false;
	}
	
	@Override
	public int addVertex() {
		LinkedList<Edge> adjacentVertex=new LinkedList<Edge>();
		vertexesList.add(this.size(),adjacentVertex);
		return this.size();
	}

	@Override
	public boolean containsVertex(int index) {
		if(this.size()>index) return true;
		else return false;
	}

	@Override
	public void removeVertex(int index) throws NoSuchElementException {
		if(containsVertex(index)==false) throw new NoSuchElementException();
		vertexesList.remove(index);
		
		for(int i=0; i<this.size(); i++) {
			for(int y=0; y<vertexesList.get(i).size(); y++) {
				if(vertexesList.get(i).get(y).vertex==index) { 
					vertexesList.get(i).remove(y);
				    y--; 	
				}
				else if(vertexesList.get(i).get(y).vertex>index) {
					//vertexList.get(i).set(y, (vertexList.get(i).get(y)).vertex-1);
					vertexesList.get(i).get(y).vertex-=1;
				}
			}
		}
		
	}

	@Override
	public void addEdge(int sourceVertexIndex, int targetVertexIndex) throws IllegalArgumentException {
		 if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) throw new IllegalArgumentException();
		 
		 vertexesList.get(sourceVertexIndex).add( new Edge(targetVertexIndex,defaultEdgeWeight));
		
	}

	@Override
	public boolean containsEdge(int sourceVertexIndex, int targetVertexIndex) throws IllegalArgumentException {
		 if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) throw new IllegalArgumentException();
		 for(Edge ver : vertexesList.get(sourceVertexIndex))
			 if(targetVertexIndex == ver.vertex)
				 return true;
		 return false;
		 //if(vertexList.get(sourceVertexIndex).contains(targetVertexIndex) && vertexList.get(targetVertexIndex).contains(sourceVertexIndex)) return true;
		 //else return false;
	}
	
	public boolean compareEdge(int sourceVertexIndex, int targetVertexIndex, double weight) throws IllegalArgumentException {
		 if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) throw new IllegalArgumentException();
		 for(Edge ver : vertexesList.get(sourceVertexIndex))
			 if(targetVertexIndex == ver.vertex && weight == ver.weight)
				 return true;
		 return false;
		 //if(vertexList.get(sourceVertexIndex).contains(targetVertexIndex) && vertexList.get(targetVertexIndex).contains(sourceVertexIndex)) return true;
		 //else return false;
	}

	@Override
	public void removeEdge(int sourceVertexIndex, int targetVertexIndex) {
		if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) 
			return;
	 
		 for(Edge ver : vertexesList.get(sourceVertexIndex)) {
		     if(targetVertexIndex == ver.vertex) {
		         vertexesList.get(sourceVertexIndex).remove(vertexesList.get(sourceVertexIndex).indexOf(ver));				     
		     }		     
		 }
		
	}

	@Override
	public Set<Integer> getAdjacent(int vertexIndex) throws NoSuchElementException {
		if(containsVertex(vertexIndex)==false) {
		    throw new NoSuchElementException();		    
		} 
		
		Set<Integer> adjacent=new HashSet<Integer>();
		
		for(Edge ver : vertexesList.get(vertexIndex)) {
		    adjacent.add(ver.vertex);		    
		}
		
		return adjacent;
	}

	@Override
	public boolean isAdjacent(int sourceVertexIndex, int targetVertexIndex) throws IllegalArgumentException {
		if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) {
		    throw new IllegalArgumentException();		    
		}
		
		return containsEdge(sourceVertexIndex,targetVertexIndex);
	}

	@Override
	public int size() {
		return vertexesList.size();
	}

	@Override
	public boolean isDirected() {
		return true;
	}

	@Override
	public boolean isCyclic() {
		VisitForest forest=new VisitForest(this, VisitType.DFS_TOT);
		for(int i=0; i<this.size(); i++) {
			if(forest.getColor(i)==Color.WHITE && VisitaRicCiclo(forest, i))
				return true;
		}
		return false;
	}
	
	private boolean VisitaRicCiclo(VisitForest forest, int u){
		forest.setColor(u, Color.GRAY);
		for(Integer v : this.getAdjacent(u)) {
			if(forest.getColor(v)==Color.WHITE) {
				forest.setParent(v, u);
				if(VisitaRicCiclo(forest, v)) return true;
			}
			else if(v!=forest.getPartent(u)) return true;
		}
		forest.setColor(u, Color.BLACK);
		return false;
	}

	@Override
	public boolean isDAG() {
	 // directed e' sempre true, se non ha cicli allora e' true altrimenti false
	    return !isCyclic();
	}

	@Override
	public VisitForest getBFSTree(int startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
		if(this.containsVertex(startingVertex)==false) throw new IllegalArgumentException();
		
		VisitForest forest=new VisitForest(this, VisitType.BFS);
	
		LinkedList<Integer> frangia=new LinkedList<Integer>();
		forest.setColor(startingVertex, Color.GRAY);
		frangia.add(startingVertex);
		Integer u;
		while(!frangia.isEmpty()) {
			u=frangia.get(0); 	
			if(log)
				System.out.print(u + "-");
			for(Edge v : vertexesList.get(u)) {
				if(forest.getColor(v.vertex)==Color.WHITE) {
					forest.setColor(v.vertex, Color.GRAY);  	
					forest.setParent(v.vertex, u);
				    frangia.add(v.vertex);
				}
			}
			forest.setColor(u, Color.BLACK);
			frangia.remove(0);
		}
		if(log)
			System.out.print("\n");			
		return forest;
	}

	@Override
	public VisitForest getDFSTree(int startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
		if(this.containsVertex(startingVertex)==false) throw new IllegalArgumentException();
		
		LinkedList<Integer> stack=new LinkedList<Integer>();
		Integer[] arr=new Integer[this.size()];
				
		for(int i=0; i<this.size(); i++) 
			arr[i]=0;
		
		VisitForest forest=new VisitForest(this, VisitType.DFS);
		
		forest.setColor(startingVertex, Color.GRAY); 
		
		stack.add(startingVertex); 
		Edge v;
		while(!stack.isEmpty()) {
			while(arr[stack.get(0)]!=null) {
			    if(vertexesList.get(stack.get(0)).size() > 0) {
			        if(vertexesList.get(stack.get(0)).size() > 0) {
			            v=vertexesList.get(stack.get(0)).get(arr[stack.get(0)]);
			            arr[stack.get(0)]++;
			            if(arr[stack.get(0)]==vertexesList.get(stack.get(0)).size())
			                arr[stack.get(0)]=null;
			            if(forest.getColor(v.vertex)==Color.WHITE) {
			                if(log)
			                    System.out.print(v.vertex+"-");
			                forest.setColor(v.vertex, Color.GRAY);
			                forest.setParent(v.vertex, stack.get(0));
			                stack.add(0, v.vertex);
			            }
			            
			        }
			    } else {
			        arr[stack.get(0)]=null;
			    }
			}	
			forest.setColor(stack.get(0), Color.BLACK);
			stack.remove(0);
		}
		if(log)
			System.out.print("\n");		
		return forest;
	}

	@Override
	public VisitForest getDFSTOTForest(int startingVertex) {
		VisitForest forest=new VisitForest(this, VisitType.DFS_TOT);
		for(int i=0; i<this.size(); i++) {
			if(forest.getColor(i)==Color.WHITE) {
				if(log)
					System.out.print("Visito: " + i + "\n");
					VisitDFS(forest, i);
			}else {
				if(log)
					System.out.print("Salto: " + i + "\n");
			}
				
		}
		return forest;
	}

	private void VisitDFS(VisitForest forest,int startingVertex) {
		LinkedList<Integer> stack=new LinkedList<Integer>();
		Integer[] arr=new Integer[this.size()];
				
		for(int i=0; i<this.size(); i++) 
			arr[i]=0;
		
		forest.setColor(startingVertex, Color.GRAY); 
		if(log)
			System.out.print(startingVertex+"-");
		stack.add(startingVertex); 
		Edge v;
		while(!stack.isEmpty()) {
			while(arr[stack.get(0)]!=null) {
			    if(vertexesList.get(stack.get(0)).size() > 0) {
			        v=vertexesList.get(stack.get(0)).get(arr[stack.get(0)]);
			        arr[stack.get(0)]++;
			        if(arr[stack.get(0)]==vertexesList.get(stack.get(0)).size())
			            arr[stack.get(0)]=null;
			        if(forest.getColor(v.vertex)==Color.WHITE) {
			            if(log)
			                System.out.print(v.vertex+"-");
			            forest.setColor(v.vertex, Color.GRAY);
			            forest.setParent(v.vertex, stack.get(0));
			            stack.add(0, v.vertex);
			        }
			        
			    }
				else {
                    arr[stack.get(0)]=null;
                }
			}	
			forest.setColor(stack.get(0), Color.BLACK);
			stack.remove(0);
		}
		if(log)
			System.out.print("\n");		
	}

	@Override
	public VisitForest getDFSTOTForest(int[] vertexOrdering)
			throws UnsupportedOperationException, IllegalArgumentException {
		VisitForest forest=new VisitForest(this, VisitType.DFS_TOT);
		//Visit before order required
		for(int i=0; i<vertexOrdering.length; i++) {
			if(forest.getColor(vertexOrdering[i])==Color.WHITE) {
				if(log)
					System.out.print("Visito in ordine: " + vertexOrdering[i] + "\n");
				VisitDFS(forest, vertexOrdering[i]);
			}else {
				if(log)
					System.out.print("Salto: " + vertexOrdering[i] + "\n");
			}
				
		}
		
		//After only white vertex not connected and not started from before visit
		for(int i=0; i<this.size(); i++) {
			if(forest.getColor(i)==Color.WHITE) {
				if(log)
					System.out.print("Visito: " + i + "\n");
				VisitDFS(forest, i);
			}else {
				if(log)
					System.out.print("Salto: " + i + "\n");
			}				
		}
		return forest;
	}

	@Override
	public int[] topologicalSort() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Set<Integer>> stronglyConnectedComponents() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Set<Integer>> connectedComponents() throws UnsupportedOperationException {
		VisitForest forest=new VisitForest(this, VisitType.DFS_TOT);
		Set<Set<Integer>> connectedComponents = new HashSet<>();
		for(int i=0; i<this.size(); i++) {
			if(forest.getColor(i)==Color.WHITE) 
				connectedComponents.add(VisitConnectedComponents(forest, i));
		}
		return connectedComponents;
	}

	private Set<Integer> VisitConnectedComponents(VisitForest forest, int startingVertex) {
		Set<Integer> connectedComponent = new HashSet<>();
		LinkedList<Integer> stack=new LinkedList<Integer>();
		Integer[] arr=new Integer[this.size()];
				
		for(int i=0; i<this.size(); i++) 
			arr[i]=0;
		
		forest.setColor(startingVertex, Color.GRAY); 
		stack.add(startingVertex); 
		connectedComponent.add(startingVertex);
		Edge v;
		while(!stack.isEmpty()) {
			while(arr[stack.get(0)]!=null) {
			    if(vertexesList.get(stack.get(0)).size() > 0) {
			        v=vertexesList.get(stack.get(0)).get(arr[stack.get(0)]);
			        arr[stack.get(0)]++;
			        if(arr[stack.get(0)]==vertexesList.get(stack.get(0)).size())
			            arr[stack.get(0)]=null;
			        if(forest.getColor(v.vertex)==Color.WHITE) {
			            forest.setColor(v.vertex, Color.GRAY);
			            forest.setParent(v.vertex, stack.get(0));
			            connectedComponent.add(v.vertex);
			            stack.add(0, v.vertex);
			        }
			    }
				else {
                    arr[stack.get(0)]=null;
                }
			}	
			forest.setColor(stack.get(0), Color.BLACK);
			stack.remove(0);
		}
		return connectedComponent;
	}
	
	@Override
	public double getEdgeWeight(int sourceVertexIndex, int targetVertexIndex) throws IllegalArgumentException, NoSuchElementException {
		if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) {
		    throw new IllegalArgumentException();		    
		}
		for(Edge v : vertexesList.get(sourceVertexIndex)) {
			if(v.vertex == targetVertexIndex) {
			    return v.weight;			    
			}
		}
		throw new NoSuchElementException();
	}

	@Override
	public void setEdgeWeight(int sourceVertexIndex, int targetVertexIndex, double weight) throws IllegalArgumentException, NoSuchElementException {
		if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) 
			 throw new IllegalArgumentException();
		for(Edge v : vertexesList.get(sourceVertexIndex)) {
			if(v.vertex == targetVertexIndex) {
				v.weight = weight;
			}
		}	
	}

	@Override
	public WeightedGraph getBellmanFordShortestPaths(int startingVertex)
			throws UnsupportedOperationException, IllegalArgumentException {

		if (this.containsVertex(startingVertex) == false)
			throw new IllegalArgumentException();
		double dist[] = new double[this.vertexesList.size()];
		VisitForest forest = new VisitForest(this, VisitType.BFS);

		// inizializzo tutto a max value
		for (double d : dist) {
			d = defaultEdgeWeight;
		}

		for (int i = 0; i < vertexesList.size() - 1; i++) {
			for (int u = 0; u < vertexesList.size(); u++) {
				for (int v = 0; v < vertexesList.get(u).size(); v++) {
					if (dist[v] > dist[u] + vertexesList.get(u).get(v).weight) {
						dist[v] = dist[u] + vertexesList.get(u).get(v).weight;
						forest.setParent(v, u);
					}
				}
			}
		}
		if (log)
			System.out.print("\n");

		// controllo i cicli negativi
		for (int u = 0; u < vertexesList.size(); u++) {
			for (int v = 0; v < vertexesList.get(u).size(); v++) {
				if (dist[v] > dist[u] + vertexesList.get(u).get(v).weight) {
					throw new IllegalArgumentException("Graph contains negative weight cycle");
				}
			}
		}

		return this;
	}

	@Override
	public WeightedGraph getDijkstraShortestPaths(int startingVertex)
			throws UnsupportedOperationException, IllegalArgumentException {
	    throw new UnsupportedOperationException();
	}

	@Override
	public WeightedGraph getPrimMST(int startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
	    throw new UnsupportedOperationException();
	}

	@Override
	public WeightedGraph getKruskalMST() throws UnsupportedOperationException {
	    throw new UnsupportedOperationException();
	}

	@Override
	public WeightedGraph getFloydWarshallShortestPaths() throws UnsupportedOperationException {
	    throw new UnsupportedOperationException();
	}
	
	public boolean equals(Object o) {
		AdjListDirWeight g = (AdjListDirWeight)o;
		if(g.size() != this.size())
			return false;
		for(int i=0; i<this.size(); i++) {
			for(Edge ver : vertexesList.get(i)) {
				if(g.compareEdge(i, ver.vertex, ver.weight )==false)
					return false;
			}
		}
		return true;
	}
	
	public void viewGraph() {
		for(int i=0; i<vertexesList.size(); i++) {
			System.out.print("Vertex: "+ (i) +", adjacentVertex : ");
			for(Edge ver : vertexesList.get(i))
				System.out.print("[V:"+ ver.vertex + " W:" + ver.weight + "], ");
			System.out.print("\n");
		}
	}


}