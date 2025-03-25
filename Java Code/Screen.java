import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class Screen extends JPanel implements MouseMotionListener {

    private Cube c1 = new Cube(0,0,0,10);
    private Cube c2 = new Cube(-20,0,0,10);
    private Cube c3 = new Cube(20,0,0,10);
    public static ArrayList<Cube> bodies = new ArrayList<Cube>();
    public vec mousePos = vec.ones.multiply(10);


    public Screen() {
        bodies.add(c1);
        bodies.add(c2);
        bodies.add(c3);
        addMouseMotionListener(this);
    }

    @Override
    public void paintComponent(Graphics g){
        g.setColor(new Color(30,30,30));
        g.fillRect(0, 0, getWidth(), getHeight());
        for(Cube c : bodies){
            c.update();
            c.paint(g);
        }

        vec ray = vec.rayCast(Camera.pos, Camera.forward.add(mousePos.subtract(new vec(800,450,0)).divide(900)));
        if(ray != null) {
            vec t = Camera.project(ray).add(Window.size.multiply(0.5));
            g.setColor(Color.green);
            //g.fillArc((int) t.x-5, (int) t.y-5, 10, 10, 0, 360);
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePos.x = e.getX();
        mousePos.y = e.getY();
    }
}
