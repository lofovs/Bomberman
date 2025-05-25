# Bomberman

# Install

You need 

- Java Runtime (Java 17)

Run the .msi file to install

## Description

This is a Bomberman game made in Java using the Swing library. It is a school project for the course "Object-Oriented Programming" at the University of Bergen. 

Bomberman is a game involving 4 players. The goal of the game is to blow up all the other players, and the last person standing wins. You can place one bomb at a time, which will explode after 3 seconds, blowing up the tiles around it. Players stepping on an explosion will lose 1 life, and enter a cooldown period where they can't take damage. There are destructible tiles that can be blown up, and indestructible tiles that cannot be blown up. There is a clock which will count down from 2 minutes at game start, resulting in a draw if the game is still on when the clock reaches 0.

## How to play

You play as the white bomberman starting in the lower left corner. The other 3 players are bots. 

- You use the arrow keys to move around, and the space bar to place a bomb. 

- 'm' is used to mute/unmute the music.
- 'p' is used to pause/unpause the game.
- 'esc' is used to exit the game.

# How the game works

The game is built using the MVC architecture. The main premise behind the game logic is that the games takes place on an 13x11 grid, with randomly generated destructible tiles inside the grid and a wall of indestructible tiles around the grid. The players are spawned in each corner, and when they 'die' they get moved out of the grid. The same logic applies to the bombs - all the bombs are secretely placed right outside the grid, and get moved in whenever the respective player places a bomb - and only starts ticking when inside the grid. Each player has 2 types of bombs - one 'real' bomb and one 'placeholder' bomb. The placeholder bomb is used to hold the location of where the bomb was to create explosions. The explosions are just different colored tiles, and remove the destructible tiles they hit. The players only move once every clock tick, with special movement handicaps applied to the human player to prevent imbalance. The AI is programmed to move randomly but avoid bombs and explosions, and to not place bombs where they would put themselves in danger. 

ChatGPT was used to optimize and clean up existing code in the model, and many tests were created with ChatGPT.

## Model 

The most important classes in the model are:
- 'BombermanModel' - contains the game logic, has methods for the AI behavior, and methods for placing bombs and moving the bomberman. Updates the view.
- 'BombermanBoard' - the board for the game, contains methods assisting the model in checking for collisions, movement and explosions.
- 'Player, HumanPlayer and AIPlayer' - contains information about the players, such as their position, number of lives, and also contain the bomb objects.
- 'Bomb' - contains information about the bombs, such as their position, and the timer for when they will explode.

## View

The view is almost entirely built up by the 'BombermanView' class. 
- 'BombermanView' - draws the players, bombs, tiles, game window, scoreboard, and everything that is visible.

## Controller

The controller is almost entirely built up by the 'BombermanController' class.
- 'BombermanController' - handles the input from the user, and updates the model accordingly.

# Manual testing

## AI Behavior

The AI behaves in a 'random' manner with some safety measures. This resulted in it being quite difficult to test the AI behavior, as it is not always clear what the AI should do, without installing external frameworks to steer the AI. 

Ways to see if the AI is working as intended:
- the AI places bombs randomly (33% chance)
- the AI moves in a random direction 
- the AI tries to avoid bombs and explosions
- the AI avoids placing bombs where they would put themselves in danger

## Game logic

Ways to see if the game logic is working as intended:
- the game ends when there is only one player left
- the game ends in a draw when the clock reaches 0
- the game ends in a draw when all players are dead
- you can't go through walls
- you can't go through bombs after they have been placed
- explosions damage the player
- explosions destroy cracked walls

# Link to video demonstration
https://youtu.be/Us2zqq0Nqa4


