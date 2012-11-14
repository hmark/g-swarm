import os
import shutil

ENEMIES_PATH = "C:/Users/h/Workspace/_github/g-swarm/bot/conf/robots.lst"
TEMPLATE_PATH = "C:/Users/h/Workspace/_github/g-swarm/bot/conf/battle/gswarm.battle"
TARGET_DIR = "C:/Robocode/battles/gswarm/"

def createBattleFile(enemy, level, id):
	battle_name = "gswarm" + id + "_" + level + ".battle"
	robot_name = "gswarm.GSwarmRobot" + id
	target_path = TARGET_DIR + battle_name
	shutil.copyfile(TEMPLATE_PATH, target_path + "_")

	# replace #ROBOT# string in target battle config
	with open(target_path, "wt") as out:
		for line in open(target_path + "_"):
			out.write(line.replace('#ENEMY#', enemy).replace('#ROBOT#', robot_name))
			
	os.remove(target_path + "_")

with open(ENEMIES_PATH) as f:
    enemies = f.readlines()

enemies = [enemy.strip() for enemy in enemies]
level = 0

for enemy in enemies:
	level += 1
	for i in range(0, 99):
		createBattleFile(enemy, str(level), str(10000 + i))
