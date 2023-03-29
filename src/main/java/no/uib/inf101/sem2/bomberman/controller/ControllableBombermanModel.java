package no.uib.inf101.sem2.bomberman.controller;

public interface ControllableBombermanModel {

    boolean placeBomb();

    boolean movePlayer(int deltaRow, int deltaCol);

}
