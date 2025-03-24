import java.io.FileNotFoundException;

public class Oblig7 {
    public static void main(String[] args) {
        try {
            Labyrint labyrint = Labyrint.lesFil("labyrinter/1.in");
            //labyrint.finnVegenUt(1, 1);
            labyrint.brukarInput();
        } catch (FileNotFoundException e) {
            System.out.println("Filen ikke funnet.");
        }

    }
}
