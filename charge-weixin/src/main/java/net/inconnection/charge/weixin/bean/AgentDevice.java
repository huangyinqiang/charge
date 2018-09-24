package net.inconnection.charge.weixin.bean;

import java.util.Date;

public class AgentDevice extends SuperReq {
    private static final long serialVersionUID = -4515553839143211540L;
    private Long id;
    private String name;
    private String status;
    private Integer sockeSum;
    private Integer usedSockeSum;
    private Integer noUsedSockeSum;
    private Date updateTime;

    public AgentDevice() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSockeSum() {
        return sockeSum;
    }

    public void setSockeSum(Integer sockeSum) {
        this.sockeSum = sockeSum;
    }

    public Integer getUsedSockeSum() {
        return usedSockeSum;
    }

    public void setUsedSockeSum(Integer usedSockeSum) {
        this.usedSockeSum = usedSockeSum;
    }

    public Integer getNoUsedSockeSum() {
        return noUsedSockeSum;
    }

    public void setNoUsedSockeSum(Integer noUsedSockeSum) {
        this.noUsedSockeSum = noUsedSockeSum;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

