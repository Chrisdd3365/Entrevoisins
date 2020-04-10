package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all my Neighbours
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();

    /**
     * Get all favorites Neighbours
     * @return {@link List}
     */
    List<Neighbour> getFavoritesNeighbours();

    /**
     * Deletes a neighbour
     * @param neighbour
     */
    void deleteNeighbour(Neighbour neighbour);

    /**
     * Deletes a neighbour
     * @param favoriteNeighbour
     */
    void deleteFavoriteNeighbour(Neighbour favoriteNeighbour);

    /**
     * Create a neighbour
     * @param neighbour
     */
    void createNeighbour(Neighbour neighbour);

    /**
     * Create a neighbour
     * @param favoriteNeighbour
     */
    void createFavoriteNeighbour(Neighbour favoriteNeighbour);

}
