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



public class MainTest {

    @Test
    public void getCorrectAnswerTest(){
        Main main = new Main();
        String player1 = "player1";
        int t1count = 1;

        String player2 = "player2";

        if(t1count == 1 && player1.equals("player1")){
            assertThat("", equalTo(main.getCorrectAnswer(player1)));
        }

    }


}
