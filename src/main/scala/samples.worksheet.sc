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
//value1 = "a string updated"

// immutable data structure
val list1 = List(1, 2, 3)
prettyType(list1)
// elements of list1 can't be changed
//list1(0) = 2

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

def incr1(x: Int) = x + 1
// _ transforms a function as a value (Eta expansion)
prettyType(incr1 _)

val incr2 = { x: Int => x + 1 }
prettyType(incr2)

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

// Using => enables lazy evaluation
def lazyEval(string: => String) = ""

// ->

// <- ADT **************************************************

// redefining scala Boolean as an ADT
sealed trait Bool

case object True  extends Bool
case object False extends Bool

case class Boolean2(
  b1: Bool,
  b2: Bool)

// ->

// <- Pattern Matching *************************************

// exhaustive match - compile will fail is a match is missing
def xor_1(boolean2: Boolean2) = boolean2 match {
  case Boolean2(True, True)   => 1
  case Boolean2(False, False) => 1
  case Boolean2(True, False)  => 0
  case Boolean2(False, True)  => 0
}

xor_1(Boolean2(True, False))

// using if and _ placeholder
def xor_2(boolean2: Boolean2) = boolean2 match {
  case Boolean2(b1, b2) if b1 == b2 => 1
  case _                            => 0
}

xor_2(Boolean2(True, False))

// ->

// <- List *************************************************

list1
val list2 = list1 :+ 4
List(List(), List(1))

val list3 = (1 to 10).toList

// ->

// <- Option ***********************************************

val none       = Option.empty[String]
val someString = Some("string")

// ->

// <- Map **************************************************

// a map is a list of tuples
val map1 = Map("hello" -> 1, "world" -> 2)
val map2 = map1 + ("foo" -> 3)

// ->

// <- Functor **********************************************

list1.map(a => a + 1)

someString.map(_ ++ " appended")

// a tuple (k, v) is used to represent a map entry
map1.map { case (k, v) => (k, v + 1) }

// ->

// <- Monoid ***********************************************

list1 ++ list2

// option is considered as List of 0, 1 elements
someString ++ none
someString ++ none ++ someString

map1 ++ map2

// ->

// <- Monad ************************************************

list1

list1.flatMap(i => List(i, i + 1))

someString.flatMap(string => Some(string ++ string))

// ->

// <- Either ***********************************************

val right = Right(1)
val left  = Left("error")

right flatMap (_ => left)
right flatMap (i => Right(i + 1))

// ->

// <- For comprehension ************************************

for {
  a <- List(1, 2, 3)
  if a > 1
  f <- List({ i: Int => i * 2 }, { i: Int => i * 3 })
} yield {
  f(a)
}

// ->

// <- Exercise *********************************************

// ->
