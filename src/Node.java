public class Node {
    private Double x;
    private Double y;

    public Node(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public boolean equals(Node node) {
        if (node.getX().equals(this.x) && node.getY().equals(this.y))
            return true;
        return false;
    }
}
