package com.ivansurguchev.onetwotest.entity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ivansurguchev on 07.10.16.
 */

public class HotelGroup {
    public int minPrice;
    public int maxPrice;
    public List<Hotel> hotels = new LinkedList<>();
}
