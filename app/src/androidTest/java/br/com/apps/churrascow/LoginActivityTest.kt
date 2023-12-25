package br.com.apps.churrascow

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import br.com.apps.churrascow.database.AppDataBase
import br.com.apps.churrascow.ui.activities.LoginActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginActivityTest {

    @get: Rule
    val rule = ActivityScenarioRule(LoginActivity::class.java)

    @Before
    fun prepare() {
        AppDataBase
            .getDb(InstrumentationRegistry.getInstrumentation().targetContext)
            .clearAllTables()
    }

    @Test
    fun shouldBeVisibleWhenAppStarts() {
        onView(withId(R.id.login_fragment_banner)).check(matches(isDisplayed()))
        onView(withId(R.id.login_fragment_btn_register)).check(matches(isDisplayed()))
        onView(withId(R.id.login_fragment_support)).check(matches(isDisplayed()))
        onView(withId(R.id.login_fragment_btn_access)).check(matches(isDisplayed()))
        onView(withId(R.id.login_fragment_login_panel))
            .check(
                matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE))
            )
    }

    @Test
    fun shouldRegisterAndLogin() {
        onView(withId(R.id.login_fragment_btn_register)).perform(click())

        onView(withId(R.id.register_panel_edit_text_name))
            .perform(
                typeText("Guilherme"),
                closeSoftKeyboard()
            )
        onView(withId(R.id.register_panel_edit_text_email))
            .perform(
                typeText("guilherme@email.com"),
                closeSoftKeyboard()
            )
        onView(withId(R.id.register_panel_edit_text_password))
            .perform(
                typeText("123456"),
                closeSoftKeyboard()
            )
        onView(withId(R.id.register_panel_edit_text_password_confirmation))
            .perform(
                typeText("123456"),
                closeSoftKeyboard()
            )
        onView(withId(R.id.register_fragment_btn)).perform(click())



        onView(withId(R.id.login_fragment_btn_access)).perform(click())
        onView(withId(R.id.login_panel_edit_text_email))
            .perform(
                typeText("guilherme@email.com"),
                closeSoftKeyboard()
            )
        onView(withId(R.id.login_panel_edit_text_password))
            .perform(
                typeText("123456"),
                closeSoftKeyboard()
            )
        onView(withId(R.id.login_panel_login_btn)).perform(click())
    }

    @Test
    fun shouldPrepareViewForLoginAfterAccessBtnClick() {
        onView(withId(R.id.login_fragment_btn_access)).perform(click())

        onView(withId(R.id.login_fragment_banner)).check(matches(isDisplayed()))
        onView(withId(R.id.login_fragment_login_panel)).check(matches(isDisplayed()))
        onView(withId(R.id.login_fragment_support)).check(matches(isDisplayed()))
        onView(withId(R.id.login_fragment_btn_access)).check(
            matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE))
        )
        onView(withId(R.id.login_fragment_btn_register)).check(
            matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE))
        )
        onView(withId(R.id.logo)).check(
            matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE))
        )
    }

    @Test
    fun shouldCheckLoginEditTexts() {
        onView(withId(R.id.login_fragment_btn_access)).perform(click())

        onView(withId(R.id.login_panel_edit_text_email))
            .perform(
                typeText("a@email.com"),
                closeSoftKeyboard()
            )
        onView(withId(R.id.login_panel_edit_text_password))
            .perform(
                typeText("123456"),
                closeSoftKeyboard()
            )

        onView(withId(R.id.login_panel_edit_text_email))
            .perform(
                replaceText("b@email.com"),
                closeSoftKeyboard()
            )
        onView(withId(R.id.login_panel_edit_text_password))
            .perform(
                replaceText("123"),
                closeSoftKeyboard()
            )

    }

}