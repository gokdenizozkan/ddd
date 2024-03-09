package com.gokdenizozkan.ddd.core.datastructure;

import java.util.function.Function;

public record Tuple<TYPE> (
        TYPE left,
        TYPE right
) {

    public static <TYPE> Tuple<TYPE> of(TYPE left, TYPE right) {
        return new Tuple<>(left, right);
    }

    public <MAPPED_TYPE> Tuple<MAPPED_TYPE> mapped(Function<TYPE, MAPPED_TYPE> mapper) {
        return new Tuple<>(mapper.apply(left), mapper.apply(right));
    }
}
