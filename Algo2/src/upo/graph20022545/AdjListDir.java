package upo.graph20022545;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import upo.graph.base.Graph;
import upo.graph.base.VisitForest;
import upo.graph.base.VisitForest.Color;
import upo.graph.base.VisitForest.VisitType;
import upo.graph.basenew.*;

/**
 * Implementazione mediante <strong>liste di adiacenza</strong> di un grafo <strong>orientato non pesato</strong>.
 * 
 * @author Alessandro Vaprio 20022545
 */
public class AdjListDir implements Graph{

	private List<LinkedList<String>> adjList;	
	private List<String> verticesList;
	private int size;
	
	public AdjListDir() {		
		adjList=new LinkedList<LinkedList<String>>();	
		size=0;
	}
	
	public void stampa() {		
		for(int i=0; i<adjList.size(); i++) {
			System.out.println("Indice: "+i+", Vertici adiacenti: "+adjList.get(i));
		}
	}
	
	@Override
	public int addVertex(String v) {
		LinkedList<String> verticiAdiacenti=new LinkedList<String>(); 
		adjList.add(verticiAdiacenti);
		verticesList.add(v);
		size++;
		return size-1;
	}

	@Override
	public boolean containsVertex(String v) {
		return adjList.contains(v);
	}

	@Override
	public void removeVertex(String v) throws NoSuchElementException {
		if(containsVertex(v)==false) throw new NoSuchElementException();
			
//		adjList.remove(v);
		
		size--;
			
		if(size==0) return;
		int idxToDel=0; 
		for(int u=0; u<size; u++) {
			//correggendo le liste di adiacenza dopo l'eliminazione
			for(int i=0; i<adjList.get(u).size(); i++) {
				if(adjList.get(u).get(i)==v) { //si tratta del vertice eliminato
					adjList.get(u).remove(i);
					idxToDel=i;
				    i--; // devo ricontrollare la posizione corrente, altrimenti salterei il controllo di un vertice	
				}
				else if(i > idxToDel)
					adjList.get(u).set(i, (adjList.get(u).get(i-1))); 
			}
			//fine visita
		}
		verticesList.remove(v);
	}

	@Override
	public void addEdge(String sourceVertexIndex, String targetVertexIndex) throws IllegalArgumentException {
		 if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) 
			 throw new IllegalArgumentException();
		 
