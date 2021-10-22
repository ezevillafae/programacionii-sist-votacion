package entidades;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import estructurasdedatos.Tupla;

public abstract class Mesa {
    private int codigoID;
    private static int codigoContador = 0;
    private Votante presidenteMesa;
    private HashMap<Integer,Integer> franjasHorarias; // (franja-cupo)
    
    public Mesa(Votante presidenteMesa) {
    	if(presidenteMesa != null) {
    		this.codigoID = codigoContador++;
            this.franjasHorarias= new HashMap<>();
            crearFranjasHorarias();
            asignarPresidente(presidenteMesa);
    	}else {
    		throw new RuntimeException();
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

    public void actualizarCupo(int franjaHoraria) {
        this.franjasHorarias.replace(franjaHoraria, this.franjasHorarias.get(franjaHoraria)+1);
    }
    
    public abstract int buscarFranjaHoraria();

    public Tupla<Integer,Integer> dameTurno() {
        int franjaDisponible = buscarFranjaHoraria();
        if (franjaDisponible!=-1) {
            Tupla<Integer,Integer> turno= new Tupla<>(dameCodigoMesa(),franjaDisponible);
            actualizarCupo(franjaDisponible);
            return turno;
        }
        else
            return null;
    }

	public HashMap<Integer, Integer> getFranjasHorarias() {
		return franjasHorarias;
	}
	
	public int cantidadDeVotantes() {
		int cantidadDeVotantes=0;
		Set<Integer> franjas = this.franjasHorarias.keySet();
		for (Integer franja : franjas) {
			cantidadDeVotantes += this.franjasHorarias.get(franja);
		}
		return cantidadDeVotantes;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("-------------------------------------------\n");
		sb.append("Mesa N° ").append(this.codigoID).append("\n");
		sb.append(this.getClass().getName()).append("\n");
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

