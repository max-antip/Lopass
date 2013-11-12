package com.lopass.gui.custom;


import java.awt.*;

class CloseButtonLP extends Component {
    private int x;
    private int y;
    private int width;
    private int height;
    private float opaque = 0.4f;
    private Image closeImg;
    private Rectangle closeBounds;

    private Window parent;

    private Thread transparencyTread;
    private TransparencyRun transparencyRun;


    CloseButtonLP(Window parent, Image closeImg) {
        this.parent = parent;
        this.closeImg = closeImg;

        transparencyRun = new TransparencyRun();
        transparencyRun.setAppearance(true);
        transparencyRun.setPeriod(20);
        transparencyTread = new Thread(transparencyRun, "Close button custom Thrd");
        transparencyTread.start();

    }

    public void appear() {
        transparencyRun.setAppearance(true);
    }

    public void disappear() {
        transparencyRun.setAppearance(false);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private Image getCloseImg() {
        return closeImg;
    }

    private void setCloseImg(Image closeImg) {
        this.closeImg = closeImg;
    }

    private float getOpaque() {
        return opaque;
    }

    private void setOpaque(float opaque) {
        this.opaque = opaque;
    }

    public Rectangle getBounds() {
        if (closeBounds == null) {
            closeBounds = new Rectangle(x, y, width, height);
        }
        return closeBounds;
    }

    private void setCloseBounds(Rectangle closeBounds) {
        this.closeBounds = closeBounds;
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AlphaComposite ac = java.awt.AlphaComposite.
                getInstance(AlphaComposite.SRC_OVER, opaque);
        g2d.setComposite(ac);

        //location
        x = parent.getWidth() - 20;
        y = 10;
        g2d.drawImage(closeImg, x, y, width, height, null);
    }

    class TransparencyRun implements Runnable {
        private boolean appearance;
        private int period;

        private boolean isStopped;
        Object monitor = new Object();

        @Override
        public void run() {
            try {

                for (; ; ) {

                    if (parent != null && !isStopped) {
                        if (appearance) {
                            if (opaque < 0.9F) {
                                opaque = opaque + 0.1F;
                                parent.repaint();
                            }
                        } else {
                            if (opaque > 0.4F) {
                                opaque = opaque - 0.1F;
                                parent.repaint();
                            }
                        }
                        Thread.sleep(period);
                    } else {
                        break;
                    }

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        public void setAppearance(boolean appearance) {
            this.appearance = appearance;
        }

        public int getPeriod() {
            return period;
        }

        public void setPeriod(int period) {
            this.period = period;
        }

        public void stop() {
            isStopped = true;
        }
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b == false) {
            transparencyRun.stop();
        }
    }
}

