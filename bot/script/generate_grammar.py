"""Simple script used for generating parametric grammars.
"""

i = 0
n = -1.0
s = ""
while i <= 200:
    if i < 200:
        s += "%.2f | " % n
    else:
        s += "%.2f" % n

    i += 1
    n += 0.01

print(s)
