package com.miya.dto;

import java.lang.reflect.InvocationTargetException;

public abstract  class Converter<S,T> {
    protected abstract   T doForward(S s) throws InvocationTargetException, IllegalAccessException;
    protected  abstract  S doBackward(T t) throws InvocationTargetException, IllegalAccessException;
}
