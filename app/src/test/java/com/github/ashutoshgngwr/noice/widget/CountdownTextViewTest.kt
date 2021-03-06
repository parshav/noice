package com.github.ashutoshgngwr.noice.widget

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.shadows.ShadowLooper

@RunWith(RobolectricTestRunner::class)
class CountdownTextViewTest {

  private lateinit var view: CountdownTextView

  @Before
  fun setup() {
    view = CountdownTextView(RuntimeEnvironment.systemContext)
  }

  @Test
  fun testWithZeroCountdown() {
    view.startCountdown(0L)
    assertEquals("00h 00m 00s", view.text.toString())
  }

  @Test
  fun testWithNonZeroCountdown_textOnStart() {
    view.startCountdown(3661000L)
    assertNotEquals("00h 00m 0s", view.text.toString())
  }

  @Test
  fun testWithNonZeroCountdown_textUntilExpiry() {
    view.startCountdown(2000L)
    assertEquals("00h 00m 02s", view.text.toString())

    // bad code ahead: execute delayed tasks twice for 1 sec lapse since CountdownTextView has a
    // refresh interval set to 500ms.
    ShadowLooper.runUiThreadTasksIncludingDelayedTasks()
    ShadowLooper.runUiThreadTasksIncludingDelayedTasks()
    assertEquals("00h 00m 01s", view.text.toString())

    ShadowLooper.runUiThreadTasksIncludingDelayedTasks()
    ShadowLooper.runUiThreadTasksIncludingDelayedTasks()
    assertEquals("00h 00m 00s", view.text.toString())
  }
}
