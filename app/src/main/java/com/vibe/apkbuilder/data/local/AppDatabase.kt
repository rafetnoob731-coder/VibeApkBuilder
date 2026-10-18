package com.vibe.apkbuilder.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import com.vibe.apkbuilder.data.model.Project
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val email: String,
    val displayName: String,
    val passwordHash: String,
    val salt: String,
    val createdAt: Long = System.currentTimeMillis(),
    val lastLoginAt: Long = 0,
    val isEmailVerified: Boolean = false
)

@Entity(tableName = "subscriptions")
data class SubscriptionEntity(
    @PrimaryKey val userId: String,
    val plan: String, // free | pro | universe
    val status: String, // active | expired | cancelled
    val creditsRemaining: Int = 0,
    val creditsTotal: Int = 0,
    val periodStart: Long = 0,
    val periodEnd: Long = 0,
    val provider: String = "local", // local | play_billing
    val purchaseToken: String? = null
)

@Entity(tableName = "ai_usage")
data class AiUsageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String,
    val provider: String,
    val model: String,
    val tokensIn: Int,
    val tokensOut: Int,
    val costCredits: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "license")
data class LicenseEntity(
    @PrimaryKey val id: Int = 1,
    val deviceId: String,
    val licenseKey: String,
    val signature: String,
    val issuedAt: Long,
    val expiresAt: Long,
    val isValid: Boolean = true
)

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun findByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun findById(id: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: UserEntity)

    @Update
    suspend fun update(user: UserEntity)
}

@Dao
interface ProjectDao {
    @Query("SELECT * FROM projects ORDER BY lastModified DESC")
    fun observeAll(): Flow<List<Project>>

    @Query("SELECT * FROM projects WHERE id = :id LIMIT 1")
    suspend fun findById(id: String): Project?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(project: Project)

    @Query("DELETE FROM projects WHERE id = :id")
    suspend fun delete(id: String)
}

@Dao
interface SubscriptionDao {
    @Query("SELECT * FROM subscriptions WHERE userId = :userId LIMIT 1")
    fun observe(userId: String): Flow<SubscriptionEntity?>

    @Query("SELECT * FROM subscriptions WHERE userId = :userId LIMIT 1")
    suspend fun get(userId: String): SubscriptionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(sub: SubscriptionEntity)
}

@Dao
interface AiUsageDao {
    @Insert
    suspend fun insert(usage: AiUsageEntity)

    @Query("SELECT SUM(costCredits) FROM ai_usage WHERE userId = :userId AND timestamp >= :since")
    suspend fun creditsUsedSince(userId: String, since: Long): Int?
}

@Dao
interface LicenseDao {
    @Query("SELECT * FROM license WHERE id = 1 LIMIT 1")
    suspend fun get(): LicenseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(license: LicenseEntity)
}

@Database(
    entities = [
        UserEntity::class,
        Project::class,
        SubscriptionEntity::class,
        AiUsageEntity::class,
        LicenseEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun projectDao(): ProjectDao
    abstract fun subscriptionDao(): SubscriptionDao
    abstract fun aiUsageDao(): AiUsageDao
    abstract fun licenseDao(): LicenseDao
}
