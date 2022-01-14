# Bomberman

Semester work on the subject Informatics 1 for Faculty of Management Science and Informatics UNIZA.

## What is is about?

Game for 2 players whose task is to destroy the other with a bomb. Players appear on a map that the user can change in the `map.csv` file. The map shows 3 basic types of blocks, an _indestructible wall_, a _destructible wall_ and an _empty block_. The player can only move through empty blocks. When a player drops a bomb, then it explodes after a specific time and destroys all destructible walls in it's blast radius. An explosion is displayed after the explosion. If a player is in the radius of a bomb at the time of an explosion, or goes into an explosion, his life will be lost. If the bomb destroys the destructible wall, there is a 50% chance that a power up will drop from the destroyed block. There are 2 types of upgrades in the game, _increase the radius of the bomb blast_ and _add a bomb_. If there is another bomb in the radius of the bomb, it will explode immediately. If a player is on the same block as the upgrade, it is automatically activated. If the player has no life left, the other player wins. There is no time limit in the game.

## Controls

|         | Player 1 (player in bottom right corner)| Player 2 (player in top left corner)  |
|---------|:---------------------------------------:|:-------------------------------------:|
| Command |                   Key                   |                   Key                 |
| UP      |                    ↑                    |                    W                  |
| DOWN    |                    ↓                    |                    S                  |
| LEFT    |                    ←                    |                    A                  |
| RIGHT   |                    →                    |                    D                  |
| BOMB    |                  SPACE                  |                    G                  |
| EXIT    |                   ESC                   |                   ESC                 |

## Instructions for creating your own map

We will create a .csv (comma-separated values) file. When creating a map, characters are used to indicate individual blocks. It is _important_ to save the map in the _maps_ folder.

* `N` - indestructible wall,
* `Z` - destructible wall,
* `O` - empty block

The recommended _width_ of the map is 23 blocks and _height_ is 20 blocks.

## Author words
This is translated version (from Slovak language to English language) of my final code for semester work on the subject Informatics 1 for Faculty of Management Science and Informatics UNIZA. I worked on this project from 21.11.2021 to 6.1.2022. The whole project is written in Java.
