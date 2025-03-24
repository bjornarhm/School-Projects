public class Aapning extends KvitRute{

    public Aapning(int radNummer, int kolonneNummer){
        super(radNummer, kolonneNummer);
    }

    @Override
    public void finn(Rute fra) {
        System.out.println("Aapning funnet paa: (" + radNummer + ", " + kolonneNummer + ")");
    }
    
}
