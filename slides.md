%title: Intro to FP using Scala

-> ## Agenda <-

• What
• Why
• General principles
• Category Theory

---

-> ## What is FP ? <-

Functional programming is a programming paradigm that treats
computation as the evaluation of mathematical functions and
avoids changing-state and mutable data

• Wikipedia

---

-> ## Why <-

"Functional programs contain no assignment, statements, so
variables, once given a value, never change. 

More generally, functional programs contain no side-effects
at all. A function call can have no effect other than to
compute its result. This eliminates a major source of bugs,
and also makes the order of execution irrelevant — since no
side-effect can change an expression’s value, it can be
evaluated at any time." 

• John Hughes. "Why Functional Programming Matters", 1990

---

-> ## Immutability <-

"Functional programs contain no assignment statements,
so variables, once given a value, never change."


---
-> ## Referential Transparency <-

"More generally, functional programs contain no side-effects
at all. A function call can have no effect other than to
compute its result. This eliminates a major source of
bugs ..."

---

-> ## Pure function and Side-effect <- 

*What is a pure function?*

 • A function that has no side effects.

*What is a side effect?*

 • Modifying a data structure in place
 • Setting a field on an object 
 • Modifying a variable outside the scope of the function
 • Throwing an exception
 • ... 

---

-> ## Lazy evaluation and easier parallelization <- 

"... and also makes the order of execution irrelevant — since
no side-effect can change an expression’s value, it can be
evaluated at any time."

---

-> ## Algebraic Data Types <-

Algebraic Data Types (ADTs for short) are a way of
structuring data. Mostly used with pattern matching. One use
them to make illegal states impossible to represent.

There are two basic categories of ADTs:

 • product types
 • sum types

---

-> ## Algebraic Data Types: Sum Type <-

```
   
  sealed trait Bool
   
  case object True extends Bool
  case object False extends Bool
   
```

Arity is the sum of its types: 
 • 2 : *True*, *False*

---

-> ## Algebraic Data Types: Product Type <-

```
   
  case class Boolean2(b1: Bool, b2: Bool)
   
```

Arity is the product of its fields: 
 • 4 : *True/True*, *True/False*, *False/True*, *False/False*

---

-> ## Pattern Matching <-

Pattern matching is a mechanism for checking a value against
a pattern. A successful match can also deconstruct a value
into its constituent parts.

```
   
  val x: Int = generateRandonInt()
  
  x match {
    case 0 => "zero"
    case 1 => "one"
    case 2 => "two"
    case _ => "other"
  }
   
```

---

-> ## Pattern Matching <-

For ADT, pattern matching can verified at compile time that
all cases are covered. 

```
   
  val b: Bool = ???
  
  bool match {
    case True  => "true"
    case False => "false"
  }
   
```

---

-> ## Pattern Matching <-

Pattern matching can also deconstruct case class.

```
   
  val boolean2: Boolean2 = ???
  
  boolean2 match {
    case Boolean2(True, True) => ???
    case Boolean2(any, False) => // any can be True/False
  }
   
```

---

-> ## Category theory <-

"Category theory formalizes mathematical structure and its
concepts in terms of a labeled directed graph called a
category, whose nodes are called objects, and whose labelled
directed edges are called arrows (or morphisms)."

• Wikipedia

---

-> ## Category Theory: Functor <-


```
   
  // for a functor F[A]
  def map(f: A => B): F[B]
   
```

- `map` is a higher-order function: take
other functions as parameters or return functions as
results

---

-> ## Category Theory: Monoid <- 

concat / ++

M[A] concat M[A] = M[A]

Option[A]
List[A]
Map[A, B]

---

-> ## Category Theory: Monad <-

"A monad is just a monoid in the category of endofunctors,
what's the  problem?"

flatMap

M[A] flatMap (A => M[B]) = M[B]

Option[A]
List[A]
Map[K, V]

for yield: generator / filters

---

Error Handling - Happy path

• Either[E, A]
• Right(a)
• Left(e)

---

Async programming

Future[A]

- doesn't respect referential transparency

---

Future[Either[E, A]]

---

-> Wrap-up <-

---

To remove

    - Pure functions and functions as values
    - Combinators
    - Abstracting over types
    - Composition

Functions as Objects - High Order Function
