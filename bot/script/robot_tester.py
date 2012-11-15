import re
import os
import shutil
import subprocess
import sys
from time import time
import threading
import traceback

class Logger():

	def __init__(self, robots_src_path, iter_str):
		if not os.path.exists(robots_src_path + "/logs"):
			os.mkdir(robots_src_path + "/logs")

		self.f = open(robots_src_path + "/logs/" + iter_str + ".log", "w")

	def write(self, text):
		self.f.write(text + "\n")

class CompilationThread(threading.Thread):

	def __init__(self, semaphore, particle_id, robots_src_path):
		threading.Thread.__init__(self)

		self.semaphore = semaphore
		self.particle_id = particle_id
		self.JAVAC_PATH = "C:/Program Files/Java/jdk1.7.0_03/bin/javac"
		self.robots_src_path = robots_src_path
		self.ROBOCODE_PATH = "C:/robocode"

	def compileRobot(self):
		target_dir_path = self.robots_src_path + "/particle" + self.particle_id
		target_scr_path = target_dir_path + "/GSwarmRobot" + self.particle_id + ".java"
		
		if os.path.isfile(target_scr_path):
			self.semaphore.acquire()
			subprocess.call("%s -d %s -cp %s/libs/*; %s" % (self.JAVAC_PATH, target_dir_path, self.ROBOCODE_PATH, target_scr_path))
			self.semaphore.release()
		else:
			logger.write("Missing source dir for robot #" + self.particle_id)

	def run(self):
		self.compileRobot()

class BattleThread(threading.Thread):

	def __init__(self, semaphore, particle_id, robots_src_path, enemies):
		threading.Thread.__init__(self)

		self.MAX_DIFFICULTY = len(enemies)
		self.difficulty = 1
		self.ROBOCODE_PATH = "C:/robocode"
		self.total = 0
		
		self.semaphore = semaphore
		self.particle_id = particle_id
		self.robots_src_path = robots_src_path
		self.enemies = enemies
		
		self.updateBattleConfig()

	def updateBattleConfig(self):
		self.BATTLE_CONFIG_PATH = self.ROBOCODE_PATH + "/battles/gswarm/gswarm" + self.particle_id + "_" + str(self.difficulty) + ".battle"

	def copyRobot(self):
		src_path = self.robots_src_path + "/particle" + self.particle_id + "/gswarm/GSwarmRobot" + self.particle_id + ".class"
		dest_path = self.ROBOCODE_PATH + "/robots/gswarm"
		shutil.copy(src_path, dest_path)
		
	def startBattle(self):
		fh = open("NUL","w")
		self.RESULT_PATH = self.robots_src_path + "/particle" + self.particle_id + "/result" + str(self.difficulty) + ".rsl"
		self.BATTLE_LOG_PATH = self.robots_src_path + "/particle" + self.particle_id + "/battle.log"
		start_time = time()
		command = "java -DPARALLEL=true -Xmx512M -Dsun.io.useCanonCaches=false -cp %s/libs/robocode.jar robocode.Robocode -cwd %s -battle %s -nodisplay -results %s" % (self.ROBOCODE_PATH, self.ROBOCODE_PATH, self.BATTLE_CONFIG_PATH, self.RESULT_PATH)
		subprocess.call(command, stdout = fh, stderr = fh)
		logger.write("battle time: " + str(time() - start_time))
		fh.close()

	def getBattleResult(self):
		with open(self.RESULT_PATH) as f:
		    lines = f.readlines()

		# regex = re.compile(r"[0-9]*%") # percentual score
		regex = re.compile(r"\*[\t][0-9]*") # raw score

		# log battle result to battle.log file
		with open(self.BATTLE_LOG_PATH, "a") as f:
			f.write(lines[2])
			f.write(lines[3])
			f.write("\n")

		for line in lines:
			if re.search(r"GSwarmRobot", line):
				result = int(re.findall(regex, line)[0][2:])
				return result

	def updateScore(self):
		difficulty_coefficient = float(self.enemies[self.difficulty - 1][2])
		self.total += int(self.getBattleResult() * difficulty_coefficient)

	def isRobotSuccesful(self):
		score_limit = int(self.enemies[self.difficulty - 1][3])
		return self.total >= score_limit

	def outputTotalScore(self):
		result_path = self.robots_src_path + "/particle" + self.particle_id + "/score.log"
		f = open(result_path, "w")
		f.write(str(self.total) + "\n")
	
	def run(self):
		try:
			self.semaphore.acquire()
			logger.write("battle semaphore acquired " + self.particle_id)

			while True:
				self.copyRobot()
				self.startBattle()
				self.updateScore()

				if self.difficulty < self.MAX_DIFFICULTY and self.isRobotSuccesful():
					self.difficulty += 1
					self.updateBattleConfig()
				else:
					self.outputTotalScore()
					break

		except Exception as e:
			logger.write(str(e))
			logger.write(traceback.print_exc())
		finally:
			logger.write("battle semaphore released" + self.particle_id)
			self.semaphore.release()

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

def getEnemyCoefficients():
	ENEMIES_PATH = "C:/Users/h/Workspace/_github/g-swarm/bot/conf/robots.lst"
	with open(ENEMIES_PATH) as f:
		enemies = f.readlines()

	return [enemy.strip().split(" ") for enemy in enemies] # [difficulty, name, coefficient, limit]

PARTICLES = int(sys.argv[1])
ITER_STR = str(10000 + int(sys.argv[2]))
ROBOTS_SRC_PATH = os.path.dirname(__file__) + "/../" + sys.argv[3] + "iter" + ITER_STR

logger = Logger(ROBOTS_SRC_PATH, ITER_STR)
enemies = getEnemyCoefficients()

COMPILATION_SEMAS = 30
TEST_SEMAS = 30

# compile robots
try:
	sema = threading.Semaphore(COMPILATION_SEMAS)
	threads = []
	start_time = time()
	for i in range(0, PARTICLES):
		t = CompilationThread(sema, transToParticleId(i), ROBOTS_SRC_PATH)
		threads.append(t)

	[t.start() for t in threads]
	[t.join() for t in threads]

except Exception as e:
	logger.write("Compilation Error!")
	logger.write(str(e))
	logger.write(traceback.print_exc())

logger.write("Compilation time: " + str(time() - start_time))

# test robots
try:
	sema = threading.Semaphore(TEST_SEMAS)
	threads = []
	start_time = time()
	for i in range(0, PARTICLES):
		t = BattleThread(sema, transToParticleId(i), ROBOTS_SRC_PATH, enemies)
		threads.append(t)

	[t.start() for t in threads]
	[t.join() for t in threads]

except Exception as e:
	logger.write("Testing Error!")
	logger.write(str(e))
	logger.write(traceback.print_exc())

logger.write("Test time: " + str(time() - start_time))
