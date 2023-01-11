package tests

import org.scalatest._
import game.gameobjects.{Player}
import game.enemyai.{AIPlayer, PlayerLocation}
import game.lo4_data_structures.linkedlist.LinkedListNode
import game.maps.GridLocation
import game.physics.PhysicsVector

class LectureTask3 extends FunSuite {
  val EPSILON: Double = 0.001

  def compareDoubles(d1: Double, d2: Double): Boolean = {
    Math.abs(d1 - d2) < EPSILON
  }

  def compareLocations(a: PlayerLocation, b: PlayerLocation): Boolean = {
    if (compareDoubles(a.x, b.x) && compareDoubles(a.y, b.y) && a.playerId == b.playerId) {
      true
    }
    else {
      false
    }
  }

  def compareVectors(a: PhysicsVector, b: PhysicsVector): Boolean = {
    if (compareDoubles(a.x, b.x) && compareDoubles(a.y, b.y)) {
      true
    }
    else {
      false
    }
  }

  test("possible paths"){
    val player: AIPlayer = new AIPlayer("24")

    val aGrid: GridLocation = new GridLocation(0,0)
    val bGrid: GridLocation = new GridLocation(2,4)

    val output = player.computePath(aGrid,bGrid)

    val firstElement = output.apply(0).value
    val secondElement = output.apply(1).value
    assert(output.size()==7)

    val cGrid: GridLocation = new GridLocation(1,3)
    val dGrid: GridLocation = new GridLocation(2,2)

    val output2 = player.computePath(cGrid,dGrid)
    val firstElement2 = output2.apply(0).value
    val secondElement2 = output2.apply(1).value
    val lastElement2 = output2.apply(2).value
    assert(firstElement2.x==cGrid.x)
    assert(firstElement2.y==cGrid.y)
    assert(lastElement2.x==dGrid.x)
    assert(lastElement2.y==dGrid.y)
    assert(output2.size()==3)

    //first condition: Only valid moves
    //for element in output:
    //check that x and y do not change at the same time

    //second condition: Start and end are correct
    //assert(output.head.x==start.x && output.head.y==start.y)
    //assert(output(output.length-1).x==end.x && output(output.length-1).y==end.y)

    //third condition: smallest length possible
    //assert(output.length==5)


  }




}