package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import entidades.MesaEnfermedadPreexistente;
import entidades.MesaGenerica;
import entidades.MesaMayores;
import entidades.MesaTrabajadores;
import entidades.Votante;

public class MesaTest {

	private MesaEnfermedadPreexistente mEnfPreex;
	private MesaGenerica mGenerica;
	private MesaMayores mMayores;
	private MesaTrabajadores mTrabajadores;
	
	@Before
	public void setUp() {
			Votante v1 = new Votante("Anonimo",123,20,false,false);
			Votante v2 = new Votante("Anonimo",124,20,false,false);
			Votante v3 = new Votante("Anonimo",125,20,false,false);
			Votante v4 = new Votante("Anonimo",126,20,false,false);
			mEnfPreex = new MesaEnfermedadPreexistente(v1);
			mGenerica = new MesaGenerica(v2);
			mMayores = new MesaMayores(v3);
			mTrabajadores = new MesaTrabajadores(v4);
	}
	
	@Test
	public void equalsTest() {
		assertNotEquals(mEnfPreex, mGenerica);
		assertNotEquals(mMayores, mTrabajadores);
		assertEquals(mEnfPreex, mEnfPreex);
	}

}
