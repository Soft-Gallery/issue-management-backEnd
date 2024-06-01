package com.softgallery.issuemanagementbackEnd.service.project;

import com.softgallery.issuemanagementbackEnd.authentication.JWTUtil;
import com.softgallery.issuemanagementbackEnd.dto.project.ProjectDTO;
import com.softgallery.issuemanagementbackEnd.dto.user.UserDTO;
import com.softgallery.issuemanagementbackEnd.entity.project.ProjectEntity;
import com.softgallery.issuemanagementbackEnd.repository.project.ProjectRepository;
import com.softgallery.issuemanagementbackEnd.service.projectMember.ProjectMemberService;
import com.softgallery.issuemanagementbackEnd.service.user.UserService;
import com.softgallery.issuemanagementbackEnd.service.user.Role;

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

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMemberService projectMemberService;

    @Mock
    private UserService userService;

    @Mock
    private JWTUtil jwtUtil;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("프로젝트 생성 테스트")
    void testCreateProject() {
        // given
        ProjectDTO projectDTO = new ProjectDTO(null, "Test Project", "Test Description", ProjectState.InProgress, "admin123");
        String token = "Bearer token";
        String userId = "user123";
        UserDTO userDTO = new UserDTO(userId, "User Name", "user@example.com", "password", Role.ROLE_ADMIN);

        when(jwtUtil.getUserId(any())).thenReturn(userId);
        when(userService.getUser(anyString())).thenReturn(userDTO);
        when(projectRepository.save(any(ProjectEntity.class))).thenReturn(new ProjectEntity());

        // when
        boolean result = projectService.createProject(projectDTO, token);

        // then
        assertTrue(result);
        verify(projectRepository, times(1)).save(any(ProjectEntity.class));
        verify(userService, times(1)).getUser(anyString());
        verify(projectMemberService, times(1)).addProjectMember(anyList());
    }

    @Test
    @DisplayName("특정 프로젝트 가져오기 테스트")
    void testGetProject() {
        // given
        Long projectId = 1L;
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setProjectId(projectId);
        projectEntity.setName("Test Project");
        projectEntity.setDescription("Test Description");
        projectEntity.setProjectState(ProjectState.InProgress);
        projectEntity.setAdminId("admin123");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectEntity));

        // when
        ProjectDTO result = projectService.getProject(projectId);

        // then
        assertNotNull(result);
        assertEquals("Test Project", result.getName());
        verify(projectRepository, times(1)).findById(projectId);
    }

    @Test
    @DisplayName("프로젝트 업데이트 테스트")
    void testUpdateProject() {
        // given
        Long projectId = 1L;
        ProjectDTO projectDTO = new ProjectDTO(projectId, "Updated Project", "Updated Description", ProjectState.Closed, "admin123");
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setProjectId(projectId);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectEntity));

        // when
        boolean result = projectService.updateProject(projectDTO);

        // then
        assertTrue(result);
        assertEquals("Updated Project", projectEntity.getName());
        assertEquals("Updated Description", projectEntity.getDescription());
        assertEquals(ProjectState.Closed, projectEntity.getProjectState());
        verify(projectRepository, times(1)).save(projectEntity);
    }

    @Test
    @DisplayName("프로젝트 삭제 테스트")
    void testDeleteProject() {
        // given
        Long projectId = 1L;

        // when
        boolean result = projectService.deleteProject(projectId);

        // then
        assertTrue(result);
        verify(projectRepository, times(1)).deleteById(projectId);
    }

    @Test
    @DisplayName("프로젝트 상태 변경 테스트")
    void testChangeDiffState() {
        // given
        Long projectId = 1L;
        ProjectState newState = ProjectState.Closed;
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setProjectId(projectId);
        projectEntity.setProjectState(ProjectState.InProgress);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectEntity));

        // when
        boolean result = projectService.changeDiffState(projectId, newState);

        // then
        assertTrue(result);
        assertEquals(newState, projectEntity.getProjectState());
        verify(projectRepository, times(1)).save(projectEntity);
    }

    @Test
    @DisplayName("프로젝트에 사용자 할당 테스트")
    void testAssignUserToProject() {
        // given
        Long projectId = 1L;
        UserDTO userDTO = new UserDTO("user123", "User Name", "user@example.com", "password", Role.ROLE_ADMIN);

        // when
        projectService.assignUserToProject(projectId, userDTO);

        // then
        verify(projectMemberService, times(1)).addProjectMember(anyList());
    }
}
