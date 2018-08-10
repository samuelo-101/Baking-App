package bakingapp.udacity.com.bakingapp;


import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import bakingapp.udacity.com.bakingapp.viewholder.BakingListRecyclerViewAdapterViewHolder;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void test_recyclerview_should_exist() {
        onView((withId(R.id.recyclerView_baking_list)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void test_recyclerview_items_background_colors_loaded_successfully() {
        onView((withId(R.id.recyclerView_baking_list)))
                .check(matches(isDisplayed()));

        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recyclerView_baking_list)).perform(
                RecyclerViewActions.scrollToHolder(
                        viewHolderMatcherBackgroundColor(mActivityRule.getActivity().getResources().getColor(R.color.md_red_400))
                ));

        onView(withId(R.id.recyclerView_baking_list)).perform(
                RecyclerViewActions.scrollToHolder(
                        viewHolderMatcherBackgroundColor(mActivityRule.getActivity().getResources().getColor(R.color.md_blue_400))
                ));

        onView(withId(R.id.recyclerView_baking_list)).perform(
                RecyclerViewActions.scrollToHolder(
                        viewHolderMatcherBackgroundColor(mActivityRule.getActivity().getResources().getColor(R.color.md_green_400))
                ));

        onView(withId(R.id.recyclerView_baking_list)).perform(
                RecyclerViewActions.scrollToHolder(
                        viewHolderMatcherBackgroundColor(mActivityRule.getActivity().getResources().getColor(R.color.md_purple_500))
                ));
    }

    @Test
    public void test_recyclerview_first_item_click_should_launch_recipe_details_activity() {
        Intents.init();

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

        intended(hasComponent(RecipeDetailsActivity.class.getName()));

        onView((withId(R.id.toolbar)))
                .check(matches(isDisplayed()));

        Intents.release();
    }

    public static Matcher<RecyclerView.ViewHolder> viewHolderMatcherBackgroundColor(final int color) {
        return new BoundedMatcher<RecyclerView.ViewHolder, BakingListRecyclerViewAdapterViewHolder>(BakingListRecyclerViewAdapterViewHolder.class) {

            @Override
            public void describeTo(org.hamcrest.Description description) {

            }

            @Override
            protected boolean matchesSafely(BakingListRecyclerViewAdapterViewHolder item) {
                ConstraintLayout mConstraintLayoutContainer = item.mConstraintLayoutContainer;
                Drawable background = mConstraintLayoutContainer.getBackground();
                return (background instanceof ColorDrawable) && ((ColorDrawable) background).getColor() == color;
            }
        };
    }
}
