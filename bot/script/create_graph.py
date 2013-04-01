import Charts
from os import listdir

chart = Charts.PyChart(0, 'Vývoj fitnes', 'Iterácia', 'Fitnes')
gbest = []
lbest_ahead = []
lbest_gun = []
lbest_turn = []
best = 0

TARGET_DIR = "../test/test2013-03-27-23-52-50"

for fpath in listdir(TARGET_DIR):
    try:
        fname = TARGET_DIR + "/" + fpath + "/logs/gun_data.log"
        f = open(fname, 'r')
        lines = f.readlines()

        best = max(best, float(lines[1].split()[-1]))
        lbest_gun.append(float(lines[3].split()[-1]))

        fname = TARGET_DIR + "/" + fpath + "/logs/turn_data.log"
        f = open(fname, 'r')
        lines = f.readlines()

        best = max(best, float(lines[1].split()[-1]))
        lbest_turn.append(float(lines[3].split()[-1]))

        gbest.append(best)

        if len(gbest) == 100:
            break

    except IOError:
        print(f)
        break

# drawing chart plots
#chart.addYLimit(0, 15)
print(len(gbest), len(lbest_ahead), len(lbest_gun), len(lbest_turn))
chart.addYLimit(0, 3)
chart.addPlotXY(0, range(1, 101), gbest, "gBest")
#chart.addPlotXY(0, range(1, 101), lbest_ahead, "lBest modulu posunu")
chart.addPlotXY(0, range(1, 101), lbest_gun, "lBest modulu natocenia hlavne")
chart.addPlotXY(0, range(1, 101), lbest_turn, "lBest modulu natocenia tela")

# add chart legend
chart.addLegend(0)

# show charts window
chart.show()
