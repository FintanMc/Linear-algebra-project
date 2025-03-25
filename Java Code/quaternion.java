public class quaternion {

    public double scalar;
    public vec vector;

    @Override
    public String toString() {
        return "quaternion{" + "scalar=" + scalar + ", vector=" + vector + '}';
    }

    public quaternion(double scalar, vec vector) {
        this.scalar = scalar;
        this.vector = vector;
    }

    public quaternion multiply(quaternion q) {
        double s = q.scalar*scalar - q.vector.x*vector.x - q.vector.y*vector.y - q.vector.z*vector.z;
        double i = q.scalar*vector.x + q.vector.x*scalar - q.vector.y*vector.z + q.vector.z*vector.y;
        double j = q.scalar*vector.y - q.vector.x*vector.z - q.vector.y*scalar - q.vector.z*vector.x;
        double k = q.scalar*vector.z - q.vector.x*vector.y - q.vector.y*vector.x - q.vector.z*scalar;
        return new quaternion(s, new vec(i,j,k));
    }

    public quaternion multiply(double q) {
        double s = q*scalar;
        double i = vector.x*q;
        double j = vector.y*q;
        double k = vector.z*q;
        return new quaternion(s, new vec(i,j,k));
    }

    public static quaternion rotate(double angle, vec v) {
        return new quaternion(Math.cos(angle/2), v.multiply(Math.sin(angle/2))).toUnit();
    }

    public quaternion add(quaternion q) {
        return new quaternion(scalar + q.scalar, vector.add(q.vector));
    }

    public quaternion toUnit() {
        double n = Math.sqrt(vector.x*vector.x + vector.y*vector.y + vector.z*vector.z + scalar*scalar);
        return new quaternion(scalar/n, vector.divide(n));
    }

    public void increment(quaternion q) {
        quaternion t = this.add(this.multiply(q.multiply(0.5*Window.deltaTime))).toUnit();
        scalar = t.scalar;
        vector = t.vector;
    }
}
