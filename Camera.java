
public class Camera{

    public static vec pos = new vec(0,0,-50);
    public static vec forward = new vec(0,0,1);
    public static vec up = new vec(0,1,0);
    public static vec right = new vec(1,0,0);
    private static double yaw=0, pitch = 0;

    public static vec project(vec point){
        vec d = point.subtract(pos).matMul(mat.rotMatUP(yaw)).matMul(mat.rotMatRIGHT(pitch));
        vec b = d.multiply(1000/d.z-2);
        return new vec(b.x,b.y,d.z);
    }

}
