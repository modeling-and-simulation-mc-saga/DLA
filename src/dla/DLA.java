package dla;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

/**
 * 粒子を一つ一つ入れる方法
 *
 * @author tadaki
 */
public class DLA extends AbstractDLA {

    public DLA(int radius, Random random) {
        super(radius, random);
    }

    @Override
    public Point oneStep() {
        return inject();
    }

    public Point inject() {
        Point p = new Point(0, 0);
        setNewPosition(p);//境界に粒子を設定

        while (true) {//吸着するまで繰り返す
            if (isAdjacent(p)) {//クラスタに吸着
                cells[p.x][p.y] = 1;
                return p;
            }
            //ランダムに移動
            int k = random.nextInt(4);
            switch (k) {
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
        String filename = DLA.class.getSimpleName() + "-output.txt";
        
        Random random = new Random(seed);
        DLA dla = new DLA(radius, random);
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
