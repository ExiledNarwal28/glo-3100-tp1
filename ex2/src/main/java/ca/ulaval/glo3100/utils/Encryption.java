package ca.ulaval.glo3100.utils;

public interface Encryption<T> {
    T encrypt(T firstElement, T secondElement);
}
