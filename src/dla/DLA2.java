package dla;

import java.awt.Point;
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
public class DLA2 extends AbstractDLA {

    private final int numParticles;//粒子数
    private final List<Point> particles;

    public DLA2(int radius, int numParticles, Random random) {
        super(radius, random);

        particles = new ArrayList<>();
        this.numParticles = numParticles;
        int count = 0;
        while (count < numParticles) {
            int x = random.nextInt(n);
            int y = random.nextInt(n);
            Point p = new Point(x, y);
            if (!isAdjacent(p)) {
                particles.add(p);
                count++;
            }
        }
    }

    @Override
    public Point oneStep() {
        while (true) {
            int k = random.nextInt(numParticles);
            Point p = particles.get(k);
            if (cells[p.x][p.y] == 1) {
                setNewPosition(p);
            }
            if (isAdjacent(p)) {
                cells[p.x][p.y] = 1;
                Point pp = new Point(p.x, p.y);
                setNewPosition(p);
                return pp;
            }
            int d = random.nextInt(4);
            switch (d) {
                case 0:
                    p.translate(1, 0);
                    break;
                case 1:
                    p.translate(0, 1);
                    break;
                case 2:
                    p.translate(-1, 0);
                    break;
                default:
                    p.translate(0, -1);
                    break;
            }
            adjustPosition(p);
        }
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        long seed = 3212L;//乱数シード
        int radius = 500;//最大半径
        int maxNum = 30000;//最大吸着粒子数
        int numParticles = 1000;//同時浮動する粒子数
        String filename = DLA2.class.getSimpleName() + "-output.txt";

        Random random = new Random(seed);
        DLA2 dla = new DLA2(radius, numParticles, random);

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
            }
        }
    }
}
