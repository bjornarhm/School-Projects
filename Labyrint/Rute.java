
public abstract class Rute {
    public int radNummer;
    public int kolonneNummer;
    private Labyrint labyrint;

    public Rute nord;
    public Rute vest;
    public Rute sor;
    public Rute aust;

    public Rute(int radNummer, int kolonneNummer){
        this.radNummer = radNummer;
        this.kolonneNummer = kolonneNummer;

    }

    public void settNaboar(Labyrint labyrint) {
        
        if (radNummer > 0) {
            nord = labyrint.RuteObjekter[radNummer-1][kolonneNummer];
        }
        if (radNummer < labyrint.rader-1) {
            sor = labyrint.RuteObjekter[radNummer+1][kolonneNummer];
        }
        if (kolonneNummer > 0) {
            vest = labyrint.RuteObjekter[radNummer][kolonneNummer-1];
        }
        if (kolonneNummer < labyrint.kolonner-1) {
            aust = labyrint.RuteObjekter[radNummer][kolonneNummer+1];
        }
    }
    
    public abstract String toString();

    public abstract void finn(Rute fra);
    
}
