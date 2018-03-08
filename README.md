# Alerts Calculation #
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
$ cd target/scala-2.12
$ ./alerts
```
Or if you want to filter by Company name (there is not need for quotes)
```sh
$ ./alerts EVIL Corp
```
You can paste this sample to test it directly into the consoe
```
{"id": 123,"mentions": [{ "company": "EVIL Corp", "topic": "Unfair Lending Practices", "impact": 525 },{ "company": "Tyrell Corporation", "topic": "Unfair Lending Practices", "impact": 25 }]}
{"id": 123,"mentions": [{ "company": "EVIL Corp", "topic": "Unfair Lending Practices", "impact": 225 },{ "company": "Tyrell Corporation", "topic": "Unfair Lending Practices", "impact": 215 }]}
{"id": 123,"mentions": [{ "company": "EVIL Corp", "topic": "Unfair Lending Practices", "impact": 25 },{ "company": "Tyrell Corporation", "topic": "Unfair Lending Practices", "impact": 25 }]}
{"id": 123,"mentions": [{ "company": "Smoke Solutiions", "topic": "Unfair Lending Practices", "impact": 525 },{ "company": "Tyrell Corporation", "topic": "Unfair Lending Practices", "impact": 125 }]}
{"id": 123,"mentions": [{ "company": "Smoke Solutiions", "topic": "Unfair Lending Practices", "impact": 225 },{ "company": "Tyrell Corporation", "topic": "Unfair Lending Practices", "impact": 25 }]}
{"id": 123,"mentions": [{ "company": "Smoke Solutiions", "topic": "Unfair Lending Practices", "impact": 25 },{ "company": "Tyrell Corporation", "topic": "Unfair Lending Practices", "impact": 25 }]}
```
And hit enter, the application will issue alerts if found every 30 seconds.
