package estructurasdedatos;

public class Tupla<A,B> {
	private final A primerElemento;
	private final B segundoElemento;
	
	public Tupla(A e1, B e2) {
		this.primerElemento = e1;
		this.segundoElemento = e2;
	}

	public A getPrimerElemento() {
		return primerElemento;
	}


	public B getSegundoElemento() {
		return segundoElemento;
	}


	
}
