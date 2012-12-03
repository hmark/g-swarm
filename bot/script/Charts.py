import matplotlib.pyplot as plt

__author__ = "Marek Hlavac (hmark.eu)"
__license__ = "GPL"

class PyChart:
	"""Class for creating MatPlotLib charts.
	"""

	def __init__(self, figure, title, xlabel, ylabel):
		"""Constructor.

	    Args:
	        figure: chart number
	        title: chart title
	        xlabel: x-axis label
	        ylabel: y-axis label
	    """
		fig = plt.figure(figure)
		ax = fig.add_subplot(111)
		ax.grid(True)
		plt.title(title)
		plt.xlabel(xlabel)
		plt.ylabel(ylabel)

	def addYLimit(self, lowerlimit, upperlimit):
		"""Setting y-axis limit.

	    Args:
	        lowerlimit: lower limit (usually zero)
	        upperlimit: upper limit
	    """
		plt.ylim((lowerlimit, upperlimit))

	def addPlotY(self, figure, values, label):
		"""Setting y-axis values without x-axis values.

	    Args:
	        figure: chart number
	        values: plot data
	        label: data title
	    """
		plt.figure(figure)
		plt.plot(values, label=label)

	def addPlotXY(self, figure, valuesx, valuesy, label):
		"""Setting y-axis values with x-axis values.

	    Args:
	        figure: chart number
	        valuesx: plot data
	        valuesy: plot data
	        label: data title
	    """
		plt.figure(figure)
		plt.plot(valuesx, valuesy, label=label)
		
	def addLegend(self, figure):
		"""Create legend for chart based on plots.

	    Args:
	        figure: chart number
	    """
		plt.figure(figure)
		plt.legend(bbox_to_anchor=(0.98, 0.98), loc=1, borderaxespad=0.)

	def show(self):
		"""Draw and show all plots in different windows.
	    """
		plt.show()
