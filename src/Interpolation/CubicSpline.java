package Interpolation;

import Main.Point;

public class CubicSpline {
    Spline[] splines;

    private class Spline {
        public double a, b, c, d, x;
    }

    public CubicSpline(Point[] points) {
        int n = points.length;
        splines = new Spline[n];

        for (int i = 0; i < n; i++) {
            splines[i] = new Spline();
            splines[i].x = points[i].getX();
            splines[i].a = points[i].getY();
        }
        splines[0].c = splines[n - 1].c = 0;

        double[] alpha = new double[n - 1];
        double[] beta = new double[n - 1];
        alpha[0] = beta[0] = 0;

        for (int i = 1; i < n - 1; i++) {
            double A = points[i].getX() - points[i - 1].getX();
            double B = points[i + 1].getX() - points[i].getX();
            double C = 2 * (A + B);
            double F = 6 * ((points[i + 1].getY() - points[i].getY()) / B
                    - (points[i].getY() - points[i - 1].getY()) / A);

            alpha[i] = -1 * B / (A * alpha[i - 1] + C);
            beta[i] = (F - A * beta[i - 1]) / (A * alpha[i - 1] + C);
        }

        for (int i = n - 2; i > 0 ; i--) {
            splines[i].c = alpha[i] * splines[i + 1].c + beta[i];
        }

        for (int i = n - 1; i > 0; i--)
        {
            double hi = points[i].getX() - points[i - 1].getX();
            splines[i].d = (splines[i].c - splines[i - 1].c) / hi;
            splines[i].b = hi * (2.0 * splines[i].c + splines[i - 1].c) / 6.0
                    + (points[i].getY() - points[i - 1].getY()) / hi;
        }
    }

    public double interpolate(double x) {
        int n = splines.length;
        Spline s;

        if (x <= splines[0].x) {
            s = splines[1];
        } else if (x >= splines[n - 1].x) {
            s = splines[n - 1];
        } else {
            int i = 0;
            int j = n - 1;
            while (i + 1 < j)
            {
                int k = i + (j - i) / 2;
                if (x <= splines[k].x)
                {
                    j = k;
                }
                else
                {
                    i = k;
                }
            }
            s = splines[j];
        }

        return s.a + s.b * (x - s.x) + s.c / 2 * Math.pow(x - s.x, 2) + s.d / 6 * Math.pow(x - s.x, 3);
    }
}