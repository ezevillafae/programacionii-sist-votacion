package entidades;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class MesaEnfermedadPreexistente extends Mesa{

	public MesaEnfermedadPreexistente(Votante presidenteMesa) {
		super(presidenteMesa);
	}

	@Override
	public void crearFranjasHorarias() {
		HashMap<Integer, Integer> franjas = getFranjasHorarias();
		for (int i = 8; i < 18; i++) {
			franjas.put(i, Definiciones.cupoXFranjaHorariaEnfPreexistente);
		}
	}	
	
	public int buscarFranjaHoraria() {
        Iterator<Entry<Integer, Integer>> it = this.getFranjasHorarias().entrySet().iterator();
        int franjaDisponible=-1;
        while (it.hasNext() && franjaDisponible==-1) {
            Entry<Integer, Integer> franja = it.next();
            if (franja.getValue()<20) {
                franjaDisponible= franja.getKey();
                actualizarCupo(franjaDisponible);
            }
        }

        return franjaDisponible;
    }

}
