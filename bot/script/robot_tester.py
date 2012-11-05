import os
import shutil
import subprocess
import sys
from time import time
import threading

class CompilationThread(threading.Thread):

	def __init__(self, particle_id, robots_src_path):
		threading.Thread.__init__(self)

		self.particle_id = particle_id
		self.JAVAC_PATH = "C:/Program Files/Java/jdk1.7.0_03/bin/javac"
		self.robots_src_path = robots_src_path
		self.ROBOCODE_PATH = "C:/robocode"

	def compileRobot(self):
		target_dir_path = self.robots_src_path + "/particle" + self.particle_id
		target_scr_path = target_dir_path + "/GSwarmRobot.java"
		if os.path.isfile(target_scr_path):
			subprocess.call("%s -d %s -cp %s/libs/*; %s" % (self.JAVAC_PATH, target_dir_path, self.ROBOCODE_PATH, target_scr_path))
		else:
			print("Missing source dir for robot #" + self.particle_id)

	def run(self):
		self.compileRobot()

class BattleThread(threading.Thread):

	def __init__(self, particle_id, robots_src_path):
		threading.Thread.__init__(self)

		self.particle_id = particle_id
		self.robots_src_path = robots_src_path
		self.ROBOCODE_PATH = "C:/robocode"
		self.BATTLE_CONFIG_PATH = self.ROBOCODE_PATH + "/battles/gswarm.battle"

	#def createBattleFile(self):
		# TODO
	#	pass

	#def setRobotPackage(self):
		# TODO
	#	pass

	def copyRobot(self):
		src_path = self.robots_src_path + "/particle" + self.particle_id + "/GSwarmRobot.class"
		dest_path = self.ROBOCODE_PATH + "/robots"
		shutil.copy(src_path, dest_path)
		
	def startBattle(self):
		fh = open("NUL","w")
		print("battle start", self.particle_id)
		result_path = self.robots_src_path + "/particle" + self.particle_id + "/result.rsl"
		subprocess.call("java -DPARALLEL=true -Xmx512M -Dsun.io.useCanonCaches=false -cp %s/libs/robocode.jar robocode.Robocode -cwd %s -battle %s -nodisplay -results %s" % (self.ROBOCODE_PATH, self.ROBOCODE_PATH, self.BATTLE_CONFIG_PATH, result_path), stdout = fh, stderr = fh)
		print("battle end", self.particle_id)
		fh.close()

	def run(self):
		try:
			self.copyRobot()
			self.startBattle()
		except IOError:
			print("Unable to compile robot #" + self.particle_id)

#### SCRIPT START ####

"""
if len(sys.argv) != 2:
	print("Incorrect number of arguments!")
	print("Usage:")
	print("robot_tester.py %1 %2 %3")
	print("%1 - number of particles")
	print("%2 - iteration num")
	print("%3 - robots directory name"
	sys.exit()
"""

def transToParticleId(id):
	return str(10000 + i)

sys.argv = [100, 0, "test/test2012-11-05-18-35-12"]

PARTICLES = sys.argv[0]
ROBOTS_SRC_PATH = os.path.dirname(__file__) + "/../" + sys.argv[2] + "/iter" + str(10000 + sys.argv[1])

# compile robots
threads = []
start_time = time()
for i in range(0, 50):
	t = CompilationThread(transToParticleId(i), ROBOTS_SRC_PATH)
	threads.append(t)

[t.start() for t in threads]
[t.join() for t in threads]

print("Compilation time: " + str(time() - start_time))
print("----------------------------")

# test robots
threads = []
start_time = time()
for i in range(0, 50):
	t = BattleThread(transToParticleId(i), ROBOTS_SRC_PATH)
	threads.append(t)

[t.start() for t in threads]
[t.join() for t in threads]

print("Test time: " + str(time() - start_time))
