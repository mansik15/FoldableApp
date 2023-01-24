package com.app.foldableapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import androidx.window.layout.WindowMetricsCalculator
import com.app.foldableapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var windowInfoTracker: WindowInfoTracker
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        windowInfoTracker = WindowInfoTracker.getOrCreate(this@MainActivity)

        obtainWindowMetrics()
        onWindowLayoutInfoChange()
    }

    private fun obtainWindowMetrics() {
        val wmc = WindowMetricsCalculator.getOrCreate()
        val currentWM = wmc.computeCurrentWindowMetrics(this).bounds.flattenToString()
        val maximumWM = wmc.computeMaximumWindowMetrics(this).bounds.flattenToString()
        binding.windowMetrics.text =
            "CurrentWindowMetrics: ${currentWM}\nMaximumWindowMetrics: ${maximumWM}"
    }

    private fun onWindowLayoutInfoChange() {
        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                windowInfoTracker.windowLayoutInfo(this@MainActivity)
                    .collect { value ->
                        updateUI(value)
                    }
            }
        }
    }

    private fun updateUI(newLayoutInfo: WindowLayoutInfo) {
        binding.layoutChange.text = newLayoutInfo.toString()
        if (newLayoutInfo.displayFeatures.isNotEmpty()) {
            binding.configurationChanged.text = "Spanned across displays"
        } else {
            binding.configurationChanged.text = "One logic/physical display - unspanned"
        }
    }
}