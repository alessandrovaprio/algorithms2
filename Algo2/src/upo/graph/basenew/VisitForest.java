// 
// Decompiled by Procyon v0.5.36
// 

package upo.graph.basenew;

import java.util.NoSuchElementException;
import java.util.HashSet;
import java.util.Set;

import upo.graph.basenew.Graph;

import java.util.Arrays;

public final class VisitForest
{
    private final Graph graph;
    public final VisitType visitType;
    private Color[] vertexColor;
    private Integer[] parent;
    private Double[] distance;
    private Integer[] startTime;
    private Integer[] endTime;
    
    public VisitForest(final Graph graph, final VisitType visitType) {
        this.graph = graph;
        this.visitType = visitType;
        this.initialize();
    }
    
    private void initialize() {
        Arrays.fill(this.vertexColor = new Color[this.graph.size()], Color.WHITE);
        Arrays.fill(this.parent = new Integer[this.graph.size()], null);
        Arrays.fill(this.distance = new Double[this.graph.size()], null);
        Arrays.fill(this.startTime = new Integer[this.graph.size()], -1);
        Arrays.fill(this.endTime = new Integer[this.graph.size()], -1);
    }
    
    public Set<String> getRoots() {
        final Set<String> res = new HashSet<String>();
        for (int i = 0; i < this.parent.length; ++i) {
            if (this.parent[i] == null) {
                res.add(this.graph.getVertexLabel(i));
            }
        }
        return res;
    }
    
    public Color getColor(final String vertex) throws NoSuchElementException {
        if (this.graph.getVertexIndex(vertex) >= this.graph.size()) {
            throw new NoSuchElementException("Il vertice di indice " + vertex + " non appartiene al grafo");
        }
        return this.vertexColor[this.graph.getVertexIndex(vertex)];
    }
    
    public void setColor(final String vertex, final Color color) throws NoSuchElementException, IllegalArgumentException {
        if (this.graph.getVertexIndex(vertex) >= this.graph.size()) {
            throw new NoSuchElementException("Il vertice di indice " + vertex + " non appartiene al grafo");
        }
        if (this.vertexColor[this.graph.getVertexIndex(vertex)].compareTo(color) > 0) {
            throw new IllegalArgumentException("Il colore di un vertice non puo' passare da GRAY a WHITE o da BLACK a GRAY o WHITE");
        }
        System.out.println("getVIndex "+this.graph.getVertexIndex(vertex));
        this.vertexColor[this.graph.getVertexIndex(vertex)] = color;
    }
    
    public String getPartent(final String vertex) throws NoSuchElementException {
        if (this.graph.getVertexIndex(vertex) >= this.graph.size()) {
            throw new NoSuchElementException("Il vertice di indice " + vertex + " non appartiene al grafo");
        }
        return this.graph.getVertexLabel(this.parent[this.graph.getVertexIndex(vertex)]);
    }
    
    public void setParent(final String vertex, final String parent) throws NoSuchElementException, IllegalArgumentException {
        if (this.graph.getVertexIndex(vertex) >= this.graph.size()) {
            throw new NoSuchElementException("Il vertice di indice " + vertex + " non appartiene al grafo");
        }
        if (this.graph.getVertexIndex(parent) >= this.graph.size()) {
            throw new NoSuchElementException("Il vertice di indice " + parent + " non appartiene al grafo");
        }
        this.parent[this.graph.getVertexIndex(vertex)] = this.graph.getVertexIndex(parent);
    }
    
    public Double getDistance(final String vertex) throws NoSuchElementException {
        if (this.graph.getVertexIndex(vertex) >= this.graph.size()) {
            throw new NoSuchElementException("Il vertice di indice " + vertex + " non appartiene al grafo");
        }
        return this.distance[this.graph.getVertexIndex(vertex)];
    }
    
    public void setDistance(final String vertex, final double distance) throws NoSuchElementException, IllegalArgumentException {
        if (this.graph.getVertexIndex(vertex) >= this.graph.size()) {
            throw new NoSuchElementException("Il vertice di indice " + vertex + " non appartiene al grafo");
        }
        this.distance[this.graph.getVertexIndex(vertex)] = distance;
    }
    
    public Integer getStartTime(final String vertex) throws NoSuchElementException {
        if (this.graph.getVertexIndex(vertex) >= this.graph.size()) {
            throw new NoSuchElementException("Il vertice di indice " + vertex + " non appartiene al grafo");
        }
        return this.startTime[this.graph.getVertexIndex(vertex)];
    }
    
    public void setStartTime(final String vertex, final Integer startTime) throws NoSuchElementException {
        if (this.graph.getVertexIndex(vertex) >= this.graph.size()) {
            throw new NoSuchElementException("Il vertice di indice " + vertex + " non appartiene al grafo");
        }
        this.startTime[this.graph.getVertexIndex(vertex)] = startTime;
    }
    
    public Integer getEndTime(final String vertex) throws NoSuchElementException {
        if (this.graph.getVertexIndex(vertex) >= this.graph.size()) {
            throw new NoSuchElementException("Il vertice di indice " + vertex + " non appartiene al grafo");
        }
        return this.endTime[this.graph.getVertexIndex(vertex)];
    }
    
    public void setEndTime(final String vertex, final Integer endTime) throws NoSuchElementException {
        if (this.graph.getVertexIndex(vertex) >= this.graph.size()) {
            throw new NoSuchElementException("Il vertice di indice " + vertex + " non appartiene al grafo");
        }
        this.endTime[this.graph.getVertexIndex(vertex)] = endTime;
    }
    
    public enum Color
    {
        WHITE("WHITE", 0), 
        GRAY("GRAY", 1), 
        BLACK("BLACK", 2);
        
        private Color(final String name, final int ordinal) {
        }
    }
    
    public enum VisitType
    {
        BFS("BFS", 0), 
        DFS("DFS", 1), 
        DFS_TOT("DFS_TOT", 2);
        
        private VisitType(final String name, final int ordinal) {
        }
    }
}