public class Verden {
    public Rutenett rutenett;
    public int genNr=0;

    public Verden(int rad, int kol){
        rutenett = new Rutenett(rad, kol);
        rutenett.fyllMedTilfeldigeCeller();
        rutenett.kobleAlleCeller();
        
    }
    public void tegn(){
        //System.out.println("generasjonsnummer: "+genNr);
        //System.out.println("levende celler: : "+rutenett.antallLevende());
        rutenett.tegnRutenett();
        
        
    }
    public void oppdatering(){
        
        for(int i = 0; i <rutenett.antRader; i++){
            for ( int j = 0; j<rutenett.antKolonner; j++){
                Celle celle = rutenett.hentCelle(i, j);
                celle.tellLevendeNaboer();
            }
        }
        
        for (int i = 0; i<rutenett.antRader; i++){
            for (int j = 0; j < rutenett.antKolonner; j++){
                Celle celle = rutenett.hentCelle(i, j);
                celle.oppdaterStatus();
            }
        
        }
        genNr++;
    }

    public int hentLevende(){
        return rutenett.antallLevende();
    }

    public Rutenett hentRutenett(){
        return rutenett;
    }
    
}
