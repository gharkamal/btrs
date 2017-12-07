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
            email_age = createRandEmail(line.split())
            password = sha256("{0}{1}".format(line,email_age["email"]).encode("utf-8")).hexdigest()[:6]
            fullname = line.replace('\n','').split()
            resultStr += "{0}\t{1}\t{2}\t{3}\t{4}\t{5}\n".format(
                fullname[0],
                fullname[1],
                email_age["email"],
                password,
                random.choice(range(10000,100000)),
                email_age["age"]
            )
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
    x = random.choice(range(1970,2007))
    age = 2018 - x
    s2 = str(x)[random.randint(0,1)*2:]
    domain = random.choice(["com", "net", "edu", "org", "gov", "info"])
    s = [s0, s1, s2]
    random.shuffle(s)
    return {
        "email": "{0}{1}{2}@example.{3}".format(s[0], s[1], s[2], domain),
        "age": age
    }

["Success","Error. Make sure there are exactly two file names",][execute()]
