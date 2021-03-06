package Model.ADTs;

import Exceptions.InterpreterException;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public interface MyLatchTableInterface<V> {
    AtomicInteger allocate() throws InterpreterException;
    void add(Integer key, V value);
    V getElementWithKey(Integer key);
    void remove(Integer key) throws InterpreterException;
    void update(Integer key, V value);
    Set<Integer> keySet();
    Map<Integer, V> getDictionary();
    boolean containsKey(Integer key);
}