/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Adambots 245
 */
public class Listy {
    private Object[] _internal;
    private int currentSize;
    public Listy() {
        _internal = new Object[100];
    }
    
    public int size() {
        return currentSize;
    }
    public void add(Object o) {
        if (currentSize >= _internal.length) {
            Object[] k = new Object[_internal.length + 100];
            for (int i = 0; i < _internal.length; i++) {
                k[i] = _internal[i];
            }
            _internal = k;
        }
        _internal[currentSize] = o;
        currentSize++;
    }
    public Object get(int i) {
        return _internal[i];
    }
}
