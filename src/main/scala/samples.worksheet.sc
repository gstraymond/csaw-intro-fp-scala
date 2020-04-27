// <- Tools

import scala.reflect.runtime.universe._
def prettyType[T: TypeTag](v: T) = typeOf[T].baseClasses.map(typeOf[T].baseType).head.toString

// ->

// <- Immutability

val string1 = "hello world"
prettyType(string1)
// can't be re-assigned
//value1 = "a string updated"
//
val list1 = List(1, 2, 3)
prettyType(list1)
// elements of list1 can't be changed
//list1(0) = 2

// ->

// <- Referential Tranparency

def incr(x: Int) = x + 1
// _ transforms a function as a value (Eta expansion)
prettyType(incr _)

val incrAlt = { x: Int => x + 1 }
prettyType(incrAlt)

// ->

// <- List
list1
val list2 = list1 :+ 4
List(List(), List(1))

val list3 = (1 to 10).toList

// ->

// <- Option

val none       = Option.empty[String]
val someString = Some("string")

// ->

// <- Map

val map1 = Map("hello" -> 1, "world" -> 2)
val map2 = map1 + ("foo" -> 3)

// ->

// <- Functor

list1.map(a => a + 1)
list1.filter(a => a % 2 == 0)

// ->

// <- Monoid

list1 ++ list2
// option is considered as List of 0, 1 elements
someString ++ none
someString ++ none ++ someString

map1 ++ map2

// ->

// <- Monad
list1
list1.flatMap(i => List(i, i + 1))

// ->

// <- Either

val right = Right(1)
val left  = Left("error")

right flatMap (_ => left)
right flatMap (i => Right(i + 1))

// ->

// <- For comprehension

for {
  a <- List(1, 2, 3)
  if a > 1
  f <- List({ i: Int => i * 2 }, { i: Int => i * 3 })
} yield {
  f(a)
}

// ->

// <- Pattern matching

sealed abstract class Duration(val amount: Int)
case class Second(seconds: Int) extends Duration(seconds)
case class Minute(minutes: Int) extends Duration(minutes)
case class Hour(hours: Int)     extends Duration(hours)
case class Day(days: Int)       extends Duration(days)
case class Year(years: Int)     extends Duration(years)

def convert(duration: Duration): List[Duration] =
  convert2(duration)
//convert1(duration)

def convert1(duration: Duration): List[Duration] =
  duration match {
    case Second(seconds) if seconds > 60 => convert1(Minute(seconds / 60)) :+ Second(seconds % 60)
    case Minute(minutes) if minutes > 60 => convert1(Hour(minutes / 60)) :+ Minute(minutes % 60)
    case Hour(hours) if hours > 24       => convert1(Day(hours / 24)) :+ Hour(hours % 24)
    case other                           => List(other)
  }

def rules(duration: Duration) = duration match {
  case _: Second => Some(60, Second, Minute)
  case _: Minute => Some(60, Minute, Hour)
  case _: Hour   => Some(24, Hour, Day)
  case _: Day    => Some(365, Day, Year)
  case _: Year   => None
}

def convert2(duration: Duration): List[Duration] =
  rules(duration) match {
    case Some((max, currentDuration, nextDuration)) if duration.amount > max =>
      convert2(nextDuration(duration.amount / max)) :+ currentDuration(duration.amount % max)
    case _ => List(duration)
  }

convert(Second(12))
convert(Second(72))
convert(Second(6666))
convert(Second(1000000))
convert(Second(10000000))
convert(Second(100000000))
convert(Minute(73))
convert(Minute(6666))
convert(Minute(1000000))

// ->
