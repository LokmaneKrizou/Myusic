package com.devbea.myusic

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test

class PlaylistDetailsFeature : BaseUITest() {

    private fun navigateToPlaylistDetails() {
        onView(
            allOf(
                withId(R.id.playlist_image),
                isDescendantOfA(
                    nthChildOf(
                        withId(R.id.playlists_list),
                        0
                    )
                )
            )
        ).perform(ViewActions.click())

    }

    @Test
    fun displaysPlaylistNameAndDetails() {
        navigateToPlaylistDetails()
        onView(withId(R.id.playlist_details_name)).check(matches(withText("Hard Rock Cafe")))
            .check(matches(isDisplayed()))
        onView(withId(R.id.playlist_details_description)).check(matches(withText("Rock your senses with this timeless signature vibe list. \n\n • Poison \n • You shook me all night \n • Zombie \n • Rock'n Me \n • Thunderstruck \n • I Hate Myself for Loving you \n • Crazy \n • Knockin' on Heavens Door")))
            .check(matches(isDisplayed()))

    }
}