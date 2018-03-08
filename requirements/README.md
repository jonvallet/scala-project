# Polecat coding exercise

## Instructions

As part of your application to join Polecat as a software engineer we'd like to you to write some code to solve the following requirements, or as much as you can achieve in a reasonable time.

### What you should do

- Read all the instructions carefully
- Allow 1-2 hours (we don't expect a larger time investment)
- Write code that meets some or all of the requirements listed below
- When you've finished:
  - Make sure the code works
  - Write any instructions needed to run it
  - Zip it up and email it back to us, or provide a link to where it can be downloaded

Please do not post the code (or these instructions) publicly e.g. on a public github repo.

### Our criteria

The purpose of the exercise is to allow you to demonstrate your programming ability in a lower-pressure situation than within an interview.

We are specifically looking for:
- a program we can run, easily, that works (for at least some of the requirements)
- code we can read, where the behaviour is easy to predict
- clues about your thought process - comments perhaps, or a README, or at least clear naming
- evidence you can use functional programming
- ideally, a streams-based / reactive approach

It's fine to use libraries that support your approach, but it is not fine to use an existing application that solves the problem for you.

We're particularly interested in a streams/reactive approach because this mirrors how a lot of our data processing systems work. We have code written in Java, Clojure and JS (Ramda / Most.js) that uses this style, but please choose a language and tools you are very comfortable with rather than trying to learn something new.

## Challenge

We would like you to build a prototype alerting system.

We ingest large numbers of documents that mention companies and topics. We would like to be able to tell our clients if their company is suddenly being mentioned a lot in relation to a specific topic. For example, this could represent a looming reputational crisis (e.g. perhaps the CEO wrote some outrageous comments on twitter that are going viral).

The program will receive documents which are marked up with "mentions" of various companies and topics along with a score for how impactful that "mention" appears to be. The impact score is a relative number from 0 to 1000 that indicates how much of an impact this document is likely to have on the company's reputation. For instance a document on the front page of the Financial Times will have a high impact.

Your program should be configured with a time window to monitor (such as 30 seconds) and a threshold score (such as 100). As soon as the total score for any company/topic pair observed during the rolling time window exceeds the configured threshold, the program should emit an alert.

To keep things simple, input will be sent to the program via its STDIN channel, one document per line encoded in JSON. Alerts should be emitted to STDOUT in the same format. You can assume that input will be correctly-formed - no need to take care over validating input data.

## Input data schema (document)

Here's an example input document:

```javascript
{
  "id": 123,
  "mentions": [
    { "company": "EVIL Corp", "topic": "Unfair Lending Practices", "impact": 525 },
    { "company": "Tyrell Corporation", "topic": "Unfair Lending Practices", "impact": 25 }
  ]
}
```

(Your program will receive each such document squashed onto a single line of JSON rather than nicely formatted as above).

This example represents a single document that mentioned "EVIL Corp" and "Tyrell Corporation" in relation to "Unfair Lending Practices" but was much more impactful for "EVIL Corp" (perhaps "Tyrell Corporation" was only mentioned in passing).

The company and topic identifiers are strings. Impact is an integer ranging from 0 to 1000 inclusive.

## Output data schema (alert)

Here's an example alert:

```javascript
{
  "company": "EVIL Corp",
  "topic": "Unfair Lending Practices",
  "impactTotal": 10530,
  "periodStartMillis": 1517590000000,
  "periodEndMillis": 1517590030000
}
```

(Your program should emit such alerts on a single line per alert document).

This example indicates that in the 30s period identified by the pair of millisecond timestamps, documents totalling an impact of 10530 were observed for the company "EVIL Corp" and the topic "Unfair Lending Practices".

## How to get started

If the problem seems daunting, you might consider following a sequence of steps something like the following:

- be able to read input documents into appropriate data structures
- start with a simple document counter, emit a naive alert when the limit is reached and reset the counter to 0
- split the counter by company and topic
- increment by the impact value instead of by 1
- ensure the running totals are based only on the most recent n documents rather than all documents seen so far
- ensure the running totals are based on documents arriving in the configured time window rather than the most recent n

You may find it easier to approach the steps above in a different order.

## Extensions

If you manage to complete all of the above with time to spare, you might explore one or more of the following possible extensions (or invent your own):

- choose an appropriate method to avoid spamming consumers with alerts if a flood of messages arrives
- configure the program with companies to pay attention to, ignore the others
- allow the program to also monitor total impact score for a company across all topics
- write a periodic status message showing the company/topic pairs closest to threshold
- periodically write a chart image to a file showing the progression of impact over time for the top N company/topic pairs (or any other chart that shows something interesting about the state of the system)
- allow documents to indicate their own timestamp instead of taking the current time when the document is observed
  - and therefore make some allowance for late-arriving documents in the alerting
- examine time and space behaviour of your solution and optimise it further for one or the other
