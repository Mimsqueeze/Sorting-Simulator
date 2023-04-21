
public class WaitThread extends Thread {
    
    @Override
    public void run() {
        try {
            System.in.read();
        } catch (Exception e) {
            
        }
    }
}
