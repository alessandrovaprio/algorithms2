// 
// Decompiled by Procyon v0.5.36
// 

package upo.graph.basenew;

import java.util.Set;
import java.util.NoSuchElementException;

public interface Graph
{
    int getVertexIndex(final String p0);
    
    String getVertexLabel(final Integer p0);
    
    int addVertex(final String p0);
    
    boolean containsVertex(final String p0);
    
    void removeVertex(final String p0) throws NoSuchElementException;
    
    void addEdge(final String p0, final String p1) throws IllegalArgumentException;
    
    boolean containsEdge(final String p0, final String p1) throws IllegalArgumentException;
    
    void removeEdge(final String p0, final String p1) throws IllegalArgumentException, NoSuchElementException;
    
    Set<String> getAdjacent(final String p0) throws NoSuchElementException;
    
    boolean isAdjacent(final String p0, final String p1) throws IllegalArgumentException;
    
    int size();
    
    boolean isDirected();
    
    boolean isCyclic();
    
    boolean isDAG();
    
    VisitForest getBFSTree(final String p0) throws UnsupportedOperationException, IllegalArgumentException;
    
    VisitForest getDFSTree(final String p0) throws UnsupportedOperationException, IllegalArgumentException;
    
    VisitForest getDFSTOTForest(final String p0) throws UnsupportedOperationException, IllegalArgumentException;
    
    VisitForest getDFSTOTForest(final String[] p0) throws UnsupportedOperationException, IllegalArgumentException;
    
    String[] topologicalSort() throws UnsupportedOperationException;
    
    Set<Set<String>> stronglyConnectedComponents() throws UnsupportedOperationException;
    
    Set<Set<String>> connectedComponents() throws UnsupportedOperationException;
    
    boolean equals(final Object p0);
}