package bme;

public class SPair<T, R> {
    T key;
    R value;
    public SPair(T key, R value) {
        this.key = key;
        this.value = value;
    }
    public T getKey() {
        return key;
    }
    public R getValue() {
        return value;
    }
}
