import os
import shutil

SRC_PATH = "C:/Users/h/Workspace/_github/g-swarm/bot/conf/battle/gswarm.battle"
TARGET_DIR = "C:/Robocode/battles/gswarm/"

def createBattleFile(id):
	battle_name = "gswarm" + id + ".battle"
	robot_name = "GSwarmRobot" + id
	target_path = TARGET_DIR + battle_name
	shutil.copyfile(SRC_PATH, target_path + "_")

	# replace #ROBOT# string in target battle config
	with open(target_path, "wt") as out:
		for line in open(target_path + "_"):
			out.write(line.replace('#ROBOT#', robot_name))
			
	os.remove(target_path + "_")

for i in range(0, 99):
	createBattleFile(str(10000 + i))
