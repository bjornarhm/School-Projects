class Celle {
    boolean levende = false;
    Celle[] naboer=new Celle[8];
    int antNaboer = 0;
    int antLevendeNaboer =0;

    public boolean settDoed(){
        return levende=false;
    }
    public boolean settLevende(){
        return levende = true;
    }
    public boolean erLevende(){
        return levende;
    }
    public char hentStatusTegn(){
        if (levende) {
            char tegn ='*';
            return tegn;
        }

        else {
            char tegn = '\0';
            return tegn;
        }
    }
    public void leggTilNabo(Celle nabo){
        if(nabo!=null){
            naboer[antNaboer]= nabo;
            antNaboer++;
        }
        
    }
    public void tellLevendeNaboer(){
        antLevendeNaboer = 0;
        for(Celle nabo : naboer){
            if (nabo!=null && nabo.erLevende()){
                antLevendeNaboer++;
            }
        
        }
       
    }
    public void oppdaterStatus(){
        if(antLevendeNaboer>3){
            settDoed();
        }
        if(antLevendeNaboer<2){
            settDoed();
        }
        if(levende==false && antLevendeNaboer==3){
            settLevende();
        }
    }
}
