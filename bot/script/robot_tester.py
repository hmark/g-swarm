import re
import os
import shutil
import subprocess
import sys
from time import time, ctime
import threading

JAVAC_PATH = ""
ROBOCODE_PATH = ""
ENEMIES_PATH = ""

SWARM_NAME = ""

COMPILATION_SEMAS = 30
TEST_SEMAS = 30

compiled_robots = []


class Logger():
    """File logger.
    """

    def __init__(self, robots_src_path, iter_str):
        """Prepare log files.

        Args:
            robots_src_path: direct path to actual test
            iter_str: iteration path (e.g.: test/iteration)
        """
        if not os.path.exists(robots_src_path + "/logs"):
            os.mkdir(robots_src_path + "/logs")

        self.f = open(robots_src_path + "/logs/" + iter_str + ".log", "w")

    def write(self, text):
        """Write data to log file.

        Args:
            text: logging text
        """
        print(ctime() + ": " + str(text))
        self.f.write(ctime() + ": " + str(text) + "\n")


class CompilationThread(threading.Thread):
    """Compilation class.
    """

    def __init__(self, semaphore, particle_position, particle_id, robots_src_path):
        """Initalize compilation.

        Args:
            semaphore: semaphore used for thread management
            particle_position: particle position
            particle_id: unique particle identificator
            robots_src_path: path to robot tests
        """
        threading.Thread.__init__(self)

        self.semaphore = semaphore
        self.particle_id = particle_id
        self.particle_position = particle_position
        self.robots_src_path = robots_src_path

    def compileRobot(self):
        """Compile robot program.
        """
        target_dir_path = self.robots_src_path + "/" + SWARM_NAME + "_particle" + self.particle_id
        target_scr_path = target_dir_path + "/GSwarmRobot" + self.particle_id + ".java"

        if os.path.isfile(target_scr_path):
            self.semaphore.acquire()
            subprocess.call("%s -d %s -cp %s/libs/*; %s" % (JAVAC_PATH, target_dir_path, ROBOCODE_PATH, target_scr_path))
            compiled_robots.append(self.particle_position)
            self.semaphore.release()
        else:
            logger.write("Missing source dir for robot #" + self.particle_id)

    def run(self):
        """Run thread.
        """
        self.compileRobot()


class BattleThread(threading.Thread):
    """Battle execution class.
    """

    def __init__(self, semaphore, particle_id, robots_src_path, enemies):
        """Initalize compilation.

        Args:
            semaphore: semaphore used for thread management
            particle_position: particle position
            robots_src_path: path to robot tests
            enemies: list of enemies
        """
        threading.Thread.__init__(self)

        self.MAX_DIFFICULTY = len(enemies)
        self.difficulty = 1
        self.total = 0
        self.fitness = 0

        self.semaphore = semaphore
        self.particle_id = particle_id
        self.robots_src_path = robots_src_path
        self.enemies = enemies

        self.updateBattleConfig()

    def updateBattleConfig(self):
        """Set battle config file for test against enemy.
        """
        self.BATTLE_CONFIG_PATH = ROBOCODE_PATH + "/battles/gswarm/gswarm" + self.particle_id + "_" + str(self.difficulty) + ".battle"

    def copyRobot(self):
        """Copy compiled robot to Robocode environment for test.
        """
        src_path = self.robots_src_path + "/" + SWARM_NAME + "_particle" + self.particle_id + "/gswarm/GSwarmRobot" + self.particle_id + ".class"
        dest_path = ROBOCODE_PATH + "/robots/gswarm"
        shutil.copy(src_path, dest_path)

    def startBattle(self):
        """Start battle for compiled robot with specified battle configuration file.
        """
        fh = open("NUL", "w")
        self.RESULT_PATH = self.robots_src_path + "/" + SWARM_NAME + "_particle" + self.particle_id + "/result" + str(self.difficulty) + ".rsl"
        self.BATTLE_LOG_PATH = self.robots_src_path + "/" + SWARM_NAME + "_particle" + self.particle_id + "/battle.log"
        command = "java -DPARALLEL=true -Xmx512M -Dsun.io.useCanonCaches=false -cp %s/libs/robocode.jar robocode.Robocode -cwd %s -battle %s -nodisplay -results %s" % (ROBOCODE_PATH, ROBOCODE_PATH, self.BATTLE_CONFIG_PATH, self.RESULT_PATH)
        subprocess.call(command, stdout=fh, stderr=fh)
        fh.close()

    def getBattleResult(self):
        """Process battle result and calculate fitness from battle result.
        """
        with open(self.RESULT_PATH) as f:
            lines = f.readlines()

        # regex = re.compile(r"[0-9]*%") # percentual score
        # regex = re.compile(r"\*[\t][0-9]*")  # raw score
        # regex = re.compile(r"gswarm.*\*.*\)\s+\d+\s+\d+\s+\d+\s+\d+\s+\d+\s+\d+\s+(\d+)")  # wins

        robot_regex = re.compile(r"gswarm.*\*\W(\d*)")  # robot score
        enemy_regex = re.compile(r"(\d*) \(")  # enemy score

        robot_score = 0
        enemy_score = 0

        # log battle result to battle.log file
        with open(self.BATTLE_LOG_PATH, "a") as f:
            f.write(lines[2])
            f.write(lines[3])
            f.write("\n")

        for line in lines:
            if re.search(r"GSwarmRobot", line):
                # result = int(re.findall(regex, line)[0][2:])
                result_tuple = re.search(robot_regex, line).groups()
                for result in result_tuple:
                    robot_score += int(result)
            elif re.search(r":", line):
                result_tuple = re.search(enemy_regex, line).groups()
                for result in result_tuple:
                    enemy_score += int(result)

        if enemy_score + robot_score == 0:
            return 0

        return robot_score / (enemy_score + robot_score)

    def updateScore(self):
        """Update score (used primarily for more enemy tests).
        """
        self.fitness = self.getBattleResult()
        self.total += self.fitness

    def isRobotSuccesful(self):
        """Is robot succesfull against last enemy (used for progressive fitness calcualation).
        """
        win_limit = int(self.enemies[self.difficulty - 1][2])
        return self.fitness >= win_limit

    def outputTotalScore(self):
        """Output total score to file for processing fitness in Java environment.
        """
        result_path = self.robots_src_path + "/" + SWARM_NAME + "_particle" + self.particle_id + "/score.log"
        with open(result_path, "w") as f:
            f.write(str(self.total) + "\n")

    def run(self):
        """Run thread.
        """
        try:
            while True:
                self.copyRobot()
                self.startBattle()
                self.updateScore()

                if self.difficulty < self.MAX_DIFFICULTY and self.isRobotSuccesful():
                    self.difficulty += 1
                    self.updateBattleConfig()
                else:
                    self.outputTotalScore()
                    return

        except Exception as e:
            logger.write("battle error in " + self.particle_id)
            logger.write(str(e))

