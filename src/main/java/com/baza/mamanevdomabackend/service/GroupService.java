package com.baza.mamanevdomabackend.service;

import com.baza.mamanevdomabackend.entity.Child;
import com.baza.mamanevdomabackend.entity.ChildrenGroup;
import com.baza.mamanevdomabackend.exception.GroupNotFoundException;
import com.baza.mamanevdomabackend.repository.GroupRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    private final GroupRepository grouprepository;
    private final ChildService childService;

    public GroupService(GroupRepository grouprepository, ChildService childService) {
        this.grouprepository = grouprepository;
        this.childService = childService;
    }

    public ChildrenGroup joinChildToGroup(Long groupId, Long childId) {
        ChildrenGroup group = findGroupById(groupId);
        Child child = childService.findChildById(childId);
        group.getChildren().add(child);
        return updateGroup(group);
    }

    public ChildrenGroup findGroupById(Long id) {
        return grouprepository.findById(id)
                .orElseThrow(() -> new GroupNotFoundException("Group not exists"));
    }

    public ChildrenGroup updateGroup(ChildrenGroup childrenGroup) {
        ChildrenGroup group = findGroupById(childrenGroup.getId());
        group.setChildren(childrenGroup.getChildren());
        group.setDescription(childrenGroup.getDescription());

        return grouprepository.save(group);
    }
}
