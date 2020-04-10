package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements NeighbourApiService {
    //-- PROPERTIES
    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
    private List<Neighbour> favoritesNeighbours = DummyNeighbourGenerator.generateFavoritesNeighbours();

    //-- METHODS
    /**
     * {@inheritDoc}
     * @return {@link List}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     * @return {@link List}
     */
    @Override
    public List<Neighbour> getFavoritesNeighbours() { return favoritesNeighbours; }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param favoriteNeighbour
     */
    @Override
    public void deleteFavoriteNeighbour(Neighbour favoriteNeighbour) { favoritesNeighbours.remove(favoriteNeighbour); }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param favoriteNeighbour
     */
    @Override
    public void createFavoriteNeighbour(Neighbour favoriteNeighbour) { favoritesNeighbours.add(favoriteNeighbour); }

}