#### SCRIPT START ####

if len(sys.argv) != 5:
    print("Incorrect number of arguments!")
    print("Usage:")
    print("robot_tester.py <1> <2> <3> <4>")
    print("<1> - number of particles")
    print("<2> - iteration num")
    print("<3> - robots directory name")
    print("<4> - swarm name")
    sys.exit()


def loadPaths():
    """Load paths for correct test execution.
    """
    global JAVAC_PATH, ROBOCODE_PATH, ENEMIES_PATH

    with open(os.path.dirname(os.path.abspath(__file__)) + "/../conf/path.conf") as f:
        paths = f.readlines()
        paths = [path.strip().replace("\"", "") for path in paths]

    for path in paths:
        p = path.split("=")
        if p[0] == "JAVAC":
            JAVAC_PATH = p[1]
        elif p[0] == "ROBOCODE":
            ROBOCODE_PATH = p[1]
        elif p[0] == "ENEMY_LIST":
            ENEMIES_PATH = p[1]


def transToParticleId(id):
    """Translate particle identifactor to robot string.
    """
    return str(10000 + i)


def getEnemies():
    """Read enemies from file.
    """
    with open(ENEMIES_PATH) as f:
        enemies = f.readlines()
    return [enemy.strip().split(" ") for enemy in enemies]  # [difficulty, name, win_limit]

loadPaths()

PARTICLES = int(sys.argv[1])
ITER_STR = str(10000 + int(sys.argv[2]))
ROBOTS_SRC_PATH = os.path.dirname(os.path.abspath(__file__)) + "/../" + sys.argv[3] + "iter" + ITER_STR
SWARM_NAME = str(sys.argv[4])

logger = Logger(ROBOTS_SRC_PATH, ITER_STR)
enemies = getEnemies()

# compile robots
try:
    sema = threading.Semaphore(COMPILATION_SEMAS)
    threads = []
    start_time = time()
    for i in range(0, PARTICLES):
        t = CompilationThread(sema, i, transToParticleId(i), ROBOTS_SRC_PATH)
        threads.append(t)

    [t.start() for t in threads]
    [t.join() for t in threads]

except Exception as e:
    logger.write("Compilation Error!")
    logger.write(str(e))

logger.write("Compilation time: " + str(time() - start_time))

# test robots

try:
    sema = threading.Semaphore(TEST_SEMAS)
    threads = []
    start_time = time()
    for i in compiled_robots:
        t = BattleThread(sema, transToParticleId(i), ROBOTS_SRC_PATH, enemies)
        threads.append(t)

    [t.start() for t in threads]
    [t.join() for t in threads]

except Exception as e:
    logger.write("Testing Error!")
    logger.write(str(e))

logger.write("Test time: " + str(time() - start_time))
