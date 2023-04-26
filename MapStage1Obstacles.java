import java.util.*;

public class MapStage1Obstacles {

    /* private MapStage1Obstacle1 obstacle1;
    private MapStage1Obstacle2 obstacle2;
    private MapStage1Obstacle3 obstacle3; */
    private MapObstacles obstacle1;
    private MapObstacles obstacle2;
    private MapObstacles obstacle3;
    /* private ArrayList<MapStage1Obstacles> obstacles; */

    public MapStage1Obstacles() {
        obstacle1 = new MapStage1Obstacle1();
        obstacle2 = new MapStage1Obstacle2();
        obstacle3 = new MapStage1Obstacle3();
    }

    class MapStage1Obstacle1 implements MapObstacles {
        
        private int x, y, width, height;

        public MapStage1Obstacle1() {
            x = 0;
            y = 64;
            width = 192;
            height = 64;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    class MapStage1Obstacle2 implements MapObstacles {
        
        private int x, y, width, height;

        public MapStage1Obstacle2() {
            x = 469;
            y = 320;
            width = 171;
            height = 128;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    class MapStage1Obstacle3 implements MapObstacles {
        
        private int x, y, width, height;

        public MapStage1Obstacle3() {
            x = 128;
            y = 448;
            width = 128;
            height = 128;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    public MapObstacles getObstacle1() {
        return obstacle1;
    }

    public MapObstacles getObstacle2() {
        return obstacle2;
    }

    public MapObstacles getObstacle3() {
        return obstacle3;
    }

    /* public int getObstacle1X() {
        return obstacle1.getX();
    }

    public int getObstacle1Y() {
        return obstacle1.getY();
    }

    public int getObstacle1Width() {
        return obstacle1.getWidth();
    }

    public int getObstacle1Height() {
        return obstacle1.getHeight();
    }

    public int getObstacle2X() {
        return obstacle2.getX();
    }

    public int getObstacle2Y() {
        return obstacle2.getY();
    }

    public int getObstacle2Width() {
        return obstacle2.getWidth();
    }

    public int getObstacle2Height() {
        return obstacle2.getHeight();
    }

    public int getObstacle3X() {
        return obstacle3.getX();
    }

    public int getObstacle3Y() {
        return obstacle3.getY();
    }

    public int getObstacle3Width() {
        return obstacle3.getWidth();
    }

    public int getObstacle3Height() {
        return obstacle3.getHeight();
    } */
}
