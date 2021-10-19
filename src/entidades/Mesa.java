package entidades;

import java.util.HashMap;
import java.util.Iterator;

import estructurasdedatos.Tupla;

public abstract class Mesa {
    private int codigoID;
    private Votante presidenteMesa;
    private HashMap<Integer,Integer> franjasHorarias; // (franja-cupo)
    private int cuposXFranjaHoraria;
    
    public Mesa(int codigoID, Votante presidenteMesa) {
        this.codigoID = codigoID;
        this.presidenteMesa = presidenteMesa;
        this.cuposXFranjaHoraria=0;
        this.franjasHorarias= new HashMap<>();
    }

    public abstract void crearFranjasHorarias(int cantidad, int cuposXFranjaHoraria);

    /*public void asignarPresidente(Votante votante){
        if (!hayPresidenteDeMesa()) {
            this.presidenteMesa=votante;
        }
    }*/

    public boolean hayPresidenteDeMesa() {
        return this.presidenteMesa!=null ;
    }

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
        this.franjasHorarias.put(franjaHoraria, this.franjasHorarias.get(franjaHoraria)-1);
    }
    
    public int buscarFranjaHoraria() {
        Iterator<Integer> it = this.franjasHorarias.keySet().iterator();
        int franjaHoraria=-1;
        while (it.hasNext()) {
            Integer franja = (Integer)it.next();
            if (this.franjasHorarias.get(franja)>0) {
                franjaHoraria= this.franjasHorarias.get(franja);
                return franjaHoraria;
            }
        }
        return franjaHoraria; // devuelve -1 si no hay cupos en ninguna franja.
    }

    public Tupla<Integer,Integer> dameTurno() {
        int franjaDisponible = buscarFranjaHoraria();
        if (franjaDisponible!=-1) {
            Tupla<Integer,Integer> turno= new Tupla<>(dameCodigoMesa(),franjaDisponible);
            return turno;
        }
        else
            return null;
    }

}

