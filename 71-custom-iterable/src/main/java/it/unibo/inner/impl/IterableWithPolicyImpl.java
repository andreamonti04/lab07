package it.unibo.inner.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T>{
    private List<T> elements;

    
    public IterableWithPolicyImpl(T[] alist){  //accetto lista di qualsiasi tipo, ma che sia dello stesso tipo di Iterable
        this.elements = List.of(alist);
    }


    public class IteratorImpl implements Iterator<T>{
        private int index = 0;


        @Override
        public boolean hasNext() {
            if(index < elements.size()){
                return true;
            }
            index++;
            return false;
        }

        @Override
        public T next() {
            if(hasNext()){
                return elements.get(index++);
            }
            throw new NoSuchElementException();            
        }
    }


    @Override
    public Iterator iterator() {
        return new IteratorImpl();    
    }

    @Override
    public void setIterationPolicy(Predicate filter) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setIterationPolicy'");
    }
}
