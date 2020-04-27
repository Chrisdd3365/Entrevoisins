package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity.checkFavoriteNeighbour;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {
    //-- PROPERTIES
    // SERVICE
    private NeighbourApiService service;
    // LISTS
    List<Neighbour> neighbours = new ArrayList<>();
    List<Neighbour> favoritesNeighbours = new ArrayList<>();

    //-- SETUP
    // INIT NEIGHBOUR API SERVICE INSTANCE
    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    //--UNIT TESTS METHODS
    // UNIT TESTING INIT LIST OF NEIGHBOURS
    @Test
    public void getNeighboursWithSuccess() {
        neighbours = service.getNeighbours();

        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;

        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }
    // UNIT TESTING DELETING NEIGHBOUR FROM LIST OF NEIGHBOURS
    @Test
    public void deleteNeighbourWithSuccess() {
        neighbours = service.getNeighbours();

        Neighbour neighbourToDelete = service.getNeighbours().get(0);

        service.deleteNeighbour(neighbourToDelete);

        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }
    // UNIT TESTING GETTING NEIGHBOUR DETAILS
    @Test
    public void getNeighbourDetailsWithSuccess() {
        Neighbour neighbour = service.getNeighbours().get(0);

        assertNotNull(neighbour.getName());
        assertNotNull(neighbour.getAvatarUrl());
        assertNotNull(neighbour.getAddress());
        assertNotNull(neighbour.getPhoneNumber());
        assertNotNull(neighbour.getAboutMe());
        assertNotNull(neighbour.getFavorite());
    }
    // UNIT TESTING ADDING NEIGHBOUR INTO FAVORITES NEIGHBOURS LIST
    @Test
    public void addNeighbourIntoFavoritesNeighboursListWithSuccess() {
        Neighbour neighbour = service.getNeighbours().get(0);
        neighbour.setFavorite(true);

        neighbours = service.getNeighbours();

        checkFavoriteNeighbour(neighbour, neighbours, favoritesNeighbours);

        assertTrue(neighbour.getFavorite());
        assertTrue(favoritesNeighbours.contains(neighbour));
    }
    // UNIT TESTING DELETING NEIGHBOUR FROM FAVORITES NEIGHBOURS LIST
    @Test
    public void deleteNeighbourFromFavoritesNeighboursListWithSuccess() {
        Neighbour neighbour = service.getNeighbours().get(0);
        neighbour.setFavorite(false);

        neighbours = service.getNeighbours();

        checkFavoriteNeighbour(neighbour, neighbours, favoritesNeighbours);

        assertFalse(neighbour.getFavorite());
        assertFalse(favoritesNeighbours.contains(neighbour));
    }
    // UNIT TESTING INIT IF NEIGHBOUR IS ADDED ONLY ONCE INTO FAVORITES NEIGHBOURS LIST
    @Test
    public void checkNeighbourIsAddedOnceWithSuccess() {
        Neighbour neighbour1 = service.getNeighbours().get(1);
        neighbour1.setFavorite(true);

        Neighbour neighbour2 = service.getNeighbours().get(1);
        neighbour2.setFavorite(true);

        neighbours = service.getNeighbours();

        checkFavoriteNeighbour(neighbour1, neighbours, favoritesNeighbours);
        checkFavoriteNeighbour(neighbour2, neighbours, favoritesNeighbours);

        assertEquals(1, favoritesNeighbours.size());
    }

}
