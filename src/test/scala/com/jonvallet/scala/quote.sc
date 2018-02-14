import com.jonvallet.scala.quote.{Loan, Quote}

val loans = List(
  Loan(   "Bob",   0.075, 640),
  Loan(  "Jane",   0.069, 480),
  Loan(  "Fred",   0.071, 520),
  Loan(  "Mary",   0.104, 170),
  Loan(  "John",   0.081, 320),
  Loan(  "Dave",   0.074, 140),
  Loan("Angela",   0.071,  60),
)

Quote.rate(loans, 2000)
