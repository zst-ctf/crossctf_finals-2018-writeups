# Choose Your Own Adventure 2
Misc - 476 points

## Challenge 
	
>As you **float int**o interstellar space, you **cast** your gaze onto the **pointers** of your instrument...

> Update: Pls do The Evilness first. That challenge is really much easier than y'alls think.

> Final Hint: IEEE-754

> Non-standard flag format: [0-9]+

> len(flag) < 16.

> nc ctf.pwn.sg 11006

> Creator - prokarius (@prokarius)

## Solution

From the hint, we merely need to cast the integer into a float

	1068077148 -> 1.3247179985e+00
	1805536512 -> 3.8274780344e+26
	1005526689 -> 7.2973524220e-03
	1727990831 -> 6.0221410038e+23
	1301214146 -> 2.9979244800e+08
	428181300 -> 1.3806485791e-23
	1107313295 -> 3.2064998627e+01
	-2147483648 -> -0.0000000000e+00
	993912976 -> 2.8977729380e-03
	778615823 -> 5.2917722287e-11
	1090848777 -> 8.3144617081e+00

From the previous theme of chemistry in CYOA, the numbers had a meaning to it.

Upon seeing `2.9979244800e+08`, it reminds me of the speed of light. With this, we can identify the theme as physics.

However, it was difficult to form the sentence since each of the number had variations in the names.

Eg. for `-2147483648`, it could be absolute zero or empty or nought and so on.

## Flag

	??