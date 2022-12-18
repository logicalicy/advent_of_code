# 2022/12/04

Fourth day of [advent of code](https://adventofcode.com/): an elf did camp cleanup with Rust.

## Part #1

Camp needs to be cleaned up to make space for supplies from the ships.
- Camp space is sectioned
- Every section has a unique ID
- Each Elf is assigned a range of section IDs

But many section assignments overlap!
- Elves pair up to compare their assignments
- Section assignments for each pair of elves (`input.txt`)
- For some pairs, an assignment can fully contain the other (e.g. 2-8 fully contains 3-7)

_Question:_ In how many assignment pairs does one range fully contain the other?

### Run

```
cd ./2022/12/04/elves
cargo build
cargo test
cargo run
```

## Part #2

Elves would like to know the number of pairs that overlap at all.

_Question:_ In how many assignment pairs do the ranges overlap?

### Run

```
cd ./2022/12/04/elves
cargo build
cargo test
cargo run
```

## Language notes

Some Rustacean language features:

* There is an "ownership" model: values have an owner, values are dropped when the owner goes out of scope
* Passing an object by value transfers "ownership" (e.g. in `impl Foo { fn bar(self) { ... } }`, `bar` has taken ownership)
  * You might transfer ownership for threads
* Passing an object by reference allows "ownership" to be retained (e.g. in `impl Foo { fn bar(&self) { ... } }`, the caller retains ownership)
  * Here, the caller retains "ownership" and the callee "borrows"
* `&String` and `&str`
  * Borrowed type `&String` can be coerced into `&str` but not vice versa
  * `&str` is immutable
  * `&mut String` is mutable
* "Slice" is a type of reference, e.g. `let s = String::from("hello world"); let hello = &s[0..5];`
* Macros are called with `!`, e.g. `println!("Hello world")`
* `{ ... }` blocks evaluate to a value
* `let mut x` is a mutable variable
* Doesn't require `return` keyword for returning a value at the end of a block
* References are denoted by a `&` prefix
* `match` is used for pattern-matching: `match x { 1 => "one", 2 => "two", _ => () }`
* Methods are added to `struct`s by creating a corresponding `impl`
* `Option` type is like `Maybe` in Haskell, `None` like `Nothing`, `Some(x)` like `Just x`
* `Vector` stores a list of values, e.g. `let v: Vec<i32> = Vec::new()`
* There's a `vec!` macro, e.g. `let v = vec![1, 2, 3]`
* `std::collections::HashMap` stores a hash map, e.g. `let mut mappings = HashMap::new()`
* Generic types are denoted by `<T>`
* Traits are like Java behavioural interfaces, e.g. `pub trait Runnable { fn runnable(&self); }`
* Anonymous functions are denoted with `| x | { ... }`
* Types have a "lifetime"
  * If a nested type cannot be inferred to have the same lifetime as its parents', a hint may be needed
  * Variables can be dropped while still borrowed
  * E.g. `fn foo<'a>(bar: &'a Vec<&'a str>)`
* ...and more!
