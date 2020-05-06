// <- Tools ************************************************

import scala.reflect.runtime.universe._
def prettyType[T: TypeTag](
  v: T,
  level: Int = 1
) = typeOf[T].baseClasses.map(typeOf[T].baseType).take(level).mkString(", ")

// ->

// <- List *************************************************

val list1 = List(1, 2, 3)
val list2 = list1 :+ 4
List(List(), List(1))

val list3 = (1 to 10).toList

// ->

// <- Option ***********************************************

val none       = Option.empty[String]
val someString = Some("hello")

// ->

// <- Map **************************************************

// a map is a list of tuples
val map1 = Map("hello" -> 1, "world" -> 2)
val map2 = map1 + ("foo" -> 3)

// ->
//
// <- Functor **********************************************

list1.map(a => a + 1)

none.map(_ ++ " app")
someString.map(_ ++ " appended")

// a tuple (k, v) is used to represent a map entry
// F[K,V] => F[K1, V1]
map1.map { case (k, v) => (k, v + 1) }

// ->

// <- Monoid ***********************************************

// List[A]
List.empty[Boolean]

list1 ++ list2

// Option[A]

Option.empty[Boolean]

// option is considered as List of 0, 1 elements
someString ++ none
someString ++ none ++ someString

// Map[K, V]

Map.empty[String, String]

map1 ++ map2

// ->

// <- Monad ************************************************

// list
List(1)

list1

list1.flatMap(i => List(i, i + 1))

// option
Option(1)

none.flatMap(string => Some(string ++ string))
someString.flatMap(string => Some(string ++ string))

// map
val map3 = Map("k" -> "v")
map3.flatMap { case (k, v) =>
  Map(k -> k, v -> v)
}

// ->

// <- For-comprehension ************************************

// filtering
for {
  i <- List(1, 2, 3, 4)
  if i % 2 == 0
  j <- List(1, 2, 3, 4)
  if j % 2 == 1
} yield i + j

// pattern matching deconstruct
case class Person(name: String, age: Int)
val john = Person("john", 25)

for {
  Person(_, age) <- List(john, john.copy(age = 12))
} yield age

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
