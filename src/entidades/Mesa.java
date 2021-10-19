package entidades;

import java.util.HashMap;
import java.util.Iterator;

import estructurasdedatos.Tupla;

public abstract class Mesa {
    private int codigoID;
    private static int codigoContador = 0;
    private Votante presidenteMesa;
    private HashMap<Integer,Integer> franjasHorarias; // (franja-cupo)
    
    public Mesa(Votante presidenteMesa) throws Exception {
    	if(presidenteMesa != null) {
    		this.codigoID = codigoContador++;
            this.franjasHorarias= new HashMap<>();
            crearFranjasHorarias();
            asignarPresidente(presidenteMesa);
    	}else {
    		throw new Exception();
    	}
    }

    public abstract void crearFranjasHorarias();

    public void asignarPresidente(Votante votante){
        if(votante == null || votante.esPresidenteDeMesa() || hayPresidenteDeMesa()) {
        	throw new RuntimeException();
        }
 
        this.presidenteMesa=votante;
        Tupla<Integer, Integer> turno = dameTurno();
        presidenteMesa.setPresidenteDeMesa();
        presidenteMesa.asignarTurno(turno.getPrimerElemento(), turno.getSegundoElemento());
    }

    public boolean hayPresidenteDeMesa() {
        return this.presidenteMesa!=null ;
    }
    
    /**
     * 
     * 
     * @return true, si existe algún valor en franjasHorarias tal que > 0
     * @Advertencia, no se usa este metodo
     *
     */
    public boolean hayTurnosDisponibles() {
        Iterator<Integer> it = this.franjasHorarias.keySet().iterator();
        boolean existeCupoDisponible = false;
        while (it.hasNext()) {
            Integer franja = (Integer)it.next();
            existeCupoDisponible= existeCupoDisponible || this.franjasHorarias.get(franja) >0;
        }
        return existeCupoDisponible;
    }

    public int dameCodigoMesa() {
        return this.codigoID;
    }

    public void decrementarCupo(int franjaHoraria) {
        this.franjasHorarias.replace(franjaHoraria, this.franjasHorarias.get(franjaHoraria)-1);
    }
    
    public int buscarFranjaHoraria() {
        Iterator<Integer> it = this.franjasHorarias.keySet().iterator();
        int franjaHoraria=-1;
        while (it.hasNext()) {
            Integer franja = (Integer)it.next();
            if (this.franjasHorarias.get(franja)>0) {
                franjaHoraria= franja;
                return franjaHoraria;
            }
        }
        return franjaHoraria; // devuelve -1 si no hay cupos en ninguna franja.
    }

    public Tupla<Integer,Integer> dameTurno() {
        int franjaDisponible = buscarFranjaHoraria();
        if (franjaDisponible!=-1) {
            Tupla<Integer,Integer> turno= new Tupla<>(dameCodigoMesa(),franjaDisponible);
            decrementarCupo(franjaDisponible);
            return turno;
        }
        else
            return null;
    }

	public HashMap<Integer, Integer> getFranjasHorarias() {
		return franjasHorarias;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("-------------------------------------------\n");
		sb.append("Mesa N° ").append(this.codigoID).append("\n");
		sb.append("Presidente de mesa :").append(this.presidenteMesa.getDni()).append("\n");
		
		Iterator<Integer> it = this.franjasHorarias.keySet().iterator();
		while(it.hasNext()) {
			Integer horario = it.next();
			sb.append("Hora : ").append(horario).append(" Cupos : ").append(this.franjasHorarias.get(horario));
			sb.append("\n");
		}
		return sb.toString();
	}
    
    
    

}

