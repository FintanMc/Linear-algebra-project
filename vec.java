public class vec {

    public double x,y,z;
    public static vec zeros = new vec(0,0,0);
    public static vec ones = new vec(1,1,1);

    public vec(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "vec [x=" + x + ", y=" + y + ", z=" + z + "]";
    }

    public vec matMul(mat m) {
        vec out = new vec(0,0,0);
        out.x = x*m.entries[0][0]+y*m.entries[0][1]+z*m.entries[0][2];
        out.y = x*m.entries[1][0]+y*m.entries[1][1]+z*m.entries[1][2];
        out.z = x*m.entries[2][0]+y*m.entries[2][1]+z*m.entries[2][2];
        return out;
    }

    public vec add(vec v) {
        return new vec(x+v.x, y+v.y, z+v.z);
    }
    public vec add(double v) {
        return new vec(x+v, y+v, z+v);
    }

    public vec subtract(vec v) {
        return new vec(x-v.x, y-v.y, z-v.z);
    }

    public vec subtract(double v) {
        return new vec(x-v, y-v, z-v);
    }

    public vec multiply(vec v) {
        return new vec(x*v.x, y*v.y, z*v.z);
    }

    public vec multiply(double v) {
        return new vec(x*v, y*v, z*v);
    }

    public vec divide(vec v) {
        return new vec(x/v.x, y/v.y, z/v.z);
    }

    public vec divide(double v) {
        return new vec(x/v, y/v, z/v);
    }

    public double dot(vec v) {
        return x*v.x + y*v.y + z*v.z;
    }

    public vec cross(vec v) {
        return new vec(y*v.z - z*v.y, z*v.x - x*v.z, x*v.y - y*v.x);
    }

    public vec qRotate(quaternion q){
        vec v1 = this.multiply(q.scalar*q.scalar - q.vector.dot(q.vector));
        vec v2 = q.vector.multiply(q.vector.dot(this)).multiply(2);
        vec v3 = q.vector.cross(this).multiply(q.scalar*2);
        return v1.add(v2).add(v3);
    }

    public vec normalize() {
        double len = Math.sqrt(x*x + y*y + z*z);
        return new vec(x/len, y/len, z/len);
    }

    public double magnitude() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public void increment(vec v) {
        x+=v.x;
        y+=v.y;
        z+=v.z;
    }

    public vec lerp(vec v, double t) {
        return add(v.subtract(this).multiply(t));
    }

    public static vec randomVec(){
        return new vec(Math.random()-0.5,Math.random()-0.5,Math.random()-0.5).normalize();
    }

    public static vec rayCast(vec ro, vec rd){
        for(Cube c : Screen.bodies) {
            double d = 0;
            for (int i = 0; i < 5000; i++) {
                d += 0.5;
                vec p = ro.add(rd.normalize().multiply(d));
                if(c.pointIntersecting(p))
                    return p;
            }
        }
        return null;
    }
}
