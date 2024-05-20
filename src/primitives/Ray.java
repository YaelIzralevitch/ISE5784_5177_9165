package primitives;

/**
 * This class contains functions and calculations on a Ray
 */
public class Ray {
    private final Point head;
    private final Vector direction;

    /**
     * Constructor with parameters
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    /**
     * @return  head
     */
    public Point getHead() { return head; }

    /**
     * @return direction
     */
    public Vector getDirection() { return direction; }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray ray)
                && this.head.equals(ray.head)
                && this.direction.equals(ray.direction);

    }

    /**
     * toString function
     */
    @Override
    public String toString() {
        return this.head + " " + this.direction;
    }
}
