package io.bnguyen.vocare.server.db;

import java.util.Collection;

public interface Store<T>
{
    public void clear();
    public void add(T element);
    public T    findById(int id);
    public Collection<T> values();
}
