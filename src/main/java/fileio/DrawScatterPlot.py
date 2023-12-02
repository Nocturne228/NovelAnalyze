import pandas as pd
import matplotlib.pyplot as plt

plt.rcParams['font.sans-serif'] = ['Arial Unicode MS']
plt.rcParams['axes.unicode_minus'] = False

csv_file_path = '/Users/nocturne/Downloads/Project/Java/NovelAnalyze/src/main/resources/data.csv'
df = pd.read_csv(csv_file_path)

csv_label_file_path = '/Users/nocturne/Downloads/Project/Java/NovelAnalyze/src/main/resources/label.csv'
df_label = pd.read_csv(csv_label_file_path)

# 将 label 映射到对应的人名
label_to_name = dict(zip(df_label['label'], df_label['name']))

colors = ['blue', 'red', 'green', 'purple', 'orange', 'pink', 'brown', 'gray', 'cyan', 'magenta']

fig, ax = plt.subplots(figsize=(10, 6))
ax.set_facecolor((244/255, 244/255, 244/255))  # 设置图表的背景颜色
fig.patch.set_facecolor((244/255, 244/255, 244/255))

for label in range(10):
    subset = df[df['label'] == label]
    ax.scatter(subset['data'], subset['label'], label=label_to_name[label], color=colors[label], alpha=0.7)

ax.set_title('Scatter Plot Example')
ax.set_xlabel('Data')
ax.set_ylabel('Label')

ax.legend(loc='upper right', bbox_to_anchor=(1.15, 1), prop={'size': 7})

width_in_pixels = 500
height_in_pixels = 400
dpi = 100

fig.set_size_inches(width_in_pixels / dpi, height_in_pixels / dpi)

plt.savefig('/Users/nocturne/Downloads/Project/Java/NovelAnalyze/src/main/resources/scatter_plot.png', dpi=dpi, bbox_inches='tight')
