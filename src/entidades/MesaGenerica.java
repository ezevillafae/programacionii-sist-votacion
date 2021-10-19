package entidades;

import java.util.HashMap;

public class MesaGenerica extends Mesa{

	public MesaGenerica(int codigoID, Votante presidenteMesa) throws Exception {
		super(codigoID, presidenteMesa);
	}

	@Override
	public void crearFranjasHorarias() {
		HashMap<Integer, Integer> franjas = getFranjasHorarias();
		for (int i = 8; i < 18; i++) {
			franjas.put(i, Fixture.INSTANCE.cupoXFranjaHorariaEnGenerica);
		}
	}
	
	
	
	

}
