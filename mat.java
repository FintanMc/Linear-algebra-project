public class mat {

    public double[][] entries = new double[3][3];
    public static final mat identity = new mat(1,0,0,0,1,0,0,0,1);

    public mat(mat m) {entries = m.entries;}
    public mat(double[][] ent) {entries = ent;}

    public mat(double a1, double a2, double a3, double b1, double b2, double b3, double c1, double c2, double c3) {
        entries[0][0] = a1;
        entries[0][1] = a2;
        entries[0][2] = a3;
        entries[1][0] = b1;
        entries[1][1] = b2;
        entries[1][2] = b3;
        entries[2][0] = c1;
        entries[2][1] = c2;
        entries[2][2] = c3;
    }

    public static mat rotMatFORWARD(double a){
        return new mat(Math.cos(a), Math.sin(a),0, -Math.sin(a), Math.cos(a), 0,0,0,1);
    }

    public static mat rotMatRIGHT(double a){
        return new mat(1,0,0,0,Math.cos(a), Math.sin(a),0, -Math.sin(a), Math.cos(a));
    }

    public static mat rotMatUP(double a){
        return new mat(Math.cos(a),0, Math.sin(a),0,1,0, -Math.sin(a),0, Math.cos(a));
    }

    public mat mult(double b){
        double[][] e = new double[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                e[i][j] = entries[i][j]*b;
            }
        }
        return new mat(e);
    }

    public static double determinant2d(double a, double b, double c, double d){
        return a*d - b*c;
    }

    public mat transpose(){
        return new mat(entries[0][0], entries[1][0], entries[2][0], entries[0][1], entries[1][1], entries[2][1], entries[0][2], entries[1][2], entries[2][2]);
    }

    public mat inverse(){
        double det = entries[0][0]*determinant2d(entries[1][1],entries[1][2], entries[2][1], entries[2][2])
                    - entries[0][1]*determinant2d(entries[1][0],entries[1][2], entries[2][0], entries[2][2])
                    + entries[0][2]*determinant2d(entries[1][0],entries[1][1], entries[2][0], entries[2][1]);

        double d1 = determinant2d(entries[1][1],entries[1][2], entries[2][1], entries[2][2])/det;
        double d2 = determinant2d(entries[1][0],entries[1][2], entries[2][0], entries[2][2])/det;
        double d3 = determinant2d(entries[1][0],entries[1][1], entries[2][0], entries[2][1])/det;
        double d4 = determinant2d(entries[0][1],entries[1][2], entries[2][1], entries[2][2])/det;
        double d5 = determinant2d(entries[0][0],entries[1][2], entries[2][0], entries[2][2])/det;
        double d6 = determinant2d(entries[0][0],entries[1][1], entries[2][0], entries[2][1])/det;
        double d7 = determinant2d(entries[0][1],entries[1][2], entries[1][1], entries[2][2])/det;
        double d8 = determinant2d(entries[0][0],entries[1][2], entries[1][0], entries[2][2])/det;
        double d9 = determinant2d(entries[0][0],entries[1][1], entries[1][0], entries[2][1])/det;

        return new mat(d1,-d2,d3,d4,-d5,d6,d7,-d8,d9).transpose();
    }


}
