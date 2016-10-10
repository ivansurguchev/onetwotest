package com.ivansurguchev.onetwotest.entity;

/**
 * Created by ivansurguchev on 07.10.16.
 */

public class Hotel implements Comparable<Hotel>{
    public long id;
    public int price;
    public String name;

    @Override
    public int compareTo(Hotel h) {
        return price - h.price;
    }
}
