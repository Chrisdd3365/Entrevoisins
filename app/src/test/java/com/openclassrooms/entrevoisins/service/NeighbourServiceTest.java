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

import static org.junit.Assert.assertArrayEquals;
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

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void getNeighbourDetailsWithSuccess() {
        Neighbour neighbour = service.getNeighbours().get(0);
        assertNotNull(neighbour.getId());
        assertNotNull(neighbour.getName());
        assertNotNull(neighbour.getAvatarUrl());
        assertNotNull(neighbour.getAddress());
        assertNotNull(neighbour.getPhoneNumber());
        assertNotNull(neighbour.getAboutMe());
        assertNotNull(neighbour.getFavorite());
    }

    @Test
    public void addNeighbourIntoFavoritesNeighboursListWithSuccess() {
        Neighbour neighbour = service.getNeighbours().get(0);
        neighbour.setFavorite(true);
        assertTrue(neighbour.getFavorite());
    }

    @Test
    public void removeNeighbourFromFavoritesNeighboursListWithSuccess() {
        Neighbour neighbour = service.getNeighbours().get(0);
        neighbour.setFavorite(false);
        assertFalse(neighbour.getFavorite());
    }

    @Test
    public void getFavoritesNeighboursWithSuccess() {
        List <Neighbour> favoritesNeighbours = new ArrayList<>();

        Neighbour neighbour1 = service.getNeighbours().get(1);
        Neighbour neighbour2 = service.getNeighbours().get(2);

        neighbour1.setFavorite(true);
        neighbour2.setFavorite(true);

        favoritesNeighbours.add(neighbour1);
        favoritesNeighbours.add(neighbour2);

        assertEquals(2, favoritesNeighbours.size());
    }

}
