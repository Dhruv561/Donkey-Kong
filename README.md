Donkey Kong Project README<br>
Built with Bagel game engine, Maven and OOP principles in Java

Assumptions:
- If either enter or 1 is pressed, user is put into level 1.
- The points from level one carry over to level 2.
- There is no time multiplier for level 1, only level 2 has a time multiplier.
- If game is lost, points are set to 0.
- To gain points for jumping over a barrel, mario's x coordinate needs to be in between barrel's
  x coordinate whilst is y coordinate is over the barrels. This means mario can jump over barrel half
  way and go back and the points will still be counted.
- If monkeys reach the edge of a platform or wall, regardless of its position, it will move to the next
  movement pattern and change direction.
- Monkeys can not interact with barrels, ladders or other entities except mario.
- Both bullets and bananas can only travel 300 pixels before being destroyed.
- If mario kills an intelligent monkey, all bananas which are currently or previously thrown are destroyed.
- Mario will lose if touching bananas, monkeys, donkey kong or barrels if holding gun, only holding a hammer
  would result in the game not being lost.
- If mario has a gun and then picks up a hammer, his bullets are set to 0. If he then picks up another gun,
  his bullets are set to 5 not 10.
- If mario is holding a hammer and runs into a banana, the hammer will not destroy the banana and the game
  will be lost.
- If mario fires a bullet at a banana, the projectiles will not collide and destroy each other. 
