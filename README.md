# Tariff Comparison #
Builds a java executable to compare tariffs

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
$ cd target/scala-2.12
$ ./tariff
$ ./tariff cost 2000 2300
$ ./tariff usage greener-energy power 40
```