# Human Powered Flag Generator
Mobile - 444 points

## Challenge 

> Keep cranking until the whole flag appears!

> Creator - prokarius (@prokarius)

> [app-debug.apk](app-debug.apk)

## Solution

Really interesting challenge, although I don't feel it can be classified as mobile.

### Understanding the app

![screenshot.jpg](screenshot.jpg)

We have an app thathas a "Crank!" button. Upon pressing it repeatedly, portions of the flag are generated.

There's also another button to "Speed Up" but it appears to do nothing at first glance.

### Get the source code

First, let's [decompile the app](https://blog.bramp.net/post/2015/08/01/decompile-and-recompile-android-apk/) and get the code to understand what is happening.

	$ apktool d -r -s app-debug.apk
	$ dex2jar app-debug/classes.dex
	$ jd-gui classes-dex2jar.jar

Inside MainActivity.class:

	public class MainActivity extends AppCompatActivity
	{
	  private TextView flagDisplay;
	  private TextView percent;
	  private Status status;
	  
	  private void crank()
	  {
	    this.status.crank(3.8D);
	    Resources localResources = getResources();
	    this.flagDisplay.setText(localResources.getString(2131427359, new Object[] { this.status.flag() }));
	    this.percent.setText(localResources.getString(2131427360, new Object[] { Integer.valueOf(this.status.level()), this.status.percent() }));
	  }
	  
	  protected void onCreate(Bundle paramBundle)
	  {
	    super.onCreate(paramBundle);
	    setContentView(2131296283);
	    this.status = new Status();
	    this.flagDisplay = ((TextView)findViewById(2131165244));
	    this.percent = ((TextView)findViewById(2131165274));
	    findViewById(2131165301).setOnClickListener(new MainActivity..Lambda.0(this));
	    findViewById(2131165229).setOnClickListener(new MainActivity..Lambda.1(this));
	  }
	}

We see that `this.status.crank(3.8D);` is being called repeatedly. Perhaps I can just run the code to crank on a PC. 

I created a project in IntelliJ and ran it. (See folder `crossctf_mobile_1`)

However, it seems to get stuck around level 3 after about 5 min. It appears that each crank gets exponentially slower.

---

Inside `speedUp()` (Speed Up Button):

- The integer prod is divided down by 10 until the digit in the ones' place is not zero.

- Essentially, it ***removes all trailing zeros, and speeds up computation***.

---

Inside `extract()`:

- ***The last 3 digits of the `prod` is taken***.


---

#### Understanding each crank

Looking at `Status.class`, we realise that it uses BigInteger and lot of complicated computations.

My first instinct was that I needed to ***optimise the algorithm***.


##### Optimise `fastExpo()`

The first function that caught my eye was `fastExpo()`.

	private static BigInteger fastExpo(BigInteger paramBigInteger1, BigInteger paramBigInteger2) {
	    Object localObject2;
	    for (Object localObject1 = BigInteger.ONE; paramBigInteger2.compareTo(BigInteger.ONE) != 0; localObject1 = localObject2) {
	      localObject2 = localObject1;
	      if (paramBigInteger2.and(BigInteger.ONE).equals(BigInteger.ONE)) {
	        localObject2 = ((BigInteger)localObject1).multiply(paramBigInteger1);
	      }
	      paramBigInteger1 = paramBigInteger1.multiply(paramBigInteger1);
	      paramBigInteger2 = paramBigInteger2.shiftRight(1);
	    }
	    return ((BigInteger)localObject1).multiply(paramBigInteger1);
	}

Refactorise and remove redundant code to make it easier to analyse

    private static BigInteger fastExpo(BigInteger X, BigInteger Y) {
        BigInteger _B = BigInteger.ONE;

        // for the number of bits in Y
        while (Y.compareTo(BigInteger.ONE) != 0) {
            //BigInteger _A = _B;
            if (Y.and(BigInteger.ONE).equals(BigInteger.ONE)) {
                _B = (_B).multiply(X);
            }
            //_B = _A;
            X = X.multiply(X); // x^2
            Y = Y.shiftRight(1);
        }
        return _B.multiply(X);
    }

Here is my understanding:

For each bit that is `1` in the parameter `Y`:
- Square the input `X`
- Multiply `_B` by the current value of `X` only if the current bit of `Y` is `1`

