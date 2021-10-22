package entidades;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import estructurasdedatos.Tupla;

public class MesaTrabajadores extends Mesa{

	public MesaTrabajadores(Votante presidenteMesa) {
		super(presidenteMesa);
	}

	@Override
	public void crearFranjasHorarias() {
		HashMap<Integer, Integer> franjas = getFranjasHorarias();
		franjas.put(8, 0);
	}
	
	@Override
	public int buscarFranjaHoraria() {
		Iterator<Integer> it = getFranjasHorarias().keySet().iterator();
		return it.next();
	}
	
	@Override
	public Tupla<Integer, Integer> dameTurno() {
		int franjaDisponible = buscarFranjaHoraria();
		actualizarCupo(franjaDisponible);
		Tupla<Integer,Integer> turno= new Tupla<>(dameCodigoMesa(),franjaDisponible);
		return turno;
	}
	
	
}
