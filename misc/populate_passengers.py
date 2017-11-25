##################################################################
#
# Populates passenger relation
#
# Joe .K
#
##################################################################


import random

def populate(reservation_rate=0.4,acc_hldr_pbty=0.6,wifi_pbty=0.5):
    total_seats = 1344
    acc_hldr_pbty = (1.0 - acc_hldr_pbty) * 10
    wifi_pbty = (1.0 - wifi_pbty) * 10
    reserved_seats = int(total_seats * reservation_rate)
    output_str = ""
    count = 0
    acc_holders = list(range(1000,1200))
    while count < reserved_seats:
        rand = random.randint(0,100) # if mod 0
        id = None
        if acc_holders:
            if not rand % acc_hldr_pbty:
                id = random.choice(acc_holders)
                acc_holders.remove(id)
            else:
                id = 0
        else:
            id = 0
        output_str += "{0}\t{1}\n".format(id, int(not rand % wifi_pbty))
        count += 1
    file = open("passengers.txt", "w")
    file.write(output_str)
    file.close()


populate()