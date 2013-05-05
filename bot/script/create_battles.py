import os
import shutil

ENEMIES_PATH = ""
TEMPLATE_PATH = ""
ROBOCODE_PATH = ""


def loadPaths():
    """Load paths from config file.
    """
    global TEMPLATE_PATH, ENEMIES_PATH, ROBOCODE_PATH

    with open(os.path.dirname(os.path.abspath(__file__)) + "/../conf/path.conf") as f:
        paths = f.readlines()
        paths = [path.strip().replace("\"", "") for path in paths]

    for path in paths:
        p = path.split("=")
        if p[0] == "BATTLE_TEMPLATE":
            TEMPLATE_PATH = p[1]
        elif p[0] == "ROBOCODE":
            ROBOCODE_PATH = p[1]
        elif p[0] == "ENEMY_LIST":
            ENEMIES_PATH = p[1]


def createBattleFile(enemy, level, id):
    """Create battle file for enemy.

    Args:
        enemy: enemy full name (full package name)
        level: enemy position in the list
        id: robot identificator
    """
    battle_name = "gswarm" + id + "_" + level + ".battle"
    robot_name = "gswarm.GSwarmRobot" + id
    target_path = ROBOCODE_PATH + "/battles/gswarm/" + battle_name
    shutil.copyfile(TEMPLATE_PATH, target_path + "_")

    # replace #ROBOT# string in target battle config
    with open(target_path, "wt") as out:
        for line in open(target_path + "_"):
            out.write(line.replace('#ENEMY#', enemy).replace('#ROBOT#', robot_name))

    os.remove(target_path + "_")

loadPaths()

# read enemies from config
with open(ENEMIES_PATH) as f:
    enemies = f.readlines()

enemies = [enemy.split(" ")[1].replace("-", " ") for enemy in enemies]

level = 0

for enemy in enemies:
    level += 1
    for i in range(0, 100):
        createBattleFile(enemy, str(level), str(10000 + i))
