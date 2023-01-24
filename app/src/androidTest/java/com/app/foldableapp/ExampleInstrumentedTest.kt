package com.app.foldableapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.PositionAssertions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.window.layout.FoldingFeature.Orientation.Companion.VERTICAL
import androidx.window.layout.FoldingFeature.State.Companion.FLAT
import androidx.window.testing.layout.FoldingFeature
import androidx.window.testing.layout.TestWindowLayoutInfo
import androidx.window.testing.layout.WindowLayoutInfoPublisherRule
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private val activityRule = ActivityScenarioRule(MainActivity::class.java)
    private val publisherRule = WindowLayoutInfoPublisherRule()

    @get:Rule
    val testRule: TestRule

    init {
        testRule = RuleChain.outerRule(publisherRule).around(activityRule)
    }

    @Test
    fun testText_is_left_of_Vertical_FoldingFeature() {
        activityRule.scenario.onActivity { activity ->
            val hinge = FoldingFeature(
                activity = activity,
                state = FLAT,
                orientation = VERTICAL,
                size = 2
            )

            val expected = TestWindowLayoutInfo(listOf(hinge))
            publisherRule.overrideWindowLayoutInfo(expected)
        }
        // Add Assertion with EspressoMatcher here
        /*onView(withId(R.id.layout_change)).check(
            PositionAssertions.isCompletelyLeftOf(
                withId(R.id.folding_feature)
            )
        )*/
    }
}