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
 * Implementazione mediante <strong>liste di adiacenza</strong> di un grafo <strong>non orientato non pesato</strong>.
 * 
 * @author Alessandro Vaprio 20022545
 *
 */
public class AdjListDir implements Graph{
	
	private List<LinkedList<Integer>> vertexesList;	
	
	public AdjListDir() {		
		vertexesList=new LinkedList<LinkedList<Integer>>();	
	}

	@Override
	public int addVertex() {
		LinkedList<Integer> adjacentVertex=new LinkedList<Integer>(); 
		vertexesList.add(this.size(),adjacentVertex);
		return this.size();
	}

	@Override
	public boolean containsVertex(int index) {
		if(this.size()>index) {
		    return true;		    
		} 
		else {
		    return false;   
		}
	}

	@Override
	public void removeVertex(int index) throws NoSuchElementException {
		if(containsVertex(index)==false) throw new NoSuchElementException();
		vertexesList.remove(index);
		
		for(int i=0; i<this.size(); i++) {
			for(int y=0; y<vertexesList.get(i).size(); y++) {
				if(vertexesList.get(i).get(y)==index) { 
					vertexesList.get(i).remove(y);
				    y--; 	
				}
				else if(vertexesList.get(i).get(y)>index)
					vertexesList.get(i).set(y, (vertexesList.get(i).get(y))-1); 
			}
		}
	}

	@Override
	public void addEdge(int sourceVertexIndex, int targetVertexIndex) throws IllegalArgumentException {
		 if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) throw new IllegalArgumentException();
		 
