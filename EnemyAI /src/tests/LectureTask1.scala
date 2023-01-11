package tests

import org.scalatest._
import game.enemyai.{AIPlayer, PlayerLocation}
import game.lo4_data_structures.linkedlist.LinkedListNode

class LectureTask1 extends FunSuite {
  val EPSILON: Double = 0.001

  def compareDoubles(d1: Double, d2: Double): Boolean = {
    Math.abs(d1 - d2) < EPSILON
  }

  def compareLocations(a: PlayerLocation, b: PlayerLocation): Boolean = {
    if (compareDoubles(a.x,b.x) && compareDoubles(a.y,b.y) && a.playerId == b.playerId){
      true
    }
    else{
      false
    }
  }



  test("your test") {
    val player: AIPlayer = new AIPlayer("24")

    val aLoc: PlayerLocation = new PlayerLocation(0,0,"24")
    val bLoc: PlayerLocation = new PlayerLocation(5,10,"cat")
    val cLoc: PlayerLocation = new PlayerLocation(20,4,"Jesse")

    var linkedList: LinkedListNode[PlayerLocation] = new LinkedListNode[PlayerLocation](cLoc,null)
    linkedList = new LinkedListNode[PlayerLocation](bLoc,linkedList)
    linkedList = new LinkedListNode[PlayerLocation](aLoc,linkedList)

    val output = player.locatePlayer("cat",linkedList)
    assert(compareLocations(output,bLoc))



  }

  test("Testing closest player method"){
    val player: AIPlayer = new AIPlayer("24")

    val aLoc: PlayerLocation = new PlayerLocation(0,0,"24")
    val bLoc: PlayerLocation = new PlayerLocation(5,10,"cat")
    val cLoc: PlayerLocation = new PlayerLocation(20,4,"Jesse")

    var linkedList: LinkedListNode[PlayerLocation] = new LinkedListNode[PlayerLocation](cLoc,null)
    linkedList = new LinkedListNode[PlayerLocation](bLoc,linkedList)
    linkedList = new LinkedListNode[PlayerLocation](aLoc,linkedList)

    val output = player.closestPlayer(linkedList)
    assert(compareLocations(output,bLoc))

  }

}
