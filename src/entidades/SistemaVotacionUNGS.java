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
	
	private boolean verificarMesa(String tipoMesa, int dni)   {
		if( !tipoMesa.equals(Fixture.INSTANCE.mayor65) ||
			!tipoMesa.equals(Fixture.INSTANCE.enfPreexistente) ||
			!tipoMesa.equals(Fixture.INSTANCE.trabajador) ||
			!tipoMesa.equals(Fixture.INSTANCE.general)) {
			return false;
		}
		
		if(!estaRegistrado(dni))
			return false;
		if(this.personasRegistradas.get(dni).tieneTurno())
			return false;
		
		return true;
	}
	
	public int agregarMesa(String tipoMesa, int dni) throws Exception {
		if(!verificarMesa(tipoMesa, dni)) {
			throw new Exception();
		}
		Votante presidente = this.personasRegistradas.get(dni);
		Mesa mesa_nueva = null;
		
		if(tipoMesa.equals(Fixture.INSTANCE.enfPreexistente)) {
			mesa_nueva = new MesaEnfermedadPreexistente(presidente);
			this.mesas.add(mesa_nueva);
		}else if(tipoMesa.equals(Fixture.INSTANCE.mayor65)){
			
		}else if(tipoMesa.equals(Fixture.INSTANCE.trabajador)) {
			
		}else {
			
		}
		
		return mesa_nueva.dameCodigoMesa();
		
	}

}
