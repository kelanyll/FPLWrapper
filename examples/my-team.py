import requests
import matplotlib.pyplot as plt

def getPlayer(name):
    r = requests.get("http://www.kelanyll.com/fpl-wrapper/player?name={}".format(name))
    return r.json()

# PPG = points per game per value
def calculatePPGPV(player):
    currentPrice = player["history"][-1]["value"] / 10
    points = 0
    games = 0
    for gameweek in player["history"]:
        points += gameweek["total_points"]
        if gameweek["minutes"] > 0:
            games += 1

    if games > 0:
        return points / games / currentPrice

    return 0

class Player:
    def __init__(self, player):
        self.player = player
        self.ppgpv = calculatePPGPV(player)

    def getPpgpv(self):
        return self.ppgpv

r = requests.post("http://www.kelanyll.com/fpl-wrapper/my-team", data ={'email': 'yll.kelani@hotmail.co.uk',
                                                                        'password': 'insert-password'});
team = r.json()

players = list(map(lambda x: Player(getPlayer(x["name"])), team))
players.append(Player(getPlayer("Reece James")))
players.sort(key=lambda x: x.ppgpv)

fig, ax = plt.subplots(figsize=(6,6))
ax.set_ylabel("Points per game per value")
playersIndices = range(0, len(players))
bars = plt.bar(playersIndices, list(map(lambda x: x.ppgpv, players)))
jamesIndex = next(i for i,x in enumerate(players) if x.player['name'] == "Reece James")
bars[jamesIndex].set_color('green')
plt.xticks(playersIndices, list(map(lambda x: x.player['name'], players)), rotation="vertical")
plt.gcf().subplots_adjust(bottom=0.32)
plt.show()
