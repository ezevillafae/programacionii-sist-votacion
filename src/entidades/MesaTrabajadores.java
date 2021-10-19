package entidades;

import java.util.HashMap;


import estructurasdedatos.Tupla;

public class MesaTrabajadores extends Mesa{

	public MesaTrabajadores(Votante presidenteMesa) throws Exception {
		super(presidenteMesa);
	}

	@Override
	public void crearFranjasHorarias() {
		HashMap<Integer, Integer> franjas = getFranjasHorarias();
		franjas.put(8, Fixture.INSTANCE.cupoXFranjaHorariaTrabajadores);
	}
	
	@Override
	public int buscarFranjaHoraria() {
		int franjaDisponible = getFranjasHorarias().keySet().iterator().next();
		return franjaDisponible;
	}
	
	@Override
	public Tupla<Integer, Integer> dameTurno() {
		int franjaDisponible = buscarFranjaHoraria();
		Tupla<Integer,Integer> turno= new Tupla<>(dameCodigoMesa(),franjaDisponible);
		return turno;
	}
}
