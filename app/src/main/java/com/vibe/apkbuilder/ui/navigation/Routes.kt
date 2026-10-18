package com.vibe.apkbuilder.ui.navigation

sealed class Route(val path: String) {
    data object Splash : Route("splash")
    data object Dashboard : Route("dashboard")
    data object Projects : Route("projects")
    data object ImportProject : Route("import")
    data object ProjectAnalysis : Route("analysis/{projectId}") {
        fun create(projectId: String) = "analysis/$projectId"
    }
    data object Configuration : Route("config/{projectId}") {
        fun create(projectId: String) = "config/$projectId"
    }
    data object Environment : Route("environment/{projectId}") {
        fun create(projectId: String) = "environment/$projectId"
    }
    data object BuildCenter : Route("build/{projectId}") {
        fun create(projectId: String) = "build/$projectId"
    }
    data object AiAssistant : Route("ai/{projectId}") {
        fun create(projectId: String) = "ai/$projectId"
    }
    data object WebToApp : Route("webtoapp")
    data object VisualBuilder : Route("visual/{projectId}") {
        fun create(projectId: String) = "visual/$projectId"
    }
    data object Permissions : Route("permissions/{projectId}") {
        fun create(projectId: String) = "permissions/$projectId"
    }
    data object PlayStore : Route("playstore/{projectId}") {
        fun create(projectId: String) = "playstore/$projectId"
    }
    data object RejectionAnalyzer : Route("rejection/{projectId}") {
        fun create(projectId: String) = "rejection/$projectId"
    }
    data object Subscription : Route("subscription")
    data object AiProviders : Route("providers")
    data object Publishing : Route("publishing/{projectId}") {
        fun create(projectId: String) = "publishing/$projectId"
    }
    data object RejectionFix : Route("rejectionfix/{projectId}") {
        fun create(projectId: String) = "rejectionfix/$projectId"
    }
    data object History : Route("history/{projectId}") {
        fun create(projectId: String) = "history/$projectId"
    }
    data object Settings : Route("settings")
}
