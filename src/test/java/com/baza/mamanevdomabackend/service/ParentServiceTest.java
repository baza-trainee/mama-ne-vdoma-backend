package com.baza.mamanevdomabackend.service;

import com.baza.mamanevdomabackend.entity.Parent;
import com.baza.mamanevdomabackend.exception.ParentNotFoundException;
import com.baza.mamanevdomabackend.repository.ParentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ParentServiceTest {

    @InjectMocks
    private ParentService parentService;

    @Mock
    private ParentRepository parentRepository;

    private final Parent expectedParent = new Parent();

    private static final String TEST_EMAIL = "test@gmail.com";
    private static final String TEST_USERNAME = "testUsername123";

    @Before
    public void initMocks() throws Exception {
        MockitoAnnotations.openMocks(this).close();

        expectedParent.setEmail(TEST_EMAIL);
    }

    @Test
    public void testFindParentByEmail() {
        when(parentRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(expectedParent));
        Parent actualParent = parentService.findParentByEmail(TEST_EMAIL);

        assertEquals(actualParent, expectedParent);
    }

    @Test
    public void testFindParentByEmailNotFound() {
        when(parentRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(ParentNotFoundException.class, () -> parentService.findParentByEmail(""));
    }

    @Test
    public void testFindParentByUsername() {
        when(parentRepository.findByNickname(TEST_USERNAME)).thenReturn(Optional.of(expectedParent));
        Parent actualParent = parentService.findParentByNickname(TEST_USERNAME);

        assertEquals(actualParent, expectedParent);
    }

    @Test
    public void testFindParentByUsernameNotFound() {
        when(parentRepository.findByNickname(anyString())).thenReturn(Optional.empty());

        assertThrows(ParentNotFoundException.class, () -> parentService.findParentByNickname(""));
    }

}
