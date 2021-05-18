package dla;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 常時一定数の粒子が浮動
 *
 * @author tadaki
 */
public class DLAContinuous extends AbstractDLA {

    private final int numParticles;//粒子数
    private final List<Particle> particles;

    public DLAContinuous(int radius, int numParticles, Random random) {
        super(radius, random);

        particles = new ArrayList<>();
        this.numParticles = numParticles;
        int count = 0;
        while (count < numParticles) {
            double x = n * random.nextDouble();
            double y = n * random.nextDouble();
            Particle p = new Particle(x,y);
            if (!isAdjacent(p.getPosition())) {
                particles.add(p);
                count++;
            }
        }
    }

    private boolean isAdjacent(Point2D.Double p) {
        int x = (int) Math.ceil(p.x);
        int y = (int) Math.ceil(p.y);
        x = x % n;
        y = y % n;
        return isAdjacent(new Point(x, y));
    }

    @Override
    public Point oneStep() {
        while (true) {
            int k = random.nextInt(numParticles);
            Particle particle = particles.get(k);
            Point2D.Double p = particle.getPosition();
            int x = (int) Math.ceil(p.x);
            int y = (int) Math.ceil(p.y);
            x = x % n;
            y = y % n;
            if (cells[x][y] == 1) {
                setNewPosition(p);
            }
            if (isAdjacent(p)) {
                cells[x][y] = 1;
                Point pp = new Point(x, y);
                setNewPosition(p);
                return pp;
            }
            particle.update(random);
            adjustPosition(p);
        }
    }

    protected void setNewPosition(Point2D.Double p) {
        int k = random.nextInt(2);
        double r = n * random.nextDouble();
        switch (k) {
            case 0:
                p.setLocation(0., r);
                break;
            default:
                p.setLocation(r, 0.);
                break;
        }
    }

    protected void adjustPosition(Point2D.Double p) {
        double x = (p.x + n) % n;
        double y = (p.y + n) % n;
        p.setLocation(x, y);
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        long seed = 3212L;//乱数シード
        int radius = 500;//最大半径
        int maxNum = 30000;//最大吸着粒子数
        int numParticles = 10000;//同時浮動する粒子数
        String filename = DLAContinuous.class.getSimpleName() + "-output.txt";

        Random random = new Random(seed);
        DLAContinuous dla = new DLAContinuous(radius, numParticles, random);

        try (BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(new File(filename))))) {
            for (int t = 0; t < maxNum; t++) {
                Point p = dla.oneStep();
                if (p != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(p.x).append(" ").append(p.y);
                    out.write(sb.toString());
                    out.newLine();
                }
                System.out.println(t+" ("+p.x+","+p.y+")");
            }
        }
    }
}
