package com.baza.mamanevdomabackend.service;

import com.baza.mamanevdomabackend.entity.ChildrenGroup;
import com.baza.mamanevdomabackend.entity.Parent;
import com.baza.mamanevdomabackend.exception.GroupNotFoundException;
import com.baza.mamanevdomabackend.repository.GroupRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GroupServiceTest {
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private ChildService childService;
    @InjectMocks
    private GroupService groupService;


    @Before
    public void initMocks() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    private ChildrenGroup getTestGroup() {
        return new ChildrenGroup(1L,
                "description",
                new Parent(),
                new HashSet<>());
    }

    @Test
     void testFindGroupById() {
        when(groupRepository.findById(getTestGroup().getId()))
                .thenReturn(Optional.of(getTestGroup()));
        assertEquals(getTestGroup(), groupService.findGroupById(getTestGroup().getId()));
    }

    @Test
     void testFindGroupByIdNotFound() {
        when(groupRepository.findById(getTestGroup().getId()))
                .thenReturn(Optional.empty());

        assertThrows(GroupNotFoundException.class,
                () -> groupService.findGroupById(getTestGroup().getId()));
    }
}
