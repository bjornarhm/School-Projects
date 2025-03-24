public class GameOfLifeController{
    private Verden  model;
    private GameOfLifeView view;

    public GameOfLifeController(int antRader, int antKolonner){
        model = new Verden(antRader, antKolonner);
        //model.tegn();

        view = new GameOfLifeView(this, antRader, antKolonner);
    }

    public void oppdater(){
        model.oppdatering();
        view.oppdaterRutenett();
    }

    public int hentGenNR(){
        return model.genNr;
    }

    public int hentLevende(){
        return model.hentLevende();
    }

    public Rutenett hentRutenett(){
        return model.hentRutenett();
    }
}