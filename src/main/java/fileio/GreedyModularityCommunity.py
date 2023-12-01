import pandas as pd
import numpy as np
import networkx as nx

matrix_file_path="/Users/nocturne/Downloads/Project/Java/NovelAnalyze/src/main/resources/matrix.csv"
matrix_close = np.genfromtxt(matrix_file_path, delimiter=',')
num_characters = len(matrix_close)

G = nx.Graph()

for i in range(num_characters):
    G.add_node(i)

for i in range(num_characters):
    for j in range(i + 1, num_characters):
        cooccurrence_count = matrix_close[i][j]

        if cooccurrence_count >= 20:
            G.add_edge(i, j)

communities = nx.algorithms.community.modularity_max.greedy_modularity_communities(G)

for team in communities:
    print("Team:", team)
