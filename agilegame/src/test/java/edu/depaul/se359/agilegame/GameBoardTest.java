package edu.depaul.se359.agilegame;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class GameBoardTest {

    @Test
    public void GameBoardTest(){
        GameBoard gameBoard = GameBoard.getInstance(25, 45);

        assertThat(25, equalTo(gameBoard.xN));
        assertThat(45, equalTo(gameBoard.yN));

    }

    @Test
    public void getCoordinateValueTest(){
        GameBoard gameBoard = GameBoard.getInstance(25, 45);

        assertThat(gameBoard.gameGrid[1][2], equalTo(gameBoard.getCoordinateValue(1,2)));
    }

    @Test
    public void getMapLengthTest(){
        GameBoard gameBoard = GameBoard.getInstance(25, 45);

        assertThat(25, equalTo(gameBoard.gameGrid.length));
    }


}
