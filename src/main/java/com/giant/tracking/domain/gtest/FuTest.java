package com.giant.tracking.domain.gtest;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : com.giant.tracking.domain.gtest
 * fileName       : FuTest
 * author         : akfur
 * date           : 2024-05-20
 * description    :
 * ======================================================================
 * DATE             AUTHOR            NOTE
 * ----------------------------------------------------------------------
 * 2024-05-20         akfur
 */
public class FuTest<T> {

    public <T>void sss(FuTest<? extends T> asd) {



    }

    public static void main(String[] args) {
//        List<Object> data = new ArrayList<>();
//        data.add(new Fruit());
//        data.add(new Banana());
//        data.add(new Banana());
//
//        method(data);

        Fruit f1 = new Fruit();
        Apple f2 = new Apple();

        f1 = f2;

    }

    public static void method(List<? extends Fruit> item) {

    }
}
