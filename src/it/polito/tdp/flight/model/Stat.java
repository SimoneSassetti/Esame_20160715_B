package it.polito.tdp.flight.model;

public class Stat implements Comparable<Stat>{
	
	private Airport a;
	private double dis;
	public Stat(Airport a, double dis) {
		super();
		this.a = a;
		this.dis = dis;
	}
	public Airport getA() {
		return a;
	}
	public void setA(Airport a) {
		this.a = a;
	}
	public double getDis() {
		return dis;
	}
	public void setDis(double dis) {
		this.dis = dis;
	}
	@Override
	public int compareTo(Stat s) {
		return Double.compare(this.dis, s.dis);
	}	
}
