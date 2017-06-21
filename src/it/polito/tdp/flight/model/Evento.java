package it.polito.tdp.flight.model;

public class Evento implements Comparable<Evento>{
	
	private int numPasseggero;
	private Airport a;
	private double time;
	private int count;
	
	public Evento(int numPasseggero, Airport a, double time, int count) {
		super();
		this.numPasseggero = numPasseggero;
		this.a = a;
		this.time = time;
		this.count = count;
	}

	public int getNumPasseggero() {
		return numPasseggero;
	}

	public void setNumPasseggero(int numPasseggero) {
		this.numPasseggero = numPasseggero;
	}

	public Airport getA() {
		return a;
	}

	public void setA(Airport a) {
		this.a = a;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public int getCount() {
		return count;
	}

	public void setCount() {
		this.count++;
	}

	@Override
	public int compareTo(Evento e) {
		return Double.compare(this.time, e.time);
	}

	@Override
	public String toString() {
		return numPasseggero+"";
	}
}
