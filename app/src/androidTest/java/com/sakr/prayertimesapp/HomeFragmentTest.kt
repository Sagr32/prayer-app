package com.sakr.prayertimesapp

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
//UI Testing
@MediumTest
class HomeFragmentTest {

    @Test
    fun clickBottomNav() {
//
//        // GIVEN - On the home screen
//        val scenario = launchFragmentInContainer<HomeFragment>(Bundle(), R.style.AppTheme)
//        val navController = mock(NavController::class.java)
//        scenario.onFragment {
//            Navigation.setViewNavController(it.view!!, navController)
//        }
//
//        // WHEN - Click on the first list item
//        onView(withId(R.id.addReminderFAB))
//            .perform(
//                click()
//            )
//
//
//        // THEN - Verify that we navigate to the first detail screen
//        verify(navController).navigate(
//            ReminderListFragmentDirections.toSaveReminder()
//        )
    }
}