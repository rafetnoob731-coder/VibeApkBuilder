package com.vibe.apkbuilder.ads

/**
 * Ads only on Free plan.
 * Pro / Universe: no ads.
 * Placeholder — wire AdMob / mediated network with real unit IDs in production.
 */
object AdsManager {
    @Volatile var adsEnabled: Boolean = true

    fun onPlanChanged(planId: String) {
        adsEnabled = planId == "free"
    }

    fun shouldShowBanner(): Boolean = adsEnabled
    fun shouldShowInterstitialAfterBuild(): Boolean = adsEnabled
}
