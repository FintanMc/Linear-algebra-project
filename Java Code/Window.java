import javax.swing.*;

public class Window extends JFrame {

    public static Screen screen = new Screen();
    public static vec size = new vec(1600,900,0);
    public static int frame = 0;
    public static double deltaTime = 0;

    public Window() {
        add(screen);
        setTitle("Linear Algebra Project - Fintan McCamley");
        setBounds(160,80,(int)size.x,(int)size.y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        Thread pt = new Thread(() -> {
            while (true) {
                double t = System.currentTimeMillis();
                screen.repaint();
                frame++;
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                deltaTime = (System.currentTimeMillis() - t)/1000;
            }
        });
        pt.start();
        
    }

    public static void main(String[] args) {
        new Window();
    }
}