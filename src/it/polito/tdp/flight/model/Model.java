package it.polito.tdp.flight.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flight.db.FlightDAO;

public class Model {
	
	private List<Airport> aeroporti;
	private Map<Integer,Airport> mappaPorti;
	private FlightDAO dao;
	private List<Route> rotte;
	private SimpleDirectedWeightedGraph<Airport,DefaultWeightedEdge> grafo;
	
	public Model(){
		dao=new FlightDAO();
	}
	
	public List<Airport> getPorti(){
		if(aeroporti==null){
			mappaPorti=new HashMap<>();
			aeroporti=dao.getAllAirports();
			for(Airport a: aeroporti){
				mappaPorti.put(a.getAirportId(), a);
			}
		}
		return aeroporti;
	}
	
	public List<Route> getRotte(Integer l){
		rotte=new ArrayList<>();
		
		rotte=dao.getRottePerDistanza(l,mappaPorti);
		
		return rotte;
	}
	
	public void creaGrafo(){
		grafo=new SimpleDirectedWeightedGraph<Airport,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//vertici
		Graphs.addAllVertices(grafo, aeroporti);
		
		//archi
		for(Route r: rotte){
			Airport s=r.getSource();
			Airport d=r.getDestination();
			double durataVolto=calcolaDurata(s,d);
			DefaultWeightedEdge arco=grafo.addEdge(s, d);
			if(arco!=null){
				grafo.setEdgeWeight(arco, durataVolto);
			}
		}	
	}

	private double calcolaDurata(Airport s, Airport d) {
		return LatLngTool.distance(s.getCoord(), d.getCoord(), LengthUnit.KILOMETER)/900.0;
	}
	
	public boolean isConnected(){
		ConnectivityInspector<Airport,DefaultWeightedEdge> ci=new ConnectivityInspector<Airport,DefaultWeightedEdge>(grafo);
		
		if(ci.isGraphConnected()){
			return true;
		}else
			return false;
	}
	
	public Airport aeroportoNonRaggiungibile(){
		List<Airport> nonRaggiungibili=new ArrayList<>(grafo.vertexSet());
		List<Stat> lista=new ArrayList<>();
		
		for(Airport a: grafo.vertexSet()){
			if(a.getName().compareTo("Los Angeles Intl")==0){
				nonRaggiungibili.removeAll(Graphs.successorListOf(grafo, a));
				nonRaggiungibili.remove(a);
				for(Airport porto: nonRaggiungibili){
					double dis=LatLngTool.distance(a.getCoord(), porto.getCoord(), LengthUnit.KILOMETER);
					Stat s=new Stat(porto,dis);
					lista.add(s);
				}
			}
		}
		Collections.sort(lista);
		
		return lista.get(0).getA();
	}

	public List<Risultato> simula(int pass) {
		Simulatore sim=new Simulatore(grafo,pass,mappaPorti);
		
		sim.inserisci();
		sim.run();
		return sim.getRisultati();
	}
}
