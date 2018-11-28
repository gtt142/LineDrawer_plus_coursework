public class Line {
    private Node node1;
    private Node node2;

    public Line(Double x0, Double y0, Double x1, Double y1) {
        this.node1 = new Node(x0, y0);
        this.node2 = new Node(x1, y1);
    }

    public Double getX0() {
        return node1.getX();
    }

    public void setX0(Double x0) {
        this.node1.setX(x0);
    }

    public Double getY0() {
        return node1.getY();
    }

    public void setY0(Double y0) {
        this.node1.setY(y0);
    }

    public Double getX1() {
        return node2.getX();
    }

    public void setX1(Double x1) {
        this.node2.setX(x1);
    }

    public Double getY1() {
        return node2.getY();
    }

    public void setY1(Double y1) {
        this.node2.setY(y1);
    }

    public Node getNeighbour(Node node) {
        if(node.getX().equals(this.node1.getX()) && node.getY().equals(this.node1.getY()))
            return node2;
        else return node1;
    }

    public Node getNode1() {
        return node1;
    }

    public void setNode1(Node node1) {
        this.node1 = node1;
    }

    public Node getNode2() {
        return node2;
    }

    public void setNode2(Node node2) {
        this.node2 = node2;
    }

    public Node getNodeByCoordinate(double x, double y) {
        if(this.node1.getX().equals(x) && this.node1.getY().equals(y))
            return node1;
        if(this.node2.getX().equals(x) && this.node2.getY().equals(y))
            return node2;
        return null;
    }

    /**
     * Check lines intersection using https://habr.com/post/267037/ method
     *
     * @param line
     * @return {@code true} if the line intersect our line or on a same line with our one;
     *      {@code false} otherwise.
     */
    public boolean checkIntersection (Line line) {
        double x;
        double y;
        x = this.node2.getX() - this.node1.getX();
        y = this.node2.getY() - this.node1.getY();
        SimpleMath.Vector3d v1 = new SimpleMath.Vector3d(x, y);
        x = line.node1.getX() - this.node1.getX();
        y = line.node1.getY() - this.node1.getY();
        SimpleMath.Vector3d v2 = new SimpleMath.Vector3d(x, y);
        x = line.node2.getX() - this.node1.getX();
        y = line.node2.getY() - this.node1.getY();
        SimpleMath.Vector3d v3 = new SimpleMath.Vector3d(x, y);

        double z1 = SimpleMath.crossProduct(v1, v2).getZ();
        double z2 = SimpleMath.crossProduct(v1, v3).getZ();

        if(z1 == 0.0 && z2 == 0.0) {
            return true;
        }
        if(z1 == 0.0 || z2 == 0.0) {
            return true;
        }
        if((z1 > 0.0 && z2 > 0.0) || (z1 < 0.0 && z2 < 0.0)) {
            return false;
        }

        x = line.node2.getX() - line.node1.getX();
        y = line.node2.getY() - line.node1.getY();
        SimpleMath.Vector3d v4 = new SimpleMath.Vector3d(x, y);
        x = this.node1.getX() - line.node1.getX();
        y = this.node1.getY() - line.node1.getY();
        SimpleMath.Vector3d v5 = new SimpleMath.Vector3d(x, y);
        x = this.node2.getX() - line.node1.getX();
        y = this.node2.getY() - line.node1.getY();
        SimpleMath.Vector3d v6 = new SimpleMath.Vector3d(x, y);

        double z3 = SimpleMath.crossProduct(v4, v5).getZ();
        double z4 = SimpleMath.crossProduct(v4, v6).getZ();
        if ((z3 > 0.0 && z4 > 0.0) || (z3 < 0.0 && z4 < 0.0)) {
            return false;
        }

        return true;
    }

}
