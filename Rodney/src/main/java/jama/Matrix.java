package jama;

import java.io.*;
import java.text.*;
import java.util.*;

public class Matrix implements Cloneable, java.io.Serializable {

    /* ------------------------
   Class variables
 * ------------------------ */
    /**
     * Array for internal storage of elements.
     *
     * @serial internal array storage.
     */
    private double[][] A;

    /**
     * Row and column dimensions.
     *
     * @serial row dimension.
     * @serial column dimension.
     */
    private final int m, n;

    /* ------------------------
   Constructors
 * ------------------------ */
    /**
     * Construct an m-by-n matrix of zeros.
     *
     * @param m Number of rows.
     * @param n Number of colums.
     */
    public Matrix(int m, int n) {
        this.m = m;
        this.n = n;
        A = new double[m][n];
    }

    /**
     * Construct an m-by-n constant matrix.
     *
     * @param m Number of rows.
     * @param n Number of colums.
     * @param s Fill the matrix with this scalar value.
     */
    public Matrix(int m, int n, double s) {
        this.m = m;
        this.n = n;
        A = new double[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                A[i][j] = s;
    }

    /**
     * Construct a matrix quickly without checking arguments.
     *
     * @param A Two-dimensional array of doubles.
     * @param m Number of rows.
     * @param n Number of colums.
     */
    public Matrix(double[][] A, int m, int n) {
        this.A = A;
        this.m = m;
        this.n = n;
    }

    /**
     * Construct a matrix from a one-dimensional packed array
     *
     * @param vals One-dimensional array of doubles, packed by columns (ala
     * Fortran).
     * @param m Number of rows.
     * @exception IllegalArgumentException Array length must be a multiple of m.
     */
    public Matrix(double vals[], int m) {
        this.m = m;
        n = (m != 0 ? vals.length / m : 0);
        if (m * n != vals.length)
            throw new IllegalArgumentException("Array length must be a multiple of m.");
        A = new double[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                A[i][j] = vals[i + j * m];
    }

    /* ------------------------
   Public Methods
 * ------------------------ */
    /**
     * Make a deep copy of a matrix
     */
    public Matrix copy() {
        Matrix X = new Matrix(m, n);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++)
            System.arraycopy(A[i], 0, C[i], 0, n);
        return X;
    }

    /**
     * Clone the Matrix object.
     */
    @Override
    public Object clone() {
        return this.copy();
    }

    /**
     * Access the internal two-dimensional array.
     *
     * @return Pointer to the two-dimensional array of matrix elements.
     */
    public double[][] getArray() {
        return A;
    }

    /**
     * Copy the internal two-dimensional array.
     *
     * @return Two-dimensional array copy of matrix elements.
     */
    public double[][] getArrayCopy() {
        double[][] C = new double[m][n];
        for (int i = 0; i < m; i++)
            System.arraycopy(A[i], 0, C[i], 0, n);
        return C;
    }

    /**
     * Make a one-dimensional column packed copy of the internal array.
     *
     * @return Matrix elements packed in a one-dimensional array by columns.
     */
    public double[] getColumnPackedCopy() {
        double[] vals = new double[m * n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                vals[i + j * m] = A[i][j];
        return vals;
    }

    /**
     * Make a one-dimensional row packed copy of the internal array.
     *
     * @return Matrix elements packed in a one-dimensional array by rows.
     */
    public double[] getRowPackedCopy() {
        double[] vals = new double[m * n];
        for (int i = 0; i < m; i++)
            System.arraycopy(A[i], 0, vals, i * n, n);
        return vals;
    }

    /**
     * Get row dimension.
     *
     * @return m, the number of rows.
     */
    public int getRowDimension() {
        return m;
    }

    /**
     * Get column dimension.
     *
     * @return n, the number of columns.
     */
    public int getColumnDimension() {
        return n;
    }

    /**
     * Get a single element.
     *
     * @param i Row index.
     * @param j Column index.
     * @return A(i,j)
     * @exception ArrayIndexOutOfBoundsException
     */
    public double get(int i, int j) {
        return A[i][j];
    }

    /**
     * Set a single element.
     *
     * @param i Row index.
     * @param j Column index.
     * @param s A(i,j).
     * @exception ArrayIndexOutOfBoundsException
     */
    public void set(int i, int j, double s) {
        A[i][j] = s;
    }

    /**
     * Matrix transpose.
     *
     * @return A'
     */
    public Matrix transpose() {
        Matrix X = new Matrix(n, m);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                C[j][i] = A[i][j];
        return X;
    }

    /**
     * Unary minus
     *
     * @return -A
     */
    public Matrix uminus() {
        Matrix X = new Matrix(m, n);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = -A[i][j];
        return X;
    }

    /**
     * C = A + B
     *
     * @param B another matrix
     * @return A + B
     */
    public Matrix plus(Matrix B) {
        checkMatrixDimensions(B);
        Matrix X = new Matrix(m, n);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] + B.A[i][j];
        return X;
    }

    /**
     * A = A + B
     *
     * @param B another matrix
     * @return A + B
     */
    public Matrix plusEquals(Matrix B) {
        checkMatrixDimensions(B);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                A[i][j] = A[i][j] + B.A[i][j];
        return this;
    }

    /**
     * C = A - B
     *
     * @param B another matrix
     * @return A - B
     */
    public Matrix minus(Matrix B) {
        checkMatrixDimensions(B);
        Matrix X = new Matrix(m, n);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] - B.A[i][j];
        return X;
    }

