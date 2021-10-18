package estructurasdedatos;

public class Tupla<A,B> {
	private A primerElemento;
	private B segundoElemento;
	
	public Tupla(A e1, B e2) {
		this.primerElemento = e1;
		this.segundoElemento = e2;
	}

	public A getPrimerElemento() {
		return primerElemento;
	}

	public void setPrimerElemento(A primerElemento) {
		this.primerElemento = primerElemento;
	}

	public B getSegundoElemento() {
		return segundoElemento;
	}

	public void setSegundoElemento(B segundoElemento) {
		this.segundoElemento = segundoElemento;
	}

	
}
