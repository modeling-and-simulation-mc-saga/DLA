package dla;

import java.awt.Point;
import java.util.Random;

/**
 * DLAの共通クラス
 *
 * @author tadaki
 */
public abstract class AbstractDLA {

    protected final int radius;//中心からの最大半径
    protected final int[][] cells;//クラスタ
    protected final int n;//radius * 2
    protected final Random random;//乱数生成器

    /**
     * コンストラクタ
     *
     * @param radius
     * @param random
     */
    public AbstractDLA(int radius, Random random) {
        this.radius = radius;
        n = 2 * radius;
        cells = new int[n][n];
        cells[radius][radius] = 1;
        this.random = random;
    }

    abstract public Point oneStep();

    /**
     * 領域の外側に出た粒子を反対側から入れる
     *
     * @param p
     */
    protected void adjustPosition(Point p) {
        int x = (p.x + n) % n;
        int y = (p.y + n) % n;
        p.setLocation(x, y);
    }

    /**
     * クラスタの隣接点に居るかを判定
     *
     * @param p
     * @return
     */
    protected final boolean isAdjacent(Point p) {
        boolean b = (cells[p.x][(p.y + 1) % n] == 1)
                || (cells[p.x][(p.y - 1 + n) % n] == 1)
                || (cells[(p.x + 1) % n][p.y] == 1)
                || (cells[(p.x - 1 + n) % n][p.y] == 1);
        return b;
    }

    /**
     * 境界にランダムに再配置
     *
     * @param p
     */
    protected void setNewPosition(Point p) {
        int k = random.nextInt(2);
        int r = random.nextInt(n);
        switch (k) {
            case 0:
                p.setLocation(0, r);
                break;
            default:
                p.setLocation(r, 0);
                break;
        }
    }
}
