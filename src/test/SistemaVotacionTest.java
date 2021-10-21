package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import entidades.SistemaVotacionUNGS;

public class SistemaVotacionTest {

	private SistemaVotacionUNGS sistema;
	private static final Fixture  F = Fixture.INSTANCE;

	@Before
	public void setUp() {
		sistema = new SistemaVotacionUNGS("Sede UNGS");
		
		sistema.registrarVotante(
				F.dniFrodo, 
				"Frodo", 
				23, 
				!F.tieneEnfPrevia, 
				!F.trabaja
		);
		sistema.registrarVotante(
				F.dniEowyn,
				"Eowyn",
				25, 
				F.tieneEnfPrevia, 
				!F.trabaja
		);
		sistema.registrarVotante(
				F.dniBilbo,
				"Bilbo", 
				65, 
				F.tieneEnfPrevia, 
				!F.trabaja
		);
		sistema.registrarVotante(
				F.dniGandalf, 
				"Gandalf", 
				70, 
				!F.tieneEnfPrevia, 
				F.trabaja
		);
		sistema.registrarVotante(
				F.dniLegolas, 
				"Legolas", 
				80,
				!F.tieneEnfPrevia, 
				F.trabaja
		);
		sistema.registrarVotante(
				F.dniGaladriel, 
				"Galadriel", 
				81, 
				!F.tieneEnfPrevia, 
				F.trabaja
		);
		sistema.registrarVotante(
				F.dniArwen, 
				"Arwen", 
				50, 
				!F.tieneEnfPrevia, 
				F.trabaja
			);
		
		// # Votantes = 7
		// Mayores de 65 = 4
		// Trabajadores = 4
		// EnfPrexistente = 2
	}
	
	@Test
	public void test() {
		
	}
	
	@Test
	public void testAsignacionMesas() {
		final Integer numMesaEnfPreexistente = sistema.
				agregarMesa(F.enfPreexistente, F.dniFrodo);  // frodo es el presidente
		
		final Integer numMesaMayor65 = sistema.
				agregarMesa(F.mayor65, F.dniBilbo);
		
		final Integer numMesaGeneral = sistema.
				agregarMesa(F.general, F.dniGaladriel);
		
		final Integer numMesaTrabajador = sistema.
				agregarMesa(F.trabajador, F.dniGandalf);
		
		assertNotNull(numMesaEnfPreexistente);
		assertNotNull(numMesaMayor65);
		assertNotNull(numMesaGeneral);
		assertNotNull(numMesaTrabajador);
		
		assertNotNull(sistema.consultarTurno(F.dniFrodo));
		assertNotNull(sistema.consultarTurno(F.dniBilbo));
		assertNotNull(sistema.consultarTurno(F.dniGaladriel));
		assertNotNull(sistema.consultarTurno(F.dniGandalf));
	}

}
