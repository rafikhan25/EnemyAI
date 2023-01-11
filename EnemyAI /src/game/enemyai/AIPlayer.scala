package game.enemyai

import game.enemyai.decisiontree.{ActionNode, DecisionNode, DecisionTreeValue}
import game.lo4_data_structures.graphs.{BFS, Graph}
import game.lo4_data_structures.linkedlist.LinkedListNode
import game.lo4_data_structures.trees.BinaryTreeNode
import game.maps.GridLocation
import game.physics.PhysicsVector
import game.{AIAction, Fire, MovePlayer}
import game.enemyai.Queue



class AIPlayer(val id: String) {


  // TODO: LT1
  def locatePlayer(playerId: String, playerLocations: LinkedListNode[PlayerLocation]): PlayerLocation = {
    var result: PlayerLocation = new PlayerLocation(0,0,"")
    for (node<-playerLocations){
      if (playerId==node.playerId){
        result = node
      }
    }
    result
  }
  def distanceCalculator(l1: PlayerLocation, l2: PlayerLocation): Double = {
    val add1 = math.pow(l1.x-l2.x,2)
    val add2 = math.pow(l1.y-l2.y,2)
    math.sqrt(add1+add2)
  }

  def closestPlayer(playerLocations: LinkedListNode[PlayerLocation]): PlayerLocation = {
    var currentLocation = locatePlayer(this.id,playerLocations)
    var result: PlayerLocation = new PlayerLocation(1000000,1000000,"")
    for (locationFromList<-playerLocations){
      if (distanceCalculator(currentLocation,locationFromList)<distanceCalculator(currentLocation,result)){
        if(currentLocation.playerId!=locationFromList.playerId){
          result = locationFromList
        }
      }
    }
    result
  }


  // TODO: LT2
  def pathToDirection(playerLocations: LinkedListNode[PlayerLocation], path: LinkedListNode[GridLocation]): PhysicsVector = {
    val myLocation = locatePlayer(this.id, playerLocations)

    val moveTo = if(path.next == null){
      path.value.centerAsVector()
    }
    else{
      path.next.value.centerAsVector()
    }


    new PhysicsVector(moveTo.x - myLocation.x, moveTo.y - myLocation.y)

  }


  // TODO: LT3
  def computePath(start: GridLocation, end: GridLocation): LinkedListNode[GridLocation] = {
    //this line below establishes the return linked list that starts off with the end coordinates
    //because it's easier to work backwards when adding to a linked list

    var returnList: LinkedListNode[GridLocation] = new LinkedListNode[GridLocation](new GridLocation(end.x,end.y), null)
    //below we create a changing variable for the x coordinate to create new nodes each time we
    //increment the x value from point A towards point B
    //the reason we set these to the end x and y is because, like we mentioned before, it's easier
    //to work backwards when adding to linked lists
    var endX = end.x
    var endY = end.y

    while (endX != start.x){
      if (endX < start.x) {
        endX += 1
        returnList = new LinkedListNode[GridLocation](new GridLocation(endX, endY),returnList)
      }
      else if (endX > start.x) {
        endX -= 1
        returnList = new LinkedListNode[GridLocation](new GridLocation(endX, endY),returnList)
      }
    }



    while (endY != start.y){
      if (endY < start.y){
        endY += 1
        returnList = new LinkedListNode[GridLocation](new GridLocation(endX, endY),returnList)
      }
      else if (endY > start.y){
        endY -= 1
        returnList = new LinkedListNode[GridLocation](new GridLocation(endX, endY),returnList)
      }
    }

    returnList
  }



  def makeDecision(gameState: AIGameState, decisionTree: BinaryTreeNode[DecisionTreeValue]): AIAction = {
    //MovePlayer(this.id, Math.random() - 0.5, Math.random() - 0.5)
    val checker = decisionTree.value.check(gameState)

    if(checker > 0){
      makeDecision(gameState, decisionTree.right)
    }
    else if (checker < 0){
      makeDecision(gameState, decisionTree.left)
    }
    else{
      decisionTree.value.action(gameState)
    }
  }



  // TODO: LT5


  def distanceAvoidWalls(gameState: AIGameState,Grid1: GridLocation, Grid2: GridLocation): Int = {
    var graph: Graph[GridLocation] = gameState.levelAsGraph()
    val ID1: Int = Grid1.y*gameState.levelWidth+Grid1.x
    val ID2: Int = Grid2.y*gameState.levelWidth+Grid2.x
    val walls: List[Int] = for (wallPoint <- gameState.wallLocations) yield {
      //this line below yields the ID for each wall grid point
      wallPoint.y * gameState.levelWidth + wallPoint.x
    }

    //below, we create a new BFS object and call the bfs method on the level as a graph and the
    //id of the first grid point

    val BFSOBJ = BFS

    BFSOBJ.bfs(graph,ID1)
    val noWalls = BFSOBJ.distanceMap(ID2)
    noWalls

  }
  // TODO: LT6


  // TODO: AO1

  // TODO: AO2
  def decisionTree(referencePlayer: AIPlayer): BinaryTreeNode[DecisionTreeValue] = {
    val huntClosestPlayer = new BinaryTreeNode[DecisionTreeValue](new ActionNode((gameState: AIGameState) => {
      val myLocation: PlayerLocation = referencePlayer.locatePlayer(referencePlayer.id, gameState.playerLocations)
      val closestPlayer: PlayerLocation = referencePlayer.closestPlayer(gameState.playerLocations)
      val path = referencePlayer.computePath(myLocation.asGridLocation(), closestPlayer.asGridLocation())
      val direction: PhysicsVector = referencePlayer.pathToDirection(gameState.playerLocations, path)
      MovePlayer(referencePlayer.id, direction.x, direction.y)
    }), null, null)

    val fire = new BinaryTreeNode[DecisionTreeValue](new ActionNode((gameState: AIGameState) => {
      Fire(referencePlayer.id)
    }), null, null)

    val fireProbability = 0.1
    val decider = new BinaryTreeNode[DecisionTreeValue](
      new DecisionNode((gameState: AIGameState) => if (Math.random() < fireProbability) -1 else 1), fire, huntClosestPlayer
    )

    decider
  }


}

