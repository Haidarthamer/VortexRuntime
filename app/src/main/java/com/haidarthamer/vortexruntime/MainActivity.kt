package com.haidarthamer.vortexruntime

import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme()) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    VortexApp()
                }
            }
        }
    }
}

private data class DeviceProfile(
    val model: String,
    val androidVersion: String,
    val supportedAbis: String,
    val totalMemoryGb: Int,
    val hasVulkanFeature: Boolean
)

private fun readDeviceProfile(context: Context): DeviceProfile {
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val memoryInfo = ActivityManager.MemoryInfo()
    activityManager.getMemoryInfo(memoryInfo)
    val memoryGb = (memoryInfo.totalMem / (1024L * 1024L * 1024L)).toInt()
    val hasVulkan = context.packageManager.hasSystemFeature(
        PackageManager.FEATURE_VULKAN_HARDWARE_LEVEL
    )

    return DeviceProfile(
        model = "${Build.MANUFACTURER} ${Build.MODEL}",
        androidVersion = "Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})",
        supportedAbis = Build.SUPPORTED_ABIS.joinToString(),
        totalMemoryGb = memoryGb,
        hasVulkanFeature = hasVulkan
    )
}

@Composable
private fun VortexApp() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Home", "Runtime", "Graphics", "Multiplayer", "Tools")

    Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = { Text(title.take(1)) },
                        label = { Text(title) }
                    )
                }
            }
        }
    ) { padding ->
        when (selectedTab) {
            0 -> HomeScreen(padding)
            1 -> InfoScreen(
                padding,
                "Runtime",
                "No runtime is connected yet.",
                "VORTEX currently provides the Android app foundation and a defined integration boundary. A compatible, legally usable runtime must be supplied before game launch can be implemented."
            )
            2 -> InfoScreen(
                padding,
                "Graphics",
                "Graphics backend: not connected",
                "Vulkan capability detection is available on the Home screen. Driver installation, DXVK configuration, and runtime-specific graphics controls will only be enabled when a compatible backend is integrated."
            )
            3 -> InfoScreen(
                padding,
                "Multiplayer",
                "Multiplayer: unavailable",
                "A room browser alone is not multiplayer. Real sessions require a compatible game runtime, matching network protocol, and verified synchronization hooks. No connection or game-state sync is implemented in this foundation build."
            )
            else -> InfoScreen(
                padding,
                "Tools",
                "Device diagnostics",
                "The first milestone detects basic device capabilities. File validation, controller mapping, logs, and exportable diagnostics are planned for later milestones."
            )
        }
    }
}

@Composable
private fun HomeScreen(padding: PaddingValues) {
    val context = LocalContext.current
    val profile = remember { readDeviceProfile(context) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(padding),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text("VORTEX", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Black)
            Text("RUNTIME PLATFORM  •  EARLY ACCESS", style = MaterialTheme.typography.labelMedium)
        }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                Column(Modifier.padding(18.dp)) {
                    Text("Runtime status", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text("NOT CONNECTED", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    Text("The app foundation is ready for the next integration milestone.")
                }
            }
        }
        item {
            Text("DEVICE PROFILE", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
        }
        item { ProfileRow("Device", profile.model) }
        item { ProfileRow("Android", profile.androidVersion) }
        item { ProfileRow("Memory", "${profile.totalMemoryGb} GB detected") }
        item { ProfileRow("Supported ABIs", profile.supportedAbis) }
        item { ProfileRow("Vulkan feature", if (profile.hasVulkanFeature) "Reported by Android" else "Not reported") }
        item {
            Text(
                "Note: Android feature detection is not a Vulkan driver benchmark and does not guarantee that a specific game or renderer will run.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun ProfileRow(label: String, value: String) {
    Card {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun InfoScreen(
    padding: PaddingValues,
    title: String,
    status: String,
    details: String
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(padding).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Card {
            Column(Modifier.padding(18.dp)) {
                Text(status, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(10.dp))
                Text(details, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
