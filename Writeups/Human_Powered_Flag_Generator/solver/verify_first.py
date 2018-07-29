
import math
import gmpy2

def lucas(n):
    return 5**n + 1

def speedup(prod):
    while(prod % 10 == 0):
        prod //= 10
    return prod

def my_factorial(n):
    return math.factorial(n)


for level in range(1,13):
    print(f">> Level: {level}")
    max = 2**level + 1
    prod = 1
    for curr in range(1, max):
        lucas_numb = lucas(curr)
        print(f"Lucas: {lucas_numb}")
        prod *= my_factorial(lucas_numb-1)

    prod = speedup(prod)
    #print(prod)

    #yield last-3-digit
    print(str(prod)[-3:])

#186134520519971831808