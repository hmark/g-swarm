import Charts
from os import listdir
from os.path import isfile, join

chart = Charts.PyChart(0, 'Vývoj fitnes', 'Iterácia', 'Fitnes')
gbest = []
lbest = []

for f in listdir("../test/test2012-11-14-19-19-24"):
	try:
		f = open("../test/test2012-11-14-19-19-24/" + f + "/logs/data.log")
		lines = f.readlines()
		gbest.append(int(float(lines[1].split()[-1])))
		lbest.append(int(float(lines[3].split()[-1])))

		if len(gbest) == 150:
			break

	except IOError:
		break

# drawing chart plots
#chart.addYLimit(0, 15)
print(len(gbest), len(lbest))
chart.addYLimit(0, 40000)
chart.addPlotXY(0, range(1, 151), gbest, "gBest")
chart.addPlotXY(0, range(1, 151), lbest, "priemerná hodnota lBest")

# add chart legend
chart.addLegend(0)

# show charts window
chart.show()