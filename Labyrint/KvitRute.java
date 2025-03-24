public class KvitRute extends Rute {
    private boolean besokt = false;
    public KvitRute(int radNummer, int kolonneNummer){
        super(radNummer, kolonneNummer);
    }

    @Override
    public void finn(Rute fra){
        if (fra != nord) nord.finn(this);
        if (fra != sor) sor.finn(this);
        if (fra != vest) vest.finn(this);
        if (fra != aust) aust.finn(this);
        
    }

    public String toString(){
        String string = ".";
        return string;
    }
    
}
