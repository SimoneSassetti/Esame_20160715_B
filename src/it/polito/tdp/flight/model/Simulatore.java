package it.polito.tdp.flight.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

public class Simulatore {
	
	private SimpleDirectedWeightedGraph<Airport, DefaultWeightedEdge> grafo;
	private int passeggeri;
	private Map<Airport,Integer> aeroporti;
	private Map<Integer, Airport> mappaPorti;
	private PriorityQueue<Evento> coda;
	
	public Simulatore(SimpleDirectedWeightedGraph<Airport, DefaultWeightedEdge> grafo, int pass, Map<Integer, Airport> mappaPorti) {
		this.grafo=grafo;
		passeggeri=pass;
		this.mappaPorti=mappaPorti;
		
		aeroporti=new HashMap<Airport,Integer>();
		for(Airport a: mappaPorti.values()){
			aeroporti.put(a, 0);
		}
		
		coda=new PriorityQueue<Evento>();
	}
	
	public void inserisci(){
		for(Airport a: mappaPorti.values()){
			if(a.getName().compareTo("Charles De Gaulle")==0){
				aeroporti.put(a, passeggeri/2);
				for(int i=0; i<passeggeri/2;i++){
					Evento e =new Evento(i,a,7.0,0);
					coda.add(e);
				}
			}
			if(a.getName().compareTo("John F Kennedy Intl")==0){
				aeroporti.put(a, passeggeri/2);
				for(int i=0; i<passeggeri/2;i++){
					Evento e =new Evento(i,a,7.0,0);
					coda.add(e);
				}
			}
		}
	}

	public void run(){
		
		while(!coda.isEmpty()){
			
			Evento e =coda.poll();
			
			if(e.getCount()<5){
				
				double time=e.getTime();
				int count=e.getCount();
				
				Random r=new Random();
				List<Airport> temp=Graphs.successorListOf(grafo, e.getA());
				if(!temp.isEmpty()){
					
					Airport a=temp.get(r.nextInt(temp.size()));
					
					double durata=LatLngTool.distance(e.getA().getCoord(), a.getCoord(), LengthUnit.KILOMETER)/900.0;
					
					time=time+durata;
					
					time=Math.ceil(time);
					
					if(time%2==0){
						time=time+1;
					}
					if(time<7 || time>23){
						time=7;
					}
					int passeggeri=aeroporti.get(a);
					aeroporti.put(a, passeggeri+1);
					Evento nuovo=new Evento(e.getNumPasseggero(),a,time,count+1);
					coda.add(nuovo);
				}
			}
		}
	}
	
	public List<Risultato> getRisultati(){
		List<Risultato> lista=new ArrayList<>();
		
		for(Airport a: aeroporti.keySet()){
			if(aeroporti.get(a)>0){
				Risultato r=new Risultato(a,aeroporti.get(a));
				lista.add(r);
			}
		}
		Collections.sort(lista);
		
		return lista;
	}
}
