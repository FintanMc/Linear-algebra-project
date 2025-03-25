import java.awt.*;

public class Face {

    private vec[] points;
    private Color col;
    private boolean flip = false;

    public Face(vec[] points, Color col) {
        this.points = points;
        this.col = col;
    }

    public double getDepth(){
        return (points[0].z+points[1].z+points[2].z+points[3].z)/4;
    }

    public vec getNormal(){
        vec v1 = points[0].subtract(points[1]).cross(points[2].subtract(points[1]));
        vec v2 = points[3].subtract(points[2]).cross(points[1].subtract(points[2]));
        vec v3 = points[3].subtract(points[0]).cross(points[1].subtract(points[0]));
        vec v4 = points[0].subtract(points[3]).cross(points[2].subtract(points[3]));
        return v1.multiply((flip)?-1:1).normalize();
    }

    public void flip(){
        flip = !flip;
    }

    public void paint(Graphics g) {
        g.setColor(col);
        g.fillPolygon(  new int[]{(int)points[0].x,(int)points[1].x,(int)points[2].x, (int)points[3].x},
                        new int[]{(int)points[0].y,(int)points[1].y,(int)points[2].y, (int)points[3].y},4);
    }
}
