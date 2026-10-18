package com.vibe.apkbuilder.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vibe.apkbuilder.ui.screens.ai.AiAssistantScreen
import com.vibe.apkbuilder.ui.screens.analysis.ProjectAnalysisScreen
import com.vibe.apkbuilder.ui.screens.build.BuildCenterScreen
import com.vibe.apkbuilder.ui.screens.config.ConfigurationScreen
import com.vibe.apkbuilder.ui.screens.dashboard.DashboardScreen
import com.vibe.apkbuilder.ui.screens.environment.EnvironmentScreen
import com.vibe.apkbuilder.ui.screens.history.HistoryScreen
import com.vibe.apkbuilder.ui.screens.importproject.ImportProjectScreen
import com.vibe.apkbuilder.ui.screens.permissions.PermissionsScreen
import com.vibe.apkbuilder.ui.screens.playstore.PlayStoreScreen
import com.vibe.apkbuilder.ui.screens.projects.ProjectsScreen
import com.vibe.apkbuilder.ui.screens.providers.AiProvidersScreen
import com.vibe.apkbuilder.ui.screens.rejection.RejectionAnalyzerScreen
import com.vibe.apkbuilder.ui.screens.settings.SettingsScreen
import com.vibe.apkbuilder.ui.screens.splash.SplashScreen
import com.vibe.apkbuilder.ui.screens.subscription.SubscriptionScreen
import com.vibe.apkbuilder.ui.screens.webtoapp.WebToAppScreen

@Composable
fun VibeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Route.Splash.path,
        modifier = modifier
    ) {
        composable(Route.Splash.path) {
            SplashScreen(onFinished = {
                navController.navigate(Route.Dashboard.path) {
                    popUpTo(Route.Splash.path) { inclusive = true }
                }
            })
        }
        composable(Route.Dashboard.path) {
            DashboardScreen(
                onNavigate = { route -> navController.navigate(route) },
                onProjectClick = { id -> navController.navigate(Route.ProjectAnalysis.create(id)) }
            )
        }
        composable(Route.Projects.path) {
            ProjectsScreen(
                onBack = { navController.popBackStack() },
                onProjectClick = { id -> navController.navigate(Route.ProjectAnalysis.create(id)) },
                onImport = { navController.navigate(Route.ImportProject.path) }
            )
        }
        composable(Route.ImportProject.path) {
            ImportProjectScreen(
                onBack = { navController.popBackStack() },
                onImported = { id ->
                    navController.navigate(Route.ProjectAnalysis.create(id)) {
                        popUpTo(Route.ImportProject.path) { inclusive = true }
                    }
                }
            )
        }
        composable(Route.ProjectAnalysis.path, arguments = listOf(navArgument("projectId") { type = NavType.StringType })) { e ->
            val id = e.arguments?.getString("projectId") ?: "demo"
            ProjectAnalysisScreen(
                projectId = id,
                onBack = { navController.popBackStack() },
                onViewDetails = { navController.navigate(Route.Configuration.create(id)) }
            )
        }
        composable(Route.Configuration.path, arguments = listOf(navArgument("projectId") { type = NavType.StringType })) { e ->
            val id = e.arguments?.getString("projectId") ?: "demo"
            ConfigurationScreen(
                projectId = id,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(Route.Environment.create(id)) }
            )
        }
        composable(Route.Environment.path, arguments = listOf(navArgument("projectId") { type = NavType.StringType })) { e ->
            val id = e.arguments?.getString("projectId") ?: "demo"
            EnvironmentScreen(
                projectId = id,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(Route.BuildCenter.create(id)) }
            )
        }
        composable(Route.BuildCenter.path, arguments = listOf(navArgument("projectId") { type = NavType.StringType })) { e ->
            val id = e.arguments?.getString("projectId") ?: "demo"
            BuildCenterScreen(
                projectId = id,
                onBack = { navController.popBackStack() },
                onViewApk = { }
            )
        }
        composable(Route.WebToApp.path) {
            WebToAppScreen(
                onBack = { navController.popBackStack() },
                onConvert = { _, _ -> navController.navigate(Route.ProjectAnalysis.create("web-converted")) }
            )
        }
        composable(Route.AiAssistant.path, arguments = listOf(navArgument("projectId") { type = NavType.StringType })) { e ->
            val id = e.arguments?.getString("projectId") ?: "demo"
            AiAssistantScreen(projectId = id, onBack = { navController.popBackStack() })
        }
        composable(Route.Permissions.path, arguments = listOf(navArgument("projectId") { type = NavType.StringType })) { e ->
            val id = e.arguments?.getString("projectId") ?: "demo"
            PermissionsScreen(
                projectId = id,
                onBack = { navController.popBackStack() },
                onSave = { navController.popBackStack() }
            )
        }
        composable(Route.PlayStore.path, arguments = listOf(navArgument("projectId") { type = NavType.StringType })) { e ->
            val id = e.arguments?.getString("projectId") ?: "demo"
            PlayStoreScreen(
                projectId = id,
                onBack = { navController.popBackStack() },
                onPrepare = { navController.navigate(Route.RejectionAnalyzer.create(id)) }
            )
        }
        composable(Route.RejectionAnalyzer.path, arguments = listOf(navArgument("projectId") { type = NavType.StringType })) { e ->
            val id = e.arguments?.getString("projectId") ?: "demo"
            RejectionAnalyzerScreen(
                projectId = id,
                onBack = { navController.popBackStack() },
                onApplyFix = { },
                onNewAnalysis = { }
            )
        }
        composable(Route.Subscription.path) {
            SubscriptionScreen(onBack = { navController.popBackStack() }, onUpgrade = { })
        }
        composable(Route.AiProviders.path) {
            AiProvidersScreen(onBack = { navController.popBackStack() }, onAdd = { })
        }
        composable(Route.History.path, arguments = listOf(navArgument("projectId") { type = NavType.StringType })) { e ->
            val id = e.arguments?.getString("projectId") ?: "demo"
            HistoryScreen(
                projectId = id,
                onBack = { navController.popBackStack() },
                onRestore = { }
            )
        }
        composable(Route.Settings.path) {
            SettingsScreen(
                onBack = { navController.popBackStack() },
                onNavigate = { key ->
                    when (key) {
                        "providers" -> navController.navigate(Route.AiProviders.path)
                        "subscription" -> navController.navigate(Route.Subscription.path)
                    }
                }
            )
        }
    }
}
