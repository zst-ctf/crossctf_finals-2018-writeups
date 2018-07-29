# GoCoin! Plus Plus
Web - 312 points

## Challenge 
	
	I thought blockchain was cool, so I made my own coin.

	GoCoin! Plus Plus is the forked and improved version of GoCoin! Plus.

	Update: I've improved it! More secures and with real cryptos, it's a true cryptocoin now! Update: Stupid me wrote a broken challenge, now its really fixed!

	http://ctf.pwn.sg:1389

	Creator - quanyang (@quanyang)

## Solution

From webpage:

	GoCash! Plus is the most secure CryptoCurrency out there, we use JWT to sign and verify all our cryptowallets! We're so confident we're giving out our source code! and our Public Key!

	-----BEGIN PUBLIC KEY-----
	MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyg+EcZOSLYXqdA0Gdx6hE0PFY
	GshbSxHwBbZ0Ivw41OCD447WtiTmBKWhYkyZIW1uF2G0YpifP9CGZgIHhW6jPUwB
	u1ewHy0ch+GQeMleaVGqEOsAd65DAXi2TSCZLl66zWy0enQ/SFkm9OTTGYR0e0hM
	DFqKsA30lPd0pHbSNwIDAQAB
	-----END PUBLIC KEY-----

This means that the exploit must be something to do with JWT and the Public Key.

If we check cookies, we find the JWT string

	>> document.cookie
	"wallet_2=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJiYW5rIjowLCJyYW5kIjoxNjczNDgzNTk2NTIxMDg5OTYwNCwid2FsbGV0IjoxMH0.AQutoH5NuSQOXPopUPu1YUP2jiGOBgAYH7YRMxyeIBTq9S8mAY9TWO36E7pso9JrXbj9hYxHJu57EAi0au-1n2p0SrNJ8CtHLtgl6dVAruB3YIri9ADqOSxbEBmjKlYhdrh3oAAyaUXrMsF5W5dIVSHbyJyhcwcuQiWciNy4RpY"

We can verify the public key on https://jwt.io/

### Exploit JWT

Reference:
- https://auth0.com/blog/brute-forcing-hs256-is-possible-the-importance-of-using-strong-keys-to-sign-jwts/
- http://www.blog.hatsec.io/pentesterlab/jwt/

The trick is to change the algorithm. This way, we can change the payload while bypassing the check from asymmetric encryption (private key). 

Changing to None doesn't work though.

	{"alg":"None","typ":"JWT"}
	eyJhbGciOiJOb25lIiwidHlwIjoiSldUIn0=

Choose an symmetric encryption such as HMAC (HS256, HS512). 

Edit the payload on https://jwt.io/ and then change the cookie accordingly...

## Flag

	CrossCTF{SORRY_I_AM_STUP!D!1!!1}