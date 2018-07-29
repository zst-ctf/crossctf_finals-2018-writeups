# RetroWeb
CATEGORY - POINTS points

## Challenge 
Not so easy SQL injection at all.

http://ctf.pwn.sg:8180

Creator - quanyang (@quanyang)

## Solution


We cant use some words and operators

    if (preg_match('/\s/', $username) or preg_match('/[\/\\\\]/', $username) or preg_match('/(and|or|null|not|union|select|from|where|group|order|having|limit|into|file|case|like)/i', $username) or preg_match('/(--|\/\*|=|>|<)/', $username)) 
        exit('die hax0r!');
    $username = mysql_escape_string($username);
    $sql = "SELECT username FROM users WHERE username like '$username';";

https://www.periscopedata.com/blog/sql-symbol-cheatsheet

http://vikasprogrammer.tumblr.com/post/49251154907/blind-sql-injection-without-white-spaces
https://stackoverflow.com/questions/13026469/mysql-escape-string-vulnerabilities

	SELECT username FROM users WHERE username like '$username';



	SELECT username FROM users WHERE username like '                  ';
	SELECT username FROM users WHERE username like 'admin'&&Contains(flag,'*cross*');

	SELECT username FROM users WHERE username like 'admin'&&Contains(flag,'*cross*')&&;

	contains

## Flag

	??