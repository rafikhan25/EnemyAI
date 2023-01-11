package game.enemyai

import game.lo4_data_structures.linkedlist.LinkedListNode

class Queue[A] {
  var front: LinkedListNode[A] = null
  var back: LinkedListNode[A] = null
  def enqueue(a: A): Unit = {
    if (back == null) {
      this.back = new LinkedListNode[A](a, null)
      this.front = this.back
    } else {
      this.back.next = new LinkedListNode[A](a, null)
      this.back = this.back.next
    }
  }

  def empty(): Boolean = {
    front == null
  }
  def dequeue(): A = {
    val toReturn = this.front.value
    this.front = this.front.next
    if(this.front == null){
      this.back = null
    }
    toReturn
  }
}
