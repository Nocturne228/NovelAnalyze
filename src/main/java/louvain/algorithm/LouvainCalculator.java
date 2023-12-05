package louvain.algorithm;

import louvain.entity.Graph;
import louvain.entity.Link;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;



public class LouvainCalculator {

    private Graph graph;

    private double totalW;

    private int[] nodeCommunityNo;

    private double[] nodeBothWeight;

    private double stickingK = 0.65;

    public LouvainCalculator(Graph graph) {
        // 初始化操作
        totalW = graph.totalWeight();
        nodeCommunityNo = new int[graph.nodesNum()];
        for (int i = 0; i < nodeCommunityNo.length; i++) {
            nodeCommunityNo[i] = i;
        }
        nodeBothWeight = new double[graph.nodesNum()];
        for (int i = 0; i < nodeBothWeight.length; i++) {
            nodeBothWeight[i] = graph.getBothWeight(i);
        }
        this.graph = graph;
    }

    public void setStickingK(double stickingK) {
        this.stickingK = stickingK;
    }

    private int c(int id1, int id2) {
        return nodeCommunityNo[id1] == nodeCommunityNo[id2] ? 1 : 0;
    }

    private double getModuleQ() {
        double q = 0.0;
        Set<Integer> nodeIds = graph.getNodes();
        for (int id1 : nodeIds) {
            for (int id2 : nodeIds) {
                if (id1 == id2) {
                    continue;
                }
                double A = 0.0;
                for (Link link : graph.getLinksBetweenTwoNodes(id1, id2)) {
                    A += link.weight;
                }
                q += c(id1, id2) * (A - nodeBothWeight[id1] * nodeBothWeight[id2] / totalW * stickingK);
            }
        }
        return q / totalW;
    }

    public CommunityInfo findCommunitiesSingleLevel() {
        while (true) {
            int[] copy = nodeCommunityNo.clone();
            double Q = getModuleQ();
            if (Double.isNaN(Q)) {
                break;
            }
            for (int id1 : graph.getNodes()) {
                int bestCommunityNo = -1;
                double deltaQ = 0.0;
                int id1OriginNo = nodeCommunityNo[id1];
                for (int id2 : graph.getBothNodes(id1)) {
                    if (c(id1, id2) == 1) {
                        continue;
                    }
                    nodeCommunityNo[id1] = nodeCommunityNo[id2];
                    double currentQ = getModuleQ();
                    if (Double.isNaN(currentQ)) {
                        continue;
                    }
                    if (currentQ - Q > 0.00001 && currentQ - Q > deltaQ) {
                        deltaQ = currentQ - Q;
                        bestCommunityNo = nodeCommunityNo[id2];
                    }
                }
                if (bestCommunityNo == -1) {
                    nodeCommunityNo[id1] = id1OriginNo;
                } else {
                    nodeCommunityNo[id1] = bestCommunityNo;
                    Q = getModuleQ();
                }
            }
            if (Arrays.equals(nodeCommunityNo, copy)) {
                break;
            }
        }
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < nodeCommunityNo.length; i++) {
            int commNum = nodeCommunityNo[i];
            List<Integer> integers = map.get(commNum);
            if (integers == null) {
                integers = new ArrayList<>();
                map.put(commNum, integers);
            }
            integers.add(i);
        }
        CommunityInfo communityInfo = new CommunityInfo();
        communityInfo.communitiesNo = map.size();
        communityInfo.communityNodeIds = new int[map.size()][];
        AtomicInteger nextCommNum = new AtomicInteger(0);
        map.forEach((commNum, ids) -> {
            communityInfo.communityNodeIds[nextCommNum.get()] = new int[ids.size()];
            for (int i = 0; i < ids.size(); i++) {
                communityInfo.communityNodeIds[nextCommNum.get()][i] = ids.get(i);
            }
            nextCommNum.incrementAndGet();
        });
        communityInfo.nodeCommunityNo = new int[nodeCommunityNo.length];
        for (int c = 0; c < communityInfo.communityNodeIds.length; c++) {
            for (int nodeId : communityInfo.communityNodeIds[c]) {
                communityInfo.nodeCommunityNo[nodeId] = c;
            }
        }
        return communityInfo;
    }

}
