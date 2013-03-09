import Charts
from os import listdir

chart = Charts.PyChart(0, 'Vývoj fitnes', 'Iterácia', 'Fitnes')
gbest = []
lbest_ahead = []
lbest_gun = []
lbest_turn = []
best = 0

for fpath in listdir("../test/test2013-03-07-08-21-32"):
    try:
        fname = "../test/test2013-03-07-08-21-32/" + fpath + "/logs/ahead_data.log"
        f = open(fname, 'r')
        lines = f.readlines()

        best = max(best, int(float(lines[1].split()[-1])))
        lbest_ahead.append(int(float(lines[3].split()[-1])))

        fname = "../test/test2013-03-07-08-21-32/" + fpath + "/logs/gun_data.log"
        f = open(fname, 'r')
        lines = f.readlines()

        best = max(best, int(float(lines[1].split()[-1])))
        lbest_gun.append(int(float(lines[3].split()[-1])))

        fname = "../test/test2013-03-07-08-21-32/" + fpath + "/logs/turn_data.log"
        f = open(fname, 'r')
        lines = f.readlines()

        best = max(best, int(float(lines[1].split()[-1])))
        lbest_turn.append(int(float(lines[3].split()[-1])))

        gbest.append(best)

        if len(gbest) == 100:
            break

    except IOError:
        print(f)
        break

# drawing chart plots
#chart.addYLimit(0, 15)
print(len(gbest), len(lbest_ahead), len(lbest_gun), len(lbest_turn))
chart.addYLimit(0, 150)
chart.addPlotXY(0, range(1, 101), gbest, "gBest")
chart.addPlotXY(0, range(1, 101), lbest_ahead, "lBest modulu posunu")
chart.addPlotXY(0, range(1, 101), lbest_gun, "lBest modulu natocenia hlavne")
chart.addPlotXY(0, range(1, 101), lbest_turn, "lBest modulu natocenia tela")

# add chart legend
chart.addLegend(0)

# show charts window
chart.show()
