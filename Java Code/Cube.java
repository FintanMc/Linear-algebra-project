import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

public class Cube {

    private vec pos = new vec(0,0,0);
    private double size;
    private double angle = 0;
    private vec[] points = new vec[8];
    private vec[] projPoints = new vec[8];
    private quaternion orientation = new quaternion(1,vec.zeros);
    private vec ra1,ra2,ra3;
    private vec forward = new vec(0,0,1), up = new vec(0,1,0), right = new vec(1,0,0);
    private vec angles = new vec(10,10,10);

    public Cube(double x, double y, double z, double size) {
        pos.x = x;
        pos.y = y;
        pos.z = z;
        this.size = size/2;
        updatePoints();
        ra1 = vec.randomVec().normalize();
        ra2 = ra1.cross(new vec(0,1,0)).normalize();
        ra3 = ra1.cross(ra2).normalize();
    }

    public void updatePoints(){
        points[0] = new vec(-size,size,size);
        points[1] = new vec(size,size,size);
        points[2] = new vec(size,-size,size);
        points[3] = new vec(-size,-size,size);
        points[4] = new vec(-size,size,-size);
        points[5] = new vec(size,size,-size);
        points[6] = new vec(size,-size,-size);
        points[7] = new vec(-size,-size,-size);
    }

    public void update() {

        angle+=0.01;

        quaternion qx = quaternion.rotate(angle,ra1);
        quaternion qy = quaternion.rotate(angle,ra2);
        quaternion qz = quaternion.rotate(angle,ra3);
        orientation = qz.multiply(qx).multiply(qy).toUnit().toUnit();
        //orientation = quaternion.rotate(angle,new vec(1,-1,-1));


        //orientation.increment(new quaternion(0,new vec(1,-1,-1)));

        forward = new vec(0,0,1).qRotate(orientation);
        up = new vec(0,-1,0).qRotate(orientation);
        right = new vec(1,0,0).qRotate(orientation);
    }

    public void paint(Graphics g) {

        int i = 0;
        for(vec point : points){
            vec p = Camera.project(pos.add(point.qRotate(orientation)));
            projPoints[i] = p.add(Window.size.multiply(0.5));
            if(p.z>0) {
                g.setColor(Color.RED);
                //g.fillArc((int) (p.x + Window.size.x / 2)-5, (int) (p.y + Window.size.y / 2)-5, 10, 10, 0, 360);
            }
            i++;
        }

        Face[] faces = new Face[6];
        int alpha = 255;
        faces[0] = new Face(new vec[]{projPoints[0],projPoints[1],projPoints[2],projPoints[3]},new Color(255,0, 106,alpha));
        faces[2] = new Face(new vec[]{projPoints[5],projPoints[4],projPoints[7],projPoints[6]},new Color(255,0, 106,alpha));
        faces[1] = new Face(new vec[]{projPoints[0],projPoints[1],projPoints[5],projPoints[4]},new Color(255, 204, 0,alpha));faces[1].flip();
        faces[3] = new Face(new vec[]{projPoints[2],projPoints[3],projPoints[7],projPoints[6]},new Color(255, 204, 0,alpha));faces[3].flip();
        faces[4] = new Face(new vec[]{projPoints[2],projPoints[1],projPoints[5],projPoints[6]},new Color(0, 157,255,alpha));
        faces[5] = new Face(new vec[]{projPoints[0],projPoints[3],projPoints[7],projPoints[4]},new Color(0, 157,255,alpha));

        Arrays.sort(faces,new Comparator<Face>() {
            @Override
            public int compare(Face o1, Face o2) {
                return (int)(o2.getDepth()-o1.getDepth());
            }
        });


        for(Face face : faces){
            if(face.getNormal().dot(Camera.forward)<0)
                face.paint(g);
        }
        /*
        g.setColor(Color.green);
        vec center = Camera.project(pos).add(Window.size.multiply(0.5));
        vec vecP = Camera.project(pos.add(forward.multiply(10))).add(Window.size.multiply(0.5));
        g.drawLine((int) center.x, (int) center.y, (int) vecP.x, (int) vecP.y);

        g.setColor(Color.blue);
        vecP = Camera.project(pos.add(up.multiply(10))).add(Window.size.multiply(0.5));
        g.drawLine((int) center.x, (int) center.y, (int) vecP.x, (int) vecP.y);

        g.setColor(Color.red);
        vecP = Camera.project(pos.add(right.multiply(10))).add(Window.size.multiply(0.5));
        g.drawLine((int) center.x, (int) center.y, (int) vecP.x, (int) vecP.y);

         */
    }

    public boolean pointIntersecting(vec point) {

        vec d = point.subtract(pos);
        vec localPoint = new vec(Math.abs(d.dot(right)),Math.abs(d.dot(up)),Math.abs(d.dot(forward))).subtract(size);
        if(localPoint.x < 0 && localPoint.y < 0 && localPoint.z < 0)
            return true;
        return false;
    }

}
