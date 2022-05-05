package alternative;

import java.util.Objects;

public class Coordinate implements Comparable<Coordinate>{
    public final int _x;
    public final int _y;

    public Coordinate(final int x, final int y) {
        if (x < 1|| x > 9 || y < 1|| y > 9){
            throw new IndexOutOfBoundsException();
        }
        _x = x;
        _y = y;
    }
    public boolean coodinateIsEqual(int x, int y){
        if (x == _x){
            if (y == _y){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Coordinate that = (Coordinate) o;
        return _x == that._x && _y == that._y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_x, _y);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "_x=" + _x +
                ", _y=" + _y +
                '}';
    }

    @Override
    public int compareTo(Coordinate o) {
        int ownValue = (_x-1)*9 + _y;
        int comparedValue = (o._x-1)*9 + o._y;
        return ownValue-comparedValue;
    }
}
