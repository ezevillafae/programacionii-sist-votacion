package entidades;

import java.util.HashMap;

public class MesaGenerica extends Mesa{

	public MesaGenerica(Votante presidenteMesa) throws Exception {
		super(presidenteMesa);
	}

	@Override
	public void crearFranjasHorarias() {
		HashMap<Integer, Integer> franjas = getFranjasHorarias();
		for (int i = 8; i < 18; i++) {
			franjas.put(i, Fixture.INSTANCE.cupoXFranjaHorariaEnGenerica);
		}
	}
	
	
	
	

}
