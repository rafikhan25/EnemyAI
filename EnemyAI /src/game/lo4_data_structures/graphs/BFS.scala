package game.lo4_data_structures.graphs

import game.enemyai.Queue

object BFS {

  var distanceMap: Map[Int,Int] = Map()


  def bfs[A](graph: Graph[A], startID: Int): Unit = {

    for ((id, loc)<-graph.nodes){
      if (id==startID){
        //this line below ensures that the starting node has a distance of 0 in the map
        distanceMap = distanceMap + (id -> 0)
      }
      else{
        //this line below sets all other nodes to a default value of -1, as suggested in lecture 34
        distanceMap = distanceMap + (id-> -1)
      }
    }

    var explored: Set[Int] = Set(startID)

    val toExplore: Queue[Int] = new Queue()
    toExplore.enqueue(startID)


    while (!toExplore.empty()) {
      val nodeToExplore = toExplore.dequeue()

      for (point <- graph.adjacencyList(nodeToExplore)) {

        if(!explored.contains(point)){
          toExplore.enqueue(point)
          explored = explored + point
          //this line below overwrites the distance value previously stored in the distanceMap
          distanceMap = distanceMap + (point -> (distanceMap.getOrElse(nodeToExplore, 0)+1))
        }
      }
    }
  }

}
