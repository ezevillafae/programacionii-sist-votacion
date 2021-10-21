package entidades;

public class Main {
	public static void main(String[] args) throws Exception {
	
		SistemaVotacionUNGS sv = new SistemaVotacionUNGS("UNGS");
		sv.registrarVotante(3434535, "Nicolas", 29, false, true);
		sv.registrarVotante(42145454, "Ezequiel", 21, false, true);
		sv.registrarVotante(40404040, "Pedro", 20, false, false);
		
		sv.agregarMesa(Definiciones.mayor65, 42145454);
		sv.agregarMesa(Definiciones.trabajador, 3434535);
		sv.agregarMesa(Definiciones.general, 40404040);
		
		sv.registrarVotante(40404041, "Matias", 20, false, false);
		sv.registrarVotante(40404042, "Mariano", 20, false, false);
		sv.registrarVotante(40404043, "Jose", 20, false, false);
		
		
		System.out.println(sv.asignarTurnos());
		System.out.println(sv);
		
		
	}
}
