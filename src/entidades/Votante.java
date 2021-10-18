package entidades;

import estructurasdedatos.Tupla;

public class Votante extends Persona {
	private boolean certificadoTrabajo;
	private boolean presidenteDeMesa;
	private boolean voto;
	private Tupla<Integer, Integer> turno;
	
	public Votante(String nombre, int dni, int edad, boolean enfermedad ) throws Exception {
		super(nombre, dni, edad, enfermedad);
		this.voto = false;
		this.turno = null;
		this.presidenteDeMesa = false;
	}
	
	public boolean esPresidenteDeMesa() {
		return this.presidenteDeMesa;
	}
	
	public boolean tieneCertificadoTrabajo() {
		return this.certificadoTrabajo;
	}
	
	public void confirmarVoto() {
		this.voto = true;
	}
	
	public boolean tieneTurno() {
		return turno != null;
	}
	
	public boolean consultarVoto() {
		return this.voto;
	}
	
	public void asignarTurno(int codigoMesa, int horario) {
		this.turno = new Tupla<>(codigoMesa,horario);
	}
	
}
