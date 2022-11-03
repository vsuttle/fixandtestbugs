package com.assetco.search.results;

import java.math.*;
import java.net.*;
import java.util.*;

/**
 * Used to generate test data.
 */
public class Any {

    private static Random random = new Random();

    static AssetVendorRelationshipLevel relationshipLevel() {
        return anyEnumerationValue(AssetVendorRelationshipLevel.class);
    }

    private static <T> T anyEnumerationValue(Class<T> clazz) {
        var values = clazz.getEnumConstants();
        return values[random.nextInt(values.length)];
    }

    /**
     * Generate a topic that can be used for testing.
     */
    static AssetTopic anyTopic() {
        return new AssetTopic(string(), string());
    }

    /**
     * Generate some purchase info that can be used for testing.
     */
    public static AssetPurchaseInfo assetPurchaseInfo() {
        return new AssetPurchaseInfo(anyLong(), anyLong(), money(), money());
    }

    /**
     * Generate some amount of money.
     */
    static Money money() {
        return new Money(new BigDecimal(anyLong()));
    }

    /**
     * Generate some URI.
     */
    public static URI URI() {
        return URI.create("https://" + string());
    }

    /**
     * Generate a String.
     */
    public static String string() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generate a long integer.
     */
    public static long anyLong() {
        return random.nextInt();
    }

    /**
     * Generate a vendor object that can be used for testing.
     */
    public static AssetVendor vendor() {
        return new AssetVendor(string(), string(), relationshipLevel(), anyLong());
    }

    /**
     * Generate a complete asset that can be used for testing.
     */
    public static Asset asset() {
        return new Asset(string(), string(), URI(), URI(), assetPurchaseInfo(), assetPurchaseInfo(), setOfTopics(), vendor());
    }

    /**
     * Generate a set of topics with at least one topic in it but not more than five.
     */
    public static List<AssetTopic> setOfTopics() {
        var result = new ArrayList<AssetTopic>();
        for (var count = 1 + random.nextInt(4); count > 0; --count)
            result.add(anyTopic());

        return result;
    }

    /**
     * Select some Hotspot key.
     */
    public static HotspotKey hotspotKey() {
        return anyEnumerationValue(HotspotKey.class);
    }
}
