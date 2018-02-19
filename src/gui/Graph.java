package gui;

import Interpolation.CubicSpline;
import Main.InitialData;
import Main.Point;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.*;




import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.function.Function;

import static java.lang.Math.abs;
import static java.lang.Math.floor;
import static java.lang.Math.round;
import static java.lang.System.exit;

public class Graph extends JPanel {
    public static Point[] displayPoints;

    public Graph() {
        Point[] points;
        points=solve();
        displayPoints=points;
        CubicSpline cubicSpline = new CubicSpline(points);
        paintGraphs(cubicSpline::interpolate, points, 0, points[points.length-1].getX());
    }

    static double equation1(double x,double y,double z)
    {
        return(z);              //(y'=z)   преобразование ОДУ второго порядка в систему из двух ОДУ 1 порядка
    }
    static double equation2(double x,double y,double z)
    {
        return(x+y+z);          //z'=f(x,y,z)
    }
    public static Point[] shoot(double x0,double y0,double z0,double xn,double h,int p)
    {
        double x,y,z,k1,k2,k3,k4,l1,l2,l3,l4,k,l,x1,y1,z1;
        x=x0;
        y=y0;
        z=z0;
        int i=0;
        Point[] points=new Point[(int)floor((xn-x0)/h)+1];
        points[0]=new Point(x0,y0);
        while(x<xn)
        {
            k1=h*equation1(x,y,z);
            l1=h*equation2(x,y,z);
            k2=h*equation1(x+h/2.0,y+k1/2.0,z+l1/2.0);
            l2=h*equation2(x+h/2.0,y+k1/2.0,z+l1/2.0);
            k3=h*equation1(x+h/2.0,y+k2/2.0,z+l2/2.0);
            l3=h*equation2(x+h/2.0,y+k2/2.0,z+l2/2.0);
            k4=h*equation1(x+h,y+k3,z+l3);
            l4=h*equation2(x+h,y+k3,z+l3);
            l=1/6.0*(l1+2*l2+2*l3+l4);                          //метод Рунге-Кутты
            k=1/6.0*(k1+2*k2+2*k3+k4);
            y1=y+k;
            x1=x+h;
            z1=z+l;
            x=x1;
            y=y1;
            z=z1;
            if(xn-x<h) h=xn-x;
            i++;
            if(p==1&&i<points.length)
            {
                points[i]=new Point(x,y);
            }
        }
        return(points);
    }
    static Point[] solve()
    {
        Scanner inp = new Scanner(System.in);
        double x0,y0,h,xn,yn,z0,m1,m2,m3,b,b1,b2,b3,e;
        int p=0;
        while (true) {
            try {
                System.out.println("\n  Введите x0:");
                x0 = Double.parseDouble(inp.next());
                break;
            } catch (Exception a) {
                System.out.println("Неверно введен аргумент");
            }
        }
        while (true) {
            try {
                System.out.println("\n  Введите y0:");
                y0 = Double.parseDouble(inp.next());
                break;
            } catch (Exception a) {
                System.out.println("Неверно введен аргумент");
            }
        }
        while (true) {
            try {
                System.out.println("\n  Введите xn:");
                xn = Double.parseDouble(inp.next());
                break;
            } catch (Exception a) {
                System.out.println("Неверно введен аргумент");
            }
        }
        while (true) {
            try {
                System.out.println("\n  Введите yn::");
                yn = Double.parseDouble(inp.next());
                break;
            } catch (Exception a) {
                System.out.println("Неверно введен аргумент");
            }
        }
        if(xn<x0){
            double tempx=x0,tempy=y0;
            x0=xn;
            y0=yn;
            xn=tempx;
            yn=tempy;
        }
        while (true) {
            try {
                System.out.println("\n  Введите h:");
                h = Double.parseDouble(inp.next());
                break;
            } catch (Exception a) {
                System.out.println("Неверно введен аргумент");
            }
        }
        while (true) {
            try {
                System.out.println("\n  Введите пробную производную в точке x0:");
                m1 = Double.parseDouble(inp.next());
                break;
            } catch (Exception a) {
                System.out.println("Неверно введен аргумент");
            }
        }
        b=yn;
        z0=m1;
        Point[] resPoints=shoot(x0,y0,z0,xn,h,p=1);
        b1=resPoints[resPoints.length-1].getY();
        if(abs(b1-b)<0.00005)
        {
            resPoints=shoot(x0,y0,z0,xn,h,p=1);
            e=resPoints[resPoints.length-1].getY();
            return(resPoints);
        }
        else
        {
            while (true) {
                try {
                    System.out.println("\n Введите вторую пробную производную в точке x0:");
                    m2 = Double.parseDouble(inp.next());
                    break;
                } catch (Exception a) {
                    System.out.println("Неверно введен аргумент");
                }
            }
            z0=m2;
            resPoints=shoot(x0,y0,z0,xn,h,p=1);
            b2=resPoints[resPoints.length-1].getY();
        }
        if(abs(b2-b)<0.00005)
        {
            resPoints=shoot(x0,y0,z0,xn,h,p=1);
            e=resPoints[resPoints.length-1].getY();
            return(resPoints);
        }
        else
        {
            m3=m2+(((m2-m1)*(b-b2))/(1.0*(b2-b1)));
            if(b1-b2==0)
                exit(0);
            z0=m3;
            resPoints=shoot(x0,y0,z0,xn,h,p=1);
            b3=resPoints[resPoints.length-1].getY();
        }
        if(abs(b3-b)<0.000005)
        {
            resPoints=shoot(x0,y0,z0,xn,h,p=1);
            e=resPoints[resPoints.length-1].getY();
            return(resPoints);
        }
        do
        {
            m1=m2;
            m2=m3;
            b1=b2;
            b2=b3;
            m3=m2+(((m2-m1)*(b-b2))/(1.0*(b2-b1)));
            z0=m3;
            resPoints=shoot(x0,y0,z0,xn,h,p=1);
            b3=resPoints[resPoints.length-1].getY();

        }while(abs(b3-b)<0.0005);
        z0=m3;
        resPoints=shoot(x0,y0,z0,xn,h,p=1);
        e=resPoints[resPoints.length-1].getY();
        return(resPoints);
    }

    public void paintGraphs(Function<Double, Double> intFunc, Point[] points, double a, double b) {
        XYSeries intSeries = new XYSeries("Результат интерполяции");
        XYSeries pointSeries = new XYSeries("Точки");

        for (Point point : points) {
            pointSeries.add(point.getX(), intFunc.apply(point.getX()));
        }

        for (double i = a; i < b; i += 0.1) {
            intSeries.add(i, intFunc.apply(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(intSeries);

        JFreeChart chart = ChartFactory.createXYLineChart(InitialData.getInstance().getFuncToString(), "x", "y",
                dataset, PlotOrientation.VERTICAL, true, true, false);

        XYPlot plot = chart.getXYPlot();
        chart.setBackgroundPaint(Color.lightGray);
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        renderer.setSeriesLinesVisible(2, false);
        renderer.setSeriesShapesVisible(2, true);
        renderer.setSeriesVisibleInLegend(2, false);
        renderer.setSeriesPaint(2, Color.BLUE);

        plot.setRenderer(renderer);

        ChartPanel chartPanel = new ChartPanel(chart);
        super.removeAll();
        chartPanel.setSize(800,600);
        super.add(chartPanel);
        super.add(new SouthPanel(this));

        super.validate();
        super.repaint();
    }
}