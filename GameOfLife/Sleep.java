import javax.swing.text.View;

public class Sleep extends Thread {

    private GameOfLifeView view;
    public  Sleep(GameOfLifeView view){
        this.view = view;
    }
    @Override
    public void run(){
        while(true){
        view.startOppdatering();
        try{Thread.sleep(2000);
        }catch(Exception e){System.exit(1);}
        }
    }
    
}
