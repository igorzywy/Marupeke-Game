Displaying the data:
I use a border pane and a few grid panes to display the grid. I use the buttonsMaker method to create the
initial grid and it fills the grid with blank tiles and also adds red tiles with symbols that cannot be edited.
To get the data I use the getGrid and getEditable methods in the MarupekeGrid class to get the symbols and 
whether or not the tile is editable. I then go though each element and assign it to a button there is always 
the same amount of buttons as there are elements in the grid object. I use a random object to set the number of 
pre filled O' X' and solids and the random number is always half or less than the maximum size. I also make 
buttons to check if the player has won and the player can use this button at anytime to check if the game is 
complete without any errors and every tile is filled. The grid is then deleted from the centre of the border pane
and replaced with a congratulations message. 

Editing the data:
To iterate through the tiles to display on the buttons I check if the button is clicked and then I increment 
the loop integer until it reaches 2 where it resets it to -1. I use a switch statement and loop through 
numbers -1 to 2 each number represents a different symbol. 
I also mark the original grid and I do this by getting the row and column of the button that was pressed and 
using the mark methods made in MarupekeGrid. This ultimately changes the grid and GUI at the same 
time so they are always the same.
I replaced the _ for the blank with " " to make it better to look 
at on the GUI.  

The player has the option to change the size of the grid at anytime throughout the game. Clicking a size 
button will remove the grid in the centre and remake the whole game again with the new size creating a new 
grid with different amounts of pre-defined tiles.  



