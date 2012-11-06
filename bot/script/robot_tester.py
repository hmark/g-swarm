import os
import shutil
import subprocess
import sys
from time import time
import threading

class Logger():

	def __init__(self, robots_src_path, iter_str):
		if not os.path.exists(robots_src_path + "/logs"):
			os.mkdir(robots_src_path + "/logs")

		self.f = open(robots_src_path + "/logs/" + iter_str + ".log", "w")

	def write(self, text):
		self.f.write(text + "\n")

class CompilationThread(threading.Thread):

	def __init__(self, particle_id, robots_src_path):
		threading.Thread.__init__(self)

		self.particle_id = particle_id
		self.JAVAC_PATH = "C:/Program Files/Java/jdk1.7.0_03/bin/javac"
		self.robots_src_path = robots_src_path
		self.ROBOCODE_PATH = "C:/robocode"

	def compileRobot(self):
		target_dir_path = self.robots_src_path + "/particle" + self.particle_id
		target_scr_path = target_dir_path + "/GSwarmRobot" + self.particle_id + ".java"
		
		if os.path.isfile(target_scr_path):
			subprocess.call("%s -d %s -cp %s/libs/*; %s" % (self.JAVAC_PATH, target_dir_path, self.ROBOCODE_PATH, target_scr_path))
		else:
			logger.write("Missing source dir for robot #" + self.particle_id)

	def run(self):
		self.compileRobot()

class BattleThread(threading.Thread):

	def __init__(self, particle_id, robots_src_path):
		threading.Thread.__init__(self)

		self.particle_id = particle_id
		self.robots_src_path = robots_src_path
		self.ROBOCODE_PATH = "C:/robocode"
		self.BATTLE_CONFIG_PATH = self.ROBOCODE_PATH + "/battles/gswarm.battle"

	def copyRobot(self):
		src_path = self.robots_src_path + "/particle" + self.particle_id + "/gswarm/GSwarmRobot" + self.particle_id + ".class"
		dest_path = self.ROBOCODE_PATH + "/robots/gswarm"
		shutil.copy(src_path, dest_path)
		
	def startBattle(self):
		fh = open("NUL","w")
		logger.write("battle start for particle " + self.particle_id)
		result_path = self.robots_src_path + "/particle" + self.particle_id + "/result.rsl"
		subprocess.call("java -DPARALLEL=true -Xmx512M -Dsun.io.useCanonCaches=false -cp %s/libs/robocode.jar robocode.Robocode -cwd %s -battle %s -nodisplay -results %s" % (self.ROBOCODE_PATH, self.ROBOCODE_PATH, self.BATTLE_CONFIG_PATH, result_path), stdout = fh, stderr = fh)
		logger.write("battle end for particle" + self.particle_id)
		fh.close()

	def run(self):
		try:
			self.copyRobot()
			self.startBattle()
		except IOError as e:
			logger.write("BattleThread I/O error({0}): {1}".format(e.errno, e.strerror))

#### SCRIPT START ####

if len(sys.argv) != 4:
	print("Incorrect number of arguments!")
	print("Usage:")
	print("robot_tester.py <1> <2> <3>")
	print("<1> - number of particles")
	print("<2> - iteration num")
	print("<3> - robots directory name")
	sys.exit()

def transToParticleId(id):
	return str(10000 + i)

PARTICLES = int(sys.argv[1])
ITER_STR = str(10000 + int(sys.argv[2]))
ROBOTS_SRC_PATH = os.path.dirname(__file__) + "/../" + sys.argv[3] + "iter" + ITER_STR

logger = Logger(ROBOTS_SRC_PATH, ITER_STR)

# compile robots
threads = []
start_time = time()
for i in range(0, PARTICLES):
	t = CompilationThread(transToParticleId(i), ROBOTS_SRC_PATH)
	threads.append(t)

[t.start() for t in threads]
[t.join() for t in threads]

logger.write("Compilation time: " + str(time() - start_time))

# test robots
threads = []
start_time = time()
for i in range(0, PARTICLES):
	t = BattleThread(transToParticleId(i), ROBOTS_SRC_PATH)
	threads.append(t)

[t.start() for t in threads]
[t.join() for t in threads]

logger.write("Test time: " + str(time() - start_time))