Simplify this into a maths formula

	Let #A, #B, #C... be the bit 0, bit 1, bit 2
	respectively in the integer Y

	Let x be the original value of X

	return_value
	= x * x^(2 * #A) * x^(4 * #B) * x^(8 * #C) ...
	= x^(1 + 2#A + 4#B + 8#C ...)
	= x^(#A<<0 + #B<<1 + #C<<2 + #D<<3...)

	Notice that:
		#A<<0 = Y & 1
		#B<<1 = Y & 2
		#C<<2 = Y & 4
		#D<<3 = Y & 8
	and if we OR then together, we simply get Y.

	Hence, it is simplified to 
	return_value = X^Y

*Apparently, `fastExpo` is half correct. It is `Expo` as in exponential, but it is not fast ^.^*

---

Now, run the updated program.

It finally gets to Level 4, but still stalls after a while.

	Level: 1 / Percent: 41.67 / CrossCTF{}
	Level: 2 / Percent: 71.23 / CrossCTF{808}
	Level: 3 / Percent: 86.44 / CrossCTF{808664}
	Level: 3 / Percent: 86.45 / CrossCTF{808664}
	Level: 4 / Percent: 51.75 / CrossCTF{808664096}
	Level: 4 / Percent: 51.75 / CrossCTF{808664096}
	Level: 4 / Percent: 51.75 / CrossCTF{808664096}
	Level: 4 / Percent: 51.75 / CrossCTF{808664096}
	Level: 4 / Percent: 51.75 / CrossCTF{808664096}
	Level: 4 / Percent: 51.75 / CrossCTF{808664096}
	Level: 4 / Percent: 51.75 / CrossCTF{808664096}

##### Optimise crank()

In the program, I added debug lines to understand what was going on... (See folder `crossctf_mobile_1`).

This is the output for each Level 1 crank:

	Level=1 | f=1 | fmax=6 | prod=1
	Level=1 | f=2 | fmax=6 | prod=1
	Level=1 | f=3 | fmax=6 | prod=2
	Level=1 | f=4 | fmax=6 | prod=6
	Level: 1 / Percent: 41.67 / CrossCTF{}
	Level=1 | f=5 | fmax=6 | prod=24
	Level=1 | f=6 | fmax=6 | prod=120
	Level=1 | f=1 | fmax=26 | prod=120
	Level=1 | f=2 | fmax=26 | prod=120
	Level: 1 / Percent: 55.77 / CrossCTF{}
	Level=1 | f=3 | fmax=26 | prod=240
	Level=1 | f=4 | fmax=26 | prod=720
	Level=1 | f=5 | fmax=26 | prod=2880
	Level=1 | f=6 | fmax=26 | prod=14400
	Level: 1 / Percent: 63.46 / CrossCTF{}
	Level=1 | f=7 | fmax=26 | prod=86400
	Level=1 | f=8 | fmax=26 | prod=604800
	Level=1 | f=9 | fmax=26 | prod=4838400
	Level=1 | f=10 | fmax=26 | prod=43545600
	Level: 1 / Percent: 71.15 / CrossCTF{}
	Level=1 | f=11 | fmax=26 | prod=435456000
	Level=1 | f=12 | fmax=26 | prod=4790016000
	Level=1 | f=13 | fmax=26 | prod=57480192000
	Level=1 | f=14 | fmax=26 | prod=747242496000
	Level: 1 / Percent: 78.85 / CrossCTF{}
	Level=1 | f=15 | fmax=26 | prod=10461394944000
	Level=1 | f=16 | fmax=26 | prod=156920924160000
	Level=1 | f=17 | fmax=26 | prod=2510734786560000
	Level=1 | f=18 | fmax=26 | prod=42682491371520000
	Level: 1 / Percent: 86.54 / CrossCTF{}
	Level=1 | f=19 | fmax=26 | prod=768284844687360000
	Level=1 | f=20 | fmax=26 | prod=14597412049059840000
	Level=1 | f=21 | fmax=26 | prod=291948240981196800000
	Level=1 | f=22 | fmax=26 | prod=6130913060605132800000
	Level: 1 / Percent: 94.23 / CrossCTF{}
	Level=1 | f=23 | fmax=26 | prod=134880087333312921600000
	Level=1 | f=24 | fmax=26 | prod=3102242008666197196800000
	Level=1 | f=25 | fmax=26 | prod=74453808207988732723200000
	Level=1 | f=26 | fmax=26 | prod=1861345205199718318080000000
	Level: 1 / Percent: 100.00 / CrossCTF{}
	Level=1 | f=1 | fmax=126 | prod=1861345205199718318080000000

In essence, from `prod` and `f`, factorial is being done (multiply of 1..fmax).

This factorial is repeated with a few iterations with different values of fmax.

---

In `upF()`:
	
	fmax = fastExpo(5, curr) + 1
	fmax = 5^curr + 1

	where curr goes from 0 to max

Upon googling, I realised that 5^n+1 is a [type of Lucas Sequence](http://oeis.org/wiki/Lucas_sequences)

So the iterations of fmax are the numbers in the lucas sequence.

Let's call it `lucas()` from now on

	def lucas(n):
		return 5**n + 1

---

In `upLevel()`:

	max = BigInteger.ONE.shiftLeft(this.level).add(BigInteger.ONE)
	max = 1<<level + 1
	max = 2^level + 1


Hence, here's the breakdown of crank in psuedocode

	for level of 1..13
		prod = 1
		for curr of 1..max, where max = 2^level+1
			fmax = lucas(curr)
			prod *= factorial(fmax) // prod *= 1*2*3...*fmax
		finally, get last 3 digits of prod

I converted all these to a Python program and the first 4 levels produce an accurate number.

The program is still stuck at Level 4. The `prod` is simply getting too big.

### Optimise the main bottleneck

The factorial is the main cause of it and we need to calculate it efficiently.

Notice that the extracted digits are always the last 3 digits of a number (the `extract()` method takes mod of 1000)

Hence, the upper digits are not needed and it's the main trick to optimising this.

##### Calculate only last few digits of factorial?

Reference:
	https://www.quora.com/How-do-I-find-the-last-n-digits-of-a-factorial-without-calculating-the-whole-factorial

	For instance, take 8!

	8*7 = 56
	56*6 = 336 = 36
	36*5=180 = 80
	80*4 = 320 = 20
	20*3 = 60 
	60*2 = 120 = 20

As seen in the reference, if only the last 2 digits are needed, keep the last 2 and discard the rest for subsequent calculations.

In our case, we need 3 digits or mod 1000. So I made a function to multiply the mod 1000 of the digits together.

	def mod1000_factorial(n):
	    num = 1
	    while n >= 1:
	        num *= n % 1000
	        n = n - 1
	    return num

Despite this, it was still too slow because there are many iterations of the factorial.

##### Cache repeated factorial calculations.

I thought of doing some caching to speed up the calculations.

Take this example.
	
	mod1000_factorial(2097) = 1%1000 * 2%1000 * ... * 999%1000 *
	                          1000%1000 * 1001%1000 * ... * 1999%1000 *
	                          2001%1000 * 2000%1000 * ... * 97%1000 *

	Simplify:

	mod1000_factorial(2097) = 1 * 2 * ... * 999 *
	                          1 * 2 * ... * 999 *
	                          1 * 2 * ... * 97

Thus we can see that for the example of 2097,

If we do the mod 1000 factorial, 1000! is multiplied twice (2 thousands).

And the remainder below 1000 is 97, thus 97!

---

I made my_factorial to used a pre-calculated thousand_factorial and multiplied with n//1000

	def my_factorial(n):
	    num = math.factorial(n % 1000) + (n // 1000) * thousand_factorial
	    return num

---

Lastly, with `prod` still becoming too big, I added a modulus of (1000\*1000) to reduce the size.

(Anything smaller will compromise precision for some reason)

	prod = speedup(prod) % 1000000


At last, the algorithm is fast enough.

	Level: 1
	CrossCTF{808}
	Level: 2
	CrossCTF{808664}
	Level: 3
	CrossCTF{808664096}
	Level: 4
	CrossCTF{808664096416}
	Level: 5
	CrossCTF{808664096416256}
	Level: 6
	CrossCTF{808664096416256736}
	Level: 7
	CrossCTF{808664096416256736896}
	Level: 8
	CrossCTF{808664096416256736896016}
	Level: 9
	CrossCTF{808664096416256736896016456}
	Level: 10
	CrossCTF{808664096416256736896016456136}
	Level: 11
	CrossCTF{808664096416256736896016456136696}
	Level: 12
	CrossCTF{808664096416256736896016456136696616}

	real	0m4.774s
	user	0m4.446s
	sys	0m0.043s


4 seconds! Woot!

## Flag

	CrossCTF{808664096416256736896016456136696616}
