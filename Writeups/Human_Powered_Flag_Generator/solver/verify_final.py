
import math
import gmpy2

flag = ''
def lucas(n):
    return 5**n + 1

#def my_factorial(n):
#    return math.factorial(n)

# my_factorial(23123) = factorial(123) + 23 * factorial(1000)

def speedup(x):
    while(x % 10 == 0):
        x //= 10
    return x

thousand_factorial = math.factorial(1000)

def my_factorial(n):
    num = math.factorial(n % 1000) + (n // 1000) * thousand_factorial
    return num

def mod1000_factorial(n):
    num = 1
    while n >= 1:
        num *= n % 1000
        n = n - 1
    return num

prod = 1
max_prev = 1
for level in range(1,13):
    print(f"Level: {level}")
    max = 2**level + 1
    for curr in range(max_prev, max):
        l_numb = lucas(curr)
        #print(f"Lucas: {l_numb}")
        prod *= my_factorial(l_numb-1)
        
        # optimise prod
        prod = speedup(prod) % 1000000

    max_prev = max
    #prod = speedup(prod) % 1000000
    # instead of calling speedup, trim the zeros using string
    out = (str(prod).rstrip('0'))[-3:]
    flag += out
    print("CrossCTF{" + flag +"}")
    prod = int(out)
    #yield last-3-digit

