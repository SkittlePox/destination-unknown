#Destination Unknown
A text adventure creation engine.
Now you can make a text adventure **without knowing a programming language!**

###How to use it
The engine reads information through a json file titled `gameconfig.json` which needs to be in the same directory as the core jar file.

It would help to learn the [json format](http://www.w3schools.com/json/), else you can look at the default config file and see get the jist of it.

**The files that pertain to the creation of the text adventure are located in `out/artifacts/Destination_Unknown_jar/`**

**If you just want to make a text adventure, those are the only files you have to worry about**

Just move them into a separate foler and run one of the `run` scripts.

####Things you can customize in the JSON
* Player
 * Max health
 * Starting health
 * Starting Room
 * Inventory
* Non Player Characters (NPCs)
 * Name
 * Unique or not (The difference between 'Mario' and 'A Mario')
 * Health
 * Gender
 * Inventory
 * Items that drop when killed
* Items
 * Name
 * Damage
 * Returned Health on consumption
 * Unique or not (explained within NPCs)
* Rooms
 * Name
 * Description (Printed on first visit of room and when the player 'look's)
 * Directions from room ('north' goes to Room x)
 * Items in room
 * NPCs in room 
