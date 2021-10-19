package entidades;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import estructurasdedatos.Tupla;

public class MesaTrabajadores extends Mesa{

	public MesaTrabajadores(int codigoID, Votante presidenteMesa) throws Exception {
		super(codigoID, presidenteMesa);
	}

	@Override
	public void crearFranjasHorarias() {
		HashMap<Integer, Integer> franjas = getFranjasHorarias();
		for (int i = 8; i < 13; i++) {
			franjas.put(i, Fixture.INSTANCE.cupoXFranjaHorariaTrabajadores);
		}	
	}
	
	@Override
	public int buscarFranjaHoraria() {
		Set<Integer> franjas = getFranjasHorarias().keySet();
		int size = franjas.size();
		int elemAleatorio = new Random().nextInt(size);
		int i = 0;
		for(Integer f : franjas){
		    if (i == elemAleatorio)
		        return f;
		    i++;
		}
		return -1;
	}
	
	@Override
	public Tupla<Integer, Integer> dameTurno() {
		int franjaDisponible = buscarFranjaHoraria();
		if(franjaDisponible!=-1) {
			Tupla<Integer,Integer> turno= new Tupla<>(dameCodigoMesa(),franjaDisponible);
			return turno;
		}
		return null;
	}
	
	

}
