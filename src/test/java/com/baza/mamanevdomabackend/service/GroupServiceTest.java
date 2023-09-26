package com.baza.mamanevdomabackend.service;

import com.baza.mamanevdomabackend.entity.Child;
import com.baza.mamanevdomabackend.entity.ChildrenGroup;
import com.baza.mamanevdomabackend.entity.Parent;
import com.baza.mamanevdomabackend.exception.GroupNotFoundException;
import com.baza.mamanevdomabackend.repository.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class GroupServiceTest {
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private ChildService childService;
    @InjectMocks
    private GroupService groupService;


    @BeforeEach
    public void initMocks() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    private ChildrenGroup getTestGroup() {
        return new ChildrenGroup(1L,
                "description",
                new Parent(),
                new ArrayList<>());
    }

    @Test
    public void testFindGroupById() {
        when(groupRepository.findById(getTestGroup().getId()))
                .thenReturn(Optional.of(getTestGroup()));
        assertEquals(getTestGroup(), groupService.findGroupById(getTestGroup().getId()));
    }

    @Test
    public void testFindGroupByIdNotFound() {
        when(groupRepository.findById(getTestGroup().getId()))
                .thenReturn(Optional.empty());

        assertThrows(GroupNotFoundException.class,
                () -> groupService.findGroupById(getTestGroup().getId()));
    }

    @Test
    public void testUpdateGroup() {
        when(groupRepository.findById(getTestGroup().getId()))
                .thenReturn(Optional.of(getTestGroup()));

        getTestGroup().setDescription("update description");

        when(groupRepository.save(getTestGroup()))
                .thenReturn(getTestGroup());

        assertEquals(getTestGroup(), groupService.updateGroup(getTestGroup()));
    }

    @Test
    public void testJoinChildToGroup(){
        when(groupRepository.findById(getTestGroup().getId()))
                .thenReturn(Optional.of(getTestGroup()));

        Child child = new Child();
        when(childService.findChildById(1L)).thenReturn(child);

        getTestGroup().getChildren().add(child);
        when(groupService.updateGroup(getTestGroup())).thenReturn(getTestGroup());

        assertEquals(getTestGroup(),groupService.joinChildToGroup(
                getTestGroup().getId(),1L));
    }
}
