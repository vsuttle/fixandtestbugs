package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static com.assetco.search.results.AssetVendorRelationshipLevel.*;
import static com.assetco.search.results.HotspotKey.Showcase;

public class BugsTest {

  @Test
  public void precedingPartnerWithLongTrailingAssetsDoesNotWin() {
    final int PARTNER_VENDOR_SIZE = 4;

    final AssetVendor partnerVendor = makeVendor(Partner);
    final AssetVendor disruptingPartner = makeVendor(Partner);
    givenAssetInResultsWithVendor(disruptingPartner);

    Asset missing = givenAssetInResultsWithVendor(partnerVendor);
    ArrayList<Asset> expected =
      givenAssetsInResultsWithVendor(PARTNER_VENDOR_SIZE, partnerVendor);

    whenOptimize();
    thenHotspotDoesNotHave(Showcase, missing);
    thenHotspotHasExactly(Showcase, expected);
  }

  private AssetVendor makeVendor(AssetVendorRelationshipLevel relationshipLevel) {
    return null;
  }

  private Asset givenAssetInResultsWithVendor(AssetVendor vendor) {
    return null;
  }

  private Asset getAsset(AssetVendor vendor) {
    return null;
  }

  private AssetPurchaseInfo getPurchaseInfo() {
    return null;
  }

  private ArrayList<Asset> givenAssetsInResultsWithVendor(int count, AssetVendor vendor) {
    ArrayList result = new ArrayList<Asset>();
    for (int i = 0; i < count; ++i) {
      result.add(givenAssetInResultsWithVendor(vendor));
    }
    return result;
  }

  private void whenOptimize() {
  }

  private void thenHotspotDoesNotHave(HotspotKey key, Asset... asset) {
  }

  private void thenHotspotHasExactly(HotspotKey hotspotKey, List<Asset> expected) {
  }

}