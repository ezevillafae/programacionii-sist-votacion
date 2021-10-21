package entidades;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
		if( !tipoMesa.equals(Definiciones.mayor65) ||
			!tipoMesa.equals(Definiciones.enfPreexistente) ||
			!tipoMesa.equals(Definiciones.trabajador) ||
			!tipoMesa.equals(Definiciones.general)) {
			return false;
		}
		
		return true;
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

}