		 adjList.get(adjList.indexOf(sourceVertexIndex)).add(targetVertexIndex);
	}

	@Override
	public boolean containsEdge(String sourceVertexIndex, String targetVertexIndex) throws IllegalArgumentException {
		 if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) 
			 throw new IllegalArgumentException();
		 
		 if(adjList.get(adjList.indexOf(sourceVertexIndex)).contains(targetVertexIndex)) return true;
		 else return false;
	}

	@Override
	public void removeEdge(String sourceVertexIndex, String targetVertexIndex) throws IllegalArgumentException, NoSuchElementException {
		 if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) 
			 throw new IllegalArgumentException();
		 
		 if(adjList.get(adjList.indexOf(sourceVertexIndex)).contains(targetVertexIndex)==false)
			 throw new NoSuchElementException();
		 else {
			 for(int i=0; i<adjList.get(adjList.indexOf(sourceVertexIndex)).size(); i++) {
				 if(adjList.get(adjList.indexOf(sourceVertexIndex)).get(i)==targetVertexIndex) {
					 adjList.get(adjList.indexOf(sourceVertexIndex)).remove(i);
					 break;
				 }
			 }
		 }
	}

	@Override
	public Set<String> getAdjacent(String vertexIndex) throws NoSuchElementException {
		if(containsVertex(vertexIndex)==false) throw new NoSuchElementException();
		
		Set<String> ret=new HashSet<String>();
		
		for(String temp : adjList.get(adjList.indexOf(vertexIndex)))
			ret.add(temp);
		
		return ret;
	}

	@Override
	public boolean isAdjacent(String targetVertexIndex, String sourceVertexIndex) throws IllegalArgumentException {
		if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) 
			 throw new IllegalArgumentException();
		
		if(adjList.get(adjList.indexOf(sourceVertexIndex)).contains(targetVertexIndex)) return true;
		else return false;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isDirected() {
		return true;
	}

	@Override
	public boolean isCyclic() {
		VisitForest visita=new VisitForest(this, VisitType.DFS_TOT);
		for(int i=0; i<size; i++) {
			if(visita.getColor(verticesList.get(i))==Color.WHITE && visitaRicCiclo(visita, verticesList.get(i)))
				return true;
		}
		return false;
	}
	
	private boolean visitaRicCiclo(VisitForest visita, String u) {
		visita.setColor(u, Color.GRAY);
		for(String v : this.getAdjacent(u)) {
			if(visita.getColor(v)==Color.WHITE) {
				visita.setParent(v, u);
				if(visitaRicCiclo(visita, v)) return true;
			}
			else if(v!=visita.getPartent(u)) return true;
		}
		visita.setColor(u, Color.BLACK);
		return false;
	}

	@Override
	public boolean isDAG() {
		if(this.isCyclic()==false) return true;
		else return false;
	}

	@Override
	public VisitForest getBFSTree(String startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
		if(this.containsVertex(startingVertex)==false)
			throw new IllegalArgumentException();
		
		//algoritmo di visita
		VisitForest visita=new VisitForest(this, VisitType.BFS);
		LinkedList<String> frangia=new LinkedList<String>();
		visita.setColor(startingVertex, Color.GRAY);// comincio da startingVertex
		frangia.add(startingVertex); //enqueue
		String u;
		while(!frangia.isEmpty()) {
			u=frangia.get(0); //<-- head()	sempre da posizione 0 che è la testa
			
			//visito u
			System.out.println("Sto visitando il vertice "+u+".\n");
			//fine visita
			
			for(String v : adjList.get(adjList.indexOf(u))) {
				if(visita.getColor(v)==Color.WHITE) {
				    visita.setColor(v, Color.GRAY);  	
				    visita.setParent(v, u);
				    frangia.add(v); //enqueue
				}
			}
			

			visita.setColor(u, Color.BLACK);
			frangia.remove(0); //dequeue
		}
		
		return visita;
	}

	@Override
	public VisitForest getDFSTree(String startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
		if(this.containsVertex(startingVertex)==false) 
			throw new IllegalArgumentException();
		
		//inizializzo ptr
//		String[] ptr=new String[size];
//		for(int i=0; i<size; i++) {
//			if(adjList.get(i).size()==0)
//				ptr[i]=null;
//			else ptr[i]=adjList.get(0);
//		}
//		
//		//algoritmo di visita
		VisitForest visita=new VisitForest(this, VisitType.DFS);
//		LinkedList<String> frangia=new LinkedList<String>();
//		visita.setColor(startingVertex, Color.GRAY); // comincio da startingVertex
//		//visito startingVertex
//		System.out.println("Sto visitando il vertice "+startingVertex+".\n");
//		//fine visita	
//		frangia.add(startingVertex); //push 
//		String v;
//		while(!frangia.isEmpty()) {
//			while(ptr[frangia.indexOf(0)]!=null) {
//				v=adjList.get(frangia.get(0)).get(ptr[0]);
//				ptr[frangia.get(0)]++;
//				if(ptr[frangia.get(0)]==adjList.get(frangia.get(0)).size())
//					ptr[frangia.get(0)]=null;
//				if(visita.getColor(v)==Color.WHITE) {
//					visita.setColor(v, Color.GRAY);
//					visita.setParent(v, frangia.get(0));
//					//visito v
//					System.out.println("Sto visitando il vertice "+v+".\n");
//					//fine visita	
//					frangia.add(0, v);
//				}
//			}	
//			visita.setColor(frangia.get(0), Color.BLACK);
//			frangia.remove(0);
//		}
		
		return visita;
	}

	@Override
	public VisitForest getDFSTOTForest(int startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
		if(this.containsVertex(startingVertex)==false) 
			throw new IllegalArgumentException();
		
		//inizializzo ptr
		Integer[] ptr=new Integer[size];
		for(int i=0; i<size; i++) {
			if(adjList.get(i).size()==0)
				ptr[i]=null;
			else ptr[i]=0;
		}
		
		//algoritmo di visita
		int time=1; // tempo di visita
		VisitForest visita=new VisitForest(this, VisitType.DFS_TOT);
		LinkedList<Integer> frangia=new LinkedList<Integer>();
		visita.setColor(startingVertex, Color.GRAY); // comincio da startingVertex
		visita.setStartTime(startingVertex, time++);
		//visito startingVertex
		System.out.println("Sto visitando il vertice "+startingVertex+".\n");
		//fine visita	
		frangia.add(startingVertex); //push 
		Integer v;
		while(!frangia.isEmpty()) {
			while(ptr[frangia.get(0)]!=null) {
				v=adjList.get(frangia.get(0)).get(ptr[frangia.get(0)]);
				ptr[frangia.get(0)]++;
				if(ptr[frangia.get(0)]==adjList.get(frangia.get(0)).size())
					ptr[frangia.get(0)]=null;
				if(visita.getColor(v)==Color.WHITE) {
					visita.setColor(v, Color.GRAY);
					visita.setStartTime(v, time++);
					visita.setParent(v, frangia.get(0));
					//visito v
					System.out.println("Sto visitando il vertice "+v+".\n");
					//fine visita	
					frangia.add(0, v);
				}
			}	
			visita.setColor(frangia.get(0), Color.BLACK);
			visita.setEndTime(frangia.get(0), time++);
			frangia.remove(0);
			
			//se non trovo vertici adiacenti ad U ne scelgo uno non adiacente e lo aggiungo alla frangia
			if(frangia.isEmpty()) {
			    for(int i=0; i<adjList.size(); i++)
			    	if(visita.getColor(i)==Color.WHITE) {
			    		visita.setColor(i, Color.GRAY);
			    		visita.setStartTime(i, time++);
			    		//visito v
						System.out.println("Sto visitando il vertice "+i+".\n");
						//fine visita	
			    		frangia.add(0, i); //push
			    		break;
			    	}
			}
		}
		
		return visita;
	}

	@Override 
	public VisitForest getDFSTOTForest(int[] vertexOrdering) throws UnsupportedOperationException, IllegalArgumentException {
		
		
		/*
		 *  questa lista mi serve per controllare che vertexOrdering contenga il giusto numero di elementi 
		 *  e che contenga TUTTI gli elementi
		 */		
		List<Integer> temp=new LinkedList<Integer>();
		
		if(vertexOrdering.length!=size) 
			throw new IllegalArgumentException("Il vettore passato non contiene il giusto numero di elementi.");
		for(int i=0; i<size; i++) {
		    if(temp.contains(vertexOrdering[i]))
		    	throw new IllegalArgumentException("Il vettore passato contiene più volte lo stesso elemento.");
		    else temp.add(vertexOrdering[i]);
		}			
		
		//inizializzo ptr
		Integer[] ptr=new Integer[size];
		for(int i=0; i<size; i++) {
			if(adjList.get(i).size()==0)
				ptr[i]=null;
			else ptr[i]=0;
		}
		
		//algoritmo di visita
		int time=1; // tempo di visita
		VisitForest visita=new VisitForest(this, VisitType.DFS_TOT);
		LinkedList<Integer> frangia=new LinkedList<Integer>();
		
		for(Integer u : vertexOrdering) {
			if(visita.getColor(u)==Color.WHITE) {
				visita.setColor(u, Color.GRAY);
				visita.setStartTime(u, time++);
				//visito u
				System.out.println("Sto visitando il vertice "+u+".\n");
				//fine visita	
				frangia.add(u); //push
				Integer v;
				while(!frangia.isEmpty()) {
					while(ptr[frangia.get(0)]!=null) {
						v=adjList.get(frangia.get(0)).get(ptr[frangia.get(0)]);
						ptr[frangia.get(0)]++;
						if(ptr[frangia.get(0)]==adjList.get(frangia.get(0)).size())
							ptr[frangia.get(0)]=null;
						if(visita.getColor(v)==Color.WHITE) {
							visita.setColor(v, Color.GRAY);
							visita.setStartTime(v, time++);
							visita.setParent(v, frangia.get(0));
							//visito v
							System.out.println("Sto visitando il vertice "+v+".\n");
							//fine visita	
							frangia.add(0, v);
						}
					}	
					visita.setColor(frangia.get(0), Color.BLACK);
					visita.setEndTime(frangia.get(0), time++);
					frangia.remove(0);
				}
			}
		}
				
		return visita;
	}

	@Override
	public String[] topologicalSort() throws UnsupportedOperationException {
		if(this.isDAG()==false) throw new UnsupportedOperationException();
		
		VisitForest visita=new VisitForest(this, VisitType.DFS_TOT);
		String[] ord=new String[size];
		int[] t=new int[] {size-1};
		int[] time=new int[] {1};
		
		for(int i=0; i<size; i++) {
			if(visita.getColor(verticesList.get(i))==Color.WHITE)
				DFSTopological(visita, verticesList.get(i), ord, t, time);
		}
		return ord;
	}

	private void DFSTopological(VisitForest visita, String u, String[] ord, int[] t, int[] time) {
		visita.setColor(u, Color.GRAY);
		visita.setStartTime(u, time[0]++);
		
		for(String v : this.getAdjacent(u)) {
			if(visita.getColor(v)==Color.WHITE) {
				visita.setParent(v, u);
				DFSTopological(visita, v, ord, t, time);
			}
		}
		visita.setColor(u, Color.BLACK);
		visita.setEndTime(u, time[0]++);
		ord[t[0]]=u;
		t[0]--;
	}
	
	@Override
	public Set<Set<String>> stronglyConnectedComponents() throws UnsupportedOperationException {
		Set<Set<String>> componentiFortementeConnesse=new HashSet<Set<String>>();
		
		// 1. Visito G con DFS e costruisco un vettore di vertici in ordine decrescente dei tempi di fine visita
		VisitForest primaVisita=this.getDFSTOTForest(0);
		int[] tempiDecr=new int[size];
		int indice=0; //indice di tempiDecr
		for(int i=size*2; i>0; i--) {			
			for(int v=0; v<size; v++) {
				if(primaVisita.getEndTime(verticesList.get(v))==i) {
					tempiDecr[indice++]=v;
					break;
				}
			}
		}	
		
		// 2. Costruisco G trasposto
		AdjListDir grafoTrasposto=new AdjListDir();
		for(int i=0; i<size; i++) {
			grafoTrasposto.addVertex(verticesList.get(i));
		}		
		
		for(int i=0; i<size; i++) {
			for(int v=0; v<size; v++) {
				if(this.isAdjacent(verticesList.get(i), verticesList.get(v))) {
					grafoTrasposto.addEdge(verticesList.get(i), verticesList.get(v));
				}
			}
		}
		
		// 3. Visito G trasposto con DFS considerando i vertici nell'ordine trovato al passo 1
		VisitForest secondaVisita=grafoTrasposto.getDFSTOTForest(tempiDecr);
		
		// riempo la lista di ritorno
		for(String u : secondaVisita.getRoots()) {
			Set<String> alberoDiVisita=trovaAlbero(secondaVisita, u);
			componentiFortementeConnesse.add(alberoDiVisita);
		}
		
		return componentiFortementeConnesse;
	}

	private Set<String> trovaAlbero(VisitForest visita, String u){
		Set<String> alberoDiVisita=new HashSet<String>();
		
		alberoDiVisita.add(u);
		trovaAlberoRicorsivo(visita, alberoDiVisita, u);
		
		return alberoDiVisita;
	}
	
	private void trovaAlberoRicorsivo(VisitForest visita, Set<String> alberoDiVisita, String u) {
		for(int i=0; i<size; i++) {
			if(visita.getPartent(verticesList.get(i))==u) {
				alberoDiVisita.add(verticesList.get(i));
				trovaAlberoRicorsivo(visita, alberoDiVisita, verticesList.get(i));
			}
		}
	}
	
	@Override
	public Set<Set<String>> connectedComponents() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

}