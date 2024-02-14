package ru.job4j.pool;

public class Sums {

    private int rowSum;
    private int colSum;

    public Sums() {
    }

    public Sums(int rowSum, int colSum) {
        this.rowSum = rowSum;
        this.colSum = colSum;
    }

    public int getRowSum() {
        return rowSum;
    }

    public void setRowSum(int rowSum) {
        this.rowSum = rowSum;
    }

    public int getColSum() {
        return colSum;
    }

    public void setColSum(int colSum) {
        this.colSum = colSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sums sums = (Sums) o;
        if (rowSum != sums.rowSum) {
            return false;
        }
        return colSum == sums.colSum;
    }

    @Override
    public int hashCode() {
        int result = rowSum;
        result = 31 * result + colSum;
        return result;
    }
}
