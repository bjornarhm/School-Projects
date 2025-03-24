import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GameOfLifeView {
    private GameOfLifeController kontroll;
    private int rader, kolonner;
    JLabel tekstLevende;
    private JFrame vindu;
    private JPanel panelBrett;

    private JPanel panelInfo;
    private JButton [][]knapper ;

    public GameOfLifeView(GameOfLifeController kontroll, int antRader, int antKolonner){
        rader=antRader;
        kolonner = antKolonner;
        this.kontroll = kontroll;
        knapper = new JButton[antRader][antKolonner];

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch(Exception e){System.exit(1);}

        vindu = new JFrame("Game of Life");
        vindu.setLayout(new BorderLayout());
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vindu.setSize(1000, 1400);

        leggTilInfo();
        leggTilBrett();

        vindu.pack();
        vindu.setVisible(true);
    }

    public GameOfLifeView hentThis(){
        return this;
    }

    public void leggTilInfo(){
        if (panelInfo != null) {
            vindu.remove(panelInfo);
        }

        panelInfo = new JPanel();
        panelInfo.setLayout(new GridLayout(1, 3));

        tekstLevende =new JLabel("Levande:"+kontroll.hentLevende());
        tekstLevende.setFont(new Font("Arial", Font.BOLD, 18));
        panelInfo.add(tekstLevende);

        class StartKnapp extends JButton {
        
            public StartKnapp() {
                super("Start");
                super.addActionListener(new KnappBehandler());
            }
        
            class KnappBehandler implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //setText("WEFEF");
                    //startOppdatering();
                    Sleep test = new Sleep(hentThis());
                    test.start();
                    
                }
            }
        }
        
        
        class AvsluttningsKnapp extends JButton{
            
            public AvsluttningsKnapp(){
                super("Avslutt");

                super.addActionListener(new KnappBehandlar());
            }
            class KnappBehandlar implements ActionListener{
                @Override
                public void actionPerformed(ActionEvent e){
                    System.exit(0);
                }
            }
        }


        StartKnapp knapp = new StartKnapp();
        panelInfo.add(knapp);
        AvsluttningsKnapp knappen = new AvsluttningsKnapp();
        panelInfo.add(knappen);
        vindu.add(panelInfo, BorderLayout.NORTH);
        
    }

    public void leggTilBrett(){

        if (panelBrett != null) {
            vindu.remove(panelBrett);
        }

        panelBrett = new JPanel();
        panelBrett.setLayout(new GridLayout(rader, kolonner));
        Rutenett rutenett = kontroll.hentRutenett();

        class GameOfLifeKnapp extends JButton {
            private Celle celle;

            public GameOfLifeKnapp(Celle celle){
                this.celle = celle;
                    setText(""+celle.hentStatusTegn());
                

                super.addActionListener(new KnappBehandler());
            }
            class KnappBehandler implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent e){
                    
                    if(celle.erLevende()){
                        celle.settDoed();
                        setText(""+celle.hentStatusTegn());
                        leggTilInfo();
                    }else{
                        celle.settLevende();
                        setText(""+celle.hentStatusTegn());
                        leggTilInfo();
                    }
                }
            }

        }
        for (int rad = 0; rad < rader; rad++){
            for( int kol = 0; kol<kolonner; kol++){
                Celle celle = rutenett.hentCelle(rad, kol);
                GameOfLifeKnapp brettKnapp = new GameOfLifeKnapp(celle);
                panelBrett.add(brettKnapp);
                knapper[rad][kol] = brettKnapp;
            }
        }
        vindu.add(panelBrett, BorderLayout.CENTER);
    }

    public void oppdaterRutenett(){
        vindu.remove(panelBrett);
        leggTilBrett();
    }

    public void startOppdatering() {
        //kontroll.oppdater(); //Funker ikkje, så tar oppdatersjekken inn i viewklassen

        Rutenett rutenett = kontroll.hentRutenett();

        for (int rad = 0; rad < rader; rad++){
            for( int kol = 0; kol<kolonner; kol++){
                Celle celle = rutenett.hentCelle(rad, kol);
                celle.tellLevendeNaboer();
            } 
        }

        for (int rad = 0; rad < rader; rad++){
            for( int kol = 0; kol<kolonner; kol++){
                Celle celle = rutenett.hentCelle(rad, kol);
                celle.oppdaterStatus();
            } 
        }
        oppdaterHeileShitten();
        //System.out.println("ferdig med oppdatering");
    }

    public void oppdaterHeileShitten(){
        Rutenett rutenett = kontroll.hentRutenett();

        tekstLevende.setText("Levande:"+kontroll.hentLevende());

        for (int rad = 0; rad < rader; rad++){
            for( int kol = 0; kol<kolonner; kol++){
                Celle celle = rutenett.hentCelle(rad, kol);
                knapper[rad][kol].setText(""+ celle.hentStatusTegn());
            } 
        }
    }
    
}

