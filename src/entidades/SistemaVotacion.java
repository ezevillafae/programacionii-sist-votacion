package entidades;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import estructurasdedatos.Tupla;

public class SistemaVotacion {
	private String nombreSistema;
	private HashMap<Integer, Votante> personasRegistradas;
	private Set<Mesa> mesas;
	
	public SistemaVotacion(String nombreSistema) {
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
		//Verificacion edad y nombre contemplado en Contructor de Votante
			if(estaRegistrado(dni))
				throw new RuntimeException();	
			
			personasRegistradas.put(dni, new Votante(nombre,dni,edad,enfPrevia,trabaja));
	}
	
	private boolean verificarTipoMesa(String tipoMesa)   {
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
		if(!verificarTipoMesa(tipoMesa) || !verificarPresidentDeMesa(dni)) {
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
	
	private Tupla<Integer, Integer> buscarTurnoEnMesas(String tipoDeMesa){
		if(!verificarTipoMesa(tipoDeMesa)) {
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
			turno = buscarTurnoEnMesas(Definiciones.trabajador);
		}else if(votante.esMayorDe65() && votante.tieneEnfermedadPreexistente()) {
			turno = buscarTurnoEnMesas(Definiciones.mayor65);
			if(turno == null)
				turno = buscarTurnoEnMesas(Definiciones.enfPreexistente);
		}else if(votante.esMayorDe65()) {
			turno = buscarTurnoEnMesas(Definiciones.mayor65);
		}else if(votante.tieneEnfermedadPreexistente()) {
			turno = buscarTurnoEnMesas(Definiciones.enfPreexistente);
		}else {
			turno = buscarTurnoEnMesas(Definiciones.general);
		}
		
		if(turno != null) {
			votante.asignarTurno(turno.getX(), turno.getY());
			return turno;
		}else {
			return turno;
		}
	}
	
	public int asignarTurnos() {
		int cantTurnosAsignados = 0;
		Set<Integer> dniVotantes = this.personasRegistradas.keySet();
		for (Integer dniVotante : dniVotantes) {
			if(consultaTurno(dniVotante)==null) { //Si el votante no tiene turno
				if(asignarTurno(dniVotante)!=null) //Si se asignó un turno al votante
					cantTurnosAsignados++;
			}
		}
		return cantTurnosAsignados;
	}
	
	
	public Tupla<Integer, Integer> consultaTurno(int dni) {
		if(!estaRegistrado(dni)) {
			throw new RuntimeException();
		}
		return this.personasRegistradas.get(dni).consultarTurno();
	}
	
	public boolean votar(int dni){
        if (!estaRegistrado(dni))
            throw new RuntimeException();
        
        Votante votante= this.personasRegistradas.get(dni);
        if (votante.consultarVoto())
            return false;

        votante.confirmarVoto();
        return true;
    }
	
	public int votantesConTurno(String tipoMesa) {
		if (!verificarTipoMesa(tipoMesa))
			throw new RuntimeException("Tipo de mesa inválido");

		int cantidadDeVotantes = 0;

		for (Mesa mesa : mesas) {
			if (tipoMesa.equals(Definiciones.mayor65)) {
				if (mesa instanceof MesaMayores)
					cantidadDeVotantes += mesa.cantidadDeVotantes();
			} else if (tipoMesa.equals(Definiciones.enfPreexistente)) {
				if (mesa instanceof MesaEnfermedadPreexistente)
					cantidadDeVotantes += mesa.cantidadDeVotantes();
			} else if (tipoMesa.equals(Definiciones.trabajador)) {
				if (mesa instanceof MesaTrabajadores)
					cantidadDeVotantes += mesa.cantidadDeVotantes();
			} else if (mesa instanceof MesaGenerica) {
				cantidadDeVotantes += mesa.cantidadDeVotantes();
			}
		}
		return cantidadDeVotantes;
	}
	
	private Mesa buscarMesa(int numMesa) {
		Mesa m = null;
		for (Mesa mesa : mesas) {
			if(mesa.dameCodigoMesa()==numMesa)
				m = mesa;
		}
		return m;
	}
	
	/**
	 * Llena cada clave del Map con cada franja horaria de una mesa 
	 * 
	 * @param lista
	 * @param mesa
	 */
	private void llenarFranjasHorarias(Map<Integer,List<Integer>> lista, Mesa mesa) {
		Set<Integer> franjas = mesa.getFranjasHorarias().keySet();
		for (Integer franja : franjas) {
			lista.put(franja, new LinkedList<Integer>());
		}
	}
	
	/**
	 * Llena la lista con votantes pertenecientes a la mesa numMesa, dependiendo de que franja
	 * horaria tenga asignada el votante
	 * 
	 * @param lista
	 * @param numMesa
	 */
	private void llenarListaConVotantesEnMesa(Map<Integer,List<Integer>> lista,int numMesa) {
		Set<Integer> dniVotantes = this.personasRegistradas.keySet();
		Votante votante = null;
		Tupla<Integer,Integer> turno = null;
		for (Integer dniVotante : dniVotantes) {
			votante = this.personasRegistradas.get(dniVotante);
			if(votante.tieneTurno()) {
				turno = votante.consultarTurno();
				if(turno.getX() == numMesa) {
					lista.get(turno.getY()).add(dniVotante);
				}
			}
		}
	}
	
	public Map<Integer,List<Integer>> asignadosAMesa(int numMesa){
		Mesa m = buscarMesa(numMesa);
		
		if(m==null) 
			throw new RuntimeException();
		
		Map<Integer,List<Integer>> lista = new HashMap<Integer,List<Integer>>();
		llenarFranjasHorarias(lista, m);
		llenarListaConVotantesEnMesa(lista, numMesa);
		
		return lista;
	}
	
	
	public List<Tupla<String, Integer>> sinTurnoSegunTipoMesa(){
		int cantidadMayores = 0;
		int cantidadGenerica = 0;
		int cantidadEnfPreexistente = 0;
		int cantidadTrabajadores = 0;
		
		Set<Integer> dniVotantes = this.personasRegistradas.keySet();
		Votante votante = null;
		List<Tupla<String,Integer>> lista = new LinkedList<Tupla<String,Integer>>();
		
		for (Integer dniVotante : dniVotantes) {
			votante = this.personasRegistradas.get(dniVotante);
			if(!votante.tieneTurno()) {
				if(votante.tieneCertificadoTrabajo()) {
					cantidadTrabajadores++;
				}else if(votante.tieneEnfermedadPreexistente()) {
					cantidadEnfPreexistente++;
				}else if(votante.esMayorDe65()) {
					cantidadMayores++;
				}else {
					cantidadGenerica++;
				}
			}	
		}
		
		lista.add(new Tupla<String, Integer>(Definiciones.trabajador, cantidadTrabajadores));
		lista.add(new Tupla<String, Integer>(Definiciones.general, cantidadGenerica));
		lista.add(new Tupla<String, Integer>(Definiciones.mayor65, cantidadMayores));
		lista.add(new Tupla<String, Integer>(Definiciones.enfPreexistente, cantidadEnfPreexistente));

		return lista;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		List<Tupla<String, Integer>> sinTurno= sinTurnoSegunTipoMesa();
		Set<Integer> dniVotantes= this.personasRegistradas.keySet();
		Votante votante = null;
		
		sb.append(this.nombreSistema).append("\n");
		sb.append("Votantes sin turno:  \n");
		
		for (Tupla<String,Integer> tipoMesa : sinTurno) {
			sb.append(tipoMesa).append("\n");
		}
		
		sb.append("Votantes con turno: \n"); // devolver dni votantes, turno y si voto o no 
		for (Integer dni : dniVotantes) {
			votante = this.personasRegistradas.get(dni);
			if (votante.tieneTurno()) {
				sb.append("DNI: ").append(dni).append("Turno: ").append(votante.consultarTurno());
				sb.append(" Realizo Voto: ").append(votante.consultarVoto()).append("\n");
			}
		}
		
		for (Mesa mesa : this.mesas) {
			sb.append(mesa);
		}
		return sb.toString();

	}

}
