package it.unibo.inner.impl;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T>{
    private List<T> elements;
    private Predicate<T> filter;

    
    public IterableWithPolicyImpl(T[] alist){  //accetto lista di qualsiasi tipo, ma che sia dello stesso tipo di Iterable
        //this. campi e metodi
        //this() mi riferisco ai costruttori
        this(alist, new Predicate<T>(){           //utilizza il secondo costruttore, predicato ha un solo metodo
            @Override                               //classe anonima e dare comportamento specifico della classe anonima
            public boolean test(T elem) {
                return true;  
            }
        }); 
    }

    public IterableWithPolicyImpl(T[] alist, Predicate<T> filter){
        this.elements = List.of(alist);
        this.filter = filter;
    }

    public class IteratorImpl implements Iterator<T>{
        private int index = 0;

        @Override
        public boolean hasNext() {
            while(index < elements.size()){
                T elem = elements.get(index);
                if(filter.test(elem)){
                    return true;
                }
                index++;
            }
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
    public Iterator<T> iterator() {
        return new IteratorImpl();    
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.filter = filter;   
    }
}
