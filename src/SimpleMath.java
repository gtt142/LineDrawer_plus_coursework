public class SimpleMath {
    public static Vector3d crossProduct (Vector3d vectorA, Vector3d vectorB) {
        double x = vectorA.getY()*vectorB.getZ() - vectorA.getZ()*vectorB.getY();
        double y = vectorA.getZ()*vectorB.getX() - vectorA.getX()*vectorB.getZ();
        double z = vectorA.getX()*vectorB.getY() - vectorA.getY()*vectorB.getX();
        Vector3d vectorC = new Vector3d(x, y, z);

        return vectorC;
    }

    public static class Vector3d {
        private double x;
        private double y;
        private double z;

        public Vector3d() {
        }

        public Vector3d(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        public Vector3d(double x, double y) {
            this.x = x;
            this.y = y;
            this.z = 0.0;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getZ() {
            return z;
        }

        public void setZ(double z) {
            this.z = z;
        }

        @Override
        public String toString() {
            return "(" + x + ";" + y + ";" + z + ")";
        }
    }
}
