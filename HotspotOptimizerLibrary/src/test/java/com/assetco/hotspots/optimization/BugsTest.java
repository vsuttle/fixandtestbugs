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
    int numMembers = members == null
      ? 0
      : members.size();
    System.out.println("thenHotspotDoesNotHave: numMembers: " + numMembers);

    for(Asset asset : assets) {
      boolean found = membersHasAsset(members, asset);
      assertFalse(found, "thenHotspotDoesNotHave failed");
    }
  }

  private void thenHotspotHasExactly(HotspotKey hotspotKey, List<Asset> expected) {
    Hotspot hotspot = searchresults.getHotspot(hotspotKey);
    List<Asset> members = hotspot.getMembers();
    Asset [] membersArray  = members.toArray(new Asset[members.size()]);
    Asset [] expectedArray = expected.toArray(new Asset[expected.size()]);
    boolean matched = Arrays.deepEquals(membersArray, expectedArray);
    System.out.println("thenHotspotHasExactly: matched: " + matched);
    assertTrue(matched, "thenHotspotHasExactly failed to match");
  }

  private boolean membersHasAsset (List<Asset> members, Asset asset) {
    boolean found = false;
    System.out.println("MembersHasAsset");
    System.out.println(asset.toString() + "\n");
    for(Asset member : members) {
      System.out.println("member: " + member.toString() + "\n");
      Object memberId = member.getId();
      Object assetId  = asset.getId();

      if (memberId != null && assetId != null && memberId instanceof String && assetId instanceof String) {
        found = ((String)memberId).equals(((String) assetId));
        if (found) {
          System.out.println("membersHasAsset: found asset with id: " + assetId);
//          break;
        }
      }
    }
    return found;
  }

  private void printVendor(AssetVendor vendor) {
    String vendorDetails = vendor == null
      ? "printVendor:  vendor was null"
      : vendor.toString();
    System.out.println(vendorDetails + "\n");
  }
}