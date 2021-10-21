package entidades;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import estructurasdedatos.Tupla;

public class SistemaVotacionUNGS {
	private String nombreSistema;
	private HashMap<Integer, Votante> personasRegistradas;
	private Set<Mesa> mesas;
	
	public SistemaVotacionUNGS(String nombreSistema) {
		if(nombreSistema == null) {
			throw new RuntimeException();
		}
		this.nombreSistema = nombreSistema;
		this.personasRegistradas = new HashMap<Integer, Votante>();
		this.mesas = new HashSet<Mesa>();
	}
	
	public boolean estaRegistrado(int dni) {
		return this.personasRegistradas.containsKey(dni);
	}
	
	public void registrarVotante(int dni, String nombre, int edad, boolean enfPrevia, boolean trabaja) {
		//Verificacion edad contemplado en Contructor de Votante
		
		try {
			if(!estaRegistrado(dni))
				personasRegistradas.put(dni, new Votante(nombre,dni,edad,enfPrevia,trabaja));
			else
				throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private boolean verificarMesa(String tipoMesa)   {
		if( tipoMesa.equals(Definiciones.mayor65) ||
			tipoMesa.equals(Definiciones.enfPreexistente) ||
			tipoMesa.equals(Definiciones.trabajador) ||
			tipoMesa.equals(Definiciones.general)) {
			return true;
		}
		return false;
	}
	
	private boolean verificarPresidentDeMesa(int dni) {
		if(!estaRegistrado(dni))
			return false;
		if(this.personasRegistradas.get(dni).tieneTurno())
			return false;
		return true;
	}
	
	public int agregarMesa(String tipoMesa, int dni){
		if(!verificarMesa(tipoMesa) || !verificarPresidentDeMesa(dni)) {
			throw new RuntimeException();
		}
		Votante presidente = this.personasRegistradas.get(dni);
		Mesa mesa_nueva = null;
		
		if(tipoMesa.equals(Definiciones.enfPreexistente)) {
			mesa_nueva = new MesaEnfermedadPreexistente(presidente);
			this.mesas.add(mesa_nueva);
		}else if(tipoMesa.equals(Definiciones.mayor65)){
			mesa_nueva = new MesaMayores(presidente);
			this.mesas.add(mesa_nueva);
		}else if(tipoMesa.equals(Definiciones.trabajador)) {
			mesa_nueva = new MesaTrabajadores(presidente);
			this.mesas.add(mesa_nueva);
		}else {
			mesa_nueva = new MesaGenerica(presidente);
			this.mesas.add(mesa_nueva);
		}
		
		return mesa_nueva.dameCodigoMesa();
		
	}
	
	private Tupla<Integer, Integer> asignarMesaAVotante(Votante votante, String tipoDeMesa){
		if(!verificarMesa(tipoDeMesa) || votante == null) {
			throw new RuntimeException();
		}
		
		Tupla<Integer,Integer> turno = null;
		for (Mesa mesa : mesas) {
			if(tipoDeMesa.equals(Definiciones.mayor65)) {
				if(mesa instanceof MesaMayores)
					turno = mesa.dameTurno();
			}else if(tipoDeMesa.equals(Definiciones.enfPreexistente)) {
				if(mesa instanceof MesaEnfermedadPreexistente)
					turno = mesa.dameTurno();
			}else if(tipoDeMesa.equals(Definiciones.trabajador)) {
				if(mesa instanceof MesaTrabajadores)
					turno = mesa.dameTurno();
			}else if(tipoDeMesa.equals(Definiciones.general)) {
				if(mesa instanceof MesaGenerica)
					turno = mesa.dameTurno();
			}
		}
		return turno;
	}
	
	/**
	 * -Si es trabajador vota en mesa trabajadores
	 * -Si es mayor y enfermedadPrex, en cualquier mesa con disponibilidad
	 * -Sino, generica
	 * 
	 * @param dni
	 * @return
	 */
	public Tupla<Integer,Integer> asignarTurno(int dni){
		if(!estaRegistrado(dni)) {
			throw new RuntimeException();
		}
		
		Votante votante = this.personasRegistradas.get(dni);
		if(votante.tieneTurno()) {
			return votante.consultarTurno();
		}
		
		Tupla<Integer, Integer> turno = null;
		if(votante.tieneCertificadoTrabajo()) {
			turno = asignarMesaAVotante(votante, Definiciones.trabajador);
		}else if(votante.esMayorDe65() && votante.tieneEnfermedadPreexistente()) {
			turno = asignarMesaAVotante(votante, Definiciones.mayor65);
			if(turno == null)
				turno = asignarMesaAVotante(votante, Definiciones.enfPreexistente);
		}else if(votante.esMayorDe65()) {
			turno = asignarMesaAVotante(votante, Definiciones.mayor65);
		}else if(votante.tieneEnfermedadPreexistente()) {
			turno = asignarMesaAVotante(votante, Definiciones.enfPreexistente);
		}else {
			turno = asignarMesaAVotante(votante, Definiciones.general);
		}
		
		return turno;
	}
	
	public int asignarTurnos() {
		int cantTurnosAsignados = 0;
		Set<Integer> dniVotantes = this.personasRegistradas.keySet();
		for (Integer dniVotante : dniVotantes) {
			if(consultarTurno(dniVotante)==null) { //Si el votante no tiene turno
				if(asignarTurno(dniVotante)!=null) //Si se asignó un turno al votante
					cantTurnosAsignados++;
			}
		}
		return cantTurnosAsignados;
	}
	
	
	public Tupla<Integer, Integer> consultarTurno(int dni) {
		if(!estaRegistrado(dni)) {
			throw new RuntimeException();
		}
		return this.personasRegistradas.get(dni).consultarTurno();
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.nombreSistema).append("\n");
		for(Mesa m : this.mesas) {
			sb.append(m);
		}
		
		return sb.toString();
	}

}
