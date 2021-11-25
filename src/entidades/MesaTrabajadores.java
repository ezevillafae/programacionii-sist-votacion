package entidades;

import java.util.HashMap;
import java.util.Iterator;


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
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	

}
