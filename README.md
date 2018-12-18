# Landmine

A random walker through an m x n grid populated with mines.

## Installation

Clone the repo with the `landmine` located at:
> https://github.com/jdsteinhauser/landmine.git

## Usage

To compile to an uberjar, run:
> lein uberjar

To run, use the command:
> java -jar landmine-0.1.0-standalone.jar [args]

where
```
|Argument |Name |Default|
|--------:|:----|:-----:|
| Breadth |  m  |  100  |
|  Depth  |  n  |  100  |
| % Open  |  q  |   85  |
| # runs  |  k  | 10000 |
```

To run a 100x100 grid with 15% of squares filled with mines (85% "Don't Really Explode" (DRE)), run:
> java -jar landmine-0.1.0-standalone.jar m 100 n 100 q 85 k 10000

## Options

Mines are populated with 10% as instant "kills," 30% as "flesh wounds" (10% hit to health), and 60% as "paper cuts" (1%). Currently, this is hard-coded.

## Examples

```
> java -jar landmine-0.1.0-standalone.jar m 100 n 100 q 85 k 10000
100x100 grid, DRE 85%: 44/10000 completed, or 0.44%
"Elapsed time: 78945.40323 msecs"
```

## License

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
