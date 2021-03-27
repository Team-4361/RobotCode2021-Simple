package frc.robot.util;

import javax.swing.*;

import frc.robot.auton.Autonomous;

import java.awt.*;
import java.awt.geom.Line2D;

import me.wobblyyyy.edt.DynamicArray;
import me.wobblyyyy.pathfinder.geometry.HeadingPoint;

public class PathDrawTest {
    public static class PointSetDrawer extends JComponent {
        private final DynamicArray<HeadingPoint> points;

        public PointSetDrawer(DynamicArray<HeadingPoint> points) {
            this.points = points;
        }

        protected void draw(Graphics2D graphics, HeadingPoint point) {
            graphics.draw(new Line2D.Double(
                point.getX() + 100,
                point.getY() + 100,
                point.getX() + 100,
                point.getY() + 100
            ));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            points.itr().forEach(point -> {
                draw(g2, point);
            });
        }
    }

    public void drawBarrelPath() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        panel.setLayout(new FlowLayout());

        PointSetDrawer drawer = new PointSetDrawer(Autonomous.barrelPath);
        
        frame.setLayout(new BorderLayout());
        frame.add(drawer);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        try {
//            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
