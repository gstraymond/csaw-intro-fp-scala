// <- Tools ************************************************

import scala.reflect.runtime.universe._
def prettyType[T: TypeTag](
  v: T,
  level: Int = 1
) = typeOf[T].baseClasses.map(typeOf[T].baseType).take(level).mkString(", ")

// ->

// <- Immutability *****************************************

// immutable value
val string1 = "hello world"
prettyType(string1)
// can't be re-assigned
//string1 = "a string updated"

// immutable data structure
val list1 = List(1, 2, 3)
prettyType(list1)
// elements of list1 can't be changed
//list1(0) = 2
//list1.updated(0, 2)

// immutable class
case class Person(
  name: String,
  age: Int)
val john = Person("John", 25)
//john.age = 26
john.copy(age = 26)

// ->

// <- Pure functions ***************************************

// declaring pure functions

def increment_v1(x: Int) = x + 1
// _ transforms a function as a value (Eta expansion)
prettyType(increment_v1 _)

val increment_v2 = { x: Int => x + 1 }
prettyType(increment_v2)

// not referentially transparent
def nrt1(): String = throw new RuntimeException("oops")
prettyType(nrt1 _)

// not referentially transparent
var mutableString  = "hello"
def nrt2(): String = { mutableString = "world"; "oops" }
prettyType(nrt2 _)

// ->

// <- Lazy evaluation **************************************

// by default, Scala use strict evaluation
def strictEval(string: String) = ""

strictEval("a" + "b" + "c")

// Using => enables lazy evaluation
def lazyEval(string: => String) = ""

lazyEval("a" + "b" + "c")

// ->

// <- Substitution model ***********************************

increment_v1(increment_v1(increment_v1(2)))

increment_v1(increment_v1(2 + 1))

increment_v1(increment_v1(3))

increment_v1(3 + 1)

increment_v1(4)

//  ->
