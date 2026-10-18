package com.vibe.apkbuilder.security

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

/**
 * License + anti-mod helpers.
 * Phone-side checks only — not a substitute for server attestation.
 */
object SecurityKit {

    private const val PBKDF2_ITERATIONS = 120_000
    private const val KEY_LENGTH = 256

    fun deviceId(context: Context): String {
        val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID) ?: "unknown"
        return sha256("$androidId:${Build.FINGERPRINT}")
    }

    fun hashPassword(password: String, salt: ByteArray = randomSalt()): Pair<String, String> {
        val spec = PBEKeySpec(password.toCharArray(), salt, PBKDF2_ITERATIONS, KEY_LENGTH)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val hash = factory.generateSecret(spec).encoded
        return hash.toHex() to salt.toHex()
    }

    fun verifyPassword(password: String, storedHashHex: String, saltHex: String): Boolean {
        val salt = saltHex.fromHex()
        val (computed, _) = hashPassword(password, salt)
        return constantTimeEquals(computed, storedHashHex)
    }

    fun randomSalt(size: Int = 16): ByteArray {
        val s = ByteArray(size)
        SecureRandom().nextBytes(s)
        return s
    }

    fun sha256(input: String): String {
        val digest = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return digest.toHex()
    }

    /** Basic integrity signals — raise risk score if triggered. */
    fun integrityRisk(context: Context): Int {
        var risk = 0
        try {
            val pm = context.packageManager
            val info = if (Build.VERSION.SDK_INT >= 33) {
                pm.getPackageInfo(context.packageName, PackageManager.PackageInfoFlags.of(0))
            } else {
                @Suppress("DEPRECATION")
                pm.getPackageInfo(context.packageName, 0)
            }
            // installer package empty often means sideload / mod path
            val installer = if (Build.VERSION.SDK_INT >= 30) {
                pm.getInstallSourceInfo(context.packageName).installingPackageName
            } else {
                @Suppress("DEPRECATION")
                pm.getInstallerPackageName(context.packageName)
            }
            if (installer.isNullOrBlank()) risk += 2
        } catch (_: Exception) {
            risk += 3
        }
        // debugger
        if (android.os.Debug.isDebuggerConnected()) risk += 4
        return risk
    }

    fun signLicensePayload(payload: String, secret: String): String = sha256("$payload|$secret")

    private fun ByteArray.toHex(): String = joinToString("") { "%02x".format(it) }
    private fun String.fromHex(): ByteArray = chunked(2).map { it.toInt(16).toByte() }.toByteArray()

    private fun constantTimeEquals(a: String, b: String): Boolean {
        if (a.length != b.length) return false
        var r = 0
        for (i in a.indices) r = r or (a[i].code xor b[i].code)
        return r == 0
    }
}
