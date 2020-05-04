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

• [Wikipedia](https://en.wikipedia.org/wiki/Functional_programming)

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

• John Hughes, [Why Functional Programming Matters](https://www.cs.kent.ac.uk/people/staff/dat/miranda/whyfp90.pdf), 1990

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

-> ## To sum up <- 

"Functional programming is about being able to use "the
substitution model of evaluation". That's it. 

Everything else: immutability, "pure" functions, and so on,
is in service of that goal. They aren't goals in their own
right."

• [Noel Welsh](https://twitter.com/noelwelsh/status/1255797421935923206)

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
   
  val x: Int = ???
  
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

"... Roughly, it is a general mathematical theory of
structures and of systems of structures... At minimum, it is
a powerful language, or conceptual framework, allowing us to
see the universal components of a family of structures of a
given kind, and how structures of different kinds are
interrelated..."

• [Stanford Encyclopedia](https://plato.stanford.edu/entries/category-theory/)

---

-> ## Category Theory: Functor <-

"A functor is something that can be mapped over."

```
   
  // for a Functor[A]
  def map(f: A => B): Functor[B]
   
```

• `map` is a higher-order function: take other functions as
parameters or return functions as results

---

-> ## Category Theory: Monoid <- 

"A monoid is something that can be aggregated."

Defines associativy (++) and identity (empty).

```
   
  def empty(): Monoid[A]
   
  // for a Monoid[A]
  def ++(m: Monoid[A]): Monoid[A]
   
```

---

-> ## Category Theory: Monad <-

"A monad is just a monoid in the category of endofunctors,
what's the  problem?"

Defines composition (flatMap) and pure (unit)

```
   
  def unit(a: A): Monad[A]
   
  // for a Monad[A]
  def flatMap(f: A => Monad[B]): Monad[B]
   
```

--- 

-> ## For comprehension <-

To avoid callback hell, Scala has the for comprehension
operator.

```
   
  for {
    a <- monadA
    b <- monadB
    c <- monadC
  } yield a + b + c
   
  // is equivalent to
   
  monadA.flatMap(a => 
    monadB.flatMap(b => 
      monadC.map(c => a + b + c) 
    )
  )
   
```

---

-> ## Error Handling: Happy Path <-

"In the context of software or information modeling, a happy
path is a default scenario featuring no exceptional or error
conditions. For example, the happy path for a function
validating credit card numbers would be where none of the
validation rules raise an error, thus letting execution
continue successfully to the end, generating a positive
response."

• [Wikipedia](https://en.wikipedia.org/wiki/Happy_path)

---

-> ## Error Handling: Either monad <-

`Either[E, A]` is an ADT with 2 types
• `Right(a)`
• `Left(e)`

Has a fail-fast mechanism.

---

-> ## Asynchronous programming <- 

`Future[A]`

Future doesn't respect referential transparency:
• strict evaluation
• failure is handled by Exceptions

---

-> ## Async + error handling <-

`Future[Either[E, A]]`

Not easy to handle even with for-comprehension. We can
either:
• write our own monad, eg. `FutureEither[E, A]`
• use monad transformer for cats/scalaz lib `EitherT[F, E, A]`
• use cats-effects library like `IO[A]` or `ZIO[R, E, A]` 

---

-> Wrap-up <-

---

-> Exercise <-

Decompose a duration (amount of seconds, minutes...):
• how many days, hours, minutes and seconds represents
  1 000 000 seconds ? 
