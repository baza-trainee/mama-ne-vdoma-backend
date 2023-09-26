package com.baza.mamanevdomabackend.controller;

import com.baza.mamanevdomabackend.entity.ChildrenGroup;
import com.baza.mamanevdomabackend.payload.response.MessageResponse;
import com.baza.mamanevdomabackend.service.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/join/{group-id}/{child-id}")
    public ResponseEntity<ChildrenGroup> joinChildToGroup(
            @PathVariable(name = "group-id") Long groupId,
            @PathVariable(name = "child-id") Long childId) {
        return ResponseEntity.ok(groupService.joinChildToGroup(groupId, childId));
    }
}
