package ca.ulaval.glo3100.utils;

public interface Operation<T> {
    T operate(T firstElement, T secondElement);
}
