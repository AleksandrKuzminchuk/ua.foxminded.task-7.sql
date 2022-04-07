package ua.foxminded.task7.sql.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.foxminded.task7.sql.dao.GroupDao;
import ua.foxminded.task7.sql.model.Group;
import ua.foxminded.task7.sql.service.impl.GroupServiceImpl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GroupServiceTest {
    private static GroupDao groupDaoMock;
    private static GroupService testingGroupService;

    @BeforeAll
    static void beforeAll(){
        groupDaoMock = Mockito.mock(GroupDao.class);
        testingGroupService = new GroupServiceImpl(groupDaoMock);
    }

    @BeforeEach
    void reset(){
        Mockito.reset(groupDaoMock);
    }

    @Test
    void shouldSaveGroup(){
        Group expectedGroup = getGroup();
        when(groupDaoMock.save(getGroup())).thenReturn(getGroupOptional());

        Group result = testingGroupService.save(getGroup());

        assertNotNull(result);
        assertEquals(expectedGroup, result);

        verify(groupDaoMock, atMostOnce()).save(getGroup());
    }

    @Test
    void shouldThrowExceptionNotNullInMethodSave(){
        assertThrows(IllegalArgumentException.class, () -> {testingGroupService.save(null);});
    }

    @Test
    void shouldFindCourseById() throws Exception {
        Group expectedGroup = getGroup();
        when(groupDaoMock.findById(1)).thenReturn(getGroupOptional());

        Group result = testingGroupService.findById(1);

        assertNotNull(result);
        assertEquals(expectedGroup, result);

        verify(groupDaoMock, atMostOnce()).findById(1);
    }

    @Test
    void shouldThrowExceptionNotNullInMethodFindById(){
        assertThrows(IllegalArgumentException.class, () -> {testingGroupService.findById(null);});
    }

    @Test
    void shouldFindAll(){
        List<Group> expectedGroup = getGroups();
        when(groupDaoMock.findAll()).thenReturn(expectedGroup);

        List<Group> result = testingGroupService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(expectedGroup, result);
        assertEquals(2, result.size());

        verify(groupDaoMock, atMostOnce()).findAll();
    }

    @Test
    void expectedEmptyListIfNoGroupsFound(){
        when(groupDaoMock.findAll()).thenReturn(Collections.singletonList(null));

        List<Group> result = testingGroupService.findAll();

        assertNotNull(result);
    }

    @Test
    void shouldReturnCountGroups(){
        long preparedCountGroups = 10;
        when(groupDaoMock.count()).thenReturn(preparedCountGroups);
        long foundCountGroup = testingGroupService.count();

        assertEquals(10, foundCountGroup);

        verify(groupDaoMock, atMostOnce()).count();
    }

    @Test
    void shouldDeleteGroupById(){
        doNothing().when(groupDaoMock).deleteById(1);

        testingGroupService.deleteById(1);

        verify(groupDaoMock, atMostOnce()).deleteById(1);
    }

    @Test
    void shouldThrowExceptionNotNullInMethodDeleteById(){
        assertThrows(IllegalArgumentException.class, () -> {testingGroupService.deleteById(null);});
    }

    @Test
    void shouldDeleteGroup(){
        doNothing().when(groupDaoMock).delete(getGroup());

        testingGroupService.delete(getGroup());

        verify(groupDaoMock, atMostOnce()).delete(getGroup());
    }

    @Test
    void shouldThrowExceptionNotNullInMethodDelete(){
        assertThrows(IllegalArgumentException.class, () -> {testingGroupService.delete(null);});
    }

    @Test
    void shouldDeleteAll(){
        doNothing().when(groupDaoMock).deleteAll();

        testingGroupService.deleteAll();

        verify(groupDaoMock, atMostOnce()).deleteAll();
    }

    @Test
    void shouldUpdateGroup(){
        doNothing().when(groupDaoMock).updateGroup(getGroup());

        testingGroupService.updateGroup(getGroup());

        verify(groupDaoMock, atMostOnce()).updateGroup(getGroup());
    }

    @Test
    void shouldThrowExceptionNotNullInMethodUpdateGroup(){
        assertThrows(IllegalArgumentException.class, () -> {testingGroupService.updateGroup(null);});
    }

    @Test
    void shouldSaveAll(){
        doNothing().when(groupDaoMock).saveAll(getGroups());

        testingGroupService.saveAll(getGroups());

        verify(groupDaoMock, atMostOnce()).saveAll(getGroups());
    }

    @Test
    void shouldFindByStudentsCountsLessEqual(){
        List<Group> expectedGroups = getGroups();
        when(groupDaoMock.findByStudentsCountsLessEqual(10)).thenReturn(expectedGroups);

        List<Group> result = testingGroupService.findByStudentsCountsLessEqual(10);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(expectedGroups, result);
        assertEquals(2, result.size());

        verify(groupDaoMock, atMostOnce()).findByStudentsCountsLessEqual(10);
    }

    @Test
    void shouldThrowExceptionNoyNullInMethodFindByStudentsCountsLessEqual(){
        assertThrows(IllegalArgumentException.class, () -> {testingGroupService.findByStudentsCountsLessEqual(null);});
    }

    private Group getGroup(){
        return new Group(1, "Kr-Om");
    }

    private Optional<Group> getGroupOptional(){
        return Optional.of(new Group(1, "Kr-Om"));
    }

    private List<Group> getGroups(){
        Group group1 = new Group(1, "kO-Lf");
        Group group2 = new Group(2, "Fd-rW");
        List<Group> groups = new LinkedList<>();
        groups.add(group1);
        groups.add(group2);
        return groups;
    }


}