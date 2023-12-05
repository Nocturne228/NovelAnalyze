package louvain.algorithm;


import javafx.css.Match;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommunityInfo {

    /**
     * 节点编号
     */
    public int communitiesNo;

    /**
     * i代表节点编号，value代表社区编号
     */
    public int[] nodeCommunityNo;

    /**
     * i代表社区编号，j代表节点编号
     */
    public int[][] communityNodeIds;

    @Override
    public String toString() {
        return "CommunityInfo{" +
                "communitiesNo=" + communitiesNo +
                ", nodeCommunityNo=" + Arrays.toString(nodeCommunityNo) +
                ", communityNodeIds=" + Arrays.deepToString(communityNodeIds) +
                '}';
    }

    public int[][] getCommunityNodeIds()
    {
        return communityNodeIds;
    }

    public int getCommunitiesNo()
    {
        return communitiesNo;
    }

    public int[] getNodeCommunityNo()
    {
        return nodeCommunityNo;
    }
}

