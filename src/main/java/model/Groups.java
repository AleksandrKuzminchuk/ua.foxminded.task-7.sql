package main.java.model;

import java.util.Objects;

public class Groups {

    private Integer groupId;
    private String groupName;

    public Groups() {
    }

    public Groups(Integer groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Groups groups = (Groups) o;
        return Objects.equals(groupId, groups.groupId) && Objects.equals(groupName, groups.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, groupName);
    }

    @Override
    public String toString() {
        return "Groups{" +
                "group_id=" + groupId +
                ", group_name='" + groupName + '\'' +
                '}';
    }
}
