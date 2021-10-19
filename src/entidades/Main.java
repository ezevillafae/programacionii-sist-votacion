package entidades;

public class Main {
	public static void main(String[] args) throws Exception {
		Votante pres1;
		Votante pres2;
		try {
			pres1= new Votante("Nicolas", 3434535, 29, false, true);
			pres2 = new Votante("Ezequiel", 42145454, 21, false, true);
			MesaMayores mesaMayores = new MesaMayores(1010, pres1);
			MesaGenerica mesaGenerica = new MesaGenerica(2020, pres2);
			
			System.out.println(mesaMayores);
			System.out.println(mesaGenerica);
			
			System.out.println(pres1);
			System.out.println(pres2);
			
		} catch (Exception e) {
			System.out.println("Error al crear la mesa");
		}
		
		
		
	}
}
