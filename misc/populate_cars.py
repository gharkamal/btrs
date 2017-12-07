##################################################################
#
# Populates car relation
#
# Joe .K
#
##################################################################


import random

def populate(reserved_num=537):
    all_available_seats = []
    for car in range(0,4): # car
        for seat_row in range(1,15):
            for seat_position in ["A","B","C","D"]:
                seat = str(seat_row) + seat_position
                for train in range(100,106):
                    all_available_seats.append("{0}\t{1}\t{2}".format(car,seat,train))
    reserved_seats = random.sample(all_available_seats, reserved_num)
    count = 500
    new_reserved_seats = []
    for reserved_seat in reserved_seats:
        reserved_seat += "\t{}".format(count)
        new_reserved_seats.append(reserved_seat)
        count += 1
    file = open("cars.txt", "w")
    file.write("\n".join(new_reserved_seats))
    file.close()


populate()