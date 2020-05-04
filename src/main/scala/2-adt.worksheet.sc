// <- Tools ************************************************

import scala.reflect.runtime.universe._
def prettyType[T: TypeTag](
  v: T,
  level: Int = 1
) = typeOf[T].baseClasses.map(typeOf[T].baseType).take(level).mkString(", ")

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
