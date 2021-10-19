package entidades;


import estructurasdedatos.Tupla;

public class Votante extends Persona {
	private boolean certificadoTrabajo;
	private boolean presidenteDeMesa;
	private boolean voto;
	private Tupla<Integer, Integer> turno;
	
	public Votante(String nombre, int dni, int edad, boolean enfermedad, boolean certificadoTrabajo ) throws Exception {
		super(nombre, dni, edad, enfermedad);
		this.voto = false;
		this.turno = null;
		this.certificadoTrabajo=certificadoTrabajo;
		this.presidenteDeMesa = false;
	}
	
	public boolean esPresidenteDeMesa() {
		return this.presidenteDeMesa;
	}
	
	public void setPresidenteDeMesa() {
		if (!tieneTurno()) {
			this.presidenteDeMesa = true;
		}
	}

	public boolean tieneCertificadoTrabajo() {
		return this.certificadoTrabajo;
	}
	
	public void confirmarVoto() {
		if (tieneTurno()) {
			this.voto = true;
		}
	}
	
	public boolean tieneTurno() {
		return turno != null;
	}
	
	public boolean consultarVoto() {
		return this.voto;
	}
	
	public Tupla<Integer,Integer> asignarTurno(int codigoMesa, int horario) {
		if (!tieneTurno()) // advertencia pregunta 2 veces (sistema en asignarTurno)
			this.turno = new Tupla<>(codigoMesa,horario);
		return consultarTurno();
	}

	public Tupla<Integer,Integer> consultarTurno() {
		return this.turno;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Persona : {");
		sb.append("\n");
		sb.append("nombre= ").append(getNombre());
		sb.append("\n");
		sb.append("dni= ").append(getDni());
		sb.append("\n");
		sb.append("edad= ").append(getEdad());
		sb.append("\n");
		sb.append("enfermedad= ").append(tieneEnfermedadPreexistente());
		sb.append("\n");
		sb.append("votó= ").append(this.voto);
		sb.append("\n");
		sb.append("Es presidente de Mesa= ").append(this.presidenteDeMesa);
		sb.append("\n");
		sb.append("Turno= ").append(this.turno);
		sb.append("\n}\n");
		return sb.toString();
	}
	
}
