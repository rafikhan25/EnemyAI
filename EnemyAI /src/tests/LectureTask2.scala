package tests

import org.scalatest._
import game.enemyai.{AIPlayer, PlayerLocation}
import game.lo4_data_structures.linkedlist.LinkedListNode
import game.maps.GridLocation
import game.physics.PhysicsVector

class LectureTask2 extends FunSuite {
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
  def compareVectors(a: PhysicsVector, b: PhysicsVector): Boolean = {
    if (compareDoubles(a.x,b.x) && compareDoubles(a.y,b.y)){
      true
    }
    else{
      false
    }
  }



  test("pathToDirection"){
    val player: AIPlayer = new AIPlayer("24")

    val aLoc: PlayerLocation = new PlayerLocation(0,0,"24")
    val bLoc: PlayerLocation = new PlayerLocation(5,10,"cat")
    val cLoc: PlayerLocation = new PlayerLocation(20,4,"Jesse")

    var linkedList: LinkedListNode[PlayerLocation] = new LinkedListNode[PlayerLocation](cLoc,null)
    linkedList = new LinkedListNode[PlayerLocation](bLoc,linkedList)
    linkedList = new LinkedListNode[PlayerLocation](aLoc,linkedList)

    val aGrid: GridLocation = new GridLocation(0,0)
    val bGrid: GridLocation = new GridLocation(0,1)
    val cGrid: GridLocation = new GridLocation(0,2)

    var linkedList2: LinkedListNode[GridLocation] = new LinkedListNode[GridLocation](cGrid,null)
    linkedList2 = new LinkedListNode[GridLocation](bGrid,linkedList2)
    linkedList2 = new LinkedListNode[GridLocation](aGrid,linkedList2)



    val output = player.pathToDirection(linkedList,linkedList2).normal2d()
    val expected = new PhysicsVector(0.316,0.949,0)
    assert(compareVectors(output,expected))

    val output2 = player.pathToDirection(linkedList,linkedList2)
    val expected2 = new PhysicsVector(0.5,1.5,0)
    assert(compareVectors(output2,expected2))
    assert(!compareVectors(output,expected2))


  }
  test("test 2"){
    val player: AIPlayer = new AIPlayer("cat")

    val bLoc: PlayerLocation = new PlayerLocation(5,10,"cat")
    val cLoc: PlayerLocation = new PlayerLocation(20,4,"Jesse")

    var linkedList: LinkedListNode[PlayerLocation] = new LinkedListNode[PlayerLocation](cLoc,null)
    linkedList = new LinkedListNode[PlayerLocation](bLoc,linkedList)


    val aGrid: GridLocation = new GridLocation(5,10)
    val bGrid: GridLocation = new GridLocation(2,20)


    var linkedList2: LinkedListNode[GridLocation] = new LinkedListNode[GridLocation](bGrid,null)
    linkedList2 = new LinkedListNode[GridLocation](aGrid,linkedList2)

    val output = player.pathToDirection(linkedList,linkedList2)
    val expected = new PhysicsVector(-2.5,10.5,0)
    assert(compareVectors(output,expected),output)


  }


}
