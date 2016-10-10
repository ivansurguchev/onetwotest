package com.ivansurguchev.onetwotest;

import android.util.Log;

import com.ivansurguchev.onetwotest.entity.Hotel;
import com.ivansurguchev.onetwotest.entity.HotelGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ivansurguchev.onetwotest.Common.MAX_GROUPS_COUNT;
import static com.ivansurguchev.onetwotest.Common.MIN_GROUP_SIZE;
import static com.ivansurguchev.onetwotest.Common.ranges;

/**
 * Sorts hotels lists
 */

public class HotelSorter {

    private final static String LOG_TAG = HotelSorter.class.getSimpleName();

    /**
     * @param hotels Unsorted list of hotels
     * @return Groups of hotels sorted by price
     */
    public static List<HotelGroup> sortToGroups(List<Hotel> hotels) {
        Log.d(LOG_TAG, "Sorting the list of " + hotels.size() + " hotels...");
        sortByPrice(hotels);
        List<HotelGroup> groups = divideListToHotelGroups(hotels);
        if (hotels.size() > MIN_GROUP_SIZE) {
            mergeSmallGroups(groups, MIN_GROUP_SIZE);
        } else {
            mergeSmallGroups(groups, 1);
        }
        setMaxGroupsCount(groups, MAX_GROUPS_COUNT);
        Log.d(LOG_TAG, "Sorting completed! " + groups.size() + " hotel groups created!");
        return groups;
    }

    /**
     * @param hotels Unsorted list of hotels
     * @return Sorted (asc.) by price list of hotels
     */
    private static void sortByPrice(List<Hotel> hotels) {
        Collections.sort(hotels);
    }

    /**
     * Works with hotel price
     * @param hotels Sorted (asc.) list of hotels.
     * @return List of hotel groups, divided by price.
     */
    private static List<HotelGroup> divideListToHotelGroups(List<Hotel> hotels) {
        List<HotelGroup> groups = new ArrayList<>(ranges.length - 1);
        int j = 0;

        for (int i = 0; i < hotels.size(); i++) {
            while(j < ranges.length - 1) {
                HotelGroup g ;
                if (j >= groups.size()) {
                    g = new HotelGroup();
                    g.minPrice = ranges[j];
                    g.maxPrice = ranges[j+1];
                    groups.add(g);
                } else {
                    g = groups.get(j);
                }

                if (hotels.get(i).price < g.maxPrice) {
                    g.hotels.add(hotels.get(i));
                    break;
                } else {
                    j++;
                }
            }
        }
        return groups;
    }

    /**
     * @param groups
     * @return List of groups of hotels with MIN_GROUP_SIZE hotels or more.
     */
    private static void mergeSmallGroups(List<HotelGroup> groups, int minSize) {
        for (int i = 0; i < groups.size() - 1; i++) {
            HotelGroup g1 = groups.get(i);
            HotelGroup g2 = groups.get(i+1);

            if (g1.hotels.size() < minSize || g2.hotels.size() < minSize) {
                g1.maxPrice = g2.maxPrice;
                g1.hotels.addAll(g2.hotels);
                groups.remove(g2);
                i--;
            }
        }
    }

    /**
     * Merges groups if its count is more than maxCount
     * @param groups
     * @param maxCount
     */
    private static void setMaxGroupsCount(List<HotelGroup> groups, int maxCount) {
        if (groups.size() <= maxCount) {
            return;
        }
        // Here we just merge the cheapest groups, because usually
        // users are more interested in it
        while (groups.size() > maxCount) {
            HotelGroup g1 = groups.get(0);
            HotelGroup g2 = groups.get(1);

            g1.maxPrice = g2.maxPrice;
            g1.hotels.addAll(g2.hotels);
            groups.remove(g2);
        }
    }

}
