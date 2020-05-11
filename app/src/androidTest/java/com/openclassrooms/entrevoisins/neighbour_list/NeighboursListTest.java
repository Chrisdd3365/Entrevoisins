package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.StringContains.containsString;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {
    //-- PROPERTIES
    // This is fixed
    // INIT ITEMS COUNT
    private static int ITEMS_COUNT = 12;
    // ACTIVITY
    private ListNeighbourActivity mListNeighbourActivity;

    //-- RULE
    @Rule
    public ActivityTestRule<ListNeighbourActivity> mListNeighbourActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    //-- SETUP
    // INIT ACTIVITY
    @Before
    public void setUp() {
        mListNeighbourActivity = mListNeighbourActivityRule.getActivity();

        assertThat(mListNeighbourActivity, notNullValue());
    }

    //-- UNIT TESTS METHODS
    /**
     * We ensure that our recyclerview is displaying at least one item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours) ) ).check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given: We remove the element at position 2
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours) ) ).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours) ) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction( )) );
        // Then: the number of element is 11
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours) ) ).check(withItemCount(ITEMS_COUNT-1) );
    }

    /**
     * When we click on 1 item, it shows the details activity of this item
     */
    @Test
    public void myNeighboursList_onClickListener_shouldShowDetailsActivity() {
        // When perform a click on a item
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours) ) ).perform(actionOnItemAtPosition(0, click() ) );
        // Check if the name's text view of the neighbour is displayed in the details activity
        onView(withId(R.id.user_name) ).check(matches(isDisplayed() ) );
        // Then: check if the name of the neighbour is the expected one
        onView(withId(R.id.user_name)).check(matches(withText(containsString(DI.getNeighbourApiService().getNeighbours().get(0).getName() ) ) ) );
    }

    /**
     * When we add a neighbour into the favorites neighbours list, check if it displays only the neighbour set as favorite neighbour
     */
    @Test
    public void myFavoritesNeighboursList_onClickListener_shouldShowFavoriteNeighbourIntoFavoriteList() {
        // When perform a click on a item, it shows the details activity
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours) ) ).perform(actionOnItemAtPosition(4, click() ) );
        // Perform a click on favorite button
        onView(withId(R.id.favorite_image_button) ).perform(click() );
        // Going back to the list neighbours activity
        onView(withId(R.id.back_button) ).perform(click() );
        // Display favorites neighbours list by clicking on the second tab item of view pager
        onView(withId(R.id.container) ).perform(swipeLeft() );
        // Only this item is displayed into the favorites neighbours list
        onView(allOf(isDisplayed(), withId(R.id.item_list_avatar) ) ).check(matches(isDisplayed() ) );
        onView(allOf(isDisplayed(), withId(R.id.item_list_name) ) ).check(matches(isDisplayed() ) );
        // Then: check if the name and the avatar are the expected ones
        onView(allOf(isDisplayed(), withId(R.id.item_list_avatar) ) ).check(matches(withText(containsString(DI.getNeighbourApiService().getNeighbours().get(4).getAvatarUrl() ) ) ) );
        onView(allOf(isDisplayed(), withId(R.id.item_list_name) ) ).check(matches(withText(containsString(DI.getNeighbourApiService().getNeighbours().get(4).getName() ) ) ) );

    }

}