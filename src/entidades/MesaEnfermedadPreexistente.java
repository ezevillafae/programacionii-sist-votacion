package entidades;

import java.util.HashMap;

public class MesaEnfermedadPreexistente extends Mesa{

	public MesaEnfermedadPreexistente(int codigoID, Votante presidenteMesa) throws Exception {
		super(codigoID, presidenteMesa);
		crearFranjasHorarias();
	}

	@Override
	public void crearFranjasHorarias() {
		HashMap<Integer, Integer> franjas = getFranjasHorarias();
		for (int i = 8; i < 18; i++) {
			franjas.put(i, Fixture.INSTANCE.cupoXFranjaHorariaEnfPreexistente);
		}
	}	

}
