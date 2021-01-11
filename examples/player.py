import requests
import matplotlib.pyplot as plt

import radar_chart

def getAverage(key, list):
    sum = 0
    for element in list:
        sum += float(element[key])
    return sum / len(list)

def getAverageStats(stats, list):
    averageStats = []
    for stat in stats:
        averageStats.append(getAverage(stat, list))

    return averageStats

r1 = requests.get("http://www.kelanyll.com/fpl-wrapper/player?name=Reece%20James")
r2 = requests.get("http://www.kelanyll.com/fpl-wrapper/player?name=Trent%20Alexander-Arnold")
jamesStats = r1.json()
trentStats = r2.json()

stats = ['Influence', 'Creativity', 'Threat']
lowerStats = list(map(lambda x: x.lower(), stats))
jamesAverageStats = getAverageStats(lowerStats, jamesStats['history'])
trentAverageStats = getAverageStats(lowerStats, trentStats['history'])

theta = radar_chart.radar_factory(3)
fig, ax = plt.subplots(figsize=(5,6), subplot_kw=dict(projection='radar'))
ax.set_varlabels(stats, position=[0, 0.1])
ax.set_rlabel_position(60)
ax.set_title("Reece James vs Trent Alexander-Arnold ICT", weight='bold', size='large', position=(0.5, 1.15),
             horizontalalignment='center')
ax.plot(theta, jamesAverageStats, label="Reece James", color="blue")
ax.fill(theta, jamesAverageStats, facecolor="blue", alpha=0.25)
ax.plot(theta, trentAverageStats, label="Trent Alexander-Arnold", color="red")
ax.fill(theta, trentAverageStats, facecolor="red", alpha=0.25)
ax.legend(loc=(0.7, 1), fontsize="x-small")
plt.show()