package bakingapp.udacity.com.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class RecipeDetailsActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<RecipeDetailsActivity> mActivityRule =
            new ActivityTestRule<>(RecipeDetailsActivity.class);

    @Before
    public void setup() {
        mActivityRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void test_recipe_steps_recyclerview_should_exist() {
        onView((withId(R.id.recyclerView_baking_list)))
                .check(matches(isDisplayed()));
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recyclerView_baking_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click())
        );

        onView((withId(R.id.recyclerView_recipe_steps)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void test_recipe_steps_item_click_should_succeed() {
        onView((withId(R.id.recyclerView_baking_list)))
                .check(matches(isDisplayed()));
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recyclerView_baking_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click())
        );

        onView((withId(R.id.recyclerView_recipe_steps)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.recyclerView_recipe_steps)).perform(
                RecyclerViewActions.actionOnItemAtPosition(3, click())
        );
    }

    @Test
    public void test_recipe_steps_item_click_should_display_exo_player_fragment() {
        onView((withId(R.id.recyclerView_baking_list)))
                .check(matches(isDisplayed()));
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recyclerView_baking_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click())
        );

        onView((withId(R.id.recyclerView_recipe_steps)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.recyclerView_recipe_steps)).perform(
                RecyclerViewActions.actionOnItemAtPosition(3, click())
        );

        onView((withId(R.id.frameLayout_media_container)))
                .check(matches(isDisplayed()));
    }
}
