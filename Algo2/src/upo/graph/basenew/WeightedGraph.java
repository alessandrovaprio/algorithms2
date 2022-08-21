// 
// Decompiled by Procyon v0.5.36
// 

package upo.graph.basenew;

import java.util.NoSuchElementException;

public interface WeightedGraph extends Graph
{
    public static final double defaultEdgeWeight = 1.0;
    
    double getEdgeWeight(final String p0, final String p1) throws IllegalArgumentException, NoSuchElementException;
    
    void setEdgeWeight(final String p0, final String p1, final double p2) throws IllegalArgumentException, NoSuchElementException;
    
    WeightedGraph getBellmanFordShortestPaths(final String p0) throws UnsupportedOperationException, IllegalArgumentException;
    
    WeightedGraph getDijkstraShortestPaths(final String p0) throws UnsupportedOperationException, IllegalArgumentException;
    
    WeightedGraph getPrimMST(final String p0) throws UnsupportedOperationException, IllegalArgumentException;
    
    WeightedGraph getKruskalMST() throws UnsupportedOperationException;
    
    WeightedGraph getFloydWarshallShortestPaths() throws UnsupportedOperationException;
}