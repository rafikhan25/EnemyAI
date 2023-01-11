package tests


import game.GameState
import org.scalatest._
import game.gameobjects.Player
import game.enemyai.{AIGameState, AIPlayer, PlayerLocation}
import game.lo4_data_structures.linkedlist.LinkedListNode
import game.maps.GridLocation
import game.physics.PhysicsVector

class LectureTask5 extends FunSuite {
  val EPSILON: Double = 0.001

  def compareDoubles(d1: Double, d2: Double): Boolean = {
    Math.abs(d1 - d2) < EPSILON
  }

  test("test2"){
    val player: AIPlayer = new AIPlayer("24")
    val gameState: AIGameState = new AIGameState
    gameState.levelWidth = 15
    gameState.levelHeight = 15

    gameState.wallLocations = List(new GridLocation(3,10),
      new GridLocation(3,9),
      new GridLocation(3,8),
      new GridLocation(3,7)
    )

    val gridA: GridLocation = new GridLocation(2,9)
    val gridB: GridLocation = new GridLocation(4,9)

    val output = player.distanceAvoidWalls(gameState,gridA,gridB)
    assert(output==6)



  }
  test("test1"){
    val player: AIPlayer = new AIPlayer("24")
    val gameState: AIGameState = new AIGameState
    gameState.levelWidth = 15
    gameState.levelHeight = 15

    val gridA: GridLocation = new GridLocation(2,3)
    val gridB: GridLocation = new GridLocation(5,5)

    val output = player.distanceAvoidWalls(gameState,gridA,gridB)
    assert(output==5)



  }

}
