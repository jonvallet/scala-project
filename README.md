# Quote Calculation #
Builds a jvm executable to calculate quotes

## Build ##
```sh
$ ./sbt assembly
```

## Test ##
```sh
$ ./sbt test
```

## Run ##
```sh
$ cp market.csv target/scala-2.12
$ cd target/scala-2.12
$ ./quote
$ ./quote market.csv 1000
```