# Bomberman


## Description

This is a Bomberman game made in Java using the Swing library. It is a school project for the course "Object-Oriented Programming" at the University of Bergen. 

Bomberman is a game involving 4 players. The goal of the game is to blow up all the other players, and the last person standing wins. You can place one bomb at a time, which will explode after 3 seconds, blowing up the tiles around it. Players stepping on an explosion will lose 1 life, and enter a cooldown period where they can't take damage. There are destructible tiles that can be blown up, and indestructible tiles that cannot be blown up. 


## How to play

You play as the white bomberman starting in the lower left corner. The other 3 players are bots. 

- You use the arrow keys to move around, and the space bar to place a bomb. 

- 'm' is used to mute/unmute the music.
- 'p' is used to pause/unpause the game.
- 'esc' is used to exit the game.

# Behind the scenes

The game is built using the MVC architecture. 

## Model 

The most important classes in the model are:
- 'BombermanModel' - contains the game logic, has methods for the AI behavior, and methods for placing bombs and moving the bomberman. Updates the view.
- 'BombermanBoard' - the board for the game, contains methods assisting the model in checking for collisions and updating the board. 
- 'Player, HumanPlayer and AIPlayer' - contains information about the players, such as their position, number of lives, and also contain the bomb objects.
- 'Bomb' - contains information about the bombs, such as their position, and the timer for when they will explode.

## View

The view is almost entirely built up by the 'BombermanView' class. 
- 'BombermanView' - draws the players, bombs, tiles, game window, scoreboard, and everything that is visible.

## Controller

The view is almost entirely built up by the 'BombermanController' class.
- 'BombermanController' - handles the input from the user, and updates the model accordingly.



# Link to video demonstration
https://youtu.be/Us2zqq0Nqa4


