package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;
import org.junit.jupiter.api.*;

import java.math.*;
import java.net.URI;
import java.util.*;

import static com.assetco.search.results.AssetVendorRelationshipLevel.*;
import static com.assetco.search.results.HotspotKey.Showcase;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class BugsTest {

  final static String METHOD_NOT_IMPLEMENTED = "This method has not been implemented";
  private final static Random random = new Random();

  private SearchResults searchresults;

  @BeforeEach
  void setUp() {
    this.searchresults = new SearchResults();
  }

  @Test
  public void precedingPartnerWithLongTrailingAssetsDoesNotWin() {
    final int PARTNER_VENDOR_SIZE = 4;

    final AssetVendor partnerVendor = makeVendor(Partner);
    final AssetVendor disruptingPartner = makeVendor(Partner);
    Asset missing = givenAssetInResultsWithVendor(partnerVendor);
    givenAssetInResultsWithVendor(disruptingPartner);

    ArrayList<Asset> expected =
      givenAssetsInResultsWithVendor(PARTNER_VENDOR_SIZE, partnerVendor);

    whenOptimize();
    thenHotspotDoesNotHave(Showcase, missing);
    thenHotspotHasExactly(Showcase, expected);
  }

  private AssetVendor makeVendor(AssetVendorRelationshipLevel relationshipLevel) {
    String id = Any.string();
    String displayName = Any.string();
    long royaltyRate = Any.anyLong();

    AssetVendor vendor = new AssetVendor(id, displayName, relationshipLevel, royaltyRate);
    printVendor(vendor);
    return vendor;
  }

  private Asset givenAssetInResultsWithVendor(AssetVendor vendor) {
    String id = Any.string();
    String title = Any.string();
    URI thumbnailURI = Any.URI();

    Asset asset = new Asset(
      Any.string(),                   // id
      Any.string(),                   // title
      Any.URI(),                      // thumbnailURI
      Any.URI(),                      // previewURI
      Any.assetPurchaseInfo(),        // purchaseInfoLast30Days
      Any.assetPurchaseInfo(),        // purchaseInfoLast24Hours
      Any.setOfTopics(),              // topics
      vendor                          // vendor
    );
    searchresults.addFound(asset);
    return asset;
    //    return null;
  }

  private Asset getAsset(AssetVendor vendor) {
    throw new RuntimeException(METHOD_NOT_IMPLEMENTED);
//    return null;
  }

  private AssetPurchaseInfo getPurchaseInfo() {
    throw new RuntimeException(METHOD_NOT_IMPLEMENTED);
//    return null;
  }

  private ArrayList<Asset> givenAssetsInResultsWithVendor(int count, AssetVendor vendor) {
    ArrayList result = new ArrayList<Asset>();
    for (int i = 0; i < count; ++i) {
      result.add(givenAssetInResultsWithVendor(vendor));
    }
    return result;
  }

  private void whenOptimize() {
    SearchResultHotspotOptimizer optimizer = new SearchResultHotspotOptimizer();
    optimizer.optimize(searchresults);
  }

  private void thenHotspotDoesNotHave(HotspotKey key, Asset... assets) {
    Hotspot hotspot = searchresults.getHotspot(key);
    List<Asset> members = hotspot.getMembers();
    for (Asset asset : assets) {
      assertFalse(members.contains(asset), "thenHotspotDoesNotHave failed");
    }
  }

  private void thenHotspotHasExactly(HotspotKey hotspotKey, List<Asset> expected) {
    List<Asset> members = searchresults.getHotspot(hotspotKey).getMembers();
    Assertions.assertArrayEquals(members.toArray(), expected.toArray(), "thenHotspotHasExactly failed to match");
  }

  private void printVendor(AssetVendor vendor) {
    String vendorDetails = vendor == null
      ? "printVendor:  vendor was null"
      : vendor.toString();
    System.out.println(vendorDetails + "\n");
  }
}