    /**
     * A = A - B
     *
     * @param B another matrix
     * @return A - B
     */
    public Matrix minusEquals(Matrix B) {
        checkMatrixDimensions(B);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                A[i][j] = A[i][j] - B.A[i][j];
        return this;
    }

    /**
     * Element-by-element multiplication, C = A.*B
     *
     * @param B another matrix
     * @return A.*B
     */
    public Matrix arrayTimes(Matrix B) {
        checkMatrixDimensions(B);
        Matrix X = new Matrix(m, n);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] * B.A[i][j];
        return X;
    }

    /**
     * Element-by-element multiplication in place, A = A.*B
     *
     * @param B another matrix
     * @return A.*B
     */
    public Matrix arrayTimesEquals(Matrix B) {
        checkMatrixDimensions(B);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                A[i][j] = A[i][j] * B.A[i][j];
        return this;
    }

    /**
     * Element-by-element right division, C = A./B
     *
     * @param B another matrix
     * @return A./B
     */
    public Matrix arrayRightDivide(Matrix B) {
        checkMatrixDimensions(B);
        Matrix X = new Matrix(m, n);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] / B.A[i][j];
        return X;
    }

    /**
     * Element-by-element right division in place, A = A./B
     *
     * @param B another matrix
     * @return A./B
     */
    public Matrix arrayRightDivideEquals(Matrix B) {
        checkMatrixDimensions(B);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                A[i][j] = A[i][j] / B.A[i][j];
        return this;
    }

    /**
     * Element-by-element left division, C = A.\B
     *
     * @param B another matrix
     * @return A.\B
     */
    public Matrix arrayLeftDivide(Matrix B) {
        checkMatrixDimensions(B);
        Matrix X = new Matrix(m, n);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = B.A[i][j] / A[i][j];
        return X;
    }

    /**
     * Element-by-element left division in place, A = A.\B
     *
     * @param B another matrix
     * @return A.\B
     */
    public Matrix arrayLeftDivideEquals(Matrix B) {
        checkMatrixDimensions(B);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                A[i][j] = B.A[i][j] / A[i][j];
        return this;
    }

    /**
     * Multiply a matrix by a scalar, C = s*A
     *
     * @param s scalar
     * @return s*A
     */
    public Matrix times(double s) {
        Matrix X = new Matrix(m, n);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = s * A[i][j];
        return X;
    }

    /**
     * Multiply a matrix by a scalar in place, A = s*A
     *
     * @param s scalar
     * @return replace A by s*A
     */
    public Matrix timesEquals(double s) {
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                A[i][j] = s * A[i][j];
        return this;
    }

    /**
     * Linear algebraic matrix multiplication, A * B
     *
     * @param B another matrix
     * @return Matrix product, A * B
     * @exception IllegalArgumentException Matrix inner dimensions must agree.
     */
    public Matrix times(Matrix B) {
        if (B.m != n)
            throw new IllegalArgumentException("Matrix inner dimensions must agree.");
        Matrix X = new Matrix(m, B.n);
        double[][] C = X.getArray();
        double[] Bcolj = new double[n];
        for (int j = 0; j < B.n; j++) {
            for (int k = 0; k < n; k++)
                Bcolj[k] = B.A[k][j];
            for (int i = 0; i < m; i++) {
                double[] Arowi = A[i];
                double s = 0;
                for (int k = 0; k < n; k++)
                    s += Arowi[k] * Bcolj[k];
                C[i][j] = s;
            }
        }
        return X;
    }

    /**
     * Generate matrix with random elements
     *
     * @param m Number of rows.
     * @param n Number of colums.
     * @return An m-by-n matrix with uniformly distributed random elements.
     */
    public static Matrix random(int m, int n) {
        Matrix A = new Matrix(m, n);
        double[][] X = A.getArray();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                X[i][j] = Math.random();
        return A;
    }

    /**
     * Generate identity matrix
     *
     * @param m Number of rows.
     * @param n Number of colums.
     * @return An m-by-n matrix with ones on the diagonal and zeros elsewhere.
     */
    public static Matrix identity(int m, int n) {
        Matrix A = new Matrix(m, n);
        double[][] X = A.getArray();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                X[i][j] = (i == j ? 1.0 : 0.0);
        return A;
    }

    /**
     * Print the matrix to stdout. Line the elements up in columns with a
     * Fortran-like 'Fw.d' style format.
     *
     * @param w Column width.
     * @param d Number of digits after the decimal.
     */
    public void print(int w, int d) {
        print(new PrintWriter(System.out, true), w, d);
    }

    /**
     * Print the matrix to the output stream. Line the elements up in columns
     * with a Fortran-like 'Fw.d' style format.
     *
     * @param output Output stream.
     * @param w Column width.
     * @param d Number of digits after the decimal.
     */
    public void print(PrintWriter output, int w, int d) {
        DecimalFormat format = new DecimalFormat();
        format.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
        format.setMinimumIntegerDigits(1);
        format.setMaximumFractionDigits(d);
        format.setMinimumFractionDigits(d);
        format.setGroupingUsed(false);
        print(output, format, w + 2);
    }

    /**
     * Print the matrix to stdout. Line the elements up in columns. Use the
     * format object, and right justify within columns of width characters. Note
     * that is the matrix is to be read back in, you probably will want to use a
     * NumberFormat that is set to US Locale.
     *
     * @param format A Formatting object for individual elements.
     * @param width Field width for each column.
     * @see java.text.DecimalFormat#setDecimalFormatSymbols
     */
    public void print(NumberFormat format, int width) {
        print(new PrintWriter(System.out, true), format, width);
    }

    // DecimalFormat is a little disappointing coming from Fortran or C's printf.
    // Since it doesn't pad on the left, the elements will come out different
    // widths.  Consequently, we'll pass the desired column width in as an
    // argument and do the extra padding ourselves.
    /**
     * Print the matrix to the output stream. Line the elements up in columns.
     * Use the format object, and right justify within columns of width
     * characters. Note that is the matrix is to be read back in, you probably
     * will want to use a NumberFormat that is set to US Locale.
     *
     * @param output the output stream.
     * @param format A formatting object to format the matrix elements
     * @param width Column width.
     * @see java.text.DecimalFormat#setDecimalFormatSymbols
     */
    public void print(PrintWriter output, NumberFormat format, int width) {
        output.println();  // start on new line.
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                String s = format.format(A[i][j]); // format the number
                int padding = Math.max(1, width - s.length()); // At _least_ 1 space
                for (int k = 0; k < padding; k++)
                    output.print(' ');
                output.print(s);
            }
            output.println();
        }
        output.println();   // end with blank line.
    }

    /* ------------------------
   Private Methods
 * ------------------------ */
    /**
     * Check if size(A) == size(B) *
     */
    private void checkMatrixDimensions(Matrix B) {
        if (B.m != m || B.n != n)
            throw new IllegalArgumentException("Matrix dimensions must agree.");
    }

    private static final long serialVersionUID = 1;
}
