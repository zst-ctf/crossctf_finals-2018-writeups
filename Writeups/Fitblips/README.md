# Fitblips
Crypto - 50 points

## Challenge 
	
	How many steps does your Fitblip beep?

	nc ctf.pwn.sg 4003

	Creator - amon (@nn_amon)

## Solution (Unintended solution)

Upon connecting, we see the source code:

> [source.py](source.py)

The initial conclusion is that a timing attack must be done, since it provides the elapsed time of the for-loop.

---

However, notice that `result = len(flag.flag) * 8 * user_times`.

There is no validation for `user_times`.

So we can pass in 0 times and it will print the flag.

	Password: 00
	How many times do you want to test: 0
	Flag is CrossCTF{t1m1ng_att4ck5_r_4_th3_d3vil}
	Request completed in: 0.0000s (0)

## Flag

	CrossCTF{t1m1ng_att4ck5_r_4_th3_d3vil}
