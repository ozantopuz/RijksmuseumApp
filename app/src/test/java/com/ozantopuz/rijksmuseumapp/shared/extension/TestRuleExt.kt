package com.ozantopuz.rijksmuseumapp.shared.extension

import com.ozantopuz.rijksmuseumapp.shared.rule.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest

@ExperimentalCoroutinesApi
fun CoroutinesTestRule.runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) {
    testDispatcher.runBlockingTest(block)
}
