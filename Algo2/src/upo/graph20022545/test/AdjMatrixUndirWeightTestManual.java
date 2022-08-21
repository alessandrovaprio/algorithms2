package upo.graph20022545.test;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

import upo.graph20022545.AdjMatrixUndirWeight;


public class AdjMatrixUndirWeightTestManual {

	public static void main(String[] args) {
		
		int x=0;
	    AdjMatrixUndirWeight grafo = new AdjMatrixUndirWeight();      //creo un grafo
		System.out.println("Inserire un comando: \n 1. Aggiungi vertice \n 2. Stampa grafo: \n 3. Rimuovi vertice: \n 4. Cerca vertice: \n 5. Aggiungi arco: \n "
				+ "6. Rimuovi arco: \n 7. Cerca arco: \n 8. Stampa adiacenti: \n 9. È adiacente? \n 10. Modifica il peso di un arco: \n "
				+ "11. Stampa il peso di un arco: \n 12. Effettua una visita singola in profondità: \n 13. Effettua una visita totale in profondità: \n 14. Il grafo è ciclico? \n 15. Componenti connesse: \n -1. Esci:\n");
		
		Scanner tastiera=new Scanner(System.in);
		
		do {
			System.out.println("Inserire un comando: ");
			try {
				tastiera=new Scanner(System.in);
				x=tastiera.nextInt();
			} catch(InputMismatchException e) {
				System.out.println("Inserire un comando corretto.\n");
				continue;
			}
	    
			if(x==1) {
                System.out.println("Inserire vertice.\n");
                Scanner input=new Scanner(System.in);
                String vertex = input.nextLine();
				grafo.addVertex(vertex);
				System.out.println("Aggiunto vertice " + (grafo.size()-1) +"\n" );
			}
	    
			if(x==2) {
				System.out.println("Dimensione del grafo: " + grafo.size() +"\n");
				grafo.stampa();
			}
	    
			if(x==3) {
				String v;
				try {
					System.out.println("Quale vertice vuoi eliminare?: ");
					Scanner input=new Scanner(System.in);
					v=input.nextLine();
				} catch(InputMismatchException e) {
					System.out.println("Inserire un comando corretto.");
					continue;
				}
				grafo.removeVertex(v);
				System.out.println("Verice " +v+ " eliminato correttamente.\n");
			}
	    
			if(x==4) {
				String v;
				try {
					System.out.println("Quale vertice vuoi cercare nel grafo?: ");
					v=tastiera.nextLine();
				} catch(InputMismatchException e) {
					System.out.println("Inserire un comando corretto.");
					continue;
				}
				boolean b=grafo.containsVertex(v);
				if(b) System.out.println("Il vertice è presente nel grafo "); 
				else System.out.println("Il vertice cercato non è presente nel grafo "); 
			}
	    
			if(x==5) {
				System.out.println("Tra quali vertici vuoi creare un arco?\n");
				String a, b;
				try {
					System.out.println("Primo vertice?: ");
					Scanner input=new Scanner(System.in);
					a=input.nextLine();
				} catch(InputMismatchException e) {
					System.out.println("Inserire un comando corretto.");
					continue;
				}
				try {
					System.out.println("Secondo vertice?: ");
					Scanner input=new Scanner(System.in);
					b=input.nextLine();
				} catch(InputMismatchException e) {
					System.out.println("Inserire un comando corretto.");
					continue;
				}
				grafo.addEdge(a,b);
				System.out.println("Aggiunto l'arco ("+a+", "+b+").\n");
			}
	    
			if(x==6) {
				System.out.println("Tra quali vertici vuoi rimuovere l'arco?");
				String a, b;
				try {
					System.out.println("Primo vertice?: ");
					a=tastiera.nextLine();
				} catch(InputMismatchException e) {
					System.out.println("Inserire un comando corretto.");
					continue;
				}
				try {
					System.out.println("Secondo vertice?: ");
					b=tastiera.nextLine();
				} catch(InputMismatchException e) {
					System.out.println("Inserire un comando corretto.");
					continue;
				}
				grafo.removeEdge(a,b);

				System.out.println("L'arco ("+a+", "+b+") è stato eliminato correttamente.\n");
			}
			
			if(x==7) {
				System.out.println("Tra quali vertici vuoi cercare l'arco?");
				String a, b;
				
				try {
					System.out.println("Primo vertice?: ");
					a=tastiera.nextLine();
				} catch(InputMismatchException e) {
					System.out.println("Inserire un comando corretto.");
					continue;
				}
				try {
					System.out.println("Secondo vertice?: ");
					b=tastiera.nextLine();
				} catch(InputMismatchException e) {
					System.out.println("Inserire un comando corretto.");
					continue;
				}
				
				if(grafo.containsEdge(a, b))
					System.out.println("L'arco ("+a+", "+b+") è presente nel grafo.\n");
				else 
					System.out.println("L'arco ("+a+", "+b+") non è presente nel grafo.\n");
			}
			
			if(x==8) {					
				String a;
				
				try {
					System.out.println("Di quale vertice vuoi stampare i suoi vertici adiacenti?");
					a=tastiera.nextLine();
				} catch(InputMismatchException e) {
					System.out.println("Inserire un comando corretto.");
					continue;
				}
				
				Set<String> adiacenti=grafo.getAdjacent(a);
				
				System.out.println("Vertici adiacenti: "+adiacenti);
			}
			
			if(x==9) {
				System.out.println("Tra quali vertici vuoi controllare l'adiacenza?");
				String a, b;
				
				try {
					System.out.println("Primo vertice?: ");
					a=tastiera.nextLine();
				} catch(InputMismatchException e) {
					System.out.println("Inserire un comando corretto.");
					continue;
				}
				try {
					System.out.println("Secondo vertice?: ");
					b=tastiera.nextLine();
				} catch(InputMismatchException e) {
					System.out.println("Inserire un comando corretto.");
					continue;
				}
				
				if(grafo.isAdjacent(b, a))
					System.out.println("I vertici "+b+" e "+a+" sono adiacenti.\n");
				else
					System.out.println("I vertici "+b+" e "+a+" non sono adiacenti.\n");
			}
			
			if(x==10) {
				System.out.println("Inserire i verici dell'arco di cui voui modificarne il peso.");
				String a, b;
				double weight;
				
				try {
					System.out.println("Primo vertice?: ");
					a=tastiera.nextLine();
				} catch(InputMismatchException e) {
					System.out.println("Inserire un comando corretto.");
					continue;
				}
				try {
					System.out.println("Secondo vertice?: ");
					b=tastiera.nextLine();
				} catch(InputMismatchException e) {
					System.out.println("Inserire un comando corretto.");
					continue;
				}
				
				try {
					System.out.println("Inserire il peso che si vuole attribuire all'arco: ");
					weight=tastiera.nextDouble();
				} catch(InputMismatchException e) {
					System.out.println("Inserire un comando corretto.");
					continue;
				}
				
				grafo.setEdgeWeight(a, b, weight);
				
				System.out.println("Il peso dell'arco ("+a+", "+b+") è " + weight);
			}
			
			if(x==11) {
				System.out.println("Inserire i verici dell'arco di cui si vuole conoscere il peso.");
				String a, b;
				
				try {
					System.out.println("Primo vertice?: ");
					a=tastiera.nextLine();
				} catch(InputMismatchException e) {
					System.out.println("Inserire un comando corretto.");
					continue;
				}
				try {
					System.out.println("Secondo vertice?: ");
					b=tastiera.nextLine();
				} catch(InputMismatchException e) {
					System.out.println("Inserire un comando corretto.");
					continue;
				}
				
				System.out.println("Il peso dell'arco ("+a+", "+b+") è "+grafo.getEdgeWeight(a, b));
			}
			
			if(x==12) {
				String startingVertex;
				
				try {
					System.out.println("Da quale vertice vuoi far partire la visita?");
					startingVertex=tastiera.nextLine();
				} catch(InputMismatchException e) {
					System.out.println("Inserire un comando corretto.");
					continue;
				}
				
				grafo.getDFSTree(startingVertex);
			}
			
			if(x==13) {
				String startingVertex;
				
				try {
					System.out.println("Da quale vertice vuoi far partire la visita?");
					startingVertex=tastiera.nextLine();
				} catch(InputMismatchException e) {
					System.out.println("Inserire un comando corretto.");
					continue;
				}
				
				grafo.getDFSTOTForest(startingVertex);
			}
			
			if(x==14) {
				if(grafo.isCyclic()) System.out.println("Il grafo è aciclico.\n");
				else System.out.println("Il grafo è aciclico.\n");
			}
			
			if(x==15) {
				Set<Set<String>> compConn=grafo.connectedComponents();
				
				System.out.println();
				System.out.println("Componenti connesse:");
				System.out.println();
				
				for(Set<String> temp : compConn) {
					for(String v : temp) {
						System.out.print(v+" ");
					}
					System.out.println();
					System.out.println();
				}
				
				System.out.println();
			}
			
		}  while(x!=-1);
		
		tastiera.close();
	}

}

