package dla;

import java.awt.geom.Point2D;
import java.util.Random;

/**
 *
 * @author tadaki
 */
public class Particle {

    private final Point2D.Double position;
    private final Point2D.Double velocity;

    public Particle(double x, double y) {
        position = new Point2D.Double(x, y);
        velocity = new Point2D.Double(0., 0.);
    }

    public Point2D.Double update(Random random) {
        velocity.x += .1 * random.nextDouble() - .05;
        velocity.y += .1 * random.nextDouble() - .05;
        position.x += velocity.x;
        position.y += velocity.y;
        velocity.x *= .99;
        velocity.y *= .99;
        return position;
    }

    public Point2D.Double getPosition() {
        return position;
    }
    
}
