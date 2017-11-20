##################################################################
#
# Reads a list of names (where each name is separated by \n) ...
# ... and adds Account_Holder information to each
# input file must only contain names!!!!
# and sincere apologies for any bugs
#
# Joe .K
#
##################################################################

import random
from hashlib import sha256

def execute():
    print("Enter the name of the input file and output file, comma separated, in order")
    things = input(">> ").split(",")
    if len(things) != 2:
        return 1
    resultStr = ""
    try:
        file = open(things[0],"r")
        for line in file:
            email = createRandEmail(line.split())
            password = sha256("{0}{1}".format(line,email).encode("utf-8")).hexdigest()[:6]
            resultStr += "{0}\t{1}\t{2}\t{3}\n".format(line.replace('\n',''), email, password, random.choice(range(10000,100000)))
        file.close()
    except IOError:
        "{0} not found!".format(things[0])
    
    try:
        file = open(things[1], "w")
        file.write(resultStr)
        file.close()
    except IOError:
        "{0} not found!".format(things[1])
    return 0

def createRandEmail(fl):
    if len(fl) != 2:
        return 1
    s0 = fl[0][:random.randint(1, len(fl[0]))].lower()
    s1 = fl[1][:random.randint(0, len(fl[1]))].lower()
    s2 = str(random.choice(range(1980,1999)))[random.randint(0,1)*2:]
    domain = random.choice(["com", "net", "edu", "org", "gov", "info"])
    s = [s0, s1, s2]
    random.shuffle(s)
    return "{0}{1}{2}@example.{3}".format(s[0], s[1], s[2], domain)

["Success","Error. Make sure there are exactly two file names",][execute()]
