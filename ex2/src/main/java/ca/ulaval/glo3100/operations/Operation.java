package ca.ulaval.glo3100.operations;

public interface Operation<T> {
    T operate(T firstElement, T secondElement);
}
