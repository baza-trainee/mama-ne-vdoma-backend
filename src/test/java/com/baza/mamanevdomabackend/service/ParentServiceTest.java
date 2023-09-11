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
    private static final String TEST_LOGIN = "testLogin123";

    @Before
    public void initMocks() {
        MockitoAnnotations.openMocks(this);

        expectedParent.setEmail(TEST_EMAIL);
        expectedParent.setLogin(TEST_LOGIN);
    }

    @Test
    public void testFindParentByEmail() {
        when(parentRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(expectedParent));
        Parent actualParent = parentService.findByEmail(TEST_EMAIL);

        assertEquals(actualParent, expectedParent);
    }

    @Test
    public void testFindParentByEmailNotFound() {
        when(parentRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(ParentNotFoundException.class, () -> parentService.findByEmail(""));
    }


    @Test
    public void testFindParentByLogin() {
        when(parentRepository.findByLogin(TEST_LOGIN)).thenReturn(Optional.of(expectedParent));
        Parent actualParent = parentService.findByLogin(TEST_LOGIN);

        assertEquals(actualParent, expectedParent);
    }

    @Test
    public void testFindParentByLoginNotFound() {
        when(parentRepository.findByLogin(anyString())).thenReturn(Optional.empty());

        assertThrows(ParentNotFoundException.class, () -> parentService.findByLogin(""));
    }

}
