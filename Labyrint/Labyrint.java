import java.io.File;
import java.io.FileNotFoundException;
import java.security.Principal;
import java.util.Scanner;

class Labyrint{

    public Rute[][] RuteObjekter;
    public int rader;
    public int kolonner;

    public  Labyrint(int rader, int kolonner, Rute[][] RuteObjekter){
        this.rader = rader;
        this.kolonner = kolonner;
        this.RuteObjekter = RuteObjekter;
        
    }


    public static Labyrint lesFil(String filnavn)throws FileNotFoundException{
        File fil = new File(filnavn);
        Scanner scanner = new Scanner(fil);

        int rader = scanner.nextInt();
        int kolonner = scanner.nextInt();
        scanner.nextLine();

        Rute[][] RuteObjekter = new Rute[rader][kolonner];

        for (int i = 0; i<rader; i++){
            String rad = scanner.nextLine();
            for(int j = 0; j<kolonner; j++){
                char ruteTegn = rad.charAt(j);
                if (ruteTegn=='#'){
                    RuteObjekter[i][j]= new SvartRute(i, j);
                } else if(i == 0 || i == rader - 1 || j == 0 || j == kolonner - 1){
                    RuteObjekter[i][j]=new Aapning(i, j);
                } else if(ruteTegn =='.'){
                    RuteObjekter[i][j]= new KvitRute(i, j);
                }
            }
        }

        Labyrint labyrint = new Labyrint(rader, kolonner, RuteObjekter);

        for (int i = 0; i< rader; i++){
            for (int j = 0; j<kolonner; j++){
                RuteObjekter[i][j].settNaboar(labyrint);
            }
        }

        scanner.close();

        System.out.print(labyrint);

        return labyrint;

    }
    
    public void finnVegenUt(int rad, int kol){
        if (rad < 0 || rad >= rader || kol < 0 || kol >= kolonner) {
            System.out.println("Ugyldig posisjon. Prøv igjen med gyldige koordinatar.");
            return;
        }

        Rute startRute = RuteObjekter[rad][kol];
        startRute.finn(null);
    }

    public void brukarInput(){
        Boolean stopp = true;
        Scanner inputScanner = new Scanner(System.in);
        while(stopp){
            int radInput=-0;
            int kolInput =0;
            System.out.println("Skriv inn kordinater <rad> <kolonnne>. Skriv -1 for aa stoppe");

            if (inputScanner.hasNextInt()) {
                radInput = inputScanner.nextInt();
                if(radInput==-1){
                    System.out.println("Du avslutta programmet.");;
                    stopp=false;
                    break;
                }
                
                if (inputScanner.hasNextInt()) {
                    kolInput = inputScanner.nextInt();
                } else {
                    System.out.println("Du skreiv ikkje inn eit gyldig andre tall.");
                    break;
                }
            } else {
                System.out.println("Du skreiv ikkje inn eit gyldig første tall.");
                break;
            }
            //System.out.println(radInput+""+ kolInput);

            

            finnVegenUt(radInput, kolInput);

            
        }
        inputScanner.close();
    }


    public String toString(){
        StringBuilder labyrintStringen = new StringBuilder();
        labyrintStringen.append("\nSlik ser labyrinten ut:\n");
        for (int i = 0; i< rader; i++){
            for (int j=0; j<kolonner; j++){
                labyrintStringen.append(RuteObjekter[i][j].toString());

            }
            labyrintStringen.append("\n");
        }
        labyrintStringen.append("\n");
        return labyrintStringen.toString();

    }
}