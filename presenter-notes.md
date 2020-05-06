# Presenter notes

## Agenda

- there is a trend towards the usage of FP program (Scala, Haskell, Rust, Purescript, Reason)
- better grasp what is Functional Programming
- to create safer programs
- through some example using Scala (not a pure FP prog lang but most concepts applies)
- look at Category Theory, won't do any math here :)
- will look only at the FP part of Scala

## What is FP ?

## Why

- notion of scope of execution, reasoning about code execution

## Immutability

- WORKSHEET: Immutablity
- impacts variable assignment

## Referential Transparency

- be able to reason about a function exclusively from its INPUT/OUTPUT
- invoking a pure function will ONLY return the result from its declared type

## Pure Function / Side-effect

- WORKSHEET: Pure function
- predictability

## Lazy Evaluation / Parallelization

- WORKSHEET: Lazy evaluation

## Algebraic Data Type

- WORKSHEET: ADT
- generally used to modelise precisely INPUT/OUTPUT of our program

# Exercise

```scala

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
```

