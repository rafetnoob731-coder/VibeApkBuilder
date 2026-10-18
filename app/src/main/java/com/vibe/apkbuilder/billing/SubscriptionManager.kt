package com.vibe.apkbuilder.billing

/**
 * Credit economy:
 * - Pro monthly unlocks AI features.
 * - Credits gate expensive provider calls.
 * - Mapping example: 1000 app-credits ≈ $10 real provider spend (configurable ratio).
 *
 * Real Play Billing integration goes here; local mode for offline / debug.
 */
data class Plan(
    val id: String,
    val title: String,
    val monthlyPriceUsd: Double,
    val monthlyCredits: Int,
    val features: List<String>
)

object Plans {
    val FREE = Plan(
        id = "free",
        title = "Free",
        monthlyPriceUsd = 0.0,
        monthlyCredits = 50,
        features = listOf("Basic import", "WebView convert", "Limited analysis")
    )
    val PRO = Plan(
        id = "pro",
        title = "Pro",
        monthlyPriceUsd = 19.99,
        monthlyCredits = 1000, // maps to ~$10 provider budget
        features = listOf("Full AI repair", "Rejection analyzer", "Priority builds", "BYOK + credit boost")
    )
    val UNIVERSE = Plan(
        id = "universe",
        title = "Universe",
        monthlyPriceUsd = 39.99,
        monthlyCredits = 3000,
        features = listOf("Team history", "Large projects", "Advanced automation", "Highest credit pool")
    )

    /** App credits → approximate USD provider cost (1 credit ≈ $0.01). */
    fun creditsToUsd(credits: Int): Double = credits * 0.01

    fun usdToCredits(usd: Double): Int = (usd / 0.01).toInt()
}

enum class FeatureGate {
    BASIC_IMPORT,
    WEB_TO_APP,
    AI_ANALYSIS,
    AI_AUTO_FIX,
    REJECTION_ANALYZER,
    BATCH_BUILD,
    HISTORY_RESTORE
}

object Entitlement {
    fun canAccess(planId: String, feature: FeatureGate): Boolean {
        return when (feature) {
            FeatureGate.BASIC_IMPORT, FeatureGate.WEB_TO_APP -> true
            FeatureGate.AI_ANALYSIS, FeatureGate.AI_AUTO_FIX,
            FeatureGate.REJECTION_ANALYZER, FeatureGate.BATCH_BUILD,
            FeatureGate.HISTORY_RESTORE -> planId == "pro" || planId == "universe"
        }
    }

    fun requiresCredits(feature: FeatureGate): Boolean =
        feature == FeatureGate.AI_ANALYSIS ||
            feature == FeatureGate.AI_AUTO_FIX ||
            feature == FeatureGate.REJECTION_ANALYZER
}
