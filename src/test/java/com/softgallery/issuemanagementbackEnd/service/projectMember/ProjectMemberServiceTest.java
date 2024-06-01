package com.softgallery.issuemanagementbackEnd.service.projectMember;

import com.softgallery.issuemanagementbackEnd.dto.project.ProjectDTO;
import com.softgallery.issuemanagementbackEnd.dto.project_member.ProjectMemberDTO;
import com.softgallery.issuemanagementbackEnd.dto.user.UserDTO;
import com.softgallery.issuemanagementbackEnd.entity.project.ProjectEntity;
import com.softgallery.issuemanagementbackEnd.entity.project_member.ProjectMemberEntity;
import com.softgallery.issuemanagementbackEnd.exception.ProjectMemberNotFoundException;
import com.softgallery.issuemanagementbackEnd.repository.project.ProjectRepository;
import com.softgallery.issuemanagementbackEnd.repository.project_member.ProjectMemberRepository;
import com.softgallery.issuemanagementbackEnd.service.project.ProjectState;
import com.softgallery.issuemanagementbackEnd.service.user.Role;
import com.softgallery.issuemanagementbackEnd.service.user.UserServiceIF;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProjectMemberServiceTest {

    @Mock
    private ProjectMemberRepository projectMemberRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserServiceIF userService;

    @InjectMocks
    private ProjectMemberService projectMemberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("프로젝트 멤버 추가 테스트")
    void testAddProjectMember() {
        // given
        List<ProjectMemberDTO> projectMemberDTOs = new ArrayList<>();
        projectMemberDTOs.add(new ProjectMemberDTO(1L, "user123", Role.ROLE_DEVELOPER));

        when(projectMemberRepository.existsByProjectIdAndUserId(anyLong(), anyString())).thenReturn(false);

        // when
        boolean result = projectMemberService.addProjectMember(projectMemberDTOs);

        // then
        assertTrue(result);
        verify(projectMemberRepository, times(1)).save(any(ProjectMemberEntity.class));
    }

    @Test
    @DisplayName("프로젝트의 모든 멤버 가져오기 테스트")
    void testGetMembersInProject() {
        // given
        Long projectId = 1L;
        ProjectMemberEntity projectMemberEntity = new ProjectMemberEntity();
        projectMemberEntity.setProjectId(projectId);
        projectMemberEntity.setUserId("user123");
        projectMemberEntity.setRole(Role.ROLE_DEVELOPER);

        List<ProjectMemberEntity> projectMemberEntities = new ArrayList<>();
        projectMemberEntities.add(projectMemberEntity);

        UserDTO userDTO = new UserDTO("user123", "User Name", "user@example.com", "password", Role.ROLE_DEVELOPER);

        when(projectMemberRepository.findAllByProjectId(projectId)).thenReturn(projectMemberEntities);
        when(userService.getUserById("user123")).thenReturn(userDTO);

        // when
        List<UserDTO> result = projectMemberService.getMembersInProject(projectId);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("user123", result.get(0).getId());
    }

    @Test
    @DisplayName("프로젝트의 특정 역할 멤버 가져오기 테스트")
    void testGetSpecificUsersOfRoleInProject() {
        // given
        Long projectId = 1L;
        Role role = Role.ROLE_DEVELOPER;
        ProjectMemberEntity projectMemberEntity = new ProjectMemberEntity();
        projectMemberEntity.setProjectId(projectId);
        projectMemberEntity.setUserId("user123");
        projectMemberEntity.setRole(role);

        List<ProjectMemberEntity> projectMemberEntities = new ArrayList<>();
        projectMemberEntities.add(projectMemberEntity);

        UserDTO userDTO = new UserDTO("user123", "User Name", "user@example.com", "password", Role.ROLE_DEVELOPER);

        when(projectMemberRepository.findAllByProjectIdAndRole(projectId, role)).thenReturn(projectMemberEntities);
        when(userService.getUserById("user123")).thenReturn(userDTO);

        // when
        List<UserDTO> result = projectMemberService.getSpecificUsersOfRoleInProject(projectId, role);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("user123", result.get(0).getId());
    }

    @Test
    @DisplayName("사용자가 참여한 모든 프로젝트 가져오기 테스트")
    void testGetProjectsOfUser() {
        // given
        String userId = "user123";
        ProjectMemberEntity projectMemberEntity = new ProjectMemberEntity();
        projectMemberEntity.setProjectId(1L);
        projectMemberEntity.setUserId(userId);
        projectMemberEntity.setRole(Role.ROLE_DEVELOPER);

        List<ProjectMemberEntity> projectMemberEntities = new ArrayList<>();
        projectMemberEntities.add(projectMemberEntity);

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setProjectId(1L);
        projectEntity.setName("Test Project");
        projectEntity.setDescription("Test Description");
        projectEntity.setProjectState(ProjectState.InProgress);
        projectEntity.setAdminId("admin123");

        when(projectMemberRepository.findAllByUserId(userId)).thenReturn(projectMemberEntities);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(projectEntity));

        // when
        List<ProjectDTO> result = projectMemberService.getProjectsOfUser(userId);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Test Project", result.get(0).getName());
    }

    @Test
    @DisplayName("프로젝트 멤버 삭제 테스트")
    void testDeleteProjectMember() {
        // given
        Long projectId = 1L;
        String userId = "user123";

        // when
        Boolean result = projectMemberService.deleteProjectMember(projectId, userId);

        // then
        assertTrue(result);
        verify(projectMemberRepository, times(1)).deleteByProjectIdAndUserId(projectId, userId);
    }
}
