package upo.graph20022545;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import upo.graph.basenew.VisitForest;
import upo.graph.basenew.WeightedGraph;
import upo.graph.basenew.VisitForest.Color;
import upo.graph.basenew.VisitForest.VisitType;
import upo.graph.basenew.*;


/**
 * Implementazione mediante <strong>matrice di adiacenza</strong> di un grafo <strong>non orientato pesato</strong>.
 * 
 * @author Alessandro Vaprio 20022545
 */
public class AdjMatrixUndirWeight implements WeightedGraph{

	private double[][] verticesMatrix;
	private List<String> verticesList;

	private int size;
	
	private final int INFINITE=Integer.MAX_VALUE;
	
	public List<String> getVerticesList() {
		return verticesList;
	}
	public AdjMatrixUndirWeight() {
		size=0;
		verticesMatrix= new double[0][0];
		verticesList = new ArrayList<String>();
	}
	
	public void stampa() {
		System.out.println();
		System.out.print(" ");
		for(int k=0; k<size; k++) System.out.print("   "+verticesList.get(k));
		System.out.println();
		System.out.print("  ");
		for(int k=0; k<size; k++) System.out.print(" ___");
		System.out.println();
		
		for(int i=0; i<size; i++) {
			System.out.print(verticesList.get(i)+" ");
			for(int j=0; j<size; j++) {
				if(verticesMatrix[i][j]==this.INFINITE) 
					System.out.print("|"+"\u221E  ");
				else
				    System.out.print("|"+verticesMatrix[i][j]);
			}
			System.out.println("|");
			System.out.print("  ");
			for(int k=0; k<size; k++) System.out.print("|___");
			System.out.println("|");
		}
		System.out.println();
	}

	@Override
	public int addVertex(String v) {
		double[][] temp = new double[size+1][size+1];
		
		for(int i=0; i<size; i++) { 
			for(int j=0; j<size; j++) {
				temp[i][j]=verticesMatrix[i][j];
				if (verticesList.indexOf(v) > -1) {
					verticesList.add(v);
				}
			}
		}
		
		verticesMatrix=temp;
		
		//riempo le caselle del nuovo vertice
		for(int i=0; i<size; i++)
			verticesMatrix[i][size]=this.INFINITE;
		for(int i=0; i<size; i++)
			verticesMatrix[size][i]=this.INFINITE;
		verticesMatrix[size][size]=0;
		
		verticesList.add(v);
		size++;
		return size;
	}
	
	@Override
	public boolean containsVertex(String vertex) {
		return verticesList.contains(vertex);
	}

