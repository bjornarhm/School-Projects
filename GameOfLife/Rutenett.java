class Rutenett {
    public int antRader;
    public int antKolonner;
    public Celle[][] rutene;

    public Rutenett(int antRader, int antKolonner) {
        this.antRader = antRader;
        this.antKolonner = antKolonner;
        this.rutene = new Celle[antRader][antKolonner];
    }   
    public void lagCelle(int rad, int kol) {
        Celle celle = new Celle();
        if (Math.random() <= 0.3333) {
            celle.settLevende();
        }
        rutene[rad][kol] = celle;
    }
    
    public void fyllMedTilfeldigeCeller() {
        for (int i = 0; i < antRader; i++) {
            for (int j = 0; j < antKolonner; j++) {
                lagCelle(i, j);
            }
        }
    } 
    public Celle hentCelle(int rad, int kol){
        if (rad<0 || rad >= antRader || kol < 0 || kol >= antKolonner){
            return null;
        }
        return rutene[rad][kol];
    }
    public void tegnRutenett(){
        for (int i = 0; i<antRader; i++){
            for (int j =0; j< antKolonner; j++){
                if (rutene[i][j].erLevende()){
                    System.out.print("O");
                }
                else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }
    public void settNaboer(int rad, int kol) {
        Celle celle = hentCelle(rad, kol);
        if (celle == null) {
          return;
        }
        
        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
            if (i == 0 && j == 0) {
              continue;
            }
            Celle nabo = hentCelle(rad + i, kol + j);
            if (nabo != null) {
              celle.leggTilNabo(nabo); 
            }
          }
        }
      }
    public void kobleAlleCeller(){
        for (int i = 0; i<rutene.length; i++){
            for (int j = 0; j<rutene[i].length; j++){
                settNaboer(i, j);
            }
        }
    }
    public int antallLevende(){
        int teljar = 0;
        for (int i = 0; i<rutene.length; i++){
            for (int j = 0; j<rutene[i].length; j++){
                if(rutene[i][j].erLevende()){
                    teljar+=1;
                }
            }
        }
        return teljar;
    }
}
