package com.boringkm.simpletodo.simpletodo

import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {

    companion object {
        private const val CHANNEL = "flutter.android.channel"
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
            .setMethodCallHandler { call, result ->
                run {
                    if (call.method == "getMyCacheDirectory") {
                        val path = applicationContext.cacheDir.absolutePath
                        val data = HashMap<String, Any?>()
                        data.put("path", path)
                        result.success(data)
                    } else {
                        result.error("errorCode", "errorMessage", "errorDetail")
                    }
                }
            }
    }
}