	@Override
	public void removeVertex(String vertex) throws NoSuchElementException {
		if(containsVertex(vertex)==false) throw new NoSuchElementException();
		size--;
		double[][] temp = new double[size][size];
		int index = verticesList.indexOf(vertex);
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				if(i<verticesList.indexOf(vertex)) {
					if(j>=index) temp[i][j]=verticesMatrix[i][j+1];
					else temp[i][j]=verticesMatrix[i][j];
				}
				else { //i>=size
					if(j<index) temp[i][j]=verticesMatrix[i+1][j];
					else temp[i][j]=verticesMatrix[i+1][j+1];
				}
			}
		}
		
		verticesMatrix=temp;
		verticesList.remove(index);
	}

	@Override
	public void addEdge(String sourceVertexIndex, String targetVertexIndex) throws IllegalArgumentException {
		if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false)
			throw new IllegalArgumentException();
		int sourceIndex = verticesList.indexOf(sourceVertexIndex);
		int targetIndex = verticesList.indexOf(targetVertexIndex);
		verticesMatrix[sourceIndex][targetIndex]=WeightedGraph.defaultEdgeWeight;
		verticesMatrix[targetIndex][sourceIndex]=WeightedGraph.defaultEdgeWeight;
	}

	@Override
	public boolean containsEdge(String sourceVertexIndex, String targetVertexIndex) throws IllegalArgumentException {
		if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false)
			throw new IllegalArgumentException();
		
		int sourceIndex = verticesList.indexOf(sourceVertexIndex);
		int targetIndex = verticesList.indexOf(targetVertexIndex);

		if(verticesMatrix[sourceIndex][targetIndex]!=this.INFINITE 
			&& verticesMatrix[sourceIndex][targetIndex]!=0)
			return true;
		else 
			return false;
	}
 
	@Override
	public void removeEdge(String sourceVertexIndex, String targetVertexIndex)
			throws IllegalArgumentException, NoSuchElementException {
		
		if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false)
			throw new IllegalArgumentException();
		
		int sourceIndex = verticesList.indexOf(sourceVertexIndex);
		int targetIndex = verticesList.indexOf(targetVertexIndex);
		
		if(verticesMatrix[sourceIndex][targetIndex]==this.INFINITE || verticesMatrix[sourceIndex][targetIndex]==0)
			throw new NoSuchElementException();
		

		verticesMatrix[sourceIndex][targetIndex]=this.INFINITE;
		verticesMatrix[targetIndex][sourceIndex]=this.INFINITE;
	}

	@Override
	public Set<String> getAdjacent(String vertex) throws NoSuchElementException {
		if(containsVertex(vertex)==false) 
			throw new NoSuchElementException();
		
		Set<String> ret = new HashSet<String>();
		int sourceIndex = verticesList.indexOf(vertex);
		for(int i=0; i<size; i++)
			if(verticesMatrix[i][sourceIndex]!=0 
				&& verticesMatrix[i][sourceIndex]!=this.INFINITE) {
				
				ret.add(verticesList.get(i));
			}
		
		return ret;
	}

	@Override
	public boolean isAdjacent(String targetVertexIndex, String sourceVertexIndex) throws IllegalArgumentException {
		if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false)
			throw new IllegalArgumentException();

		int sourceIndex = verticesList.indexOf(sourceVertexIndex);
		int targetIndex = verticesList.indexOf(targetVertexIndex);

		if(verticesMatrix[sourceIndex][targetIndex]!=this.INFINITE && verticesMatrix[sourceIndex][targetIndex]!=0)
			return true;
		else 
			return false;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isDirected() {
		return false;
	}

	@Override
	public boolean isCyclic() {
		VisitForest visita=new VisitForest(this, VisitType.DFS_TOT);
//		for(int i=0; i<size; i++) {
//			if(visita.getColor(""+i)==Color.WHITE && visitaRicCiclo(visita, ""+i)) 
//				return true;
//		}
		return false;
	}
	
	private boolean visitaRicCiclo(VisitForest visita, String u) {
		visita.setColor(u, Color.GRAY);
		for(String v : this.getAdjacent(u)) {
			if(visita.getColor(v)==Color.WHITE) {
				visita.setParent(v, u);
				if(visitaRicCiclo(visita, v)) return true;
			}
			else if(visita.getColor(v)==Color.GRAY) return true;
		}
		visita.setColor(u, Color.BLACK);
		return false;
	}

	@Override
	public boolean isDAG() {
		return false;
	}

	public VisitForest getBFSTree(int startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public VisitForest getDFSTree(String startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
		if(this.containsVertex(startingVertex)==false) throw new IllegalArgumentException();
		
		List<String> stack = new LinkedList<String>();
		VisitForest visita=new VisitForest(this, VisitType.DFS);
		
		visita.setColor(startingVertex, Color.GRAY);
		
		//visita startingVertex
		System.out.println("Sto visitando il vertice "+startingVertex+".");
		//fine visita
		stack.add(startingVertex); //push
		String u;
		while(stack.isEmpty()==false) {
			u=stack.get(0);
			boolean passato=false;
			for(String v : this.getAdjacent(u)) {
				System.out.println("visita.getColor(v) "+visita.getColor(v)+"." + v);
				if(visita.getColor(v)==Color.WHITE) {
					passato=true;
					visita.setColor(v, Color.GRAY);
					visita.setParent(v, u);
					//visita v
					System.out.println("Sto visitando il vertice "+v+".");
					//fine visita
					stack.add(0, v); //push
					break;
				}
			}
			if(passato==false) {
				visita.setColor(u, Color.BLACK);
				stack.remove(0);
			}
		}
		return visita;
	}

	@Override
	public VisitForest getDFSTOTForest(String startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
		if(this.containsVertex(startingVertex)==false) throw new IllegalArgumentException();
		
		List<String> stack = new LinkedList<String>();
		VisitForest visita=new VisitForest(this, VisitType.DFS_TOT);
		visita.setColor(startingVertex, Color.GRAY);
		//visita startingVertex
		System.out.println("Sto visitando il vertice "+startingVertex+".");
		//fine visita
		stack.add(startingVertex); //push
		String u;
		while(stack.isEmpty()==false) {
			u=stack.get(0);
			boolean passato=false;
			for(String v : this.getAdjacent(u)) {
				if(visita.getColor(v)==Color.WHITE) {
					passato=true;
					visita.setColor(v, Color.GRAY);
					visita.setParent(v, u);
					//visita v
					System.out.println("Sto visitando il vertice "+v+".");
					//fine visita
					stack.add(0, v); //push
					break;
				}
			}
			if(passato==false) {
				visita.setColor(u, Color.BLACK);
				stack.remove(0);
			}
			//se non trovo vertici adiacenti ad U ne scelgo uno non adiacente e lo aggiungo alla frangia
			if(stack.isEmpty()) {
			    for(int i=0; i<size; i++)
			    	if(visita.getColor(verticesList.get(i))==Color.WHITE) {
			    		visita.setColor(verticesList.get(i), Color.GRAY);
			    		//visita i
						System.out.println("Sto visitando il vertice "+i+".");
						//fine visita
					    stack.add(0, verticesList.get(i)); //push
					    break;
			    	}
			}
		}
			
		return visita;
	}

	@Override
	public Set<Set<String>> stronglyConnectedComponents() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Set<String>> connectedComponents() throws UnsupportedOperationException {
		Set<Set<String>> componentiConnesse=new HashSet<Set<String>>();
		VisitForest visitaTotale=new VisitForest(this, VisitType.DFS_TOT);
		
		for(int i=0; i<size; i++) {
			if(visitaTotale.getColor(verticesList.get(i))==Color.WHITE) {
				Set<String> alberoDiVisita=new HashSet<String>();
				alberoDiVisita.add(verticesList.get(i)); //aggiungo il primo vertice bianco trovato(la radice senza predecessori)
				VisitForest visitaSingola=this.getDFSTree(verticesList.get(i));
				for(int j=0; j<size; j++) {
					if(visitaSingola.getPartent(verticesList.get(j))!=null) {
						alberoDiVisita.add(verticesList.get(j));
						visitaTotale.setColor(verticesList.get(j), Color.BLACK);
					}
				}
				componentiConnesse.add(alberoDiVisita);
			}
		}
		
		return componentiConnesse;
	}
	
	@Override
	public double getEdgeWeight(String sourceVertex, String targetVertex) throws IllegalArgumentException, NoSuchElementException {
		if(containsVertex(sourceVertex)==false || containsVertex(targetVertex)==false)
			throw new IllegalArgumentException();
		
		if(containsEdge(sourceVertex, targetVertex)==false)
			throw new NoSuchElementException();
		
		return verticesMatrix[verticesList.indexOf(sourceVertex) ][verticesList.indexOf(targetVertex)];
	}

	@Override
	public void setEdgeWeight(String sourceVertex, String targetVertex, double weight) throws IllegalArgumentException, NoSuchElementException {
		if(containsVertex(sourceVertex)==false || containsVertex(targetVertex)==false)
			throw new IllegalArgumentException();
		
		if(containsEdge(sourceVertex, targetVertex)==false)
			throw new NoSuchElementException();
		
		verticesMatrix[verticesList.indexOf(sourceVertex)][verticesList.indexOf(targetVertex)]=weight;
		verticesMatrix[verticesList.indexOf(targetVertex)][verticesList.indexOf(sourceVertex)]=weight;
	}

	

	
	@Override
	public int getVertexIndex(String arg0) {
		int index = this.verticesList.indexOf(arg0);
		if (index < 0) {
			throw new NoSuchElementException();
		}
		return index;
	}

	
	@Override
	public String[] topologicalSort() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WeightedGraph getBellmanFordShortestPaths(String arg0)
			throws UnsupportedOperationException, IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public WeightedGraph getDijkstraShortestPaths(String arg0)
			throws UnsupportedOperationException, IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public WeightedGraph getFloydWarshallShortestPaths() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public WeightedGraph getKruskalMST() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public WeightedGraph getPrimMST(String arg0) throws UnsupportedOperationException, IllegalArgumentException {
		throw new UnsupportedOperationException();
	}
	@Override
	public String getVertexLabel(Integer arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public VisitForest getBFSTree(String p0) throws UnsupportedOperationException, IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public VisitForest getDFSTOTForest(String[] p0) throws UnsupportedOperationException, IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	

}