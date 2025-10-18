package com.zqw.agent_app.mapper;

import com.zqw.agent_app.model.po.AgentPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AgentMapper {
        /**
         * 获取所有智能体数量
         * @return 智能体数量
         */
        int getAllAgentNum();

        int getLastWeekAgentNum();

        List<AgentPO> getHotModel();
}
