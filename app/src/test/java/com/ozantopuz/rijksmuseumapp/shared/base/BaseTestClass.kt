package com.ozantopuz.rijksmuseumapp.shared.base

import androidx.annotation.CallSuper
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before

open class BaseTestClass {

    @CallSuper
    @Before
    open fun setUp() {
        MockKAnnotations.init(this)
    }

    @CallSuper
    @After
    open fun tearDown() {
        unmockkAll()
        clearAllMocks()
    }
}