		 vertexesList.get(sourceVertexIndex).add(targetVertexIndex);
		
	}

	@Override
	public boolean containsEdge(int sourceVertexIndex, int targetVertexIndex) throws IllegalArgumentException {
		 if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) throw new IllegalArgumentException();
		 
		 if(vertexesList.get(sourceVertexIndex).contains(targetVertexIndex)) {
		     return true;		 
		 }
		 else {
		     return false;		     
		 }
	}

	@Override
	public void removeEdge(int sourceVertexIndex, int targetVertexIndex) {
		if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) 
			return;
	 
		if(vertexesList.get(sourceVertexIndex).contains(targetVertexIndex)==false)
			return;
		
		vertexesList.get(sourceVertexIndex).remove(vertexesList.get(sourceVertexIndex).indexOf(targetVertexIndex));	
	}

	@Override
	public Set<Integer> getAdjacent(int vertexIndex) throws NoSuchElementException {
		if(containsVertex(vertexIndex)==false) throw new NoSuchElementException();
		
		Set<Integer> adjacent=new HashSet<Integer>();
		
		for(Integer ver : vertexesList.get(vertexIndex))
			adjacent.add(ver);
		
		return adjacent;
	}

	@Override
	public boolean isAdjacent(int sourceVertexIndex, int targetVertexIndex) throws IllegalArgumentException {
		if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) 
			 throw new IllegalArgumentException();
		
		return vertexesList.get(sourceVertexIndex).contains(targetVertexIndex);
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
	
		LinkedList<Integer> queue=new LinkedList<Integer>();
		forest.setColor(startingVertex, Color.GRAY);
		queue.add(startingVertex);
		Integer u;
		while(!queue.isEmpty()) {
			u=queue.get(0); 	
			for(Integer v : vertexesList.get(u)) {
				if(forest.getColor(v)==Color.WHITE) {
					forest.setColor(v, Color.GRAY);  	
					forest.setParent(v, u);
				    queue.add(v);
				}
			}
			forest.setColor(u, Color.BLACK);
			queue.remove(0);
		}		
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
		Integer v;
		while(!stack.isEmpty()) {
			while(arr[stack.get(0)]!=null) {
			    if(vertexesList.get(stack.get(0)).size() > 0) {
			        v=vertexesList.get(stack.get(0)).get(arr[stack.get(0)]);
			        arr[stack.get(0)]++;
			        if(arr[stack.get(0)]==vertexesList.get(stack.get(0)).size())
			            arr[stack.get(0)]=null;
			        if(forest.getColor(v)==Color.WHITE) {
			            forest.setColor(v, Color.GRAY);
			            forest.setParent(v, stack.get(0));
			            stack.add(0, v);
			        }			        
			    } else {
			        arr[stack.get(0)]=null;
			    }
			}	
			forest.setColor(stack.get(0), Color.BLACK);
			stack.remove(0);
		}		
		return forest;
	}

	@Override
	public VisitForest getDFSTOTForest(int startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
		if(this.containsVertex(startingVertex)==false) throw new IllegalArgumentException();
		VisitForest forest=new VisitForest(this, VisitType.DFS_TOT);
		for(int i=0; i<this.size(); i++) {
			if(forest.getColor(i)==Color.WHITE) {
				VisitDFS(forest, i);
			}
				
		}
		return forest;
	}
	
	private void VisitDFS(VisitForest forest,int startingVertex) {
		LinkedList<Integer> stack=new LinkedList<Integer>();
		Integer[] arr=new Integer[this.size()];
				
		for(int i=0; i<this.size(); i++) {
		    arr[i]=0;
		    
		} 
		
		forest.setColor(startingVertex, Color.GRAY); 
		stack.add(startingVertex); 
		Integer v;
		while(!stack.isEmpty()) {
			while(arr[stack.get(0)]!=null) {
			    if(vertexesList.get(stack.get(0)).size() > 0) {
			        v=vertexesList.get(stack.get(0)).get(arr[stack.get(0)]);
			        arr[stack.get(0)]++;
			        if(arr[stack.get(0)]==vertexesList.get(stack.get(0)).size()) {
			            arr[stack.get(0)]=null;				    
			        }
			        if(forest.getColor(v)==Color.WHITE) {
			            forest.setColor(v, Color.GRAY);
			            forest.setParent(v, stack.get(0));
			            stack.add(0, v);
			        }
			        
			    } else {
			        arr[stack.get(0)]=null;
			    }
			}	
			forest.setColor(stack.get(0), Color.BLACK);
			stack.remove(0);
		}	
	}

	@Override
	public VisitForest getDFSTOTForest(int[] vertexOrdering) throws UnsupportedOperationException, IllegalArgumentException {
		
		VisitForest forest=new VisitForest(this, VisitType.DFS_TOT);
		//Visit before order required
		for(int i=0; i<vertexOrdering.length; i++) {
			if(forest.getColor(vertexOrdering[i])==Color.WHITE) {
				VisitDFS(forest, vertexOrdering[i]);
			}
				
		}
		
		//After only white vertex not connected and not started from before visit
		for(int i=0; i<this.size(); i++) {
			if(forest.getColor(i)==Color.WHITE) {
				VisitDFS(forest, i);
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
			if(forest.getColor(i)==Color.WHITE) {
			    connectedComponents.add(VisitConnectedComponents(forest, i));
			    
			} 
		}
		return connectedComponents;
	}
	
	private Set<Integer> VisitConnectedComponents(VisitForest forest, int startingVertex) {
		Set<Integer> connectedComponent = new HashSet<>();
		LinkedList<Integer> stack=new LinkedList<Integer>();
		Integer[] arr=new Integer[this.size()];
				
		for(int i=0; i<this.size(); i++) {
		    arr[i]=0;
		    
		}
		
		forest.setColor(startingVertex, Color.GRAY); 
		stack.add(startingVertex); 
		connectedComponent.add(startingVertex);
		Integer v;
		while(!stack.isEmpty()) {
			while(arr[stack.get(0)]!=null) {
			    if(vertexesList.get(stack.get(0)).size() > 0) {
			        
			        v=vertexesList.get(stack.get(0)).get(arr[stack.get(0)]);
			        arr[stack.get(0)]++;
			        if(arr[stack.get(0)]==vertexesList.get(stack.get(0)).size()) {
			            arr[stack.get(0)]=null;
			            
			        }
			        if(forest.getColor(v)==Color.WHITE) {
			            forest.setColor(v, Color.GRAY);
			            forest.setParent(v, stack.get(0));
			            connectedComponent.add(v);
			            stack.add(0, v);
			        }
			    } else {
			        arr[stack.get(0)]=null;
			    }
			}	
			forest.setColor(stack.get(0), Color.BLACK);
			stack.remove(0);
		}
		return connectedComponent;
	}

	public boolean equals(Object o) {
		AdjListDir g = (AdjListDir)o;
		if(g.size() != this.size())
			return false;
		for(int i=0; i<this.size(); i++) {
			for(int y=0; y<vertexesList.get(i).size(); y++) {
				if(g.containsEdge(i, vertexesList.get(i).get(y))==false) {
				    return false;
				    
				}
			}
		}
		return true;
	}
	
	public void viewGraph() {
		for(int i=0; i<vertexesList.size(); i++) {
			System.out.println("Vertex: "+ (i) +", adjacentVertex : "+ vertexesList.get(i));
		}
	}
}