package com.zqw.agent_app.mapper;

import com.zqw.agent_app.model.po.AgentPO;
import com.zqw.agent_app.model.vo.AgentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AgentMapper {
    /**
     * 获取所有代理数量
     * @return 所有代理数量
     */
    int getAllAgentNum();

    /**
     * 获取上周代理数量
     * @return 上周代理数量
     */
    int getLastWeekAgentNum();

    /**
     * 获取热门模型
     * @return 热门模型列表
     */
    List<AgentPO> getHotModel();

    /**
     * 根据代理id获取代理信息
     * @param agentId 代理id
     * @return 代理信息
     */
    AgentPO getAgentById(Integer agentId);

    List<AgentPO> fetchModel();

    int insertAgent(AgentPO agentPO);

    List<AgentPO> selectByKeyword(String keyword);

    Boolean updateUsageCountById(AgentPO agentPO);
}
