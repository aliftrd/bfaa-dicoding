package com.github.aliftrd.gitseeker.util

import org.junit.Test
import org.junit.Assert

class NumberTest {
    @Test
    fun format() {
        Assert.assertEquals("1", Number.format(1))
        Assert.assertEquals("999", Number.format(999))
        Assert.assertEquals("1.0K", Number.format(1001))
        Assert.assertEquals("1.1K", Number.format(1100))
    }
}